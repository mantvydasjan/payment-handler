package com.paymenthandler.exception;

public class CaseNotFoundException extends Exception {

    public CaseNotFoundException(long caseId) {
        super("The case with the ID " + caseId + " could not be found.");
    }
}
