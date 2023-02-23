package com.paymenthandler.controller;

import com.paymenthandler.model.Case;
import com.paymenthandler.service.CaseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

class CaseControllerTest {

    CaseController caseController;
    CaseService caseService;
    
    @BeforeAll
    void setUp() {
        caseController = new CaseController(caseService);
    }
    
    @Test
    void canCreateCase() {
        
    }
}