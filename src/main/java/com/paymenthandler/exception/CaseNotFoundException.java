package com.paymenthandler.exception;

import java.util.UUID;

public class CaseNotFoundException extends Exception {

    public CaseNotFoundException(UUID caseId) {
        super("The case with the ID " + caseId + " could not be found.");
    }
}
