package com.nherasiutsin.currencyconversion.providers;

import com.nherasiutsin.currencyconversion.ConversionRequestDTO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class BaseCurrencyProvider implements CurrencyProvider {

    private final String url;

    public BaseCurrencyProvider(String url) {
        this.url = url;
    }

    /**
     * Default method to retrieve rates. Caches the response.
     *
     * @param request request
     * @return rates
     */
    @Override
    public Mono<Map<String, String>> getRates(ConversionRequestDTO request) {
        return WebClient
                   .create()
                   .get()
                   .uri(String.format(url, request.getFrom()))
                   .accept(MediaType.APPLICATION_JSON)
                   .retrieve()
                   .bodyToMono(ProviderResponseDTO.class)
                   .map(ProviderResponseDTO::getRates);
    }

}
