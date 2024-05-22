package com.application.helper;

import java.time.LocalDate;

public class CalculateDater {

    public String calculateDesiredDeliveryDate(int dateSumDelivery) {
        // Lấy ra ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Cộng thêm số ngày vào ngày hiện tại
        LocalDate desiredDeliveryDate = currentDate.plusDays(dateSumDelivery);

        // Trích xuất năm, tháng, ngày từ đối tượng LocalDate
        int desiredYear = desiredDeliveryDate.getYear();
        int desiredMonth = desiredDeliveryDate.getMonthValue();
        int desiredDay = desiredDeliveryDate.getDayOfMonth();

        // Trả về kết quả dưới dạng chuỗi "dd-MM-yyyy"
        return String.format("%02d-%02d-%04d", desiredDay, desiredMonth, desiredYear);
    }

}
