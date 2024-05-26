package com.application.dao;

import com.application.entity.CorrespondOrderList;
import com.application.entity.OrderItemSite;

import java.sql.SQLException;
import java.util.List;

public interface CorrespondOrderDao {
    public List<String> getOrderIdByOrderListId(String orderListId) throws SQLException;
    public List<String> getOrderIdByOrderListIdAndItemId(String orderListId, String itemId) throws SQLException;
    public CorrespondOrderList getCorrespondOrderListByOrderId(String orderId) throws SQLException;
}
