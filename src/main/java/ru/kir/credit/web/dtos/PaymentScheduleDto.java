package ru.kir.credit.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.credit.web.entities.PaymentSchedule;

import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@Data
public class PaymentScheduleDto {
    private String uuid;
    private Date paymentDate;
    private BigDecimal paymentSum;
    private BigDecimal repaymentSumCreditBody;
    private BigDecimal repaymentSumInterest;

    public PaymentScheduleDto(PaymentSchedule paymentSchedule) {
        this.uuid = paymentSchedule.getUuid();
        this.paymentDate = paymentSchedule.getPaymentDate();
        this.paymentSum = paymentSchedule.getPaymentSum();
        this.repaymentSumCreditBody = paymentSchedule.getRepaymentSumCreditBody();
        this.repaymentSumInterest = paymentSchedule.getRepaymentSumInterest();
    }

}
