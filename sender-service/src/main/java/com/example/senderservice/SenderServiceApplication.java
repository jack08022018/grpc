package com.example.senderservice;

import com.example.senderservice.entity.UserEntity;
import com.example.senderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class SenderServiceApplication implements CommandLineRunner {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(SenderServiceApplication.class, args);

//        new SpringApplicationBuilder(SenderServiceApplication.class)
//            .web(WebApplicationType.NONE)
//            .run(args);

//        Server server = ServerBuilder.forPort(9091).addService(new HelloServiceImpl()).build();
//        server.start();
//        System.out.println("Server Started at " + server.getPort());
//        server.awaitTermination();
    }

    final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        UserEntity user1 = new UserEntity("SEN001", "Sender 1", 10000000L);
        UserEntity user2 = new UserEntity("SEN002", "Sender 2", 10000000L);
        userRepository.save(user1);
        userRepository.save(user2);
    }

}
