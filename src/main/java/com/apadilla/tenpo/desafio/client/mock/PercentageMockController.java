package com.apadilla.tenpo.desafio.client.mock;

import com.apadilla.tenpo.desafio.client.dto.PercentageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Mock del servicio de porcentajes
 */
@RestController
@RequestMapping("/percentage")
@Slf4j
@ConditionalOnProperty(name = "app.client.percentage.mock.enabled", havingValue = "true", matchIfMissing = false)
public class PercentageMockController {

    @GetMapping
    public PercentageResponse getPercentage() {
        if (Math.random() < 0.3) { // 30% de probabilidad de error
            throw new RuntimeException("percentage service temporarily unavailable");
        }

        int randomPercentage = (int) (Math.random() * 10) + 1;
        log.info("mock service returning percentage: {}%", randomPercentage);
        return new PercentageResponse(randomPercentage);
    }
}
