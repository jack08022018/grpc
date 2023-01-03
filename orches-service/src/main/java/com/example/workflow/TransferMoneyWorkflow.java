package com.example.workflow;

import com.example.dto.TransferMoneyDto;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TransferMoneyWorkflow {
    @WorkflowMethod
    void transferMoney(TransferMoneyDto dto);

}
