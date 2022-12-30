package com.order.activities.impl;

import com.order.activities.TransferActivities;
import com.order.adapter.SenderService;
import com.order.dto.TransferMoneyDto;
import com.sender.TransactionRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferActivitiesImpl implements TransferActivities {
    private final SenderService senderService;

    public TransferActivitiesImpl(SenderService senderService) {
        this.senderService = senderService;
    }

    @Override
    public void deduct(TransferMoneyDto dto) {
        log.info("Start Deduct Money Activity");
//        SenderServiceGrpc.SenderServiceBlockingStub senderServiceBlockingStub = grpcChannelService.getGrpcStubSender();
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount().longValue())
                .build();
        var response = senderService.deduct(request);
        log.info("End Deduct Money Activity");
    }

    @Override
    public void refund(TransferMoneyDto dto) {

    }

    @Override
    public void credit(TransferMoneyDto dto) {

    }
}
