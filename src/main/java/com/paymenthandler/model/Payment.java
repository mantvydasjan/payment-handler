package com.paymenthandler.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Currency;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private long paymentId;
    private long amountInCents;
    private Currency currency;
    @OneToOne(mappedBy = "payment")
    @JsonBackReference
    private Case aCase;

    public Payment(long paymentId, long amountInCents, Currency currency) {
        this.paymentId = paymentId;
        this.amountInCents = amountInCents;
        this.currency = currency;
    }

    public Payment() {
    }

    public long getPaymentId() {
        return paymentId;
    }

    public long getAmountInCents() {
        return amountInCents;
    }

    public Currency getCurrency() {
        return currency;
    }
    
}
