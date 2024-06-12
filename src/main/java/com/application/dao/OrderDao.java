package com.application.dao;

import com.application.entity.*;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    List<OrdersStock> getListOrderByStatus(String status) throws SQLException;
    public void saveSelectedSite(String orderListItemId, String itemId, OrderLine orderLine, String orderParentId) throws SQLException;
    public String createOrder(String orderListItemId, String status) throws SQLException;
    public String createOrder(String orderListItemId, String orderParentId, String status) throws SQLException;

    public void createOrderItemSite(String orderId, String itemSiteId, int quantityOrdered, String desiredDeliveryDate, String deliveryType) throws SQLException;
    List<OrderDetail> getListOrderByOrderListItemIdAndStatus(String orderListItemId, String status) throws SQLException;
    List<OrderDetail> getListOrderByOrderListItemIdAndStatus(String orderListItemId, String status, String orderParentId) throws SQLException;

    public void updateOrderStatus(String orderId, String status) throws SQLException;

    public List<OrderItemPending> getOrderCanceled(String orderId) throws SQLException;
}


