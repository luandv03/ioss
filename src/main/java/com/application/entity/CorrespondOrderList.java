package com.application.entity;

import javafx.collections.ObservableList;

// Đối tượng được dùng để hiển thị các đơn hàng tương ứng với dsmhcd
public class CorrespondOrderList {
    // Mã của đơn hàng chứa mặt hàng này
    private String correspondOrderId;

    // Mã của site chứa mặt hàng này
    private String siteId;

    // Tên Site
    private String siteName;

    // Trạng thái đơn hàng
    private String status;

    // Ngày giao dự kiến
    private String desiredDeliveryDate;

    private ObservableList<OrderItemSite> list;

    public CorrespondOrderList(String correspondingOrder, String siteId, String siteName, String status, String desiredDeliveryDate, ObservableList<OrderItemSite> list)
    {
        this.correspondOrderId = correspondingOrder;
        this.siteId = siteId;
        this.siteName = siteName;
        this.status = status;
        this.desiredDeliveryDate = desiredDeliveryDate;

        this.list.clear();
        this.list.addAll(list);
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

    public String getCorrespondOrderId() {
        return correspondOrderId;
    }

    public ObservableList<OrderItemSite> getList() {
        return list;
    }

}
