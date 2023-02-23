package com.paymenthandler.service;

import com.paymenthandler.exception.CaseAlreadyResolvedException;
import com.paymenthandler.exception.CaseNotFoundException;
import com.paymenthandler.model.Case;
import com.paymenthandler.model.Country;
import com.paymenthandler.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseService {

    @Autowired
    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public void createCase(Case aCase) {
        caseRepository.save(aCase);
    }

    public Case findCaseById(Long caseId) throws CaseNotFoundException {
        return caseRepository.findById(caseId).orElseThrow(() -> new CaseNotFoundException(caseId));
    }

    public List<Case> findAllCases() {
        return caseRepository.findAll();
    }

    public Case resolveCase(Long caseId) throws CaseNotFoundException, CaseAlreadyResolvedException {
        Case aCase = caseRepository.findById(caseId).orElseThrow(() 
                -> new CaseNotFoundException(caseId));
        aCase.resolve();
        caseRepository.save(aCase);
        return aCase;

    }

    public void deleteCase(Long caseId) throws CaseNotFoundException {
        if (caseRepository.findById(caseId).isPresent()) {
            caseRepository.deleteById(caseId);
        } else throw new CaseNotFoundException(caseId);
    }

    public List<Case> findByCountry(Country country) {
        return caseRepository.findByCountry(country);
    }
}

