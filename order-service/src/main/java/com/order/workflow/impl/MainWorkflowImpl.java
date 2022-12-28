package com.order.workflow.impl;

import com.order.activities.ReceiverServiceActivity;
import com.order.activities.SenderServiceActivity;
import com.order.common.ActivityMaker;
import com.order.dto.TransferMoneyDto;
import com.order.workflow.MainWorkflow;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainWorkflowImpl implements MainWorkflow {
    private final SenderServiceActivity senderServiceActivity = ActivityMaker.getSenderActivity();
    private final ReceiverServiceActivity receiverServiceActivity = ActivityMaker.getReceiverActivity();

    @Override
    public void transferMoney(TransferMoneyDto dto) {
        log.info("Transfer money start: {}", Workflow.getInfo().getWorkflowId());
        log.info("Step1(Debit): Sender Service Deduct Money...");
        try {
            senderServiceActivity.deductMoney(dto);
        } catch (Exception e) {
            log.info("Sender Service Deduct Money Failed..." + e.getMessage());
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
