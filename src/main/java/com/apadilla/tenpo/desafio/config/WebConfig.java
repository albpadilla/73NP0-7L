package com.apadilla.tenpo.desafio.config;

import com.apadilla.tenpo.desafio.interceptor.CallHistoryInterceptor;
import com.apadilla.tenpo.desafio.interceptor.RateLimitingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CallHistoryInterceptor callHistoryInterceptor;
    private final RateLimitingInterceptor rateLimitingInterceptor;

    private final String PATH_PATTERN = "/api/**";
    private final String[] EXCLUDED_PATHS = {"/api/v1/call-history/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitingInterceptor)
                .addPathPatterns(PATH_PATTERN);

        registry.addInterceptor(callHistoryInterceptor)
                .addPathPatterns(PATH_PATTERN)
                .excludePathPatterns(EXCLUDED_PATHS);
    }
}
