package com.example.orchestratorservice.activities;

import com.example.orchestratorservice.dto.TransferMoneyReceiverRequestDto;
import com.example.orchestratorservice.exceptions.TimeoutException;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ReceiverServiceActivity {
    void updateMoney(TransferMoneyReceiverRequestDto transferMoneyReceiverRequestDto) throws TimeoutException;
}
