package ru.kir.credit.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kir.credit.web.entities.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {
}