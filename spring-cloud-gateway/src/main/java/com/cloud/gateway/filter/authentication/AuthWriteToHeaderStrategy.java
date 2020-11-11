package com.cloud.gateway.filter.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证信息以KEY=VALUE的形式放入头信息中。推荐方式。
 */
@Slf4j
public class AuthWriteToHeaderStrategy extends AbstractAuthInfoCarryStrategy {

    /**
     * 租户ID，登录情况下一定存在
     */
    private static final String DEFAULT_TENANT_ID_KEY = "DF-Tenant-ID";

    /**
     * 账号ID，可能为空
     */
    private static final String DEFAULT_ACCOUNT_ID_KEY = "DF-Account-ID";

    @Override
    Mono<Void> doChange(Account accountInfo, ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(DEFAULT_TENANT_ID_KEY, accountInfo.getTid())
                .header(DEFAULT_ACCOUNT_ID_KEY, String.valueOf(accountInfo.getSub())).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public boolean checkParamsConflict(Account accountInfo, ServerWebExchange exchange) {
        // HttpHeaders headers = exchange.getRequest().getHeaders();
        // return headers.containsKey(DEFAULT_TENANT_ID_KEY) ||
        // headers.containsKey(DEFAULT_ACCOUNT_ID_KEY);
        return false;
    }

}
