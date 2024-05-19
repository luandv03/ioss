package com.application.entity;

import java.util.List;

public class OrderListItem {
    private String orderListItemId;
    private String status;
    private int countItem;

//    private List<OrderItem> orderItemList;

    public OrderListItem(String orderListItemId, String status, int countItem) {
        this.orderListItemId = orderListItemId;
        this.status = status;
        this.countItem = countItem;
    }


    public String getOrderListItemId() {
        return orderListItemId;
    }

    public String getStatus() {
        return status;
    }

    public int getCountItem() {
        return countItem;
    }
}
