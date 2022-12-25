package com.example.orchestratorservice.service.impl;

import com.example.orchestratorservice.dto.TransactionDto;
import com.example.orchestratorservice.service.ApiService;
import com.receive.ReceiveRequest;
import com.receive.ReceiveServiceGrpc;
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

    @Value("${receiver.url}")
    String RECEIVER_URL;

    @Value("${receiver.port}")
    Integer RECEIVER_PORT;

    private SenderServiceGrpc.SenderServiceBlockingStub getGrpcStubSender() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(SENDER_URL, SENDER_PORT)
                .usePlaintext()
                .build();
        return SenderServiceGrpc
                .newBlockingStub(channel)
                .withDeadlineAfter(30, TimeUnit.SECONDS);
    }

    private ReceiveServiceGrpc.ReceiveServiceBlockingStub getGrpcStubReceiver() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(RECEIVER_URL, RECEIVER_PORT)
                .usePlaintext()
                .build();
        return ReceiveServiceGrpc
                .newBlockingStub(channel)
                .withDeadlineAfter(6, TimeUnit.SECONDS);
    }

    @Override
    public void deduct(TransactionDto dto) {
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount())
                .build();
        getGrpcStubSender().deduct(request);
    }

    @Override
    public void refund(TransactionDto dto) {
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount())
                .build();
        getGrpcStubSender().refund(request);
    }

    @Override
    public void credit(TransactionDto dto) {
        ReceiveRequest request = ReceiveRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setTransferId(dto.getTransferId())
                .setCreditAmount(dto.getCreditAmount())
                .build();
        try {
            getGrpcStubReceiver().credit(request);
        }catch (StatusRuntimeException e) {
            if(e.getStatus().getCode() == Status.DEADLINE_EXCEEDED.getCode()) {
                System.out.println("\n" + e.getStatus().getCode());
            }
            throw e;
        }
    }
}
