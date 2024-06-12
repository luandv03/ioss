package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.CorrespondOrderDao;
import com.application.entity.CorrespondOrderList;
import com.application.entity.OrderItemLoadingAndDone;
import com.application.entity.OrderItemSite;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CorrespondOrderSubsystem implements CorrespondOrderDao {

    static Connection con
            = JDBCPostgreSQLConnection.getConnection();

    @Override
    public List<String> getOrderIdByOrderListId(String orderListItemId) throws SQLException {
        String query =
                "SELECT * FROM \"order\" WHERE orderlistitemid = '" + orderListItemId + "' AND status != 'inActive'";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();

        while (rs.next()) {
            String orderId = rs.getString("orderId");
            list.add(orderId);
        }

        return list;
    }

    @Override
    public List<String> getOrderIdByOrderListIdAndItemId(String orderListItemId, String itemId) throws SQLException {
        String query =
                "SELECT o.orderid\n" +
                "FROM \"order\" o\n" +
                "JOIN orderitemsite ois ON ois.orderid = o.orderid\n" +
                "JOIN itemsite its ON its.itemSiteId = ois.itemSiteId\n" +
                "WHERE o.orderlistitemid = '" + orderListItemId + "'\n" +
                "AND its.itemid = '" + itemId + "';";

        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();

        while (rs.next()) {
            String orderId = rs.getString("orderId");
            list.add(orderId);
        }

        return list;
    }

    @Override
    public CorrespondOrderList getCorrespondOrderListByOrderId(String listOrderItemid) throws SQLException {
        String query =
                "SELECT\n" +
                "\ts.siteid,\n" +
                "\ts.siteName,\n" +
                "    o.orderid,\n" +
                "    o.status,\n" +
                "\tMAX(ois.desiredDeliveryDate) AS max_desiredDeliveryDate,\n" +
                "    i.itemid,\n" +
                "\ti.itemName,\n" +
                "    i.unit,\n" +
                "    ois.quantityOrdered\n" +
                "FROM\n" +
                "    \"order\" o\n" +
                "JOIN\n" +
                "    orderitemsite ois ON o.orderid = ois.orderid\n" +
                "JOIN\n" +
                "    itemsite isite ON ois.itemSiteId = isite.itemSiteId\n" +
                "JOIN\n" +
                "    item i ON isite.itemid = i.itemid\n" +
                "JOIN\n" +
                "    site s ON isite.siteId = s.siteId\n" +
                "WHERE\n" +
                "    o.orderid = '" + listOrderItemid + "'\n" +
                "GROUP BY\n" +
                "    s.siteid,\n" +
                "\ts.siteName,\n" +
                "    o.orderid,\n" +
                "    o.status,\n" +
                "\ti.itemid,\n" +
                "\ti.itemName,\n" +
                "    i.unit,\n" +
                "    ois.quantityOrdered";

        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        CorrespondOrderList list = new CorrespondOrderList();

        boolean first_add = false;
        while (rs.next())
        {
            String siteid = rs.getString("siteid");
            String siteName = rs.getString("siteName");
            String status = rs.getString("status");
            String orderid = rs.getString("orderid");
            String desiredDeliveryDate = rs.getString("max_desiredDeliveryDate");
            String itemid = rs.getString("itemid");
            String itemName = rs.getString("itemName");
            String unit = rs.getString("unit");
            int quantityOrdered = Integer.parseInt(rs.getString("quantityOrdered"));

            if (!first_add) {
                list = new CorrespondOrderList(orderid, siteid, siteName, status, desiredDeliveryDate);
                first_add = true;
            }

            list.CorrespondOrderAdd(itemid,itemName,unit,quantityOrdered,"");
        }

        return list;
    }
}
