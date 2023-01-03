package com.example.config;

import com.example.activities.TransferActivitiesImpl;
import com.example.adapter.SenderAdapter;
import com.example.enumerator.TemporalTaskQueue;
import com.example.workflow.TransferMoneyWorkflowImpl;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class WorkflowConfig {
    @Value("${temporal.server:127.0.0.1:7233}")
    String temporalDNS;
    @Value("${temporal.namespace:temporal}")
    String namespace;

    @Autowired
    private SenderAdapter senderAdapter;

    @Bean
    @Qualifier("customerWorkflowOptions")
    WorkflowOptions customerWorkflowOptions() {
        return WorkflowOptions.newBuilder()
                .setTaskQueue(TemporalTaskQueue.CREATE.toString())
//                .setWorkflowExecutionTimeout(Duration.ofMillis(60000))
//                .setWorkflowTaskTimeout(Duration.ofMillis(1000))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setMaximumAttempts(1)
                        .build())
                .build();
    }

    @Bean
    public WorkflowClient buildClient() throws InterruptedException {
        ActivityOptions activityOptions = ActivityOptions.newBuilder ()
                .setStartToCloseTimeout(Duration.ofSeconds(9999))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setMaximumAttempts(9999)
                        .build())
                .build();
        LocalActivityOptions localActivityOptions = LocalActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(15))
                .setLocalRetryThreshold(Duration.ofSeconds (15))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setBackoffCoefficient(1)
                        .setInitialInterval(Duration.ofMillis(10))
                        .setMaximumAttempts(10)
                        .build())
                .build();
        ActivityOptions senderOptions = ActivityOptions.newBuilder ()
                .setStartToCloseTimeout(Duration.ofSeconds(5))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setMaximumAttempts(5)
                        .build())
                .build();
        Map<String, ActivityOptions> activityOptionsMap = new HashMap<>();
        activityOptionsMap.put("sender", senderOptions);
        WorkflowImplementationOptions workflowOption = WorkflowImplementationOptions.newBuilder()
                .setDefaultActivityOptions(activityOptions)
                .setActivityOptions(activityOptionsMap)
                .setDefaultLocalActivityOptions(localActivityOptions)
                .build();

        var workflowClient = WorkflowClient.newInstance(
                WorkflowServiceStubs.newInstance(WorkflowServiceStubsOptions.newBuilder().setTarget(temporalDNS).build()),
                WorkflowClientOptions.newBuilder()
                        .setNamespace("default")
                        .build()
        );
        var transferActivities = new TransferActivitiesImpl(senderAdapter);
        var factory = WorkerFactory.newInstance(workflowClient, WorkerFactoryOptions.newBuilder().build());

        WorkerOptions workerOptions = WorkerOptions.newBuilder().build();
        var senderWorker = factory.newWorker(TemporalTaskQueue.CREATE.toString(), workerOptions);
        senderWorker.registerWorkflowImplementationTypes(workflowOption, TransferMoneyWorkflowImpl.class);
        senderWorker.registerActivitiesImplementations(transferActivities);

//        TimeUnit.SECONDS.sleep(5);
        factory.start();
        return workflowClient;
    }

}
