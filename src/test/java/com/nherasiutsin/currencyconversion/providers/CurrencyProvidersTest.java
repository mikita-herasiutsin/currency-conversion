package com.nherasiutsin.currencyconversion.providers;

import com.nherasiutsin.currencyconversion.ConversionRequestDTO;
import com.nherasiutsin.currencyconversion.ConversionResponseDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

class CurrencyProvidersTest {

    @Test
    void mainProviderShouldReturnRate() {
        callProvider(new MainCurrencyProvider());
    }

    @Test
    void secondProviderShouldReturnRate() {
        callProvider(new SecondCurrencyProvider());
    }

    private void callProvider(CurrencyProvider provider) {
        ConversionResponseDTO result = provider.convert(new ConversionRequestDTO("EUR", "USD", new BigDecimal("47.65"))).block();

        assertThat(result, Matchers.not(nullValue()));
        assertThat(result.getConverted(), Matchers.not(nullValue()));
    }

}