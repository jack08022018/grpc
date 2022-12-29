package com.order.service.impl;

import com.order.activities.TransferActivities;
import com.order.activities.impl.TransferActivitiesImpl;
import com.order.dto.TransferMoneyDto;
import com.order.enumerator.TaskQueue;
import com.order.service.ApiService;
import com.order.workflow.TransferMoneyWorkflow;
import com.order.workflow.impl.TransferMoneyWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    final WorkflowClient workflowClientCustom;

    @Override
    public void transferMoneyWorkflow(TransferMoneyDto dto) {
        dto.setTransactionId(String.valueOf(UUID.randomUUID()));
        TransferMoneyWorkflow flow = workflowClientCustom.newWorkflowStub(
            TransferMoneyWorkflow.class,
            WorkflowOptions.newBuilder()
                .setWorkflowId(TaskQueue.TRANSFER_MONEY.name() + "_" + dto.getTransactionId())
                .setTaskQueue(TaskQueue.TRANSFER_MONEY.name())
                .build());
        flow.transferMoney(dto);
//        WorkflowClient.start(flow::transferMoney, dto);
    }
}
