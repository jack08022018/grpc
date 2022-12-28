package com.order.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyDto {
    private String transactionId;
    private String userSenderId;
    private String userReceiverId;
    private BigInteger amount;
    private String accountId;
    private String recipientId;
    private BigInteger debitAmount;
    private String transferId;
    private BigInteger creditAmount;
}
