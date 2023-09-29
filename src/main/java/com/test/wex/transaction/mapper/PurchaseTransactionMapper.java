package com.test.wex.transaction.mapper;

import com.test.wex.transaction.dto.PurchaseTransactionDto;
import com.test.wex.transaction.model.PurchaseTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchaseTransactionMapper {

    PurchaseTransaction createModelFromDto(PurchaseTransactionDto dto);

    PurchaseTransactionDto createDtoFromModel(PurchaseTransaction model);

    @Mapping(target = "exchangeRate", source = "exchangeRate")
    @Mapping(target = "convertedPurchaseAmount", source = "convertedPurchaseAmount")
    PurchaseTransactionDto createDtoFromModelWithConvertedValue(
            PurchaseTransaction model, BigDecimal exchangeRate, BigDecimal convertedPurchaseAmount);
}
