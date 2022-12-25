package com.example.orchestratorservice.worker;

import com.example.orchestratorservice.activities.TransferMoneyCompletedActivity;
import com.example.orchestratorservice.enums.TaskQueue;
import com.example.orchestratorservice.workflow.WorkflowOrchestratorClient;
import com.example.orchestratorservice.workflow.impl.TransferMoneyWorkflowImpl;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
public class TransferMoneyWorker {
    private final TransferMoneyCompletedActivity transferMoneyCompletedActivity;

    private final WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void createWorker() {

        log.info("Registering Transfer Money Worker..");

        var workerOptions = WorkerOptions.newBuilder().build();

        var workflowClient = workflowOrchestratorClient.getWorkflowClient();
        WorkflowImplementationOptions workflowImplementationOptions =
                WorkflowImplementationOptions.newBuilder()
                        .setFailWorkflowExceptionTypes(NullPointerException.class)
                        .build();

        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker =
                workerFactory.newWorker(
                        TaskQueue.TRANSFER_MONEY_WORKFLOW_TASK_QUEUE.name(), workerOptions);

        // Can be called multiple times
        worker.registerWorkflowImplementationTypes(
                workflowImplementationOptions, TransferMoneyWorkflowImpl.class);

        worker.registerActivitiesImplementations(transferMoneyCompletedActivity);

        workerFactory.start();

        log.info("Registering Transfer Money Worker..");
    }
}
