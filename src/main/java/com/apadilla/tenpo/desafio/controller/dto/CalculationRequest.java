package com.apadilla.tenpo.desafio.controller.dto;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Data
@Builder
public class CalculationRequest {
    @NotNull(message = "num1 cannot be null")
    @DecimalMin(value = "0.0", message = "num1 must be positive")
    private BigDecimal num1;

    @NotNull(message = "num2 cannot be null")
    @DecimalMin(value = "0.0", message = "num1 must be positive")
    private BigDecimal num2;
}
