package com.order.activities;

import com.order.dto.TransferMoneyDto;
import io.temporal.activity.ActivityInterface;

import java.util.concurrent.TimeoutException;

@ActivityInterface
public interface ReceiverServiceActivity {
    void credit(TransferMoneyDto dto) throws TimeoutException;
}
