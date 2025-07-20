package com.apadilla.tenpo.desafio.controller.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CallHistoryResponse(
        Long id,
        LocalDateTime timestamp,
        String endpoint,
        String httpMethod,
        String requestParameters,
        String responseBody,
        Integer responseStatus,
        String errorMessage
) {
}
