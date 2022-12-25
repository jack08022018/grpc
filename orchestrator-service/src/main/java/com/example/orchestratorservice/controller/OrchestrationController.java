package com.example.orchestratorservice.controller;

import com.example.orchestratorservice.dto.OrderRequest;
import com.example.orchestratorservice.dto.OrderResponse;
import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.mapper.OrderRequestMapper;
import com.example.orchestratorservice.model.TransactionHistory;
import com.example.orchestratorservice.service.OrchestrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrchestrationController {

    private final OrchestrationService orchestrationService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Creating order, product id {}", orderRequest.getProductId());

        var order = OrderRequestMapper.MAPPER.map(orderRequest);
        var pendingOrder = orchestrationService.createOrder(order);

        var orderResponse = new OrderResponse(pendingOrder.getOrderId(), pendingOrder.getOrderStatus());
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<Object> startTransferMoneyWorkflow(@RequestBody TransferMoneyRequestDTO transferMoneyRequestDTO) {

        log.info("Transfer Money", "Transfer " + transferMoneyRequestDTO.getAmount() +  transferMoneyRequestDTO.getUserSenderId() + " To" + transferMoneyRequestDTO.getUserReceiverId());

        TransactionHistory transactionHistory = orchestrationService.startTransferMoneyWorkflow(transferMoneyRequestDTO);
        return new ResponseEntity<>(transactionHistory, HttpStatus.ACCEPTED);
    }
}
