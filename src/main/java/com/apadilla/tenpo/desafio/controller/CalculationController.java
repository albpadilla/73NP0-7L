package com.apadilla.tenpo.desafio.controller;


import com.apadilla.tenpo.desafio.controller.dto.CalculationResponse;
import com.apadilla.tenpo.desafio.controller.mapper.CalculationMapper;
import com.apadilla.tenpo.desafio.service.CalculationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/calculation")
@Slf4j
@Validated
public class CalculationController {

    private final CalculationService calculationService;
    private final CalculationMapper calculationMapper;

    public CalculationController(
            CalculationService calculationService,
            CalculationMapper calculationMapper) {
        this.calculationService = calculationService;
        this.calculationMapper = calculationMapper;
    }

    @GetMapping
    public ResponseEntity<CalculationResponse> calculate(
            @Valid @NotNull(message = "num1 cannot be null")
            @DecimalMin(value = "0.0", message = "num1 must be positive")
            @RequestParam BigDecimal num1,
            @Valid @NotNull(message = "num2 cannot be null")
            @DecimalMin(value = "0.0", message = "num2 must be positive")
            @RequestParam BigDecimal num2) {
        log.info("Calculating... [num1: {}, num2: {}]", num1, num2);
        return new ResponseEntity<>(
                this.calculationMapper.mapModelToDTOResponse(
                        this.calculationService.calculate(num1, num2)
                )
                , HttpStatus.OK);
    }
}
