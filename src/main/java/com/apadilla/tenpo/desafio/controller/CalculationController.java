package com.apadilla.tenpo.desafio.controller;


import com.apadilla.tenpo.desafio.controller.dto.CalculationRequest;
import com.apadilla.tenpo.desafio.controller.dto.CalculationResponse;
import com.apadilla.tenpo.desafio.model.CalculationResult;
import com.apadilla.tenpo.desafio.service.CalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/calculation")
@Slf4j
@Validated
public class CalculationController {

    private final CalculationService calculationService;

    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping
    public ResponseEntity<CalculationResponse> calculateWithDynamicPercent(
            @RequestBody CalculationRequest request) {

        CalculationResult calculationResult =
                this.calculationService.calculate(
                        request.getNum1(),
                        request.getNum2()
                );

        return new ResponseEntity<>(CalculationResponse.builder()
                .percent(calculationResult.percent())
                .result(calculationResult.result())
                .build(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<String> getCalculationHistory(){
        return new ResponseEntity<>("History", HttpStatus.OK);
    }
}
