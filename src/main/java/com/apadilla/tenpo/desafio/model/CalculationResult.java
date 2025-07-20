package com.apadilla.tenpo.desafio.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CalculationResult(
        BigDecimal baseSum,
        BigDecimal finalResult,
        double appliedPercentage,
        BigDecimal percentageAmount
) { }
