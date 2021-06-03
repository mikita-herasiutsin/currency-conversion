package com.nherasiutsin.currencyconversion;

import com.nherasiutsin.currencyconversion.providers.CurrencyProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class CurrencyService {

    private static final Random RANDOM = new Random();

    private static final String DEFAULT_ERROR_MESSAGE = "There are no providers available";

    private final List<CurrencyProvider> providers;

    private final CacheService cacheService;

    public CurrencyService(List<CurrencyProvider> providers, CacheService cacheService) {
        this.providers = providers;
        this.cacheService = cacheService;
    }

    public Mono<ConversionResponseDTO> convert(ConversionRequestDTO request) {
        return Mono.justOrEmpty(cacheService.getRate(request.getFrom(), request.getTo()))
                   .switchIfEmpty(Mono.defer(() ->
                                                 requestRates(request, new ArrayList<>(providers))
                                                     .map(rates -> rates.get(request.getTo()))
                                                     .map(BigDecimal::new)
                   ))
                   .doOnNext(rate -> cacheService.putRate(request.getFrom(), request.getTo(), rate))
                   .map(rate -> new ConversionResponseDTO(
                       request.getFrom(),
                       request.getTo(),
                       request.getAmount(),
                       request.getAmount().multiply(rate)
                   ))
                   .onErrorResume(t -> Mono.error(new ResponseStatusException(NOT_FOUND, DEFAULT_ERROR_MESSAGE, t)))
                   .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(NOT_FOUND, DEFAULT_ERROR_MESSAGE))));
    }

    /**
     * Tries to get rates from request param. If it fails, then tries other from fallbackProviders recursively.
     *
     * @param request           request
     * @param fallbackProviders fallback providers
     * @return rates
     */
    private Mono<Map<String, String>> requestRates(ConversionRequestDTO request, List<CurrencyProvider> fallbackProviders) {
        if (isEmpty(fallbackProviders)) {
            return Mono.empty();
        } else {
            return fallbackProviders.remove(RANDOM.nextInt(fallbackProviders.size())).getRates(request)
                            .onErrorResume(t -> requestRates(request, fallbackProviders));
        }
    }

}
