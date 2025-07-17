package com.apadilla.tenpo.desafio.service.impl;

import com.apadilla.tenpo.desafio.client.PercentageClient;
import com.apadilla.tenpo.desafio.client.dto.PercentageResponse;
import com.apadilla.tenpo.desafio.model.CalculationResult;
import com.apadilla.tenpo.desafio.service.CalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    private final PercentageClient percentageClient;

    public CalculationServiceImpl(PercentageClient percentageClient) {
        this.percentageClient = percentageClient;
    }

    @Override
    public CalculationResult calculate(BigDecimal num1, BigDecimal num2) {
        log.debug("Calculating... [num1: {}, num2: {}]", num1, num2);

        PercentageResponse percentageResponse = this.percentageClient.getPercentage();

        log.debug("External percentage: {}", percentageResponse);

        BigDecimal sum = num1.add(num2);
        BigDecimal result = sum.multiply(BigDecimal.valueOf(percentageResponse.percent()));

        log.debug("Result: {} * ( {} * {} ) = {}", sum, sum, percentageResponse, result);
        return CalculationResult.builder()
                .percent(percentageResponse.percent())
                .result(result)
                .build();
    }
}
