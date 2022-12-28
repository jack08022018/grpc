package com.order.activities.impl;

import com.order.activities.TransferActivities;
import com.order.config.GrpcChannelService;
import com.order.dto.TransferMoneyDto;
import com.sender.SenderServiceGrpc;
import com.sender.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferActivitiesImpl implements TransferActivities {
    final GrpcChannelService grpcChannelService;

    @Override
    public void deduct(TransferMoneyDto dto) {
        log.info("Start Deduct Money Activity");
        SenderServiceGrpc.SenderServiceBlockingStub stubSender = grpcChannelService.getGrpcStubSender();
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount().longValue())
                .build();
        var response = stubSender.deduct(request);
        log.info("End Deduct Money Activity");
    }

    @Override
    public void refund(TransferMoneyDto dto) {

    }

    @Override
    public void credit(TransferMoneyDto dto) {

    }
}
