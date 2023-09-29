package com.test.wex.transaction.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    public String localDateToString(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return localDate.format(formatter);
    }
}
