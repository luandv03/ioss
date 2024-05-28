package com.application.dao;

import com.application.entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    List<Order> getListOrder() throws SQLException;
    public void createOrder(Order order, String orderListItemId) throws SQLException;
}


