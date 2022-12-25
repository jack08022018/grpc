package com.example.orchestratorservice.worker;

import com.example.orchestratorservice.activities.SenderServiceActivity;
import com.example.orchestratorservice.enums.TaskQueue;
import com.example.orchestratorservice.workflow.WorkflowOrchestratorClient;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
public class SenderServiceWorker {

    private final SenderServiceActivity senderServiceActivity;

    private final WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void createWorker() {

        log.info("Registering Sender Service Worker..");

        var workerOptions = WorkerOptions.newBuilder().build();

        var workflowClient = workflowOrchestratorClient.getWorkflowClient();

        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker =
                workerFactory.newWorker(
                        TaskQueue.SENDER_ACTIVITY_WORKFLOW_QUEUE.name(), workerOptions);

        worker.registerActivitiesImplementations(senderServiceActivity);

        workerFactory.start();

        log.info("Registering Sender Service Worker..");
    }
}
