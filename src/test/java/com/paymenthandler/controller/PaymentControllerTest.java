package com.paymenthandler.controller;

import com.paymenthandler.model.Payment;
import com.paymenthandler.repository.FakePaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentControllerTest {
    
    private PaymentController controller;
    
    @BeforeEach
    void setUp() { controller = new PaymentController(new FakePaymentRepository());
    }

    @Test
    void shouldGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        
        assertEquals(payments, controller.getAllPayments().getBody());
    }
}