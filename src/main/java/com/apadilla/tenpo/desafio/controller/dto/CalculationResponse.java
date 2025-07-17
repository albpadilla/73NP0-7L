package com.apadilla.tenpo.desafio.controller.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CalculationResponse(BigDecimal percent, BigDecimal result) {
}
