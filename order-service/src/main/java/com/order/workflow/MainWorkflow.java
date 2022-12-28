package com.order.workflow;

import com.order.dto.TransferMoneyDto;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface MainWorkflow {

    @WorkflowMethod
    void transferMoney(TransferMoneyDto dto);
}
