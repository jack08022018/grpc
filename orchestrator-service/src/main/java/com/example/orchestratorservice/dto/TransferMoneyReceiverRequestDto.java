package com.example.orchestratorservice.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyReceiverRequestDto {

    private String transactionId;
    private String accountId;
    private String transferId;
    private BigInteger creditAmount;
}
