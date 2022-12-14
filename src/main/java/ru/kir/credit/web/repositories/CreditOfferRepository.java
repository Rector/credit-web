package ru.kir.credit.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kir.credit.web.entities.CreditOffer;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, String> {
}