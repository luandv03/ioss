package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.UserDao;
import com.application.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSubsystem implements UserDao {
    static Connection con
            = JDBCPostgreSQLConnection.getConnection();

    @Override
    public User login(String email, String password) throws SQLException {
        String query = "Select * from \"user\" where email = ? and password = ?";

        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        User user = null;

        while (rs.next()) {
            String userId = rs.getString("userId");
            String username = rs.getString("username");
            String newEmail = rs.getString("email");
            String role = rs.getString("role");

             user = new User(userId, username, newEmail, role);
        }

        return user;
    }
}
