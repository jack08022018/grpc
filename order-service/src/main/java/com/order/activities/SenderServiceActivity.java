package com.order.activities;


import com.order.dto.TransferMoneyDto;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SenderServiceActivity {
    void deductMoney(TransferMoneyDto dto);
    void refundMoney(TransferMoneyDto dto);
}
