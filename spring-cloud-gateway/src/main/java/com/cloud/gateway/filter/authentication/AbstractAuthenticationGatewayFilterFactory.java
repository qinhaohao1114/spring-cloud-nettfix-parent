package com.cloud.gateway.filter.authentication;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public abstract class AbstractAuthenticationGatewayFilterFactory<C extends AbstractAuthenticationGatewayFilterFactory.Config> extends AbstractGatewayFilterFactory<C>
        implements Authentication, ApplicationContextAware {

    private ApplicationContext ac;

    public AbstractAuthenticationGatewayFilterFactory(Class<C> configClass) {
        super(configClass);
    }

    @Override
    public GatewayFilter apply(C config) {
        AuthInfoCarryStrategy carryStrategy = ac.getBean(config.getAuthInfoCarryStrategy(), AuthInfoCarryStrategy.class);
        List<RequestFilterPattern> whitelist = config.getValidPatterns();
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpMethod method = request.getMethod();
            if (method == null) {
                log.error("可能遇到非标准HTTP Method:" + request.getMethodValue());
                return Mono.error(new RuntimeException("400"));
            } else if (method == HttpMethod.OPTIONS) {
                if (log.isDebugEnabled()) {
                    log.debug("Options请求默认放行");
                }
                return chain.filter(exchange);
            }
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            if (route == null) {
                log.warn("找不到路由参数");
                return Mono.error(new RuntimeException("BAD_REQUEST"));
            }
            String routeId = route.getId();
            String path = request.getPath().value();
            if (isVerified(exchange)) {
                log.warn("已经认证过不能再次认证，请检查配置项routeId:[{}], method[{}], uri:[{}]", routeId, method, path);
                return chain.filter(exchange);
            }
            boolean passAuth = whitelist.stream().anyMatch(p -> p.match(method, path));
            if (passAuth) {
                log.info("免认证通过 routeId:[{}], method[{}], uri:[{}] ", routeId, method, path);
                return chain.filter(exchange);
            }
            Account accountInfo = doAuth(config, exchange);
            exchange.getAttributes().put(GATEWAY_VERIFIED_ATTR, accountInfo);
            return carryStrategy.apply(accountInfo, exchange, chain);
        };
    }

    /**
     * 执行认证操作，由具体实现类实现
     *
     * @return 认证信息，如果验证失败抛出异常
     */
    public abstract Account doAuth(C config, ServerWebExchange exchange);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }

    @Setter
    @Getter
    public static class Config {

        /**
         * 暂时只支持标准的HTTP Method和通配符*
         */
        private final List<String> validMethods = new ArrayList<>();

        /**
         * 认证信息传递给后端服务时的携带策略。为空则不会携带认证信息到后端服务。
         *
         * @see AuthInfoCarryStrategy
         */
        private String authInfoCarryStrategy;

        /**
         * 在白名单内的URI不需要认证校验。
         * <p>
         * 格式[method(空格)url正则表达式]，例如: GET /app/info, * /images/**
         */
        private List<String> whiteList;

        {
            validMethods.addAll(Stream.of(HttpMethod.values()).map(Enum::name).map(String::toLowerCase)
                    .collect(Collectors.toList()));
            validMethods.add("*");
        }

        public List<RequestFilterPattern> getValidPatterns() {
            if (whiteList == null || whiteList.isEmpty()) {
                return Collections.emptyList();
            }
            List<RequestFilterPattern> validPatterns = new ArrayList<>();
            whiteList.forEach(r -> {
                String[] splitRegx = r.split(" ", 2);
                if (splitRegx.length != 2) {
                    throw new RuntimeException("白名单表达式书写错误:" + r);
                }
                String method = splitRegx[0];
                if (!validMethods.contains(method.toLowerCase())) {
                    throw new RuntimeException("白名单表达式书写错误:" + r + "method:" + method);
                }
                String urlRegx = splitRegx[1];
                RequestFilterPattern urlPattern = new RequestFilterPattern().setMethod(method).setUrlRegx(urlRegx);
                validPatterns.add(urlPattern);
            });
            return validPatterns;
        }

    }

    @Setter
    @Getter
    @Accessors(chain = true)
    public static class RequestFilterPattern {

        private static final AntPathMatcher matcher = new AntPathMatcher();

        static {
            matcher.setCaseSensitive(false);
            matcher.setCachePatterns(false);
        }

        private String method;

        private String urlRegx;

        public boolean match(HttpMethod method, String path) {
            return this.match(method.name(), path);
        }

        public boolean match(String method, String path) {
            if (!"*".equals(this.method)) {
                boolean match = this.method.equalsIgnoreCase(method);
                if (!match) {
                    return false;
                }
            }
            return matcher.match(urlRegx, path);
        }

    }
}
