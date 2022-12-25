package com.example.orchestratorservice.mapper;

import com.example.orchestratorservice.dto.OrderRequest;
import com.example.orchestratorservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderRequestMapper {


    public static final OrderRequestMapper MAPPER = Mappers.getMapper(OrderRequestMapper.class);

    public abstract Order map(OrderRequest request);
}
