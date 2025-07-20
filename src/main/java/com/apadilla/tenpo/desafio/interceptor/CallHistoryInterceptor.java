package com.apadilla.tenpo.desafio.interceptor;

import com.apadilla.tenpo.desafio.entity.CallHistory;
import com.apadilla.tenpo.desafio.service.CallHistoryService;
import com.apadilla.tenpo.desafio.interceptor.domain.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

/**
 * Interceptor HTTP que almacena en BD las peticiones a la api de cálculo de porcentajes.
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CallHistoryInterceptor implements HandlerInterceptor {

    private final CallHistoryService callHistoryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            RequestContext context = RequestContext.builder()
                    .startTime(LocalDateTime.now())
                    .endpoint(request.getRequestURI())
                    .httpMethod(request.getMethod())
                    .requestBody(getRequestParameters(request))
                    .build();

            RequestContext.setContext(context);
        } catch (Exception e) {
            log.error("error in CallHistoryInterceptor preHandle", e);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            RequestContext context = RequestContext.getContext();
            if (context == null) {
                log.warn("requestContext is null in afterCompletion");
                return;
            }

            context.setResponseStatus(response.getStatus());
            if (ex != null) {
                context.setErrorMessage(ex.getMessage());
            }
            saveCallHistoryAsync(context);

            log.info("call history saved for: {} {} - Status: {}",
                    context.getHttpMethod(), context.getEndpoint(),
                    context.getResponseStatus());
        } catch (Exception e) {
            log.error("error in CallHistoryInterceptor afterCompletion", e);
        } finally {
            RequestContext.clear();
        }
    }

    private String getRequestParameters(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();

        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            params.append("query: ").append(queryString);
        }

        String contentType = request.getContentType();
        if (contentType != null) {
            if (!params.isEmpty()) params.append(", ");
            params.append("content-Type: ").append(contentType);
        }

        int contentLength = request.getContentLength();
        if (contentLength > 0) {
            if (!params.isEmpty()) params.append(", ");
            params.append("content-Length: ").append(contentLength);
        }

        return !params.isEmpty() ? params.toString() : null;
    }

    private void saveCallHistoryAsync(RequestContext context) {
        try {
            CallHistory callHistory = new CallHistory();
            callHistory.setTimestamp(context.getStartTime());
            callHistory.setEndpoint(context.getEndpoint());
            callHistory.setHttpMethod(context.getHttpMethod());
            callHistory.setRequestParameters(context.getRequestBody());
            callHistory.setResponseBody(context.getResponseBody());
            callHistory.setResponseStatus(context.getResponseStatus());
            callHistory.setErrorMessage(context.getErrorMessage());

            callHistoryService.saveCallHistoryAsync(callHistory);

        } catch (Exception e) {
            log.error("error saving call history", e);
        }
    }
}
