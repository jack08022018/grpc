package com.order.config;

import com.order.activities.TransferActivities;
import com.order.activities.impl.TransferActivitiesImpl;
import com.order.adapter.SenderService;
import com.order.adapter.SenderServiceImpl;
import com.order.enumerator.TemporalTaskQueue;
import com.order.workflow.TransferMoneyWorkflowImpl;
import com.uber.m3.tally.RootScopeBuilder;
import com.uber.m3.util.ImmutableMap;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class WorkflowClientConfig {
    @Value("${temporal.server:127.0.0.1:7233}")
    String temporalDNS;
    @Value("${temporal.namespace}")
    String namespace;

    @Autowired
    private SenderService senderService;

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

        var workflowOptions = WorkflowServiceStubsOptions.newBuilder()
                .setTarget(temporalDNS)
                .build();
//        var workflowClient = WorkflowClient.newInstance(
//                WorkflowServiceStubs.newServiceStubs(workflowOptions),
//                WorkflowClientOptions.newBuilder()
//                        .setNamespace(namespace)
//                        .build()
//        );
        var workflowClient = WorkflowClient.newInstance(
                WorkflowServiceStubs.newInstance(WorkflowServiceStubsOptions.newBuilder().setTarget(temporalDNS).build()),
                WorkflowClientOptions.newBuilder()
                        .setNamespace("default")
                        .build()
        );
        var transferActivities = new TransferActivitiesImpl(senderService);
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
