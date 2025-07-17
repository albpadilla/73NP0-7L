package com.apadilla.tenpo.desafio.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
public record CalculationResult(BigDecimal result, double percent) {
}
