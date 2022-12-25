package com.example.orchestratorservice.repository;

import com.example.orchestratorservice.enums.TransferMoneyStatus;
import com.example.orchestratorservice.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String> {

    List<TransactionHistory> findByTransferMoneyStatus(TransferMoneyStatus transferMoneyStatus);
}
