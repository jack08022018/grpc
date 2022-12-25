package com.example.orchestratorservice.exceptions;

public class TimeoutException extends Exception {
    public TimeoutException(String message) {
        super(message);
    }
}
