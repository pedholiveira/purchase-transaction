package com.test.wex.transaction.integration.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.test.wex.transaction.dto.deserializer.BigDecimalCurrency2JsonDeserializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@SuperBuilder
@Jacksonized
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExchangeRateData {

    String currency;
    @JsonProperty("exchange_rate")
    @JsonDeserialize(using = BigDecimalCurrency2JsonDeserializer.class)
    BigDecimal exchangeRate;
    @JsonProperty("effective_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate effectiveDate;
}
