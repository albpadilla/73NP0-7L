package com.apadilla.tenpo.desafio.interceptor.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Clase para mantener información contextual sobre una solicitud HTTP y su respuesta.
 * Necesaria para mantener el historial de peticiones en BD.
 *
 */
@Data
@Builder
public class RequestContext {
    private static final ThreadLocal<RequestContext> contextHolder = new ThreadLocal<>();

    private LocalDateTime startTime;
    private String endpoint;
    private String httpMethod;
    private String requestBody;
    private Integer responseStatus;
    private String responseBody;
    private String errorMessage;

    public static void setContext(RequestContext context) {
        contextHolder.set(context);
    }

    public static RequestContext getContext() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }

}
