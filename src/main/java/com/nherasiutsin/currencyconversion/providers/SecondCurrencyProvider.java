package com.nherasiutsin.currencyconversion.providers;

import org.springframework.stereotype.Service;

@Service
public class SecondCurrencyProvider extends BaseCurrencyProvider {

    public SecondCurrencyProvider() {
        super("http://api.exchangeratesapi.io/latest?access_key=c1c316469a43afdb3a3e03075d3b6fe8&base=%s");
    }

}
