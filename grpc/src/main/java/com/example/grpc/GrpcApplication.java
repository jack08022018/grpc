package com.example.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcApplication.class, args);
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        new SpringApplicationBuilder(GrpcApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//
//        Server server = ServerBuilder.forPort(8008)
//                .addService(new HelloServiceImpl())
//                .build();
//        server.start();
//        System.out.println("Server Started at " + server.getPort());
//        server.awaitTermination();
//    }

}
