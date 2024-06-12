package com.application.entity;

// 1 Mặt hàng trong dsmhcđ
public class OrderItem extends Item {
    //Số lượng cần đặt
    private int quantityOrdered;

    //Ngày nhận mong muốn
    private String desiredDeliveryDate;

    public OrderItem(String itemId, String itemName, String unit, int quantityOrdered, String desiredDeliveryDate) {
        super(itemId, itemName, unit);
        this.quantityOrdered = quantityOrdered;
        this.desiredDeliveryDate = desiredDeliveryDate;
    }

    public  OrderItem() {
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public void setDesiredDeliveryDate(String desiredDeliveryDate) {
        this.desiredDeliveryDate = desiredDeliveryDate;
    }

    public String getDesiredDeliveryDate() {
        return desiredDeliveryDate;
    }
}
