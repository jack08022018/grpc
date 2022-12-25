package com.example.orchestratorservice.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequestDTO {

    private String userSenderId;
    private String userReceiverId;
    private BigInteger amount;
}
