package com.nherasiutsin.currencyconversion.providers;

import com.nherasiutsin.currencyconversion.ConversionRequestDTO;
import com.nherasiutsin.currencyconversion.ConversionResponseDTO;
import reactor.core.publisher.Mono;

public interface CurrencyProvider {

    Mono<ConversionResponseDTO> convert(ConversionRequestDTO request);

}
