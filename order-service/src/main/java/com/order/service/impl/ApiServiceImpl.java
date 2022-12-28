package com.order.service.impl;

import com.order.dto.TransferMoneyDto;
import com.order.service.ApiService;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    final WorkflowClient workflowClient;

    @Override
    public void transferMoneyWorkflow(TransferMoneyDto dto) {
//        TransferMoneyWorkflow transferMoneyWorkflow = workflowClient.newWorkflowStub(
//                TransferMoneyWorkflow.class,
//                WorkflowOptions.newBuilder()
//                        .setWorkflowId("TransferMoneyWorkflow-" + "-" + transactionHistory.getId())
//                        .setTaskQueue(TaskQueue.TRANSFER_MONEY_WORKFLOW_TASK_QUEUE.name())
//                        .build());
//        WorkflowClient.start(transferMoneyWorkflow::startTransferMoneyWorkflow, transferMoneyRequestDTO, transactionHistory);

    }
}
