package com.example.orchestratorservice.dto;

import com.example.orchestratorservice.enums.TransferMoneyStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyResponseDTO {

    private Long userSenderId;
    private Long userReceiverId;
    private Double amount;
    private TransferMoneyStatus transferMoneyStatus;
}
