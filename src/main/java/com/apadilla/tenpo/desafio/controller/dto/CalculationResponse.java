package com.apadilla.tenpo.desafio.controller.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CalculationResponse(
        BigDecimal baseSum,
        BigDecimal finalResult,
        double appliedPercentage,
        BigDecimal percentageAmount
) {
}
