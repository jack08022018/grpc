package com.example.activities;

import com.example.dto.TransferMoneyDto;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface TransferActivities {
    @ActivityMethod
    void deduct(TransferMoneyDto dto);

    @ActivityMethod
    void refund(TransferMoneyDto dto);

    @ActivityMethod
    void credit(TransferMoneyDto dto);

    @ActivityMethod
    String hello(String input);
}
