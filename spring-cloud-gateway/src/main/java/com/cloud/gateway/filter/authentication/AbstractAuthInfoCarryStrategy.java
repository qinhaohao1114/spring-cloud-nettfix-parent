package com.cloud.gateway.filter.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public abstract class AbstractAuthInfoCarryStrategy implements AuthInfoCarryStrategy {

    /**
     * 应用修改
     */
    abstract Mono<Void> doChange(Account accountInfo, ServerWebExchange exchange, GatewayFilterChain chain);

    @Override
    public Mono<Void> apply(Account accountInfo, ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isWrote(exchange)) {
            return chain.filter(exchange);
        }
        if (checkParamsConflict(accountInfo, exchange)) {
            log.error("参数冲突中止请求");
            throw new RuntimeException("BAD_REQUEST");
        }
        return doChange(accountInfo, exchange, chain);
    }

}
