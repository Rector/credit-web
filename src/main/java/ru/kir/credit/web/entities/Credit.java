package ru.kir.credit.web.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "credits")
@Data
@NoArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

}