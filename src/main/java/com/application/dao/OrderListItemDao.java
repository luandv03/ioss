package com.application.dao;

import com.application.entity.OrderItemPending;
import com.application.entity.OrderListItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderListItemDao {
    public List<OrderListItem> getListOrderItemAll() throws SQLException;
    public List<OrderItemPending> getListOrderItemById(String orderListItemId, String status) throws SQLException;

}
