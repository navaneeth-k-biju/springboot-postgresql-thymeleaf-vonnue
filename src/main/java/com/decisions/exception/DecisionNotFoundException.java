package com.decisions.exception;

public class DecisionNotFoundException extends RuntimeException {

    public DecisionNotFoundException(Long id) {
        super("Decision not found with id: " + id);
    }
}
