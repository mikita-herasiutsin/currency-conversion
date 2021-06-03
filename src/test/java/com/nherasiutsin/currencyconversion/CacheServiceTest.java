package com.nherasiutsin.currencyconversion;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class CacheServiceTest {

    @Test
    public void shouldReturnValueFromCache() {
        CacheService cacheService = new CacheService(10);
        String from = "USD";
        String to = "EUR";
        BigDecimal value = new BigDecimal("34.3");

        cacheService.putRate(from, to, value);
        BigDecimal result = cacheService.getRate(from, to);

        assertThat(result, is(value));
    }

    @Test
    public void shouldNotReturnStaleValueFromCache() throws InterruptedException {
        CacheService cacheService = new CacheService(1);
        String from = "USD";
        String to = "EUR";
        BigDecimal value = new BigDecimal("34.3");

        cacheService.putRate(from, to, value);
        Thread.sleep(2000);
        BigDecimal result = cacheService.getRate(from, to);

        assertThat(result, is(nullValue()));
    }

}