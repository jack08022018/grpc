package com.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {
//    private final OrchestrationService orchestrationService;
//
//    @PostMapping("/transferMoney")
//    public <T> T startTransferMoneyWorkflow(@RequestBody TransferMoneyRequestDTO transferMoneyRequestDTO) throws ExecutionException, InterruptedException {
//        log.info("Transfer Money", "Transfer " + transferMoneyRequestDTO.getAmount() +  transferMoneyRequestDTO.getUserSenderId() + " To" + transferMoneyRequestDTO.getUserReceiverId());
//        TransactionHistory transactionHistory = orchestrationService.startTransferMoneyWorkflow(transferMoneyRequestDTO);
//        return transactionHistory;
//    }
}
