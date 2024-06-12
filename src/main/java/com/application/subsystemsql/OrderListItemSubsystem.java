package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.OrderListItemDao;
import com.application.entity.OrderItemPending;
import com.application.entity.OrderListItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderListItemSubsystem implements OrderListItemDao {

    static Connection con
            = JDBCPostgreSQLConnection.getConnection();

    @Override
    public String createOrderListItem(String status) throws SQLException {
        String query = "insert into orderListItem(status, created_at, updated_at) values(?, current_date, current_date) returning *";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();

        String orderListItemId = "";

        while (rs.next()) {
            orderListItemId = rs.getString("orderListItemId");

        }

        return orderListItemId;

    }

    @Override
    public List<OrderListItem> getListOrderItemAll() throws SQLException {
        String query = "select olt.*, count(itemId) as countItem from orderListItem olt\n" +
                "join orderItem using(orderListItemId) \n" +
                "group by (olt.orderlistitemid)";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<OrderListItem> orderListItem = new ArrayList<>();

        while (rs.next()) {
            String orderListItemId = rs.getString("orderListItemId");
            String status = rs.getString("status");
            String countItem = rs.getString("countItem");
            OrderListItem item = new OrderListItem(orderListItemId, status, Integer.parseInt(countItem));
            orderListItem.add(item);
        }

        return orderListItem;
    }

    @Override
    public void updateStatusOrderListItem(String orderListItemId, String status) throws SQLException {
        String q = "Update orderListItem Set status = ? Where orderListItemId = ?";
        PreparedStatement ps
                = con.prepareStatement(q);

        ps.setString(1, status);
        ps.setString(2, orderListItemId);
        ps.executeUpdate();
    }

    @Override
    public List<OrderItemPending> getListOrderItemById(String orderListItemId) throws SQLException {
        String query = "select t1.itemId, itemName, t1.quantityOrdered, t1.unit, t1.desiredDeliveryDate, COALESCE(t2.selectedQuantity, 0) selectedQuantity from (select i.itemId, i.itemName, i.unit, odi.quantityOrdered, odi.desiredDeliveryDate from orderItem odi\n" +
                " join item i using(itemId)\n" +
                " where odi.orderlistitemid = ?) as t1\n" +
                " left join \n" +
                "(select itemId, sum(selectedQuantity) selectedQuantity from (select its.itemId, o.orderId, o.orderListItemId, ois.quantityOrdered as selectedQuantity from \"order\" o\n" +
                " join orderItemSite ois using(orderId)\n" +
                "  join itemSite its on (its.itemSiteId = ois.itemsiteid)\n" +
                " where status = 'inActive' and  o.orderlistitemid = ?) as tmp\n" +
                " group by itemId) t2\n" +
                " on t1.itemId = t2.itemId";

        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderListItemId);
        ps.setString(2, orderListItemId);
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
