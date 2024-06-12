package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.OrderItemDao;
import com.application.entity.OrderItem;
import com.application.entity.OrderItemSite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderItemSubsystem implements OrderItemDao {
    static Connection con
            = JDBCPostgreSQLConnection.getConnection();
    @Override
    public void createOrderItemSite(OrderItem orderItem, String orderListItemId) throws SQLException {
        String query = "insert into orderItem(itemId, orderListItemId, quantityOrdered, desiredDeliveryDate) values(?, ?, ?, ?)";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, orderItem.getItemId());
        ps.setString(2, orderListItemId);
        ps.setInt(3, orderItem.getQuantityOrdered());

        // Chuyển đổi desiredDeliveryDate từ kiểu String sang kiểu Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date;
        try {
            date = sdf.parse(orderItem.getDesiredDeliveryDate());
        } catch (ParseException e) {
            // Xử lý ngoại lệ nếu chuỗi không đúng định dạng ngày
            e.printStackTrace();
            return; // hoặc throw ngoại lệ để thông báo cho người dùng
        }
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        ps.setDate(4, sqlDate);

        ps.execute();

    }
}
