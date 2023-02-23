package com.paymenthandler.exception;

public class CaseAlreadyResolvedException extends Exception {

    public CaseAlreadyResolvedException(long caseId) {
        super("The case with the ID " + caseId + " is already resolved.");
    }
}
