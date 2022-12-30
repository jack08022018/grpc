//package com.order.config;
//
//import com.sender.SenderServiceGrpc;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//public class GrpcChannelService {
//    @Value("${sender.url}")
//    String SENDER_URL;
//
//    @Value("${sender.port}")
//    Integer SENDER_PORT;
//
//    @Value("${receiver.url}")
//    String RECEIVER_URL;
//
//    @Value("${receiver.port}")
//    Integer RECEIVER_PORT;
//
//    public SenderServiceGrpc.SenderServiceBlockingStub getGrpcStubSender() {
//        ManagedChannel channel = ManagedChannelBuilder
//                .forAddress(SENDER_URL, SENDER_PORT)
//                .usePlaintext()
//                .build();
//        return SenderServiceGrpc
//                .newBlockingStub(channel)
//                .withDeadlineAfter(30, TimeUnit.SECONDS);
//    }
//
////    public ReceiveServiceGrpc.ReceiveServiceBlockingStub getGrpcStubReceiver() {
////        ManagedChannel channel = ManagedChannelBuilder
////                .forAddress(RECEIVER_URL, RECEIVER_PORT)
////                .usePlaintext()
////                .build();
////        return ReceiveServiceGrpc
////                .newBlockingStub(channel)
////                .withDeadlineAfter(6, TimeUnit.SECONDS);
////    }
//
//}
