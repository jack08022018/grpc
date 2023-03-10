//package com.example.receiverservice.service;
//
//import com.example.receiverservice.entity.FlagEntity;
//import com.example.receiverservice.entity.TransactionEntity;
//import com.example.receiverservice.entity.UserEntity;
//import com.example.receiverservice.repository.FlagRepository;
//import com.example.receiverservice.repository.TransactionRepository;
//import com.example.receiverservice.repository.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.grpc.Metadata;
//import io.grpc.Status;
//import io.grpc.StatusRuntimeException;
//import io.grpc.stub.StreamObserver;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.devh.boot.grpc.server.service.GrpcService;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@GrpcService
//@RequiredArgsConstructor
//public class ReceiverServiceImpl extends ReceiveServiceGrpc.ReceiveServiceImplBase {
//    final ObjectMapper customObjectMapper;
//    final UserRepository userRepository;
//    final TransactionRepository transactionRepository;
//    final FlagRepository flagRepository;
//
//    private StatusRuntimeException getException(String message) {
//        return Status.INVALID_ARGUMENT
//                .withDescription(message)
//                .asRuntimeException(new Metadata());
//    }
//
//    @Override
//    @Transactional
//    public void credit(ReceiveRequest dto, StreamObserver<ReceiveResponse> responseObserver) {
//        ReceiveResponse response = ReceiveResponse.newBuilder().build();
//        FlagEntity flag = flagRepository.findFlag();
//        if(flag.getStatus().equals("TIMEOUT")) {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        String accountId = dto.getAccountId();
//        UserEntity user = userRepository.findByAccountId(accountId);
//        if(user == null) {
//            responseObserver.onError(getException("User receiver not found"));
//            responseObserver.onCompleted();
//        }
//        String transactionId = dto.getTransactionId();
//        if(!transactionRepository.existsByTransactionId(transactionId)) {
//            Long ballance = user.getBallanceAmount();
//            Long credit = dto.getCreditAmount();
//            user.setBallanceAmount(ballance + credit);
//            userRepository.save(user);
//
//            TransactionEntity transaction = new TransactionEntity(accountId, dto.getTransferId(), credit, transactionId);
//            transactionRepository.save(transaction);
//        }
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//}