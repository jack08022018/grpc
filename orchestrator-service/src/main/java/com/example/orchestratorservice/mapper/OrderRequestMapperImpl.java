package com.example.orchestratorservice.mapper;

import com.example.orchestratorservice.dto.OrderRequest;
import com.example.orchestratorservice.model.Order;

import javax.annotation.processing.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2022-12-21T11:57:34+0700",
        comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.1.jar, environment: Java 11.0.17 (Amazon.com Inc.)"
)
public class OrderRequestMapperImpl extends OrderRequestMapper{
    @Override
    public Order map(OrderRequest request) {
        if ( request == null ) {
            return null;
        }

        Order order = new Order();

        order.setProductId( request.getProductId() );
        order.setPrice( request.getPrice() );
        order.setQuantity( request.getQuantity() );

        return order;
    }
}
