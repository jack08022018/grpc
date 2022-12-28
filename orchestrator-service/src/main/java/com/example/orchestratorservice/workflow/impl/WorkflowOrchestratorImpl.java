package com.example.orchestratorservice.workflow.impl;

import com.example.orchestratorservice.dto.OrderDTO;
import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.enums.TaskQueue;
import com.example.orchestratorservice.model.Order;
import com.example.orchestratorservice.model.TransactionHistory;
import com.example.orchestratorservice.workflow.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class WorkflowOrchestratorImpl implements WorkflowOrchestrator {

    private final WorkflowOrchestratorClient workflowOrchestratorClient;

    @Override
    public void createOrder(Order order) {
        var orderDTO = map(order);
        var workflowClient = workflowOrchestratorClient.getWorkflowClient();
        String transactionId = UUID.randomUUID().toString();
        var orderFulfillmentWorkflow =
            workflowClient.newWorkflowStub(
                OrderFulfillmentWorkflow.class,
                WorkflowOptions.newBuilder()
                               //TODO
//                                .setWorkflowId("OrderFulfillmentWorkflow" + "-" + orderDTO.getOrderId())
                               .setWorkflowId("OrderFulfillmentWorkflow-test" + "-" + transactionId)
                               .setTaskQueue(TaskQueue.ORDER_FULFILLMENT_WORKFLOW_TASK_QUEUE.name())
                               .build());
        WorkflowClient.start(orderFulfillmentWorkflow::createOrder, orderDTO);
    }

    @Override
    public void startTransferMoneyWorkflow(TransferMoneyRequestDTO transferMoneyRequestDTO, TransactionHistory transactionHistory) throws ExecutionException, InterruptedException {
        WorkflowClient workflowClient = workflowOrchestratorClient.getWorkflowClient();

        TransferMoneyWorkflow transferMoneyWorkflow = workflowClient.newWorkflowStub(
                TransferMoneyWorkflow.class,
                WorkflowOptions.newBuilder()
                   .setWorkflowId("TransferMoneyWorkflow-" + "-" + transactionHistory.getId())
                   .setTaskQueue(TaskQueue.TRANSFER_MONEY_WORKFLOW_TASK_QUEUE.name())
                   .build());
        WorkflowClient.start(transferMoneyWorkflow::startTransferMoneyWorkflow, transferMoneyRequestDTO, transactionHistory);
//        CompletableFuture<Void> childExecution = WorkflowClient.execute(transferMoneyWorkflow::startTransferMoneyWorkflow, transferMoneyRequestDTO, transactionHistory);
//        childExecution.get();

//        WorkflowClient.execute(transferMoneyWorkflow::startTransferMoneyWorkflow, transferMoneyRequestDTO, transactionHistory);
//        Async.procedure(transferMoneyWorkflow::startTransferMoneyWorkflow, transferMoneyRequestDTO, transactionHistory);
    }

    private OrderDTO map(Order order) {
        var orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setProductId(order.getProductId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setQuantity(order.getQuantity());
        return orderDTO;
    }

    @Override
    public void reExecuteTransferMoneyWorkflow(List<TransactionHistory> transactions) {
        var workflowClient = workflowOrchestratorClient.getWorkflowClient();
        transactions.forEach(el -> execute(workflowClient, el));
    }

    private void execute(WorkflowClient workflowClient, TransactionHistory transactionHistory) {
        var reExecuteTransferMoneyWorkflow =
            workflowClient.newWorkflowStub(
                ReExecuteTransferMoneyWorkflow.class,
                WorkflowOptions.newBuilder()
                               .setWorkflowId("ReExecuteTransferMoneyWorkflow-" + "-" + transactionHistory.getId())
                               .setTaskQueue(TaskQueue.RE_EXECUTE_TRANSFER_MONEY_WORKFLOW_TASK_QUEUE.name())
                               .build());
        WorkflowClient.start(reExecuteTransferMoneyWorkflow::reExecuteTransferMoneyWorkflow, transactionHistory);
    }
}
