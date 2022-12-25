package com.example.orchestratorservice.activities;


import com.example.orchestratorservice.dto.TransferMoneySenderRequestDto;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SenderServiceActivity {

    void deductMoney(TransferMoneySenderRequestDto transferMoneyReceiverRequestDto);
    void refundMoney(TransferMoneySenderRequestDto transferMoneyReceiverRequestDto);
}
