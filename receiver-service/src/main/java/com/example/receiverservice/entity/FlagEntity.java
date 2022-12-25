package com.example.receiverservice.entity;

import com.example.receiverservice.enumerator.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "flag")
public class FlagEntity implements Serializable {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "status")
    private String status;
}
