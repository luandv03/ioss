package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.OrderDao;
import com.application.entity.*;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderSubsystem implements OrderDao {

    static Connection con
            = JDBCPostgreSQLConnection.getConnection();
    @Override
    public List<OrdersStock> getListOrderByStatus(String status) throws SQLException {
        String query = "select o.orderId, o.status, o.created_at, sum(ots.quantityOrdered) sumItem from \"order\" o\n" +
                "join orderItemSite ots using(orderId)\n" +
                "where o.status in (?, 'done')" +
                "group by (orderId)"+
                "order by o.status";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();

        List<OrdersStock> listOrder  = new ArrayList<>();

        while (rs.next()) {
            String orderId = rs.getString("orderId");
            String orderStatus = rs.getString("status");
            String orderDate = rs.getString("created_at");
            int sumItem = rs.getInt("sumItem");

            OrdersStock o = new OrdersStock(orderId, null, orderStatus, orderDate, sumItem);
            listOrder.add(o);
        }

        return listOrder;
    }

    public void saveSelectedSite(String orderListItemId, String itemId, OrderLine orderLine, String orderParentId) throws SQLException {
//        -- Tim trong dsmhcd 001;đã có đơn hàng nào chưa được gửi và chứa sp của site 002 không?
//                -- Nếu có thì: 1. xem sp tương tự của site 002 đã có trong đơn đó chưa?
//        -- 1.1 Có rồi: Update số lượng
//                -- 1.2 Nếu chưa: Insert itemSite vào
//                -- Nếu không có đơn: 1. Tạo đơn, 2 orderItemSite dựa trên orderid đó
        String q1  = "select orderId, its.itemId, its.itemSiteId from \"order\" o\n" +
                "join orderItemSite ots using(orderId)\n" +
                "join itemSite its on ots.itemSiteId = its.itemSiteId\n" +
                "where o.orderlistItemId = ? and o.status = 'inActive' and siteId = ?";

        PreparedStatement ps
                = con.prepareStatement(q1);
        ps.setString(1, orderListItemId);
        ps.setString(2, orderLine.getSiteId());
        ResultSet rs = ps.executeQuery();

        String orderId = "";
        String findedItemId = "";
        String itemSiteId = "";
        boolean itemIdExistedInOrderSiteItem = false;

        while (rs.next()) {
            orderId = rs.getString("orderId");
            findedItemId = rs.getString("itemId");

            if (findedItemId.equals(itemId)) {
                itemIdExistedInOrderSiteItem = true;
                itemSiteId = rs.getString("itemSiteId");
                break;
            }

        }

        if (!orderId.isEmpty()) {
            //        -- if (rs.itemId): có tồn tại item MH001 của site S0002 này trong đơn rồi:
            //        -- Update số lượng
            if (itemIdExistedInOrderSiteItem) {
                String q2  = "Update orderItemSite \n" +
                        "Set quantityOrdered = quantityOrdered +  ? \n" +
                        "where itemSiteId = ?";
                PreparedStatement ps2
                        = con.prepareStatement(q2);
                ps2.setInt(1, orderLine.getQuantityOrdered());
                ps2.setString(2, itemSiteId);
                ps2.executeUpdate();
            } else {
//                -- if rs.lenght > 0: tồn tại đơn hàng trên
//                        -- if (rs.itemId): có tồn tại item MH001 của site S0002 này trong đơn rồi:
//                -- Update số lượng
                String newItemSiteId = getItemSiteIdByItemIdAndSiteId(orderLine.getSiteId(), itemId);
                String q2  = "insert into orderItemSite(orderId, itemSiteId, quantityOrdered, recievedQuantity, desiredDeliveryDate, deliveryType)\n" +
                        "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps2
                        = con.prepareStatement(q2);

                // Chuyển đổi desiredDeliveryDate từ kiểu String sang kiểu Date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date;
                try {
                    date = sdf.parse(orderLine.getDesiredDeliveryDate());
                } catch (ParseException e) {
                    // Xử lý ngoại lệ nếu chuỗi không đúng định dạng ngày
                    e.printStackTrace();
                    return; // hoặc throw ngoại lệ để thông báo cho người dùng
                }
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                ps2.setString(1, orderId);
                ps2.setString(2, newItemSiteId);
                ps2.setInt(3, orderLine.getQuantityOrdered());
                ps2.setInt(4, 0);
                ps2.setDate(5, sqlDate);
                ps2.setString(6, orderLine.getDeliveryType());

                ps2.executeUpdate();
            }
        } else {
            orderId = createOrder(orderListItemId, orderParentId, "inActive");
            itemSiteId = getItemSiteIdByItemIdAndSiteId(orderLine.getSiteId(), itemId);

            createOrderItemSite(orderId, itemSiteId, orderLine.getQuantityOrdered(), orderLine.getDesiredDeliveryDate(), orderLine.getDeliveryType());
        }

    }

    public String getItemSiteIdByItemIdAndSiteId(String siteId, String itemId) throws SQLException {
        String query = "select itemSiteId from itemSite where siteId = ? and itemId = ? ";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, siteId);
        ps.setString(2, itemId);
        ResultSet rs = ps.executeQuery();

        String itemSiteId = "";
        while (rs.next()) {
            itemSiteId = rs.getString("itemSiteId");
        }

        return itemSiteId;
    }

    @Override
    public String createOrder(String orderListItemId, String status) throws SQLException {
        String query = "insert into \"order\" (orderListItemId, status) values (?, ?) returning *";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderListItemId);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();

        String orderId = "";
        while (rs.next()) {
            orderId =  rs.getString("orderId");
        }

        return orderId;
    }

    @Override
    public String createOrder(String orderListItemId, String orderParentId, String status) throws SQLException {
        String query = "insert into \"order\" (orderListItemId, orderParentId, status) values (?, ?, ?) returning *";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderListItemId);
        ps.setString(2, orderParentId);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();

        String orderId = "";
        while (rs.next()) {
            orderId =  rs.getString("orderId");
        }

        return orderId;
    }

    @Override
    public void createOrderItemSite(String orderId, String itemSiteId, int quantityOrdered, String desiredDeliveryDate, String deliveryType) throws SQLException {
        String query = "insert into orderItemSite(orderId, itemSiteId, quantityOrdered, recievedQuantity, desiredDeliveryDate, deliveryType)\n" +
                "values (?, ?, ?, ?, ?, ?)";

        // Chuyển đổi desiredDeliveryDate từ kiểu String sang kiểu Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date;
        try {
            date = sdf.parse(desiredDeliveryDate);
        } catch (ParseException e) {
            // Xử lý ngoại lệ nếu chuỗi không đúng định dạng ngày
            e.printStackTrace();
            return; // hoặc throw ngoại lệ để thông báo cho người dùng
        }
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderId);
        ps.setString(2, itemSiteId);
        ps.setInt(3, quantityOrdered);
        ps.setInt(4, 0);
        ps.setDate(5, sqlDate);
        ps.setString(6, deliveryType);

        ps.executeUpdate();
    }

    @Override
    public List<OrderDetail> getListOrderByOrderListItemIdAndStatus(String orderListItemId, String status) throws SQLException {
        String query = "select oi.*, it.*, s.siteName, s.siteId  from orderitemsite oi\n" +
                "join \"order\" o using(orderId)\n" +
                "join itemSite as its using(itemSiteId)\n" +
                "join item as it on it.itemId = its.itemId\n" +
                "join site as s on s.siteId = its.siteId\n" +
                "where o.orderListItemId = ? and o.status = ?";

        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderListItemId);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        List<OrderDetail> listOrder = new ArrayList<>();

        while (rs.next()) {
            String orderId = rs.getString("orderId");
            String siteId = rs.getString("siteId");
            String siteName = rs.getString("siteName");

            //Order Item
            String itemId = rs.getString("itemId");
            String itemName = rs.getString("itemName");
            String unit = rs.getString("unit");
            int quantityOrdered = Integer.parseInt(rs.getString("quantityOrdered"));
            String desiredDeliveryDate = rs.getString("desiredDeliveryDate");
            OrderItem orderItem = new OrderItem(itemId, itemName, unit, quantityOrdered, desiredDeliveryDate);

            boolean orderIdExisted = listOrder.stream()
                    .anyMatch(person -> person.getOrderId().equals(orderId));

            if (orderIdExisted) {
                for (OrderDetail order: listOrder) {
                    if (order.getOrderId().equals(orderId)) {
                        order.addOrderItem(orderItem);
                        break;
                    }
                }
                // bỏ qua lần lặp này
                continue;
            }

            OrderDetail orderDetail = new OrderDetail(orderId, siteId, siteName);
            orderDetail.addOrderItem(orderItem);
            listOrder.add(orderDetail);
        }

        return listOrder;
    }

    public List<OrderDetail> getListOrderByOrderListItemIdAndStatus(String orderListItemId, String status, String orderParentId) throws SQLException {
        String query = "select oi.*, it.*, s.siteName, s.siteId  from orderitemsite oi\n" +
                "join \"order\" o using(orderId)\n" +
                "join itemSite as its using(itemSiteId)\n" +
                "join item as it on it.itemId = its.itemId\n" +
                "join site as s on s.siteId = its.siteId\n" +
                "where o.orderListItemId = ? and o.status = ? and o.orderParentId = ?";

        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderListItemId);
        ps.setString(2, status);
        ps.setString(3, orderParentId);
        ResultSet rs = ps.executeQuery();
        List<OrderDetail> listOrder = new ArrayList<>();

        while (rs.next()) {
            String orderId = rs.getString("orderId");
            String siteId = rs.getString("siteId");
            String siteName = rs.getString("siteName");

            //Order Item
            String itemId = rs.getString("itemId");
            String itemName = rs.getString("itemName");
            String unit = rs.getString("unit");
            int quantityOrdered = Integer.parseInt(rs.getString("quantityOrdered"));
            String desiredDeliveryDate = rs.getString("desiredDeliveryDate");
            OrderItem orderItem = new OrderItem(itemId, itemName, unit, quantityOrdered, desiredDeliveryDate);

            boolean orderIdExisted = listOrder.stream()
                    .anyMatch(person -> person.getOrderId().equals(orderId));

            if (orderIdExisted) {
                for (OrderDetail order: listOrder) {
                    if (order.getOrderId().equals(orderId)) {
                        order.addOrderItem(orderItem);
                        break;
                    }
                }
                // bỏ qua lần lặp này
                continue;
            }

            OrderDetail orderDetail = new OrderDetail(orderId, siteId, siteName);
            orderDetail.addOrderItem(orderItem);
            listOrder.add(orderDetail);
        }

        return listOrder;
    }

    @Override
    public void updateOrderStatus(String orderId, String status) throws SQLException {
        String q = "Update \"order\" Set status = ? Where orderId = ?";
        PreparedStatement ps
                = con.prepareStatement(q);

        ps.setString(1, status);
        ps.setString(2, orderId);
        ps.executeUpdate();

    }

    @Override
    public List<OrderItemPending> getOrderCanceled(String orderId) throws SQLException {
        String query = "select tmp1.itemId, itemName, unit, tmp1.quantityordered, COALESCE(tmp2.selectedQuantity, 0) as selectedQuantity, desireddeliverydate from\n" +
                "(select its.itemId, ois.quantityordered, ois.desiredDeliveryDate\n" +
                "from \"order\" o\n" +
                "join orderItemSite ois using(orderId)\n" +
                "join itemSite its on ois.itemsiteid = its.itemsiteid\n" +
                "where orderId = ?) tmp1\n" +
                "left join \n" +
                "-- Lấy ra các đơn hàng con được tạo từ đơn hàng bị hủy\n" +
                "   (select itemId, sum(selectedQuantity) selectedQuantity from \n" +
                "   (select orderId, itemId, quantityOrdered as selectedQuantity \n" +
                "   from \"order\" o\n" +
                "   join orderItemSite using (orderId)\n" +
                "   join itemSite using(itemSiteId)\n" +
                "   where orderParentId = ? and status = 'inActive') \n" +
                "   as childrenOrders\n" +
                "   group by childrenOrders.itemId) tmp2\n" +
                "on tmp1.itemid = tmp2.itemid\n" +
                "join item on tmp1.itemId = item.itemId";

        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderId);
        ps.setString(2, orderId);
        ResultSet rs = ps.executeQuery();

        List<OrderItemPending> orderItemLists = new ArrayList<>();

        while (rs.next()) {
            String itemId = rs.getString("itemId");
            String itemName = rs.getString("itemName");
            String unit = rs.getString("unit");
            String desiredDeliveryDate = rs.getString("desiredDeliveryDate");
            int quantityOrdered = Integer.parseInt(rs.getString("quantityOrdered"));
            int selectedQuantity = Integer.parseInt(rs.getString("selectedQuantity"));
            int pendingQuantity = quantityOrdered - selectedQuantity;
            OrderItemPending orderItemPending = new OrderItemPending(itemId, itemName, unit,  quantityOrdered, desiredDeliveryDate, selectedQuantity, pendingQuantity);
            orderItemLists.add(orderItemPending);
        }

        return orderItemLists;
    }
}
