package com.hubject.common.config.trace;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TraceInterceptor implements RequestInterceptor {

    private static final String TRACE_ID = "gateway-X-traceId";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.debug("进入feign拦截器...THREAD_ID: {}", MDC.get(TRACE_ID));
        requestTemplate.header(TRACE_ID, MDC.get(TRACE_ID));
    }
}
