package com.example.senderservice.entity;

import com.example.senderservice.enumerator.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "transaction")
public class TransactionEntity implements Serializable {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "recipient_id")
    private String recipientId;

    @Column(name = "debit_amount")
    private Long debitAmount;

    @Column(name = "status")
    private Status status;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    public TransactionEntity(String accountId, String recipientId, Long debitAmount, String transactionId) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.recipientId = recipientId;
        this.debitAmount = debitAmount;
        this.status = Status.SUCCESS;
        this.createAt = LocalDateTime.now();
    }

}
