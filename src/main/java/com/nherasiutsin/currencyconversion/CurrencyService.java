package com.nherasiutsin.currencyconversion;

import com.nherasiutsin.currencyconversion.providers.CurrencyProvider;
import com.nherasiutsin.currencyconversion.providers.MainCurrencyProvider;
import com.nherasiutsin.currencyconversion.providers.SecondCurrencyProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

@Service
public class CurrencyService {

    private static final Random RANDOM = new Random();

    private final List<CurrencyProvider> providers;

    public CurrencyService(MainCurrencyProvider mainProvider, SecondCurrencyProvider secondProvider) {
        providers = asList(mainProvider, secondProvider);
    }

    public Mono<ConversionResponseDTO> convert(ConversionRequestDTO request) {
        return Mono.just(providers.get(RANDOM.nextInt(providers.size())))
                   .flatMap(p -> p.convert(request));
    }

}
