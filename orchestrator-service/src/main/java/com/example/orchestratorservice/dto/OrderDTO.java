package com.example.orchestratorservice.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private Long productId;
    private Double price;
    // TODO - make it int
    private Double quantity;
}
