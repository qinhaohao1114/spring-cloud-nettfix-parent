package com.study.netflix.config;

import com.study.netfliex.pojo.Account;
import com.study.netflix.util.CurrentContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
public class ControllerIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteAddr = request.getRemoteAddr();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Map<String, String> map = (Map<String, String>) details.getDecodedDetails();
        String clientIp = map.get("clientIp");
        log.info("remoteIp:{},clientIp:{}", remoteAddr, clientIp);
        if (clientIp.equals(remoteAddr)) {
            Account instance = getInstance(map);
            CurrentContextHolder.put(instance);
            log.info(instance.toString());
            return true;
        }
        return false;
    }

    private Account getInstance(Map<String, String> map) {
        Field[] declaredFields = Account.class.getDeclaredFields();
        Account account = new Account();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                field.set(account, map.get(field.getName()));
            } catch (Exception e) {
                continue;
            }
        }
        return account;
    }
}
