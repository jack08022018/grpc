package com.example.receiverservice;

import com.example.receiverservice.entity.UserEntity;
import com.example.receiverservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;

@SpringBootApplication
@RequiredArgsConstructor
public class ReceiverServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReceiverServiceApplication.class, args);
    }

    final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        UserEntity user1 = new UserEntity("REC001", "Receive 1", 800000L);
        UserEntity user2 = new UserEntity("REC002", "Receive 2", 800000L);
        userRepository.save(user1);
        userRepository.save(user2);
    }
}