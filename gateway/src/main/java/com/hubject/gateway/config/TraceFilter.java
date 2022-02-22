package com.hubject.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.UUID;

@Component
@Slf4j
public class TraceFilter implements GlobalFilter, Ordered {

    private static final String GATEWAY_X_TRACE_ID = "gateway-X-traceId";
    private static final String SPAN_ID = "spanId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = UUID.randomUUID().toString().replace("-","");
        String spanId = UUID.randomUUID().toString();

        ServerHttpRequest request = exchange.getRequest().mutate().header(GATEWAY_X_TRACE_ID, traceId).build();
        InetSocketAddress remoteAddress = request.getRemoteAddress();

        MDC.put(GATEWAY_X_TRACE_ID, traceId);
        MDC.put(SPAN_ID, spanId);

        log.info("[gateway] ip: {}, traceId: {}", remoteAddress.getHostName(), traceId);
        return chain.filter(exchange.mutate().request(request).build());
    }
    
    //过滤器顺序
    @Override
    public int getOrder() {
        return -2147483648;
    }
}
