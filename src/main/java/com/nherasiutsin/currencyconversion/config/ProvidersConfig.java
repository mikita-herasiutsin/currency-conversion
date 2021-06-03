package com.nherasiutsin.currencyconversion.config;

import com.nherasiutsin.currencyconversion.providers.CurrencyProvider;
import com.nherasiutsin.currencyconversion.providers.MainCurrencyProvider;
import com.nherasiutsin.currencyconversion.providers.SecondCurrencyProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class ProvidersConfig {

    @Bean
    public List<CurrencyProvider> currencyProviders(MainCurrencyProvider mainProvider, SecondCurrencyProvider secondProvider) {
        return asList(mainProvider, secondProvider);
    }

}
