package com.order.activities;

import com.order.dto.TransferMoneyDto;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface TransferActivities {
    void deduct(TransferMoneyDto dto);
    void refund(TransferMoneyDto dto);
    void credit(TransferMoneyDto dto);
}
