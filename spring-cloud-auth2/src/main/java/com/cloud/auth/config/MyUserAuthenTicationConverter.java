package com.cloud.auth.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyUserAuthenTicationConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, String> stringMap = (Map<String, String>) super.convertUserAuthentication(authentication);
        stringMap.put("test_write", "测试一下");
        return stringMap;
    }
}
