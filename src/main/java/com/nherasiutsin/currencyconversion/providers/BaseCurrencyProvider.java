package com.nherasiutsin.currencyconversion.providers;

import com.nherasiutsin.currencyconversion.ConversionRequestDTO;
import com.nherasiutsin.currencyconversion.ConversionResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class BaseCurrencyProvider implements CurrencyProvider {

    private final String url;

    public BaseCurrencyProvider(String url) {
        this.url = url;
    }

    @Override
    public Mono<ConversionResponseDTO> convert(ConversionRequestDTO request) {
        return WebClient
                   .create()
                   .get()
                   .uri(String.format(url, request.getFrom()))
                   .accept(MediaType.APPLICATION_JSON)
                   .retrieve()
                   .bodyToMono(ProviderResponseDTO.class)
                   .map(r -> r.getRates().get(request.getTo()))
                   .map(BigDecimal::new)
                   .map(rate -> new ConversionResponseDTO(request.getFrom(), request.getTo(), request.getAmount(), request.getAmount().multiply(rate)));
    }

}
