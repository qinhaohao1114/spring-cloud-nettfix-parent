package com.cloud.auth.config;

import com.cloud.auth.annotation.Ig;
import com.cloud.auth.bean.Account;
import com.cloud.auth.service.JdbcUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class MyAccessTokenConverter extends DefaultAccessTokenConverter {

    @Autowired
    private JdbcUserDetailService jdbcUserDetailService;

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        //check时，不能直接放入过期时间，必须从token中取出来putall进去
        HashMap<String, Object> hashMap = new HashMap<>();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String clientIp = request.getRemoteAddr();
        hashMap.put("clientIp", clientIp);
        Authentication userAuthentication = authentication.getUserAuthentication();
        Object principal = null;
        if (userAuthentication == null) {
            String loginName = (String) token.getAdditionalInformation().get("lna");
            principal = jdbcUserDetailService.loadUserByUsername(loginName);
        } else {
            principal = authentication.getPrincipal();
        }
        setValue(hashMap, principal);
        if (token.getExpiration() != null) {
            hashMap.put("exp", token.getExpiration().getTime() / 1000);
        }
        return hashMap;
    }

    private void setValue(Map<String, Object> map, Object account) {
        Field[] declaredFields = account.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Ig.class)) {
                continue;
            }
            if (field.getName().equals("iat")) {
                map.put("iat", String.valueOf(new Date().getTime() / 1000));
            }
            if (field.getName().equals("exp")) {
                continue;
            }
            try {
                map.put(field.getName(), field.get(account));
            } catch (IllegalAccessException e) {
                log.warn("字段放入失败:{}", field.getName());
            }
        }
    }
}
