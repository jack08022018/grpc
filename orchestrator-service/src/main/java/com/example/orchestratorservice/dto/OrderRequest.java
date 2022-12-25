package com.example.orchestratorservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

//    @NotNull(message = "Product ID is required.")
//    @NotBlank(message = "Product ID cannot be blank.")
    private Long productId;

//    @NotNull(message = "Price is required.")
    private Double price;

//    @NotNull(message = "Quantity is required.")
    private Double quantity;
}
