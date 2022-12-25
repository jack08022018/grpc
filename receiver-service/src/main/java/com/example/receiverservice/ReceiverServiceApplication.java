package com.example.receiverservice;

import com.example.receiverservice.entity.FlagEntity;
import com.example.receiverservice.entity.UserEntity;
import com.example.receiverservice.repository.FlagRepository;
import com.example.receiverservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.math.BigInteger;

@SpringBootApplication
@RequiredArgsConstructor
public class ReceiverServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReceiverServiceApplication.class, args);

//        new SpringApplicationBuilder(ReceiverServiceApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
    }

    final UserRepository userRepository;
    final FlagRepository flagRepository;

    @Override
    public void run(String... args) throws Exception {
        UserEntity user1 = new UserEntity("REC001", "Receive 1", 800000L);
        UserEntity user2 = new UserEntity("REC002", "Receive 2", 800000L);
        userRepository.save(user1);
        userRepository.save(user2);

        FlagEntity flagEntity = FlagEntity.builder()
                .status("NORMAL")
                .build();
        flagRepository.save(flagEntity);
    }
}