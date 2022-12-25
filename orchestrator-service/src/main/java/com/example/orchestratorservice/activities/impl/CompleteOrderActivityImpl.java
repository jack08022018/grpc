package com.example.orchestratorservice.activities.impl;

import com.example.orchestratorservice.activities.CompleteOrderActivity;
import com.example.orchestratorservice.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RequiredArgsConstructor
public class CompleteOrderActivityImpl implements CompleteOrderActivity {
    @Override
    public void completeOrder(OrderDTO orderDTO) throws InterruptedException {
        log.info("Marking order as completed, order id {}", orderDTO.getOrderId());
        Thread.sleep(11000);
        log.info("timeout", orderDTO.getOrderId());
    }
}
