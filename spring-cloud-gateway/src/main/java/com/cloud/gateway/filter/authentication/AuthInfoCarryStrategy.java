package com.cloud.gateway.filter.authentication;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证信息传递给后端服务时的携带策略，默认支持Header和Payload两种方式。
 * <p>
 * 实现新的携带策略时：
 * <p>
 * 1、需要检测参数命名以防止可能的参数冲突，默认发生参数冲突时需要抛出异常
 * <p>
 * 2、需要注册为Spring Bean
 */
public interface AuthInfoCarryStrategy {

    /**
     * 改写请求后需要维护此对象到Exchange Attribute中，防止重复写入
     */
    String GATEWAY_WROTE_ATTR = AuthInfoCarryStrategy.class.getName() + ".GATEWAY_WROTE_ATTR";

    /**
     * 请求是否已经被改写，已改写的请求不会再执行策略
     *
     * @return true 已改写 false 未改写
     */
    default boolean isWrote(ServerWebExchange exchange) {
        return exchange.getAttribute(GATEWAY_WROTE_ATTR) != null;
    }

    /**
     * 检查参数是否有冲突
     *
     * @return true 有冲突 false 无冲突
     */
    boolean checkParamsConflict(Account account, ServerWebExchange exchange);

    /**
     * 执行策略
     */
    Mono<Void> apply(Account accountInfo, ServerWebExchange exchange, GatewayFilterChain chain);

}
