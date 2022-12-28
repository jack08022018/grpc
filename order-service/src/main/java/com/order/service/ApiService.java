package com.order.service;


import com.order.dto.TransferMoneyDto;

public interface ApiService {
    void transferMoneyWorkflow(TransferMoneyDto dto);

}
