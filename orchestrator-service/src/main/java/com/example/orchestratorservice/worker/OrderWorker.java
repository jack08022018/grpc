package com.example.orchestratorservice.worker;

import com.example.orchestratorservice.activities.CompleteOrderActivity;
import com.example.orchestratorservice.enums.TaskQueue;
import com.example.orchestratorservice.workflow.WorkflowOrchestratorClient;
import com.example.orchestratorservice.workflow.impl.OrderFulfillmentWorkflowImpl;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
public class OrderWorker {

    private final CompleteOrderActivity completeOrderActivity;
    private final WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void createWorker() {

        log.info("Registering worker..");

        var workerOptions = WorkerOptions.newBuilder().build();

        var workflowClient = workflowOrchestratorClient.getWorkflowClient();
        WorkflowImplementationOptions workflowImplementationOptions =
                WorkflowImplementationOptions.newBuilder()
                        .setFailWorkflowExceptionTypes(NullPointerException.class)
                        .build();

        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker =
                workerFactory.newWorker(
                        TaskQueue.ORDER_FULFILLMENT_WORKFLOW_TASK_QUEUE.name(), workerOptions);

        // Can be called multiple times
        worker.registerWorkflowImplementationTypes(
                workflowImplementationOptions, OrderFulfillmentWorkflowImpl.class);

        worker.registerActivitiesImplementations(completeOrderActivity);

        workerFactory.start();

        log.info("Registering order worker..");
    }
}


