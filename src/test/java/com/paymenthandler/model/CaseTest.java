package com.paymenthandler.model;

import com.paymenthandler.exception.CaseAlreadyResolvedException;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CaseTest {

    private static Case aCase;
    private static Payment payment;


    @Test
    void shouldBeResolved() throws CaseAlreadyResolvedException {
        
        payment = new Payment(10L, 200.99, Currency.getInstance("SEK"));
        aCase = new Case(payment, Country.SWEDEN);
        Resolution resolutionBefore = aCase.getResolution();
        
        aCase.resolve();
        Resolution resolutionAfter = aCase.getResolution();
        
        assertNotEquals(resolutionBefore, resolutionAfter);
        
    }

    @Test
    void shouldBeResolvedWithResubmit() throws CaseAlreadyResolvedException {
        
        payment = new Payment(10L, 200.99, Currency.getInstance("SEK"));
        aCase = new Case(payment, Country.SWEDEN);
        Resolution expectedResolution = Resolution.RESUBMIT;
        
        aCase.resolve();
        Resolution resolutionAfter = aCase.getResolution();
        
        assertEquals(expectedResolution, resolutionAfter);
    }

    @Test
    void shouldBeResolvedWithReturn() throws CaseAlreadyResolvedException {
        
        payment = new Payment(10L, -99.9, Currency.getInstance("SEK"));
        aCase = new Case(payment, Country.SWEDEN);
        Resolution expectedResolution = Resolution.RETURN;
        
        aCase.resolve();
        Resolution resolutionAfter = aCase.getResolution();
        
        assertEquals(expectedResolution, resolutionAfter);
    }


}
