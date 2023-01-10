package com.example.activities;

import com.example.dto.TransferMoneyDto;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface HelloActivities {
    @ActivityMethod
    String helloSender(String input);
}
