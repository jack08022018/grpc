package com.order.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.order.enumerator.TaskQueue;
import com.order.enumerator.TemporalTaskQueue;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.Executor;

//@EnableAsync
@Configuration
@EnableScheduling
@RequiredArgsConstructor
//@ComponentScan({"com.delivery.request"})
public class BeanConfig {

    final Environment env;

//    @Bean(name = "taskExecutor")
////    @Async("specificTaskExecutor")
//    public Executor threadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10);
//        executor.setMaxPoolSize(20);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("AsyncThread::");
//        executor.initialize();
//        return executor;
//    }

    @Bean(name = "customObjectMapper")
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Bean(name = "customRestTemplate")
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(30000);
        requestFactory.setConnectTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }

//    @Bean(name = "workflowClientCustom")
//    public WorkflowClient getWorkflowClient() {
//        var options = WorkflowServiceStubsOptions.newBuilder()
//                .setTarget(env.getProperty("workflow.client.url"))
//                .build();
//        var serviceStubs = WorkflowServiceStubs.newServiceStubs(options);
//        return WorkflowClient.newInstance(serviceStubs);
//    }

    @Bean
    @Qualifier("customerWorkflowOptions")
    WorkflowOptions customerWorkflowOptions() {
        return WorkflowOptions.newBuilder()
            .setTaskQueue(TemporalTaskQueue.CREATE.toString())
            .setWorkflowExecutionTimeout(Duration.ofMillis(60000))
            .setWorkflowTaskTimeout(Duration.ofMillis(1000))
            .setRetryOptions(RetryOptions.newBuilder()
                        .setMaximumAttempts(1)
                        .build())
            .build();
    }

//    WorkflowOptions.newBuilder()
//            .setWorkflowId(TaskQueue.TRANSFER_MONEY.name() + "_" + dto.getTransactionId())
//            .setTaskQueue(TaskQueue.TRANSFER_MONEY.name())
//            .build());
}
