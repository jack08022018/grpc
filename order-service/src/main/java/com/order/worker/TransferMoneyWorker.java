//package com.order.worker;
//
//import com.order.activities.TransferActivities;
//import com.order.enumerator.TaskQueue;
//import com.order.workflow.TransferMoneyWorkflowImpl;
//import io.temporal.client.WorkflowClient;
//import io.temporal.worker.WorkerFactory;
//import io.temporal.worker.WorkerOptions;
//import io.temporal.worker.WorkflowImplementationOptions;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TransferMoneyWorker {
//
//    private final WorkflowClient workflowClientCustom;
//    private final TransferActivities transferActivities;
//
//    @PostConstruct
//    public void createWorker() {
//        var workerOptions = WorkerOptions.newBuilder().build();
//        var workflowOptions = WorkflowImplementationOptions
//                .newBuilder()
//                .setFailWorkflowExceptionTypes(NullPointerException.class)
//                .build();
//        var workerFactory = WorkerFactory.newInstance(workflowClientCustom);
//        var worker = workerFactory.newWorker(TaskQueue.TRANSFER_MONEY.name(), workerOptions);
//        worker.registerWorkflowImplementationTypes(workflowOptions, TransferMoneyWorkflowImpl.class);
//        worker.registerActivitiesImplementations(transferActivities);
//        workerFactory.start();
//    }
//}
