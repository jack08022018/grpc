package com.example.senderservice.entity;

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
@Table(name = "account_user")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "account_id", unique = true, nullable = false)
    private String accountId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "ballance_amount")
    private Long ballanceAmount;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    public UserEntity(String accountId, String fullName, Long ballanceAmount) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.ballanceAmount = ballanceAmount;
        this.createAt = LocalDateTime.now();
    }

}
