package com.shmup.hiscores.formatters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ScoreFormatterTest {

    @Test
    public void should_format_big_decimal_score() {
        String formatted = ScoreFormatter.format(BigDecimal.valueOf(1_000_000_000_000L));
        Assertions.assertThat(formatted).isEqualTo("1.000.000.000.000");
    }

    @Test
    public void should_format_null_score() {
        String formatted = ScoreFormatter.format(null);
        Assertions.assertThat(formatted).isEqualTo("Invalid");
    }

    @Test
    public void should_format_time_score() {
        String formatted = ScoreFormatter.formatAsTime(BigDecimal.valueOf(1_000_000_000_000L));
        Assertions.assertThat(formatted).isEqualTo("46'40\"00");
    }

}