package com.example.orchestratorservice.service;


import com.example.orchestratorservice.dto.TransactionDto;

public interface ApiService {
    void deduct(TransactionDto dto);
    void refund(TransactionDto dto);
    void credit(TransactionDto dto);
}
