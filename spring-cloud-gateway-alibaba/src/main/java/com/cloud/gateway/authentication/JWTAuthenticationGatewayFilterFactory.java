package com.cloud.gateway.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
public class JWTAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<JWTAuthenticationGatewayFilterFactory.Config> {

//    public JWTAuthenticationGatewayFilterFactory() {
//        super(Config.class);
//    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            URI uri = request.getURI();
            List<String> whiteList = config.getWhiteList();
            String authInfoCarryStrategy = config.getAuthInfoCarryStrategy();
            String signKey = config.getSignKey();
            System.out.println(uri);
            return chain.filter(exchange.mutate().request(request).build());
        };
    }


    @Getter
    @Setter
    public static class Config {
        /**
         * 认证信息传递给后端服务时的携带策略。为空则不会携带认证信息到后端服务。
         */
        private String authInfoCarryStrategy;

        /**
         * 在白名单内的URI不需要认证校验。
         * <p>
         * 格式[method(空格)url正则表达式]，例如: GET /app/info, * /images/**
         */
        private List<String> whiteList;

        private String signKey;

    }
}
