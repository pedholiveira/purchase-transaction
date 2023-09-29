package com.test.wex.transaction.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalCurrency2JsonDeserializer extends NumberDeserializers.BigDecimalDeserializer {

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        BigDecimal value = super.deserialize(p, ctxt);
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }
}
