package com.apadilla.tenpo.desafio.service.impl;

import com.apadilla.tenpo.desafio.model.CalculationResult;
import com.apadilla.tenpo.desafio.service.CalculationService;
import com.apadilla.tenpo.desafio.service.PercentageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    private final PercentageService percentageService;

    public CalculationServiceImpl(PercentageService percentageService) {
        this.percentageService = percentageService;
    }

    @Override
    public CalculationResult calculate(BigDecimal num1, BigDecimal num2) {
        BigDecimal percentage = percentageService.getPercentage();
        log.info("external percentage: {}", percentage);

        BigDecimal sum = num1.add(num2);
        BigDecimal result = this.applyPercentage(sum, percentage);

        result = result.setScale(2, RoundingMode.HALF_UP);

        return CalculationResult.builder()
                .appliedPercentage(percentage.doubleValue())
                .baseSum(sum)
                .finalResult(result)
                .percentageAmount(result.subtract(sum))
                .build();
    }

    @Override
    public BigDecimal applyPercentage(BigDecimal amount, BigDecimal percentage) {
        BigDecimal percentageDecimal = percentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        BigDecimal percentageAmount = amount.multiply(percentageDecimal);
        return amount.add(percentageAmount);
    }
}
