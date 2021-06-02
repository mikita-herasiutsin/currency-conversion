package com.nherasiutsin.currencyconversion.providers;

import org.springframework.stereotype.Service;

@Service
public class MainCurrencyProvider extends BaseCurrencyProvider {

    public MainCurrencyProvider() {
        super("https://api.exchangerate-api.com/v4/latest/%s");
    }

}
