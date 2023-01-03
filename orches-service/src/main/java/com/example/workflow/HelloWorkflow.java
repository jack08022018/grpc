package com.example.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.springframework.ui.ModelMap;

@WorkflowInterface
public interface HelloWorkflow {
    @WorkflowMethod
    ModelMap executeHello(String input);
}
