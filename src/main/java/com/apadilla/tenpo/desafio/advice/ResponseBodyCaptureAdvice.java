package com.apadilla.tenpo.desafio.advice;

import com.apadilla.tenpo.desafio.interceptor.domain.RequestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Advice para capturar response body y almacenarlo en {@link RequestContext}.
 * necesario para el interceptor de peticiones
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ResponseBodyCaptureAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        try {
            String path = request.getURI().getPath();
            if (path.startsWith("/api/") && !path.contains("/call-history")) {
                RequestContext context = RequestContext.getContext();
                if (context != null) {
                    String responseBodyJson = serializeResponseBody(body);
                    context.setResponseBody(responseBodyJson);
                }
            }
        } catch (Exception e) {
            log.error("error capturing response body", e);
        }
        return body;
    }

    private String serializeResponseBody(Object body) {
        if (body == null) return null;
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            return body.toString();
        }
    }
}
