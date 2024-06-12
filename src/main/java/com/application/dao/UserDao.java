package com.application.dao;

import com.application.entity.User;

import java.sql.SQLException;

public interface UserDao {
    public User login(String email, String password) throws SQLException;
}
