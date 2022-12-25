package com.example.orchestratorservice.workflow;

import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.model.Order;
import com.example.orchestratorservice.model.TransactionHistory;

import java.util.List;

public interface WorkflowOrchestrator {
    void createOrder(Order order);

    void startTransferMoneyWorkflow(TransferMoneyRequestDTO transferMoneyRequestDTO, TransactionHistory transactionHistory);
    void reExecuteTransferMoneyWorkflow(List<TransactionHistory> transactions);
}
