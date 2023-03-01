package com.paymenthandler.exception;

import java.util.UUID;

public class CaseAlreadyResolvedException extends Exception {

    public CaseAlreadyResolvedException(UUID caseId) {
        super("The case with the ID " + caseId + " is already resolved.");
    }
}
