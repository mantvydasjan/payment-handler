package com.paymenthandler.repository;

import com.paymenthandler.model.Case;
import com.paymenthandler.model.Country;
import com.paymenthandler.model.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CaseRepository extends JpaRepository<Case, UUID> {
    List<Case> findByCountry(Country country);
    List<Case> findByResolution(Resolution resolution);
}
