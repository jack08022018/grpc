package com.example.receiverservice.repository;

import com.example.receiverservice.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    Optional<TransactionEntity> findByTransactionId(String transactionId);
    boolean existsByTransactionId(String transactionId);
}
