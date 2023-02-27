package com.paymenthandler.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.paymenthandler.exception.CaseAlreadyResolvedException;
import jakarta.persistence.*;

@Entity
@Table(name = "cases")
public class Case {

    @Transient
    private final double MIN_AMOUNT = 0;
    @Transient
    private final double MAX_AMOUNT = 1_000_000;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "case_id", nullable = false, updatable = false)
    private Long caseId;
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

    public Long getCaseId() {
        return caseId;
    }
    
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
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
        return (payment.getAmount() > MIN_AMOUNT && payment.getAmount() <= MAX_AMOUNT);
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


