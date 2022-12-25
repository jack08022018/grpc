package com.example.senderservice.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String transactionId;
    private String accountId;
    private String recipientId;
    private Long debitAmount;
}
