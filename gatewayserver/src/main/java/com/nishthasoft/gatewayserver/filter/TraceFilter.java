package com.nishthasoft.gatewayserver.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@Order(1)
public class TraceFilter implements GlobalFilter {

    @Autowired
    FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestsHeaders = exchange.getRequest().getHeaders();
        if(isCorrelationIdPresent(requestsHeaders)){
            log.debug("Nishtha soft correlation-id found in tracing filter: {}",
                    filterUtility.getCorrelationId(requestsHeaders));

        }else{
            String correlationId = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange,correlationId);
            log.debug("Nishtha soft-correlationId generated in tracking filter: {} ", correlationId);
        }

        return chain.filter(exchange);
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders){
        if(filterUtility.getCorrelationId(requestHeaders) !=null){
            return true;
        }else{
            return false;
        }
    }
}
