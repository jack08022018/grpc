package com.order.workflow;

import com.order.activities.TransferActivities;
import com.order.dto.TransferMoneyDto;
import com.order.enumerator.TaskQueue;
import com.order.workflow.TransferMoneyWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class TransferMoneyWorkflowImpl implements TransferMoneyWorkflow {

    private final ActivityOptions options = ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setTaskQueue(TaskQueue.TRANSFER_MONEY.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final TransferActivities transferActivities = Workflow.newActivityStub(TransferActivities.class, options);

    @Override
    public void transferMoney(TransferMoneyDto dto) {
        log.info("Transfer money start: {}", Workflow.getInfo().getWorkflowId());
        log.info("Step1(Debit): Sender Service Deduct Money...");
        try {
            transferActivities.deduct(dto);
        } catch (Exception e) {
            log.error("Sender Service Deduct Money Failed..." + e.getMessage());
        }

//        logger.info("Step2(Credit): Receiver Service Update Money...");
//        boolean isFailed = false;
//        try {
//            receiverServiceActivity.updateMoney(convertToReceiverRequestDto(transferMoneyRequestDTO, transactionHistory.getId()));
//        } catch (Exception e) {
//            if (e.getCause() != null && e.getCause().getMessage() !=null && e.getCause().getMessage().contains("Credit Money Timeout")) {
//                logger.info("Step4(Timeout): Receiver Service Update Money Timeout...", e.getMessage());
//                // Save transaction history with status timeout
//                transferMoneyCompletedActivity.updateTransactionMoneyForReceiverUserTimeout(transactionHistory);
//            } else {
//                logger.info("Step3(Refund): Receiver Service Update Money Failed..." + e.getMessage());
//                // Save transaction history
//                transferMoneyCompletedActivity.updateTransactionMoForReceiverMoneyUserFail(transactionHistory);
//                // Rollback money of sender user
//                senderServiceActivity.refundMoney(convertToSenderRequestDto(transferMoneyRequestDTO, transactionHistory.getId()));
//            }
//            isFailed = true;
//        }
    }
}
