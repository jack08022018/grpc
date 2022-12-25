package com.example.orchestratorservice.worker;

import com.example.orchestratorservice.activities.ReceiverServiceActivity;
import com.example.orchestratorservice.enums.TaskQueue;
import com.example.orchestratorservice.workflow.WorkflowOrchestratorClient;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
public class ReceiverServiceWorker {

    private final ReceiverServiceActivity receiverServiceActivity;

    private final WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void createWorker() {

        log.info("Registering Receiver Service Worker..");

        var workerOptions = WorkerOptions.newBuilder().build();

        var workflowClient = workflowOrchestratorClient.getWorkflowClient();

        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker =
                workerFactory.newWorker(
                        TaskQueue.RECEIVER_ACTIVITY_WORKFLOW_QUEUE.name(), workerOptions);

        worker.registerActivitiesImplementations(receiverServiceActivity);

        workerFactory.start();

        log.info("Registering Receiver Service Worker..");
    }
}
