package com.application.entity;

public class OrderLine {
    private String siteId;
    private String itemSiteId;
    // Số lượng đặt của site này
    private int quantityOrdered;
    // Ngày giao hàng dự kiến của site này;
    private String desiredDeliveryDate;
    private String deliveryType;

    public OrderLine(String siteId, int quantityOrdered, String desiredDeliveryDate, String deliveryType) {
        this.siteId = siteId;
        this.quantityOrdered = quantityOrdered;
        this.desiredDeliveryDate = desiredDeliveryDate;
        this.deliveryType = deliveryType;
    }

    public OrderLine(String siteId, String itemSiteId, int quantityOrdered, String desiredDeliveryDate, String deliveryType) {
        this.siteId = siteId;
        this.itemSiteId = itemSiteId;
        this.quantityOrdered = quantityOrdered;
        this.desiredDeliveryDate = desiredDeliveryDate;
        this.deliveryType = deliveryType;
    }

    public OrderLine() {

    }

    public String getSiteId() {
        return siteId;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public String getDesiredDeliveryDate() {
        return desiredDeliveryDate;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public String getItemSiteId() {
        return itemSiteId;
    }

    public void setItemSiteId(String itemSiteId) {
        this.itemSiteId = itemSiteId;
    }
}
