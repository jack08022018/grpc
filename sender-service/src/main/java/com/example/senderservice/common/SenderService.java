//package com.example.senderservice.common;
//
//import com.sender.SenderServiceGrpc;
//import com.sender.TransactionRequest;
//import com.sender.TransactionResponse;
//import io.grpc.stub.StreamObserver;
//import net.devh.boot.grpc.server.service.GrpcService;
//
//import java.util.concurrent.TimeUnit;
//
//@GrpcService
//public class SenderService extends SenderServiceGrpc.SenderServiceImplBase {
//    public void deduct(TransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
//        TransactionResponse response = TransactionResponse.newBuilder().build();
////        int a = 1/0;
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//}