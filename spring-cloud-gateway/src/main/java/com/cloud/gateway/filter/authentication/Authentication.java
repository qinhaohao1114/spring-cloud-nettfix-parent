package com.cloud.gateway.filter.authentication;

import org.springframework.web.server.ServerWebExchange;

/**
 * 认证接口
 */
public interface Authentication {

    /**
     * 认证通过后需要维护此对象到Exchange Attribute中
     */
    String GATEWAY_VERIFIED_ATTR = Authentication.class.getName() + ".GATEWAY_VERIFIED_ATTR";

    /**
     * 是否已经认证过
     *
     * @return true 已认证 false 未认证
     */
    default boolean isVerified(ServerWebExchange exchange) {
        return exchange.getAttribute(GATEWAY_VERIFIED_ATTR) != null;
    }

}
