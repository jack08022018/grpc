package com.example.senderservice.service;

import com.example.senderservice.entity.TransactionEntity;
import com.example.senderservice.entity.UserEntity;
import com.example.senderservice.repository.TransactionRepository;
import com.example.senderservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sender.*;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class SenderService extends SenderServiceGrpc.SenderServiceImplBase {
    final ObjectMapper customObjectMapper;
    final UserRepository userRepository;
    final TransactionRepository transactionRepository;

    private StatusRuntimeException getException(String message) {
        return Status.INVALID_ARGUMENT
                .withDescription(message)
                .asRuntimeException(new Metadata());
    }

    @Override
    @Transactional
    public void deduct(TransactionRequest dto, StreamObserver<TransactionResponse> responseObserver) {
        TransactionResponse response = TransactionResponse.newBuilder().build();

        String accountId = dto.getAccountId();
        UserEntity user = userRepository.findByAccountId(accountId);
        if(user == null) {
            responseObserver.onError(getException("User sender not found"));
            responseObserver.onCompleted();
        }
        Long ballance = user.getBallanceAmount();
        Long debit = dto.getDebitAmount();
        if(ballance.compareTo(debit) < 0) {
            responseObserver.onError(getException("Sender not enough money"));
            responseObserver.onCompleted();
        }
        user.setBallanceAmount(ballance - debit);
        userRepository.save(user);
        TransactionEntity transaction = new TransactionEntity(accountId, dto.getRecipientId(), debit, dto.getTransactionId());
        transactionRepository.save(transaction);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void refund(TransactionRequest dto, StreamObserver<TransactionResponse> responseObserver) {
        TransactionResponse response = TransactionResponse.newBuilder().build();

        Optional<TransactionEntity> transactionOptional = transactionRepository
                .findByTransactionId(dto.getTransactionId());
        if(!transactionOptional.isPresent()) {
            responseObserver.onError(getException("Transaction sender not found"));
            responseObserver.onCompleted();
        }
        TransactionEntity transaction = transactionOptional.get();
        String accountId = dto.getAccountId();
        String recipientId = dto.getRecipientId();
        Long debitAmount = dto.getDebitAmount();
        if(!accountId.equals(transaction.getAccountId())) {
            responseObserver.onError(getException("AccountId sender not correct"));
            responseObserver.onCompleted();
        }
        if(!recipientId.equals(transaction.getRecipientId())) {
            responseObserver.onError(getException("RecipientId not correct"));
            responseObserver.onCompleted();
        }
        if(debitAmount.compareTo(transaction.getDebitAmount()) != 0) {
            responseObserver.onError(getException("Debit Amount not correct"));
            responseObserver.onCompleted();
        }
        UserEntity user = userRepository.findByAccountId(accountId);
        user.setBallanceAmount(user.getBallanceAmount() + debitAmount);
        userRepository.save(user);

        transaction.setStatus(com.example.senderservice.enumerator.Status.REFUND);
        transactionRepository.save(transaction);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void hello(HelloRequest dto, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder()
                .setOutput("Hello: " + dto.getInput())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}