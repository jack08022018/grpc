package com.order.service.impl;

import com.order.dto.TransferMoneyDto;
import com.order.enumerator.TaskQueue;
import com.order.service.ApiService;
import com.order.workflow.TransferMoneyWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    final WorkflowClient workflowClient;
    final WorkflowOptions customerWorkflowOptions;

    @Override
    public void transferMoneyWorkflow(TransferMoneyDto dto) {
        dto.setTransactionId(String.valueOf(UUID.randomUUID()));
        var lmid = RandomStringUtils.randomAlphabetic(6);
        TransferMoneyWorkflow flow = workflowClient.newWorkflowStub(
                TransferMoneyWorkflow.class,
                customerWorkflowOptions.toBuilder().setWorkflowId(lmid).build()
        );
        flow.transferMoney(dto);
//        WorkflowClient.start(flow::transferMoney, dto);
    }
}
