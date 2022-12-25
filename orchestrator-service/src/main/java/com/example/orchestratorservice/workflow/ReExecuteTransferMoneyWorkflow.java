package com.example.orchestratorservice.workflow;

import com.example.orchestratorservice.model.TransactionHistory;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ReExecuteTransferMoneyWorkflow {

    @WorkflowMethod
    void reExecuteTransferMoneyWorkflow(TransactionHistory transactionHistory);
}
