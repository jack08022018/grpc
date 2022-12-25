package com.example.orchestratorservice.activities.impl;

import com.example.orchestratorservice.activities.SenderServiceActivity;
import com.example.orchestratorservice.dto.TransferMoneySenderRequestDto;
import com.example.orchestratorservice.service.GrpcChannelService;
import com.sender.SenderServiceGrpc;
import com.sender.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SenderServiceActivityImpl implements SenderServiceActivity {

    final GrpcChannelService grpcChannelService;

    @Override
    public void deductMoney(TransferMoneySenderRequestDto dto) {
        log.info("Deduct Money Activity: ");
        SenderServiceGrpc.SenderServiceBlockingStub stub = grpcChannelService.getGrpcStubSender();
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount().longValue())
                .build();
        stub.deduct(request);
//        var res = senderUserClient.deductMoney(transferMoneyReceiverRequestDto);

    }

    @Override
    public void refundMoney(TransferMoneySenderRequestDto dto) {
        log.info("Refund Money Activity: ");
//        var response = senderUserClient.refund(transferMoneyReceiverRequestDto);
        SenderServiceGrpc.SenderServiceBlockingStub stub = grpcChannelService.getGrpcStubSender();
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount().longValue())
                .build();
        stub.deduct(request);
    }
}
