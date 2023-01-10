package com.example.activities;

import com.example.adapter.SenderAdapter;
import com.sender.HelloRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloActivitiesImpl implements HelloActivities {
    private final SenderAdapter senderAdapter;

    public HelloActivitiesImpl(SenderAdapter senderAdapter) {
        this.senderAdapter = senderAdapter;
    }

    @Override
    public String helloSender(String input) {
        HelloRequest request = HelloRequest.newBuilder().setInput(input).build();
        return senderAdapter.hello(request).getOutput();
    }
}
