package com.example.orchestratorservice.activities;

import com.example.orchestratorservice.dto.OrderDTO;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CompleteOrderActivity {

    void completeOrder(OrderDTO orderDTO) throws InterruptedException;
}
