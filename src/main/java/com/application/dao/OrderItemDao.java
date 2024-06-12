package com.application.dao;

import java.sql.SQLException;

import com.application.entity.OrderItem;
import com.application.entity.OrderItemSite;

public interface OrderItemDao {
    public void createOrderItemSite(OrderItem orderItem, String orderListItemId) throws SQLException;
}
