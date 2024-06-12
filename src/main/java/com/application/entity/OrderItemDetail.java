package com.application.entity;

public class OrderItemDetail extends OrderItemSite {
    private int pickedQuantity;
    private int remainQuantity;
    public OrderItemDetail(String itemId, String itemName, String unit, int quantityOrdered, String desiredDeliveryDate, int pickedQuantity, int remainQuantity){
        super(itemId, itemName, unit,quantityOrdered,desiredDeliveryDate);
        this.pickedQuantity=pickedQuantity;
        this.remainQuantity=remainQuantity;
    }
    public OrderItemDetail() {
    }

    public int getPickedQuantity() {
        return pickedQuantity;
    }

    public int getRemainQuantity() {
        return remainQuantity;
    }

}
