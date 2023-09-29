package com.test.wex.transaction.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DateUtilTest {

    DateUtil dateUtil = new DateUtil();

    @Test
    public void testingLocalDateConvertingWhenValueIsNull() {
        Assertions.assertThat(dateUtil.localDateToString(null)).isNull();
    }

    @Test
    public void testingLocalDateConvertingWithSuccess() {
        String expected = "2023-09-29";
        LocalDate localDate = LocalDate.of(2023, 9, 29);
        Assertions.assertThat(dateUtil.localDateToString(localDate)).isEqualTo(expected);
    }
}
