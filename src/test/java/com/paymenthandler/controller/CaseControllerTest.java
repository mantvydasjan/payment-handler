package com.paymenthandler.controller;

import com.paymenthandler.exception.CaseAlreadyResolvedException;
import com.paymenthandler.model.Case;
import com.paymenthandler.model.Country;
import com.paymenthandler.model.Payment;
import com.paymenthandler.model.Resolution;
import com.paymenthandler.repository.FakeCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CaseControllerTest {

    private CaseController controller;
    private Payment payment;
    
    @BeforeEach
    void setUp() {
        controller = new CaseController(new FakeCaseRepository());
        payment = new Payment(16L, 499L, Currency.getInstance("SEK"));
    }
    
    @Test
    void shouldBeCreated() {
        Case aCase = new Case(payment, Country.SWEDEN);

        controller.createCase(aCase);
        
        assertEquals(aCase, controller.getCaseById(aCase.getCaseId()).getBody());
    }
    
    @Test
    void shouldGetAllCases() {
        Case case1 = new Case(payment, Country.SWEDEN);
        Case case2 = new Case(payment, Country.NORWAY);
        
        controller.createCase(case1);
        controller.createCase(case2);
        
        assertEquals(List.of(case1, case2), controller.getAllCases().getBody());
    }
    
    @Test
    void shouldGetCasesByCountry() {
        Case case1 = new Case(payment, Country.SWEDEN);
        Case case2 = new Case(payment, Country.NORWAY);
        Case case3 = new Case(payment, Country.SWEDEN);
        
        controller.createCase(case1);
        controller.createCase(case2);
        controller.createCase(case3);
        
        assertEquals(List.of(case1, case3), controller.getCasesByCountry(Country.SWEDEN).getBody());
    }
    
    @Test
    void shouldGetTotalAmountOfPaymentsInUnresolvedCases() throws CaseAlreadyResolvedException {
        Case case1 = new Case(payment, Country.DENMARK);
        Payment payment2 = new Payment(17L, 299L,Currency.getInstance("SEK"));
        Case case2 = new Case(payment2, Country.SWEDEN);
        controller.createCase(case1);
        controller.createCase(case2);
        
        case1.resolve();
        
        assertEquals("Total amount: {SEK=299}", 
                controller.getTotalAmountOfPaymentsInUnresolvedCases().getBody());
    }
    
    @Test
    void shouldBeResolved() {
        Case aCase = new Case(payment, Country.SWEDEN);
        controller.createCase(aCase);
        
        controller.resolveCase(aCase.getCaseId());
        
        assertEquals(Resolution.RESUBMIT, controller.getCaseById(aCase.getCaseId()).getBody().getResolution());
    }

    @Test
    void shouldNotBeFound() {
        Case case1 = new Case(payment, Country.SWEDEN);
        Case case2 = new Case(payment, Country.SWEDEN);

        controller.createCase(case1);
       
        assertEquals(HttpStatus.NOT_FOUND, controller.resolveCase(case2.getCaseId()).getStatusCode());
    }
    
    @Test
    void shouldNotResolveTwice() {
        Case aCase = new Case(payment, Country.SWEDEN);
        controller.createCase(aCase);
        
        controller.resolveCase(aCase.getCaseId());

        assertEquals(HttpStatus.BAD_REQUEST, controller.resolveCase(aCase.getCaseId()).getStatusCode());
    }

    @Test
    void shouldDeleteCase() {
        Case case1 = new Case(payment, Country.SWEDEN);
        controller.createCase(case1);
        Case case2 = new Case(payment, Country.SWEDEN);
        controller.createCase(case2);
        Case case3 = new Case(payment, Country.SWEDEN);
        controller.createCase(case3);
        
        controller.deleteCase(case2.getCaseId());
       
        assertEquals(List.of(case1, case3), controller.getAllCases().getBody());
    }
}