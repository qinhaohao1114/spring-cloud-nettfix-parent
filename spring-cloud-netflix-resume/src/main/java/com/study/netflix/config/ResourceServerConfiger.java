package com.study.netflix.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

public class ResourceServerConfiger extends ResourceServerConfigurerAdapter {

    /**
     * 该⽅法⽤于定义资源服务器向远程认证服务器发起请求，进⾏token校验等事宜
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
