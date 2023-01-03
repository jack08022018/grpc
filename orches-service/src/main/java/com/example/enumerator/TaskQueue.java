package com.example.enumerator;

public enum TaskQueue {
    TRANSFER_MONEY,

    PAYMENT_ACTIVITY_TASK_QUEUE,
    INVENTORY_ACTIVITY_TASK_QUEUE,
    SHIPPING_ACTIVITY_TASK_QUEUE,
    ORDER_FULFILLMENT_WORKFLOW_TASK_QUEUE,
    RE_EXECUTE_TRANSFER_MONEY_WORKFLOW_TASK_QUEUE,
    SENDER_ACTIVITY_WORKFLOW_QUEUE,
    RECEIVER_ACTIVITY_WORKFLOW_QUEUE,
    RE_EXECUTE_SENDER_ACTIVITY_WORKFLOW_QUEUE,
    RE_EXECUTE_RECEIVER_ACTIVITY_WORKFLOW_QUEUE
}
