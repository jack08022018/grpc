package com.example.orchestratorservice.activities.impl;

import com.example.orchestratorservice.activities.ReceiverServiceActivity;
import com.example.orchestratorservice.dto.TransferMoneyReceiverRequestDto;
import com.example.orchestratorservice.exceptions.TimeoutException;
import com.example.orchestratorservice.service.GrpcChannelService;
import com.receive.ReceiveRequest;
import com.receive.ReceiveServiceGrpc;
import com.sender.SenderServiceGrpc;
import feign.RetryableException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiverServiceActivityImpl implements ReceiverServiceActivity {

    final GrpcChannelService grpcChannelService;

    @Override
    public void updateMoney(TransferMoneyReceiverRequestDto dto) throws TimeoutException {
        log.info("Update money for user receiver", true);
        ReceiveServiceGrpc.ReceiveServiceBlockingStub stub = grpcChannelService.getGrpcStubReceiver();
        try {
            ReceiveRequest request = ReceiveRequest.newBuilder()
                    .setTransactionId(dto.getTransactionId())
                    .setAccountId(dto.getAccountId())
                    .setTransferId(dto.getTransferId())
                    .setCreditAmount(dto.getCreditAmount().longValue())
                    .build();
            stub.credit(request);
//            var updateMoneyReceiverUserResponse = receiverUserClient.updateMoney(dto);
//        } catch (Exception e) {
//            if (e instanceof RetryableException || e instanceof SocketTimeoutException) {
//                throw new TimeoutException("Credit Money Timeout");
//            } else {
//                throw e;
//            }
        }catch (StatusRuntimeException e) {
            if(e.getStatus().getCode() == Status.DEADLINE_EXCEEDED.getCode()) {
                throw new TimeoutException("Credit Money Timeout");
            }
            throw e;
        }


    }
}
