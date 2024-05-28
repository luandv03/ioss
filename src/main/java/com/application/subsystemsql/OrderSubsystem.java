package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.OrderDao;
import com.application.entity.ItemSite;
import com.application.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderSubsystem implements OrderDao {

    static Connection con
            = JDBCPostgreSQLConnection.getConnection();
    @Override
    public List<Order> getListOrder() throws SQLException {
        return null;
    }

    @Override
    public void createOrder(Order order, String orderListItemId) throws SQLException {
        String query = "insert into (orderId, orderListItemId, status) order values (1, 2, 3)";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, order.getOrderId());
        ps.setString(2, orderListItemId);
        ps.setString(3, order.getStatus());
        ResultSet rs = ps.executeQuery();
    }
}
