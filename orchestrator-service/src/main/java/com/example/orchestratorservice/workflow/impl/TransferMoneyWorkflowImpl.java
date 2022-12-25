package com.example.orchestratorservice.workflow.impl;

import com.example.orchestratorservice.activities.ReceiverServiceActivity;
import com.example.orchestratorservice.activities.SenderServiceActivity;
import com.example.orchestratorservice.activities.TransferMoneyCompletedActivity;
import com.example.orchestratorservice.dto.TransferMoneyReceiverRequestDto;
import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.dto.TransferMoneySenderRequestDto;
import com.example.orchestratorservice.enums.TaskQueue;
import com.example.orchestratorservice.model.TransactionHistory;
import com.example.orchestratorservice.workflow.TransferMoneyWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class TransferMoneyWorkflowImpl implements TransferMoneyWorkflow {

    private Logger logger = Workflow.getLogger(this.getClass().getName());

    private final LocalActivityOptions transferMoneyActivityOptions =
            LocalActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(10).build())
                    .build();

    private final ActivityOptions senderServiceActivityOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setTaskQueue(TaskQueue.SENDER_ACTIVITY_WORKFLOW_QUEUE.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final ActivityOptions receiverServiceActivityOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setTaskQueue(TaskQueue.RECEIVER_ACTIVITY_WORKFLOW_QUEUE.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final TransferMoneyCompletedActivity transferMoneyCompletedActivity =
            Workflow.newLocalActivityStub(TransferMoneyCompletedActivity.class, transferMoneyActivityOptions);

    private final SenderServiceActivity senderServiceActivity =
            Workflow.newActivityStub(SenderServiceActivity.class, senderServiceActivityOptions);

    private final ReceiverServiceActivity receiverServiceActivity =
            Workflow.newActivityStub(ReceiverServiceActivity.class, receiverServiceActivityOptions);

    @Override
    public void startTransferMoneyWorkflow(TransferMoneyRequestDTO transferMoneyRequestDTO, TransactionHistory transactionHistory) {
        logger.info("Transfer money start workflow....");
        logger.info("Workflow ID {}", Workflow.getInfo().getWorkflowId());

        logger.info("Step1(Debit): Sender Service Deduct Money...");
        try {

            // Debit money sender user
            senderServiceActivity.deductMoney(convertToSenderRequestDto(transferMoneyRequestDTO, transactionHistory.getId()));
            transferMoneyCompletedActivity.completedDeductMoneyOfSenderUser(transactionHistory);
        } catch (Exception e) {
            logger.info("Sender Service Deduct Money Failed..." + e.getMessage());

        }

        logger.info("Step2(Credit): Receiver Service Update Money...");
        boolean isFailed = false;
        try {
            receiverServiceActivity.updateMoney(convertToReceiverRequestDto(transferMoneyRequestDTO, transactionHistory.getId()));
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getMessage() !=null && e.getCause().getMessage().contains("Credit Money Timeout")) {
                logger.info("Step4(Timeout): Receiver Service Update Money Timeout...", e.getMessage());
                // Save transaction history with status timeout
                transferMoneyCompletedActivity.updateTransactionMoneyForReceiverUserTimeout(transactionHistory);
            } else {
                logger.info("Step3(Refund): Receiver Service Update Money Failed..." + e.getMessage());
                // Save transaction history
                transferMoneyCompletedActivity.updateTransactionMoForReceiverMoneyUserFail(transactionHistory);
                // Rollback money of sender user
                senderServiceActivity.refundMoney(convertToSenderRequestDto(transferMoneyRequestDTO, transactionHistory.getId()));
            }
            isFailed = true;
        }

        if (!isFailed) {
            logger.info("End: Completed Workflow...");
            transferMoneyCompletedActivity.completedTransferMoney(transferMoneyRequestDTO, transactionHistory);
        }
    }

    private TransferMoneySenderRequestDto convertToSenderRequestDto(TransferMoneyRequestDTO transferMoneyRequestDTO, String transactionId) {
        TransferMoneySenderRequestDto senderRequestDto = TransferMoneySenderRequestDto.builder()
                .transactionId(transactionId)
                .accountId(transferMoneyRequestDTO.getUserSenderId())
                .recipientId(transferMoneyRequestDTO.getUserReceiverId())
                .debitAmount(transferMoneyRequestDTO.getAmount())
                .build();
        return senderRequestDto;
    }

    private TransferMoneyReceiverRequestDto convertToReceiverRequestDto(TransferMoneyRequestDTO transferMoneyRequestDTO, String transactionId) {
        TransferMoneyReceiverRequestDto receiverRequestDto = TransferMoneyReceiverRequestDto.builder()
                .transactionId(transactionId)
                .accountId(transferMoneyRequestDTO.getUserReceiverId())
                .transferId(transferMoneyRequestDTO.getUserSenderId())
                .creditAmount(transferMoneyRequestDTO.getAmount())
                .build();
        return receiverRequestDto;
    }
}
