package com.example.grpc.config;

import com.example.grpc.common.FunctionCommonUtils;
import com.example.grpc.config.exception.ErrorResponse;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    final FunctionCommonUtils functionCommonUtils;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> mainExceptionHandler(Exception e, HandlerMethod handlerMethod, HttpServletRequest request) {
        log.error("MainExceptionHandler\n", e);
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(functionCommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ErrorResponse> StatusRuntimeExceptionHandler(StatusRuntimeException e, HandlerMethod handlerMethod, HttpServletRequest request) {
        log.error("StatusRuntimeExceptionHandler\n", e);
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(functionCommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"))
                .status(e.getStatus().getCode() + "")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
