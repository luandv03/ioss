package com.application.entity;

import javafx.collections.ObservableList;

import java.util.ArrayList;

// Đối tượng được dùng để hiển thị các đơn hàng tương ứng với dsmhcd
public class CorrespondOrderList {
    // Mã của đơn hàng chứa mặt hàng này
    private String orderId;

    // Mã của site chứa mặt hàng này
    private String siteId;

    // Tên Site
    private String siteName;

    // Trạng thái đơn hàng
    private String status;

    // Ngày giao dự kiến
    private String desiredDeliveryDate;

    private ArrayList<OrderItemSite> list = new ArrayList<>();

    public CorrespondOrderList()
    {

    }

    public CorrespondOrderList(String orderId, String siteId, String siteName, String status, String desiredDeliveryDate)
    {
        this.orderId = orderId;
        this.siteId = siteId;
        this.siteName = siteName;
        this.status = status;
        this.desiredDeliveryDate = desiredDeliveryDate;
    }

    public void CorrespondOrderAdd(String itemId, String itemName, String unit, int quantityOrdered, String desiredDeliveryDate) {
        this.list.add(new OrderItemSite(itemId,itemName,unit,quantityOrdered,desiredDeliveryDate));
    }

    public String getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getStatus() {
        return status;
    }

    public String getDesiredDeliveryDate() {
        return desiredDeliveryDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public ArrayList<OrderItemSite> getList() {
        return list;
    }

}
