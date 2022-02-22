package com.hubject.common.config.trace;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Order(value = Ordered.HIGHEST_PRECEDENCE + 100)
@Component
@WebFilter(filterName = "MDCFilter", urlPatterns = "/*")
public class MDCFilter extends OncePerRequestFilter {

    private static final String GATEWAY_X_TRACE_ID = "gateway-X-traceId";
    private static final String SPAN_ID = "spanId";



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = req.getHeader(GATEWAY_X_TRACE_ID);

            String spanId = UUID.randomUUID().toString();
            MDC.put(GATEWAY_X_TRACE_ID, traceId);
            MDC.put(SPAN_ID, spanId);
        }finally {
            filterChain.doFilter(req, httpServletResponse);
            MDC.clear();
        }
    }
}
