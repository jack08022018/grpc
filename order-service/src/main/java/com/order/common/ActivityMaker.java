//package com.order.common;
//
//import com.order.enumerator.TaskQueue;
//import io.temporal.activity.ActivityOptions;
//import io.temporal.activity.LocalActivityOptions;
//import io.temporal.common.RetryOptions;
//import io.temporal.workflow.Workflow;
//
//import java.time.Duration;
//
//public class ActivityMaker {
//    private static final ActivityOptions senderOptions = ActivityOptions.newBuilder()
//            .setStartToCloseTimeout(Duration.ofSeconds(10))
//            .setTaskQueue(TaskQueue.SENDER_ACTIVITY_WORKFLOW_QUEUE.name())
//            .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
//            .build();
//
//    private static final LocalActivityOptions transferOptions = LocalActivityOptions.newBuilder()
//                    .setStartToCloseTimeout(Duration.ofSeconds(10))
//                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(10).build())
//                    .build();
//
//    private static final ActivityOptions receiverOptions = ActivityOptions.newBuilder()
//                    .setStartToCloseTimeout(Duration.ofSeconds(10))
//                    .setTaskQueue(TaskQueue.RECEIVER_ACTIVITY_WORKFLOW_QUEUE.name())
//                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
//                    .build();
//
//    public static SenderServiceActivity getSenderActivity() {
//        return Workflow.newActivityStub(SenderServiceActivity.class, senderOptions);
//    }
//
//    public static ReceiverServiceActivity getReceiverActivity() {
//        return Workflow.newActivityStub(ReceiverServiceActivity.class, receiverOptions);
//    }
////
////    public static SenderServiceActivity getSenderActivity() {
////        return Workflow.newActivityStub(SenderServiceActivity.class, senderOptions);
////    }
//}
