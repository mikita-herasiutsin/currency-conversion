package com.nherasiutsin.currencyconversion.providers;

import com.nherasiutsin.currencyconversion.ConversionRequestDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class CurrencyProvidersTest {

    @Test
    public void mainProviderShouldReturnRate() {
        callProvider(new MainCurrencyProvider());
    }

    @Test
    public void secondProviderShouldReturnRate() {
        callProvider(new SecondCurrencyProvider("c1c316469a43afdb3a3e03075d3b6fe8"));
    }

    private void callProvider(CurrencyProvider provider) {
        Map<String, String> result = provider.getRates(new ConversionRequestDTO("EUR", "USD", new BigDecimal("47.65"))).block();

        assertThat(result, not(nullValue()));
        assertThat(result.size(), not(0));
    }

}