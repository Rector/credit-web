package ru.kir.credit.web.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "banks")
@Data
@NoArgsConstructor
public class Bank {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid")
    private String uuid;

    @ManyToMany
    @JoinTable(name = "banks_clients",
            joinColumns = @JoinColumn(name = "bank_uuid"),
            inverseJoinColumns = @JoinColumn(name = "client_uuid"))
    private List<Client> clientList;

    @ManyToMany
    @JoinTable(name = "banks_credits",
            joinColumns = @JoinColumn(name = "bank_uuid"),
            inverseJoinColumns = @JoinColumn(name = "credit_uuid"))
    private List<Credit> creditList;

}