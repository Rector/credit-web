package ru.kir.credit.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kir.credit.web.entities.Credit;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, String> {
    Optional<Credit> findByCreditLimitAndInterestRate(BigDecimal creditLimit, BigDecimal interestRate);

}