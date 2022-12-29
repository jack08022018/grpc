package com.order.activities.impl;

import com.order.activities.TransferActivities;
//import com.order.config.GrpcChannelService;
import com.order.config.GrpcChannelService;
import com.order.dto.TransferMoneyDto;
//import com.sender.SenderServiceGrpc;
//import com.sender.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferActivitiesImpl implements TransferActivities {
    final GrpcChannelService grpcChannelService;

//    @GrpcClient("sender-service")
//    private SenderServiceGrpc.SenderServiceBlockingStub senderServiceBlockingStub;

    @Override
    public void deduct(TransferMoneyDto dto) {
        log.info("Start Deduct Money Activity");
//        SenderServiceGrpc.SenderServiceBlockingStub senderServiceBlockingStub = grpcChannelService.getGrpcStubSender();
//        TransactionRequest request = TransactionRequest.newBuilder()
//                .setTransactionId(dto.getTransactionId())
//                .setAccountId(dto.getAccountId())
//                .setRecipientId(dto.getRecipientId())
//                .setDebitAmount(dto.getDebitAmount().longValue())
//                .build();
//        var response = senderServiceBlockingStub.deduct(request);
        log.info("End Deduct Money Activity");
    }

    @Override
    public void refund(TransferMoneyDto dto) {

    }

    @Override
    public void credit(TransferMoneyDto dto) {

    }
}
