package com.apadilla.tenpo.desafio.service;

import com.apadilla.tenpo.desafio.model.CalculationResult;

import java.math.BigDecimal;

public interface CalculationService {
    public CalculationResult calculate(BigDecimal num1, BigDecimal num2);
}
