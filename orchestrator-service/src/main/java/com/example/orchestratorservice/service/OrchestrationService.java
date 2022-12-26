package com.example.orchestratorservice.service;

import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.enums.OrderStatus;
import com.example.orchestratorservice.enums.TransferMoneyStatus;
import com.example.orchestratorservice.model.Order;
import com.example.orchestratorservice.model.TransactionHistory;
import com.example.orchestratorservice.repository.TransactionHistoryRepository;
import com.example.orchestratorservice.workflow.WorkflowOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrchestrationService {

    private final WorkflowOrchestrator workflowOrchestrator;

    private final TransactionHistoryRepository transactionHistoryRepository;

    public Order createOrder(Order order) {
        log.info("Creating order..");
        order.setOrderStatus(OrderStatus.PENDING);
        //TODO
        //Tru tien ben A
//        var persistedOrder = orderRepository.save(order);
        workflowOrchestrator.createOrder(order);
//        return persistedOrder;
        return new Order();
    }

    public TransactionHistory startTransferMoneyWorkflow(TransferMoneyRequestDTO transferMoneyRequestDTO) throws ExecutionException, InterruptedException {
        log.info("Start transfer money service..");
        String transactionId = UUID.randomUUID().toString();
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setId(transactionId);
        transactionHistory.setUserSenderId(transferMoneyRequestDTO.getUserSenderId());
        transactionHistory.setUserReceiverId(transferMoneyRequestDTO.getUserReceiverId());
        transactionHistory.setAmount(transferMoneyRequestDTO.getAmount());
        transactionHistory.setTransferMoneyStatus(TransferMoneyStatus.PENDING);
//         save transaction history
        transactionHistoryRepository.save(transactionHistory);

        workflowOrchestrator.startTransferMoneyWorkflow(transferMoneyRequestDTO, transactionHistory);
        return transactionHistory;

    }

    public void reExecuteTransferMoneyWorkflow() {
        log.info("ReExecute transfer money service..");
        List<TransactionHistory> transactions = transactionHistoryRepository.findByTransferMoneyStatus(TransferMoneyStatus.TIMEOUT);
        if (CollectionUtils.isEmpty(transactions)) {
            log.info("No Transactions ReExecute transfer money service..");
            return;
        }
        log.info("ReExecute transfer money service: {} transaction", transactions.size());
        workflowOrchestrator.reExecuteTransferMoneyWorkflow(transactions);
    }

}
