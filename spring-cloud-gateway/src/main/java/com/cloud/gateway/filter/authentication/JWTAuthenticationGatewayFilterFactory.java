package com.cloud.gateway.filter.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JWTAuthenticationGatewayFilterFactory extends AbstractAuthenticationGatewayFilterFactory<JWTAuthenticationGatewayFilterFactory.Config> {

    public JWTAuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

//    private final Map<String,MyJwtAccessTokenConverter> cacheJwtTokenConverter=new ConcurrentHashMap<>();

    @Override
    public Account doAuth(Config config, ServerWebExchange exchange) {
        String signKey = config.getSignKey();
//        MyJwtAccessTokenConverter jwtAccessTokenConverter = cacheJwtTokenConverter.get(signKey);
//        if(jwtAccessTokenConverter==null){
//            jwtAccessTokenConverter = new MyJwtAccessTokenConverter();
//            jwtAccessTokenConverter.setSigningKey(signKey);//签名密钥
//            jwtAccessTokenConverter.setVerifier(new MacSigner(signKey));
//            cacheJwtTokenConverter.put(signKey,jwtAccessTokenConverter);
//        }
        ServerHttpRequest request = exchange.getRequest();
        String jwt = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(jwt)) {
            throw new RuntimeException("缺少认证信息");
        }
        try {
//            Map<String, Object> map = jwtAccessTokenConverter.checkToken(jwt);
            return getAccountByJwt(new HashMap<>());
        } catch (Exception e) {
            return null;
        }
    }

    private Account getAccountByJwt(Map<String, Object> map) {
        Class<Account> clazz = Account.class;
        Account account = new Account();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object o = map.get(field.getName());
            try {
                field.set(account, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return account;
    }


    @Getter
    @Setter
    public static class Config extends AbstractAuthenticationGatewayFilterFactory.Config {

        private String signKey;

    }
}
