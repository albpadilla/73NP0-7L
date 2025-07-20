package com.apadilla.tenpo.desafio.service.impl;

import com.apadilla.tenpo.desafio.client.PercentageClient;
import com.apadilla.tenpo.desafio.client.dto.PercentageResponse;
import com.apadilla.tenpo.desafio.service.PercentageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PercentageServiceImpl implements PercentageService {

    private final PercentageClient percentageClient;
    private final CacheManager cacheManager;
    private static final String CACHE_NAME = "percentage";
    private static final String CACHE_KEY = "current_percentage";

    public PercentageServiceImpl(PercentageClient percentageClient, CacheManager cacheManager) {
        this.percentageClient = percentageClient;
        this.cacheManager = cacheManager;
    }

    @Override
    @Retryable(
        retryFor = {Exception.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public BigDecimal getPercentage() {
        log.info("trying to get percentage from external service...");
        PercentageResponse response = percentageClient.getPercentage();
        BigDecimal percentage = BigDecimal.valueOf(response.percent());

        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (cache != null) {
            cache.put(CACHE_KEY, percentage);
            log.info("OK! percentage retrieved from external service and cached: {}%", percentage);
        }

        return percentage;
    }

    @Recover
    public BigDecimal recover(Exception e) {
        log.warn("all retry attempts failed, trying to get from cache: {}", e.getMessage());

        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (cache != null) {
            Cache.ValueWrapper cachedValue = cache.get(CACHE_KEY);
            if (cachedValue != null && cachedValue.get() != null) {
                BigDecimal cachedPercentage = (BigDecimal) cachedValue.get();
                log.info("using cached percentage after all retries failed: {}%", cachedPercentage);
                return cachedPercentage;
            }
        }

        log.error("no cached value available and external service is unavailable after all retries");
        throw new RuntimeException("percentage service is unavailable and no cached value exists");
    }
}
