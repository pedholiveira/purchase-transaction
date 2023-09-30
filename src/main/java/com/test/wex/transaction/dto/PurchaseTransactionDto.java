package com.test.wex.transaction.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.wex.transaction.dto.deserializer.CurrencyDeserializer;
import com.test.wex.transaction.dto.serializer.CurrencySerializer;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@Jacksonized
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PurchaseTransactionDto {

    @JsonView({Views.Read.class})
    UUID id;

    @NotBlank(message = "Description cannot be blank.")
    @Size(max = 50, message = "Description cannot exceed 50 characters.")
    @JsonView({Views.Read.class, Views.Create.class})
    String description;

    @NotNull(message = "Transaction date cannot be null.")
    @JsonView({Views.Read.class, Views.Create.class})
    LocalDate transactionDate;

    @NotNull(message = "Purchase amount cannot be null.")
    @Positive(message = "Purchase amount must be a positive value.")
    @JsonSerialize(using = CurrencySerializer.class)
    @JsonDeserialize(using = CurrencyDeserializer.class)
    @JsonView({Views.Read.class, Views.Create.class})
    BigDecimal usdPurchaseAmount;

    @JsonSerialize(using = CurrencySerializer.class)
    @JsonView(Views.ConvertedList.class)
    BigDecimal exchangeRate;

    @JsonSerialize(using = CurrencySerializer.class)
    @JsonView(Views.ConvertedList.class)
    BigDecimal convertedPurchaseAmount;
}
