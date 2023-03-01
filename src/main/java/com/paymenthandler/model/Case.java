package com.paymenthandler.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.paymenthandler.exception.CaseAlreadyResolvedException;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "cases")
public class Case {

    @Transient
    private static final long MIN_AMOUNT = 0L;
    @Transient
    private static final long MAX_AMOUNT = 1_00_000_000L;
    @Id
    @Column(name = "case_id", nullable = false, updatable = false)
    private final UUID caseId = UUID.randomUUID();
    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;
    private Resolution resolution = Resolution.UNRESOLVED;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Case(Payment payment, Country country) {
        this.payment = payment;
        this.country = country;
    }

    public Case() {
    }

    public UUID getCaseId() {
        return caseId;
    }

    public Country getCountry() {
        return country;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public Payment getPayment() {
        return payment;
    }

    public void resolve() throws CaseAlreadyResolvedException {
        if (resolution == Resolution.UNRESOLVED) {
            resolution = isAmountValid() ? Resolution.RESUBMIT : Resolution.RETURN;
        } else throw new CaseAlreadyResolvedException(caseId);
    }

    private boolean isAmountValid() {
        return (payment.getAmountInCents() > MIN_AMOUNT && payment.getAmountInCents() <= MAX_AMOUNT);
    }
    
    @Override
    public String toString() {
        return "Case{" +
                "caseId=" + caseId +
                ", country=" + country +
                ", resolution=" + resolution +
                ", payment=" + payment +
                '}';
    }
}


