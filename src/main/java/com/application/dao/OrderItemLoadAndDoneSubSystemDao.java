package com.application.dao;

import com.application.entity.OrderItemLoadingAndDone;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemLoadAndDoneSubSystemDao {
    public List<OrderItemLoadingAndDone> getAllOrderItemByListId(String orderListItemId) throws SQLException;
}
