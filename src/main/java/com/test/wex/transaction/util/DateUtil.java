package com.test.wex.transaction.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class DateUtil {

    public String localDateToString(LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .orElse(null);
    }
}
