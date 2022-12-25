package com.example.orchestratorservice.activities.impl;

import com.example.orchestratorservice.activities.TransferMoneyCompletedActivity;
import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.enums.TransferMoneyStatus;
import com.example.orchestratorservice.model.TransactionHistory;
import com.example.orchestratorservice.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class TransferMoneyCompletedActivityImpl implements TransferMoneyCompletedActivity {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public void completedTransferMoney(TransferMoneyRequestDTO transferMoneyRequestDTO, TransactionHistory transactionHistory) {
        // Save transaction history
        // response status workflow
        transactionHistory.setTransferMoneyStatus(TransferMoneyStatus.COMPLETED);
        transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public void completedDeductMoneyOfSenderUser(TransactionHistory transactionHistory) {
        transactionHistory.setTransferMoneyStatus(TransferMoneyStatus.DEDUCTED);
        transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public void updateTransactionMoForReceiverMoneyUserFail(TransactionHistory transactionHistory) {
        transactionHistory.setTransferMoneyStatus(TransferMoneyStatus.FAILED);
        transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public void updateTransactionMoneyForReceiverUserTimeout(TransactionHistory transactionHistory) {
        transactionHistory.setTransferMoneyStatus(TransferMoneyStatus.TIMEOUT);
        transactionHistoryRepository.save(transactionHistory);
    }
}
