package com.example.orchestratorservice.workflow.impl;

import com.example.orchestratorservice.activities.ReceiverServiceActivity;
import com.example.orchestratorservice.activities.SenderServiceActivity;
import com.example.orchestratorservice.activities.TransferMoneyCompletedActivity;
import com.example.orchestratorservice.dto.TransferMoneyReceiverRequestDto;
import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.dto.TransferMoneySenderRequestDto;
import com.example.orchestratorservice.enums.TaskQueue;
import com.example.orchestratorservice.model.TransactionHistory;
import com.example.orchestratorservice.workflow.ReExecuteTransferMoneyWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class ReExecuteTransferMoneyWorkflowImpl
    implements ReExecuteTransferMoneyWorkflow {

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
    public void reExecuteTransferMoneyWorkflow(final TransactionHistory transactionHistory) {
        logger.info("ReExecute Receiver Service Update Money with id: {}", transactionHistory.getId());
        TransferMoneyRequestDTO transferMoneyRequestDTO = toTransferMoneyRequestDTO(transactionHistory);
        boolean isFailed = false;
        try {
            receiverServiceActivity.updateMoney(convertToReceiverRequestDto(transferMoneyRequestDTO, transactionHistory.getId()));
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getMessage() !=null && e.getCause().getMessage().contains("Credit Money Timeout")) {
                logger.info("ReExecute Receiver Service Update Money Timeout...", e.getMessage());
            } else {
                logger.info("ReExecute Receiver Service Update Money Failed..." + e.getMessage());
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

    private TransferMoneyRequestDTO toTransferMoneyRequestDTO(TransactionHistory transactionHistory) {
        TransferMoneyRequestDTO transferMoneyRequestDTO = TransferMoneyRequestDTO.builder()
                                                                                            .userReceiverId(transactionHistory.getUserReceiverId())
                                                                                            .userSenderId(transactionHistory.getUserSenderId())
                                                                                            .amount(transactionHistory.getAmount())
                                                                                            .build();
        return transferMoneyRequestDTO;
    }
}
