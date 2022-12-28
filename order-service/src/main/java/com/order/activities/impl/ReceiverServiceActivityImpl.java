package com.order.activities.impl;

import com.order.activities.ReceiverServiceActivity;
import com.order.activities.SenderServiceActivity;
import com.order.config.GrpcChannelService;
import com.order.dto.TransferMoneyDto;
import com.receive.ReceiveRequest;
import com.receive.ReceiveServiceGrpc;
import com.sender.SenderServiceGrpc;
import com.sender.TransactionRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiverServiceActivityImpl implements ReceiverServiceActivity {

    final GrpcChannelService grpcChannelService;

    @Override
    public void credit(TransferMoneyDto dto) throws TimeoutException {
        log.info("Update money for user receiver", true);
        ReceiveServiceGrpc.ReceiveServiceBlockingStub stubReceiver = grpcChannelService.getGrpcStubReceiver();
        try {
            ReceiveRequest request = ReceiveRequest.newBuilder()
                    .setTransactionId(dto.getTransactionId())
                    .setAccountId(dto.getAccountId())
                    .setTransferId(dto.getTransferId())
                    .setCreditAmount(dto.getCreditAmount().longValue())
                    .build();
            stubReceiver.credit(request);
        }catch (StatusRuntimeException e) {
            if(e.getStatus().getCode() == Status.DEADLINE_EXCEEDED.getCode()) {
                throw new TimeoutException("Credit Money Timeout");
            }
            throw e;
        }
    }

}
