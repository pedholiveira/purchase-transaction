package com.test.wex.transaction.integration.api.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@SuperBuilder
@Jacksonized
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TreasuryRatesExchangeResponse {

    List<ExchangeRateData> data;
}
