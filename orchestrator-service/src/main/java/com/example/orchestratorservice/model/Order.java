package com.example.orchestratorservice.model;


import com.example.orchestratorservice.enums.OrderStatus;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long orderId;
    private Long productId;
    private Double price;
    private Double quantity;
    private OrderStatus orderStatus;
}
