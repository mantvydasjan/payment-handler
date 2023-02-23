package com.paymenthandler.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Currency;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private Long paymentId;
    private Double amount;
    private Currency currency;
    @OneToOne(mappedBy = "payment")
    @JsonBackReference
    private Case aCase;

    public Payment(Long paymentId, Double amount, Currency currency) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.currency = currency;

    }

    public Payment() {
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

}
