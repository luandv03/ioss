package com.application.enums;

public class OrderListItemStatus {
    public String getOrderListItemStatus(String status) {
        return switch (status) {
            case "pending" -> "Chưa xử lý";
            case "loading" -> "Đang xử lý";
            case "done" -> "Đã xử lý";
            default -> "";
        };
    }
}
