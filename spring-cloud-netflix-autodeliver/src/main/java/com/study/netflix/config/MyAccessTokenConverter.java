package com.study.netflix.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyAccessTokenConverter extends DefaultAccessTokenConverter {


    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        // 将map放⼊认证对象中，认证对象在controller中可以拿到
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
        oAuth2Authentication.setDetails(map);
        return oAuth2Authentication;
    }
}
