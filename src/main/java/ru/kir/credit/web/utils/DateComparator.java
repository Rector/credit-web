package ru.kir.credit.web.utils;

import ru.kir.credit.web.dtos.PaymentScheduleDto;

import java.util.Comparator;

public class DateComparator implements Comparator<PaymentScheduleDto> {

    @Override
    public int compare(PaymentScheduleDto paymentScheduleDto1, PaymentScheduleDto paymentScheduleDto2) {
        return paymentScheduleDto1.getPaymentDate().compareTo(paymentScheduleDto2.getPaymentDate());
    }

}
