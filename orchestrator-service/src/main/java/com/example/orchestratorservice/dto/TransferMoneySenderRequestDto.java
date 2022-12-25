package com.example.orchestratorservice.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneySenderRequestDto {

    private String transactionId;
    private String accountId;
    private String recipientId;
    private BigInteger debitAmount;
}
