package com.example.grpc.service;

import com.example.grpc.dto.TransactionDto;

public interface ApiService {
    void deduct(TransactionDto dto);
}
