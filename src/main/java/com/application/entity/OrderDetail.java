package com.application.entity;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail extends Order{
    private String siteName;
    private final List<OrderItem> listOrderItem = new ArrayList<>();

    public OrderDetail(String orderId, String siteId, String status, String siteName) {
        super(orderId, siteId, status);
        this.siteName = siteName;
    }

    public OrderDetail(String orderId, String siteId, String siteName) {
        super(orderId, siteId);
        this.siteName = siteName;
    }

    public OrderDetail(String siteName) {
        this.siteName = siteName;
    }

    public OrderDetail() {

    }

    public String getSiteName() {
        return siteName;
    }

    public void addOrderItem(OrderItem orderItem) {
        listOrderItem.add(orderItem);
    }

    public void addOrderItem(List<OrderItem> orderItems) {
        listOrderItem.addAll(orderItems);
    }

    public List<OrderItem> getListOrderItem() {
        return listOrderItem;
    }
}
