package com.order.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Setter
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class OrderServiceAppConfig {

//    final SenderServiceActivity senderServiceActivity;
//    final WorkflowClient workflowClient;
//
//    @Bean
//    public SenderServiceWorker senderServiceWorker() {
//        return new SenderServiceWorker(senderServiceActivity, workflowClient);
//    }

}
