package com.application.dao;

import java.sql.SQLException;
import com.application.entity.OrderItemSite;

public interface OrderItemDao {
    public void createOrderItemSite(OrderItemSite orderItemSite) throws SQLException;
}
