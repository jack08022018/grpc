package com.example.orchestratorservice.mapper;

import com.example.orchestratorservice.dto.TransferMoneyRequestDTO;
import com.example.orchestratorservice.model.TransactionHistory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = ALWAYS)
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransferMoneyRequestDTO toTransferMoneyRequestDTO(TransactionHistory transactionHistory);
}
