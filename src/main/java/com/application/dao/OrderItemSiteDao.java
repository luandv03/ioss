package com.application.dao;

import com.application.entity.OrderItemSite;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemSiteDao {
    public List<OrderItemSite> getOrderItemSiteByOrderId(String orderId) throws SQLException;
    public void updateOrderItemSiteById(String orderId, String itemSiteId, int recievedQuantity) throws SQLException;
}
