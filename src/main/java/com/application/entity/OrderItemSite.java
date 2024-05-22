package com.application.entity;

// Sản phẩm mà BPĐHQT đã đặt của site, hiện đang nằm trong đơn hàng
public class OrderItemSite extends Item {
    // Số lượng đặt của site này
    private int quantityOrdered;
    // Ngày giao hàng dự kiến của site này;
    private String desiredDeliveryDate;
    private String deliveryType;

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
}
