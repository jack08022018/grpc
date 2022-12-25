package com.example.orchestratorservice.model;


import com.example.orchestratorservice.enums.TransferMoneyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_history")
@Entity
public class TransactionHistory {

    @Id
    private String id;
    private String userSenderId;
    private String userReceiverId;
    private BigInteger amount;

    @Enumerated(EnumType.STRING)
    private TransferMoneyStatus transferMoneyStatus;
}
