package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.OrderItemSiteDao;
import com.application.entity.OrderDetail;
import com.application.entity.OrderItemSite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemSiteSubsystem implements OrderItemSiteDao {
    static Connection con
            = JDBCPostgreSQLConnection.getConnection();
    @Override
    public List<OrderItemSite> getOrderItemSiteByOrderId(String orderId) throws SQLException {
        String query = "select i.*, ots.itemSiteId, ots.quantityOrdered, COALESCE(ots.recievedquantity, 0) recievedQuantity, \n" +
                "ots.desiredDeliveryDate\n" +
                "from orderItemSite ots\n" +
                "join itemSite its using(itemSiteId)\n" +
                "join item i on its.itemId = i.itemId\n" +
                "where ots.orderId = ?";

        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderId);
        ResultSet rs = ps.executeQuery();
        List<OrderItemSite> listOrder = new ArrayList<>();

        while (rs.next()) {
            String itemName = rs.getString("itemName");
            String unit = rs.getString("unit");
            String itemId = rs.getString("itemId");
            int quantityOrdered = rs.getInt("quantityOrdered");
            int recievedQuantity = rs.getInt("recievedQuantity");
            String desiredDeliveryDate = rs.getString("desiredDeliveryDate");
            String itemSiteId = rs.getString("itemSiteId");

            OrderItemSite o = new OrderItemSite(itemId, itemName, unit, quantityOrdered, recievedQuantity, desiredDeliveryDate);
            o.setItemSiteId(itemSiteId);
            listOrder.add(o);
        }

        return listOrder;
    }

    @Override
    public void updateOrderItemSiteById(String orderId, String itemSiteId, int recievedQuantity) throws SQLException {
        String query = "update orderItemSite\n" +
                "    set recievedquantity = ?\n" +
                "    where orderId = ? and itemSiteId = ?";

        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, recievedQuantity);
        ps.setString(2, orderId);
        ps.setString(3, itemSiteId);
        ps.executeUpdate();
    }
}
