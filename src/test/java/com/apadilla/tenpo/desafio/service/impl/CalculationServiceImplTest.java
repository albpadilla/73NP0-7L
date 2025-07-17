package com.apadilla.tenpo.desafio.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculationServiceImplTest {

    @InjectMocks
    private CalculationServiceImpl calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationServiceImpl();
    }

    @Test
    void calculate_shouldReturnCorrectSum_whenValidNumbers() {
        // Given
        BigDecimal num1 = BigDecimal.valueOf(5);
        BigDecimal num2 = BigDecimal.valueOf(5);
        BigDecimal expected = BigDecimal.valueOf(11.0); // (5 + 5) * 1.1 = 11

        // When
        BigDecimal result = calculationService.calculate(num1, num2);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void calculate_shouldHandleZeroValues() {
        // Given
        BigDecimal num1 = BigDecimal.ZERO;
        BigDecimal num2 = BigDecimal.ZERO;
        BigDecimal expected = BigDecimal.valueOf(0.0); // (0 + 0) * 1.1 = 0

        // When
        BigDecimal result = calculationService.calculate(num1, num2);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void calculate_shouldHandleNegativeNumbers() {
        // Given
        BigDecimal num1 = BigDecimal.valueOf(-5);
        BigDecimal num2 = BigDecimal.valueOf(3);
        BigDecimal expected = BigDecimal.valueOf(-2.2); // (-5 + 3) * 1.1 = -2.2

        // When
        BigDecimal result = calculationService.calculate(num1, num2);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void calculate_shouldHandleDecimalNumbers() {
        // Given
        BigDecimal num1 = BigDecimal.valueOf(2.5);
        BigDecimal num2 = BigDecimal.valueOf(3.5);
        BigDecimal expected = BigDecimal.valueOf(6.60); // (2.5 + 3.5) * 1.1 = 6.6

        // When
        BigDecimal result = calculationService.calculate(num1, num2);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void calculate_shouldApplyTenPercentCorrectly() {
        // Given
        BigDecimal num1 = BigDecimal.valueOf(10);
        BigDecimal num2 = BigDecimal.valueOf(20);
        BigDecimal expected = BigDecimal.valueOf(33.0); // (10 + 20) * 1.1 = 33

        // When
        BigDecimal result = calculationService.calculate(num1, num2);

        // Then
        assertEquals(expected, result);
    }
}