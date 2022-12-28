package com.order.activities.impl;

import com.order.activities.SenderServiceActivity;
import com.order.config.GrpcChannelService;
import com.order.dto.TransferMoneyDto;
import com.order.enumerator.TaskQueue;
import com.sender.SenderServiceGrpc;
import com.sender.TransactionRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class SenderServiceActivityImpl implements SenderServiceActivity {

    final GrpcChannelService grpcChannelService;

    @Override
    public void deductMoney(TransferMoneyDto dto) {
        log.info("Deduct Money Activity: ");
        SenderServiceGrpc.SenderServiceBlockingStub stubSender = grpcChannelService.getGrpcStubSender();
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount().longValue())
                .build();
        var response = stubSender.deduct(request);
        System.out.println(response);
//        var res = senderUserClient.deductMoney(transferMoneyReceiverRequestDto);

    }

    @Override
    public void refundMoney(TransferMoneyDto dto) {
        log.info("Refund Money Activity: ");
//        var response = senderUserClient.refund(transferMoneyReceiverRequestDto);
        SenderServiceGrpc.SenderServiceBlockingStub stubSender = grpcChannelService.getGrpcStubSender();
        TransactionRequest request = TransactionRequest.newBuilder()
                .setTransactionId(dto.getTransactionId())
                .setAccountId(dto.getAccountId())
                .setRecipientId(dto.getRecipientId())
                .setDebitAmount(dto.getDebitAmount().longValue())
                .build();
        stubSender.refund(request);
    }
}
