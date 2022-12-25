package com.example.senderservice.configuration;

import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@Slf4j
@GrpcAdvice
public class GrpcExceptionHandle {

//    @ExceptionHandler({ MyCustomDomainException.class, AnotherCustomDomainException.class })
    @GrpcExceptionHandler(Exception.class)
    public Status handleException(Exception e) {
        log.error("\nhandleException", e);
        return Status.fromCode(Status.Code.INVALID_ARGUMENT)
                .withDescription(e.getMessage())
//                .augmentDescription("XXX")
                .withCause(e);
    }

}
