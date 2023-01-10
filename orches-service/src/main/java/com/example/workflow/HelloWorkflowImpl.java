package com.example.workflow;

import com.example.activities.HelloActivities;
import com.example.activities.TransferActivities;
import com.example.dto.TransferMoneyDto;
import com.example.enumerator.TaskQueue;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;

import java.time.Duration;

@Slf4j
public class HelloWorkflowImpl implements HelloWorkflow {

    private final ActivityOptions options = ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setTaskQueue(TaskQueue.TRANSFER_MONEY.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final HelloActivities helloActivities = Workflow.newActivityStub(HelloActivities.class, options);

    @Override
    public ModelMap executeHello(String input) {
        log.info("Step1(Debit): Sender Service Deduct Money...");
        ModelMap result = new ModelMap();
        try {
            result.put("sender: ", helloActivities.helloSender(input));
        } catch (Exception e) {
            log.error("Sender Service Deduct Money Failed..." + e.getMessage());
        }
        return result;
    }
}
