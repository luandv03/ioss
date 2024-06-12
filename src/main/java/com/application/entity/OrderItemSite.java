package com.application.entity;

// Sản phẩm mà BPĐHQT đã đặt của site, hiện đang nằm trong đơn hàng
public class OrderItemSite extends Item {

    // Số lượng đặt của site này
    private String itemSiteId;
    private int quantityOrdered;
    private int recievedQuantity;
    // Ngày giao hàng dự kiến của site này;
    private String desiredDeliveryDate;
    private String deliveryType;

    public OrderItemSite(String itemId, String itemName, String unit, int quantityOrdered, int recievedQuantity, String desiredDeliveryDate) {
        super(itemId, itemName, unit);
        this.quantityOrdered = quantityOrdered;
        this.recievedQuantity = recievedQuantity;
        this.desiredDeliveryDate = desiredDeliveryDate;
    }

    public OrderItemSite(String itemId, String itemName, String unit, int quantityOrdered, String desiredDeliveryDate, String deliveryType) {
        super(itemId, itemName, unit);
        this.quantityOrdered = quantityOrdered;
        this.desiredDeliveryDate = desiredDeliveryDate;
        this.deliveryType = deliveryType;
    }

    public OrderItemSite(String itemId, String itemName, String unit, int quantityOrdered, String desiredDeliveryDate) {
        super(itemId, itemName, unit);
        this.quantityOrdered = quantityOrdered;
        this.desiredDeliveryDate = desiredDeliveryDate;
    }

    public OrderItemSite(int quantityOrdered, String desiredDeliveryDate) {
        this.quantityOrdered = quantityOrdered;
        this.desiredDeliveryDate = desiredDeliveryDate;
    }

    public OrderItemSite() {

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

    public int getRecievedQuantity() {
        return recievedQuantity;
    }

    public void setRecievedQuantity(int recievedQuantity) {
        this.recievedQuantity = recievedQuantity;
    }

    public String getItemSiteId() {
        return this.itemSiteId;
    }

    public void setItemSiteId(String itemSiteId) {
        this.itemSiteId = itemSiteId;
    }
}
