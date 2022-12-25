package com.example.orchestratorservice.dto;

import com.example.orchestratorservice.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long orderId;
    private OrderStatus orderStatus;
}
