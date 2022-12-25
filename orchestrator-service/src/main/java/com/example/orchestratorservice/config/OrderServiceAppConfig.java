package com.example.orchestratorservice.config;

import com.example.orchestratorservice.activities.CompleteOrderActivity;
import com.example.orchestratorservice.activities.ReceiverServiceActivity;
import com.example.orchestratorservice.activities.SenderServiceActivity;
import com.example.orchestratorservice.activities.TransferMoneyCompletedActivity;
import com.example.orchestratorservice.activities.impl.CompleteOrderActivityImpl;
import com.example.orchestratorservice.activities.impl.TransferMoneyCompletedActivityImpl;
import com.example.orchestratorservice.worker.*;
import com.example.orchestratorservice.workflow.WorkflowOrchestrator;
import com.example.orchestratorservice.workflow.WorkflowOrchestratorClient;
import com.example.orchestratorservice.workflow.impl.WorkflowOrchestratorImpl;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Setter
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class OrderServiceAppConfig {

    @Bean
    public CompleteOrderActivity createPendingOrderActivity() {
        return new CompleteOrderActivityImpl();
    }

    @Bean
    public OrderWorker orderWorker() {
        return new OrderWorker(
                createPendingOrderActivity(), workflowOrchestratorClient());
    }

    @Bean
    public WorkflowOrchestratorClient workflowOrchestratorClient() {
        return new WorkflowOrchestratorClient();
    }

    @Bean
    public WorkflowOrchestrator workflowOrchestrator() {
        return new WorkflowOrchestratorImpl(workflowOrchestratorClient());
    }

    @Bean
    public TransferMoneyCompletedActivity transferMoneyCompletedActivity() {
        return new TransferMoneyCompletedActivityImpl();
    }

    @Bean
    public TransferMoneyWorker transferMoneyWorker() {
        return new TransferMoneyWorker(transferMoneyCompletedActivity(), workflowOrchestratorClient());
    }

    @Bean
    public ReExecuteTransferMoneyWorker reExecuteTransferMoneyWorker() {
        return new ReExecuteTransferMoneyWorker(transferMoneyCompletedActivity(), workflowOrchestratorClient());
    }

    final ReceiverServiceActivity receiverServiceActivity;
    final SenderServiceActivity senderServiceActivity;

    @Bean
    public ReceiverServiceWorker receiverServiceWorker() {
        return new ReceiverServiceWorker(receiverServiceActivity, workflowOrchestratorClient());
    }

    @Bean
    public SenderServiceWorker senderServiceWorker() {
        return new SenderServiceWorker(senderServiceActivity, workflowOrchestratorClient());
    }

}
