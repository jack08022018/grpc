package com.example.activities;

import com.example.adapter.SenderAdapter;
import com.example.dto.TransferMoneyDto;
import com.sender.HelloRequest;
import com.sender.TransactionRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferActivitiesImpl implements TransferActivities {
    private final SenderAdapter senderAdapter;

    public TransferActivitiesImpl(SenderAdapter senderAdapter) {
        this.senderAdapter = senderAdapter;
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
        var response = senderAdapter.deduct(request);
        log.info("End Deduct Money Activity");
    }

    @Override
    public void refund(TransferMoneyDto dto) {

    }

    @Override
    public void credit(TransferMoneyDto dto) {

    }

    @Override
    public String hello(String input) {
        HelloRequest request = HelloRequest.newBuilder().setInput(input).build();
        return senderAdapter.hello(request).getOutput();
    }
}
