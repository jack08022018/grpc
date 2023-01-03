package com.example.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String transactionId;
    private String accountId;
    private String transferId;
    private String recipientId;
    private Long creditAmount;
    private Long debitAmount;
}
