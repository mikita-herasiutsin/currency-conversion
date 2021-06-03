package com.nherasiutsin.currencyconversion;

import com.nherasiutsin.currencyconversion.providers.CurrencyProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CurrencyServiceTest {

    private static final String FROM = "EUR";

    private static final String TO = "USD";

    private static final BigDecimal AMOUNT = new BigDecimal(5);

    private static final BigDecimal RATE = new BigDecimal("1.2");

    @Mock
    private CacheService cacheService;

    @BeforeEach
    public void openMocks() {
        initMocks(this);
    }

    @Test
    public void shouldReturnValueFromTheCacheIfExistsWithoutCallingProvider() {
        CurrencyService currencyService = new CurrencyService(
            Collections.singletonList(request -> {
                throw new IllegalStateException();
            }),
            cacheService
        );

        when(cacheService.getRate(FROM, TO)).thenReturn(RATE);

        ConversionResponseDTO result = currencyService.convert(new ConversionRequestDTO(FROM, TO, AMOUNT)).block();

        assertThat(result, is(new ConversionResponseDTO(FROM, TO, AMOUNT, AMOUNT.multiply(RATE))));
    }

    @Test
    public void shouldCallProviderWhenCacheIsEmpty() {
        ArrayList<CurrencyProvider> providers = new ArrayList<>();
        providers.add(request -> Mono.just(Map.of(TO, RATE.toString())));
        CurrencyService currencyService = new CurrencyService(providers, cacheService);

        when(cacheService.getRate(FROM, TO)).thenReturn(null);

        ConversionResponseDTO result = currencyService.convert(new ConversionRequestDTO(FROM, TO, AMOUNT)).block();

        assertThat(result, is(new ConversionResponseDTO(FROM, TO, AMOUNT, AMOUNT.multiply(RATE))));
        verify(cacheService).putRate(FROM, TO, RATE);
    }

    @Test
    public void shouldTryDifferentProviderWhenAnotherFailed() {
        AtomicInteger counter = new AtomicInteger(0);
        CurrencyProvider currencyProvider = request -> {
            if (counter.get() == 0) {
                counter.incrementAndGet();
                return Mono.error(new IllegalStateException());
            } else {
                return Mono.just(Map.of(TO, RATE.toString()));
            }
        };
        ArrayList<CurrencyProvider> providers = new ArrayList<>();
        providers.add(currencyProvider);
        providers.add(currencyProvider);
        CurrencyService currencyService = new CurrencyService(providers, cacheService);

        when(cacheService.getRate(FROM, TO)).thenReturn(null);

        ConversionResponseDTO result = currencyService.convert(new ConversionRequestDTO(FROM, TO, AMOUNT)).block();

        assertThat(result, is(new ConversionResponseDTO(FROM, TO, AMOUNT, AMOUNT.multiply(RATE))));
        verify(cacheService).putRate(FROM, TO, RATE);
    }

}