package com.nherasiutsin.currencyconversion.providers;

import com.nherasiutsin.currencyconversion.ConversionRequestDTO;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface CurrencyProvider {

    Mono<Map<String, String>> getRates(ConversionRequestDTO request);

}
