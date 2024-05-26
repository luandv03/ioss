package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.OrderItemLoadAndDoneSubSystemDao;
import com.application.entity.OrderItemLoadingAndDone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemLoadAndDoneSubsytem implements OrderItemLoadAndDoneSubSystemDao {

    static Connection con
            = JDBCPostgreSQLConnection.getConnection();

    @Override
    public List<OrderItemLoadingAndDone> getAllOrderItemByListId(String orderListItemId) throws SQLException {
        String query =
                "SELECT \n" +
                "    i.itemid,\n" +
                "    i.itemName,\n" +
                "\ti.unit,\n" +
                "    SUM(ois.quantityOrdered) AS totalQuantity,\n" +
                "\tSUM(CASE WHEN o.status <> 'in_active' THEN ois.quantityOrdered ELSE 0 END) AS totalQuantityOrdered,\n" +
                "    SUM(CASE WHEN o.status = 'done' THEN ois.quantityOrdered ELSE 0 END) AS totalQuantityOrderedDone,\n" +
                "    MAX(CASE WHEN o.status <> 'canceled' THEN ois.desiredDeliveryDate ELSE NULL END) AS latestDeliveryDate\n" +
                "FROM \n" +
                "    item i\n" +
                "JOIN \n" +
                "    itemsite isite ON i.itemid = isite.itemid\n" +
                "JOIN \n" +
                "    orderitemsite ois ON isite.itemsiteid = ois.itemsiteid\n" +
                "JOIN \n" +
                "    \"order\" o ON ois.orderid = o.orderid\n" +
                "WHERE \n" +
                "    o.orderlistitemid ='" + orderListItemId + "'\n" +
                "GROUP BY \n" +
                "    i.itemid, i.itemName;";

        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<OrderItemLoadingAndDone> list = new ArrayList<>();

        while (rs.next()) {
            String itemId = rs.getString("itemid");
            String itemName = rs.getString("itemName");
            String unit = rs.getString("unit");
            int totalQuantity = Integer.parseInt(
                                            rs.getString("totalQuantity"));
            int totalQuantityOrdered = Integer.parseInt(
                                            rs.getString("totalQuantityOrdered"));
            int totalQuantityOrderedDone = Integer.parseInt(
                                            rs.getString("totalQuantityOrderedDone"));
            String latestDeliveryDate = rs.getString("latestDeliveryDate");

            OrderItemLoadingAndDone item =
                    new OrderItemLoadingAndDone(itemId,itemName,unit,totalQuantity,totalQuantityOrdered,totalQuantityOrderedDone,latestDeliveryDate);
            list.add(item);
        }

        return list;
    }
}
