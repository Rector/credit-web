package ru.kir.credit.web.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credit_offers")
@Data
@NoArgsConstructor
public class CreditOffer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "client_uuid")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "credit_uuid")
    private Credit credit;

    @Column(name = "credit_sum")
    private BigDecimal creditSum;

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(name = "credit_offers_payment_schedules",
            joinColumns = @JoinColumn(name = "credit_offer_uuid"),
            inverseJoinColumns = @JoinColumn(name = "payment_schedule_uuid"))
    private List<PaymentSchedule> paymentScheduleList;

}