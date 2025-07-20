package com.apadilla.tenpo.desafio.controller.mapper;

import com.apadilla.tenpo.desafio.controller.dto.CalculationResponse;
import com.apadilla.tenpo.desafio.model.CalculationResult;
import org.springframework.stereotype.Component;

@Component
public class CalculationMapper {

    public CalculationResponse mapModelToDTOResponse(CalculationResult model) {
        return CalculationResponse.builder()
                .baseSum(model.baseSum())
                .appliedPercentage(model.appliedPercentage())
                .percentageAmount(model.percentageAmount())
                .finalResult(model.finalResult())
                .build();
    }
}
