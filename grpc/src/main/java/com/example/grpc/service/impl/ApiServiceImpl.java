package com.example.grpc.service.impl;

import com.example.grpc.dto.TransactionDto;
import com.example.grpc.service.ApiService;
import com.sender.SenderServiceGrpc;
import com.sender.TransactionRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ApiServiceImpl implements ApiService {
    @Value("${sender.url}")
    String SENDER_URL;

    @Value("${sender.port}")
    Integer SENDER_PORT;

    private SenderServiceGrpc.SenderServiceBlockingStub getGrpcStub() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(SENDER_URL, SENDER_PORT)
                .usePlaintext()
                .build();
        return SenderServiceGrpc
                .newBlockingStub(channel)
                .withDeadlineAfter(5, TimeUnit.SECONDS);
    }

    @Override
    public void deduct(TransactionDto dto) {
        TransactionRequest request = TransactionRequest.newBuilder()
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount())
                .build();
        try {
            getGrpcStub().deduct(request);
        }catch (StatusRuntimeException e) {
            if(e.getStatus().getCode() == Status.DEADLINE_EXCEEDED.getCode()) {
                System.out.println("\n" + e.getStatus().getCode());
            }
            throw e;
        }
    }
}
