package com.paymenthandler.controller;


import com.paymenthandler.exception.CaseAlreadyResolvedException;
import com.paymenthandler.exception.CaseNotFoundException;
import com.paymenthandler.model.Case;
import com.paymenthandler.model.Country;
import com.paymenthandler.model.Resolution;
import com.paymenthandler.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cases")
public class CaseController {

    @Autowired
    private final CaseRepository caseRepository;

    public CaseController(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @PostMapping
    public ResponseEntity<String> createCase(@RequestBody Case aCase) {
        caseRepository.save(aCase);
        return new ResponseEntity<>("Case ID " + aCase.getCaseId() + " created", HttpStatus.CREATED);
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<Case> getCaseById(@PathVariable Long caseId) {
        try {
            Case aCase = caseRepository.findById(caseId).orElseThrow(() -> new CaseNotFoundException(caseId));
            return new ResponseEntity<>(aCase, HttpStatus.FOUND);
        } catch (CaseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Case>> getAllCases() {
        List<Case> aCases = caseRepository.findAll();
        return new ResponseEntity<>(aCases, HttpStatus.OK);
    }

    @GetMapping(params = "country")
    public ResponseEntity<List<Case>> getCasesByCountry(@RequestParam(value = "country") Country country) {
        List<Case> aCases = caseRepository.findByCountry(country);
        return new ResponseEntity<>(aCases, HttpStatus.OK);
    }

    @PutMapping("/{caseId}/resolution")
    public ResponseEntity<String> resolveCase(@PathVariable Long caseId) {
        try {
            Case aCase = caseRepository.findById(caseId).orElseThrow(() -> new CaseNotFoundException(caseId));
            aCase.resolve();
            return new ResponseEntity<>("Case ID " + caseId + " resolved. Resolution: " + aCase.getResolution(), HttpStatus.OK);
        } catch (CaseNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CaseAlreadyResolvedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{caseId}")
    public ResponseEntity<String> deleteCase(@PathVariable Long caseId) {
        if (caseRepository.findById(caseId).isPresent()) {
            caseRepository.deleteById(caseId);
            return new ResponseEntity<>("Case id " + caseId + " deleted.", HttpStatus.OK);
        } else return new ResponseEntity<>("Case id " + caseId + " not found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/totalAmount")
    public ResponseEntity<String> getTotalAmountOfPaymentsInUnresolvedCases() {
        List<Case> unresolvedCases = caseRepository.findByResolution(Resolution.UNRESOLVED);
        var groupByCurrency = unresolvedCases.stream().
                collect(Collectors.groupingBy(aCase -> aCase.getPayment().getCurrency(), 
                        Collectors.summingDouble(value -> value.getPayment().getAmount())));
        return new ResponseEntity<>("Total amount: " + groupByCurrency, HttpStatus.OK);
    }
}


