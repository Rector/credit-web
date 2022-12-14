package ru.kir.credit.web.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "payment_schedules")
@Data
@NoArgsConstructor
public class PaymentSchedule {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_sum")
    private BigDecimal paymentSum;

    @Column(name = "repayment_sum_credit_body")
    private BigDecimal repaymentSumCreditBody;

    @Column(name = "repayment_sum_interest")
    private BigDecimal repaymentSumInterest;

}