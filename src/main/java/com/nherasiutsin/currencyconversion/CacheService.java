package com.nherasiutsin.currencyconversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Simple cache implementation with TTL.
 */
@Service
public class CacheService {

    private static final Map<String, RateValue> rates = new ConcurrentHashMap<>();

    /**
     * Cache TTL in seconds
     */
    private final Integer ttl;

    public CacheService(@Value("${cache.ttl:0}") Integer ttl) {
        this.ttl = ttl;
    }

    public BigDecimal getRate(String from, String to) {
        return Optional.ofNullable(rates.get(buildKey(from, to)))
                       .filter(v -> v.getCreated().isAfter(ZonedDateTime.now().minusSeconds(ttl)))
                       .map(RateValue::getRate)
                       .orElse(null);
    }

    public void putRate(String from, String to, BigDecimal value) {
        rates.put(buildKey(from, to), new RateValue(value, ZonedDateTime.now()));
    }

    private String buildKey(String from, String to) {
        return String.format("%s-%s", from, to);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class RateValue {

        private BigDecimal rate;

        private ZonedDateTime created;

    }

}
