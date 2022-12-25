package com.example.orchestratorservice.workflow;

import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.model.TransactionHistory;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TransferMoneyWorkflow {

    @WorkflowMethod
    void startTransferMoneyWorkflow(TransferMoneyRequestDTO transferMoneyRequestDTO, TransactionHistory transactionHistory);
}
