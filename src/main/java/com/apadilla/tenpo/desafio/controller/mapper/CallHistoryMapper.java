package com.apadilla.tenpo.desafio.controller.mapper;

import com.apadilla.tenpo.desafio.controller.dto.CallHistoryResponse;
import com.apadilla.tenpo.desafio.entity.CallHistory;
import org.springframework.stereotype.Component;

@Component
public class CallHistoryMapper {

    public CallHistoryResponse mapEntityToDTOResponse(CallHistory entity) {
        return CallHistoryResponse.builder()
                .id(entity.getId())
                .timestamp(entity.getTimestamp())
                .endpoint(entity.getEndpoint())
                .httpMethod(entity.getHttpMethod())
                .requestParameters(entity.getRequestParameters())
                .responseBody(entity.getResponseBody())
                .responseStatus(entity.getResponseStatus())
                .errorMessage(entity.getErrorMessage())
                .build();
    }
}
