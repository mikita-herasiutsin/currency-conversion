package com.nherasiutsin.currencyconversion.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecondCurrencyProvider extends BaseCurrencyProvider {

    public SecondCurrencyProvider(@Value("${provider.second.key:}") String key) {
        super("http://api.exchangeratesapi.io/latest?access_key=" + key + "&base=%s");
    }

}
