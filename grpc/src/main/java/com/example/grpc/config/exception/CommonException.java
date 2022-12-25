package com.example.grpc.config.exception;

public class CommonException extends Exception {
    public CommonException() {}

    public CommonException(String message) {
        super(message);
    }
}
