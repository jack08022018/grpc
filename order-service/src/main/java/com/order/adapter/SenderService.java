package com.order.adapter;

import com.sender.*;

public interface SenderService {
    TransactionResponse deduct(TransactionRequest request);
}
