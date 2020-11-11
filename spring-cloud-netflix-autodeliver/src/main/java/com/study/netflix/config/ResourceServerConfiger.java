package com.study.netflix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@EnableWebSecurity
public class ResourceServerConfiger extends ResourceServerConfigurerAdapter {

    private static String sign_key = "qhhs";

    @Autowired
    private MyAccessTokenConverter myAccessTokenConverter;

    /**
     * 该⽅法⽤于定义资源服务器向远程认证服务器发起请求，进⾏token校验等事宜
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("autodeliver");
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:9999/oauth/check_token");
//        remoteTokenServices.setClientId("client_qhh");
//        remoteTokenServices.setClientSecret("qhh921114");
//        resources.tokenServices(remoteTokenServices);
        resources.resourceId("autodeliver").tokenStore(tokenStore()).stateless(true);//无状态设置
    }

    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(sign_key);//签名密钥
        jwtAccessTokenConverter.setVerifier(new MacSigner(sign_key));
        jwtAccessTokenConverter.setAccessTokenConverter(myAccessTokenConverter);
        return jwtAccessTokenConverter;

    }

    /**
     * 场景：⼀个服务中可能有很多资源（API接⼝）
     * 某⼀些API接⼝，需要先认证，才能访问
     * 某⼀些API接⼝，压根就不需要认证，本来就是对外开放的接⼝
     * 我们就需要对不同特点的接⼝区分对待（在当前configure⽅法中完成），设置
     * 是否需要经过认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/autodeliver/**").authenticated()
                .antMatchers("/demo/**").authenticated()
                .anyRequest().permitAll();
    }
}
