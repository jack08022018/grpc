package com.example.orchestratorservice.workflow;

import com.example.orchestratorservice.dto.OrderDTO;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderFulfillmentWorkflow {

    @WorkflowMethod
    void createOrder(OrderDTO orderDTO);
}
