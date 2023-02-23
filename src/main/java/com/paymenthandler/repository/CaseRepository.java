package com.paymenthandler.repository;

import com.paymenthandler.model.Case;
import com.paymenthandler.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
    List<Case> findByCountry(Country country);
}
