package com.paymenthandler.controller;


import com.paymenthandler.exception.CaseAlreadyResolvedException;
import com.paymenthandler.exception.CaseNotFoundException;
import com.paymenthandler.model.Case;
import com.paymenthandler.model.Country;
import com.paymenthandler.service.CaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cases")
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @PostMapping
    public ResponseEntity<?> createCase(@RequestBody Case aCase) {
        caseService.createCase(aCase);
        return new ResponseEntity<>("Case ID " + aCase.getCaseId() + " created", HttpStatus.CREATED);
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<?> retrieveCaseById(@PathVariable Long caseId) {
        try {
            Case aCase = caseService.findCaseById(caseId);
            return new ResponseEntity<>(aCase, HttpStatus.FOUND);
        } catch (CaseNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Case>> getAllCases() {
        List<Case> aCases = caseService.findAllCases();
        return new ResponseEntity<>(aCases, HttpStatus.OK);
    }

    @DeleteMapping("/{caseId}")
    public ResponseEntity<?> deleteCase(@PathVariable Long caseId) {
        try {
            caseService.deleteCase(caseId);
            return new ResponseEntity<>("Case id " + caseId + " deleted.", HttpStatus.GONE);
        } catch (CaseNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "country")
    public ResponseEntity<?> getCasesByCountry(@RequestParam(value = "country") Country country) {
        List<Case> aCases = caseService.findByCountry(country);
        return new ResponseEntity<>(aCases, HttpStatus.OK);
    }

    @PutMapping("/{caseId}")
    public ResponseEntity<?> resolveCase(@PathVariable Long caseId) {
        try {
            Case aCase = caseService.resolveCase(caseId);
            return new ResponseEntity<>("Case ID " + caseId + " resolved. Resolution: " + aCase.getResolution(), HttpStatus.ACCEPTED);
        } catch (CaseNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CaseAlreadyResolvedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.ALREADY_REPORTED);
        }
    }
}


