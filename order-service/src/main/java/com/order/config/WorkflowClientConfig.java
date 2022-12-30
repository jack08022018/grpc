package com.order.config;

import com.order.activities.TransferActivities;
import com.order.activities.impl.TransferActivitiesImpl;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class WorkflowClientConfig {
    @Value("${temporal.server:127.0.0.1:7233}")
    String temporalDNS;
    @Value("${temporal.namespace}")
    String namespace;
    @Value("${temporal.defaultActivityStartToCloseTimeout}")
    Long defaultActivityStartToCloseTimeout;
    @Value("${temporal.defaultActivityMaximumRetryAttempts:9999}")
    Integer defaultActivityMaximumRetryAttempts;

    //default Local Activity
    @Value("${temporal.defaultLocalActivityStartToCloseTimeout}")
    Long defaultLocalActivityStartToCloseTimeout;
    @Value("${temporal.defaultLocalActivityMaximumRetryAttempts:9999}")
    Integer defaultLocalActivityMaximumRetryAttempts;
    @Value("${temporal.defaultLocalActivityRetryInterval:10}")
    Integer defaultLocalActivityRetryInterval;

    //getInfo
    @Value("${temporal.getInfoStartToCloseTimeout}")
    Long getInfoStartToCloseTimeout;
    @Value("${temporal.getInfoMaximumAttempts}")
    Integer getInfoMaximumAttempts;
    @Value("${temporal.isUseSsl:false}")
    Boolean isUseSsl;
    @Value("${temporal.worker.maxConcurrentActivityTaskPollers:5}")
    Integer maxConcurrentActivityTaskPollers;
    @Value("${temporal.worker.maxConcurrentWorkflowTaskPollers:2}")
    Integer maxConcurrentWorkflowTaskPollers;
    @Value("${temporal.worker.maxConcurrent Activity ExecutionSize:200}")
    Integer maxConcurrentActivityExecutionSize;
    @Value("${temporal.worker.MaxConcurrentWorkflowTaskExecutionSize:200}")
    Integer maxConcurrentWorkflowTaskExecutionSize;
    @Value("${temporal.worker.maxConcurrentLocalActivityExecutionSize:200}")
    Integer maxConcurrentLocalActivityExecutionSize;
    @Value("${temporal.workerFactory.maxWorkflowThreadCount:600}")
    Integer maxWorkflowThreadCount;
    @Value("${temporal.workerFactory.workflowHostLocalPollThreadCount:5}")
    Integer workflowHostLocalPollThreadCount;
    @Value("${temporal.worker.maxWorkerActivitiesPerSecond:0}")
    double maxWorkerActivitiesPerSecond;
    @Value("${temporal.worker.maxTaskQueueActivitiesPerSecond:0}")
    double maxTaskQueueActivitiesPerSecond;
    @Value("${temporal.prometheus.port:9090}")
    Integer prometheusPort;
    @Value("${temporal.prometheus.sleep:30000}")
    Long prometheusSleep;

    @Bean
    public WorkflowClient buildClient(SenderServiceImpl senderService) {
        var workflowOptions = WorkflowServiceStubsOptions.newBuilder()
                .setTarget(temporalDNS)
                .build();
        var workerFactoryOptions = WorkerFactoryOptions.newBuilder ()
                .build();
        WorkerOptions workerOptions = WorkerOptions.newBuilder().build();
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
                WorkflowServiceStubs.newInstance(workflowOptions),
                WorkflowClientOptions.newBuilder()
                        .setNamespace(namespace)
                        .build()
        );
        var transferActivities = new TransferActivitiesImpl(senderService);
        var factory = WorkerFactory.newInstance(workflowClient, workerFactoryOptions);
        var senderWorker = factory.newWorker(TemporalTaskQueue.CREATE.toString(), workerOptions);
        senderWorker.registerWorkflowImplementationTypes(workflowOption, TransferMoneyWorkflowImpl.class);
        senderWorker.registerActivitiesImplementations(transferActivities);

        factory.start();
        return workflowClient;
    }

}
