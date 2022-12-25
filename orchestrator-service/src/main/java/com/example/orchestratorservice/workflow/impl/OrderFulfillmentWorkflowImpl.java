package com.example.orchestratorservice.workflow.impl;

import com.example.orchestratorservice.activities.CompleteOrderActivity;
import com.example.orchestratorservice.dto.OrderDTO;
import com.example.orchestratorservice.model.Order;
import com.example.orchestratorservice.workflow.OrderFulfillmentWorkflow;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class OrderFulfillmentWorkflowImpl implements OrderFulfillmentWorkflow {

    private Logger logger = Workflow.getLogger(this.getClass().getName());

    private final LocalActivityOptions localActivityOptions =
            LocalActivityOptions.newBuilder()
//                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(10).build())
                    .build();

    private final CompleteOrderActivity orderActivity =
            Workflow.newLocalActivityStub(CompleteOrderActivity.class, localActivityOptions);

    @Override
    public void createOrder(OrderDTO orderDTO) {
        logger.info("OrderFulfillmentWorkflowImpl createOrder");
        logger.info("Workflow ID {}", Workflow.getInfo().getWorkflowId());

        logger.info("Debiting payment..");
        //TODO
        //Call service B

        logger.info("Completing order..");
        try {
            orderActivity.completeOrder(orderDTO);
        } catch (Exception e) {
            logger.error("ERROR");
        }


    }

    private Order map(OrderDTO orderDTO) {
        var order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setProductId(orderDTO.getProductId());
        order.setPrice(orderDTO.getPrice());
        order.setQuantity(orderDTO.getQuantity());
        return order;
    }
}
