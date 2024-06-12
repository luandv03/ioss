package com.application.enums;

public class OrderStatus {

    public String getOrderStatus(String orderStatus) {
        return switch (orderStatus) {
            case "inActive" -> "Chưa gửi yêu cầu";
            case "active" -> "Chờ xác nhận";
            case "delivering" -> "Chờ giao";
            case "done" -> "Đã nhận";
            case "canceled" -> "Đã hủy";
            case "deleted" -> "Đã xóa";
            default -> "";
        };
    }
}
