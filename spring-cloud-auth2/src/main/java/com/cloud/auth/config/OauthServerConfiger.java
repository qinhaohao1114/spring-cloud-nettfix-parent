package com.cloud.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OauthServerConfiger extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager securityConfiger;

    private static String sign_key = "qhhs";

    @Autowired
    private MyAccessTokenConverter myAccessTokenConverter;

    @Autowired
    private MyUserAuthenTicationConverter myUserAuthenTicationConverter;

    @Autowired
    private DataSource dataSource;

    /**
     * 认证服务器最终是以api接⼝的⽅式对外提供服务（校验合法性并⽣成令牌、校验令牌等）
     * 那么，以api接⼝⽅式对外的话，就涉及到接⼝的访问权限，我们需要在这⾥进⾏必要的配
     * 置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }

    /**
     * 客户端详情配置，
     * ⽐如client_id， secret
     * 当前这个服务就如同QQ平台，拉勾⽹作为客户端需要qq平台进⾏登录授权认证等，提前需
     * 要到QQ平台注册， QQ平台会给拉勾⽹
     * 颁发client_id等必要参数，表明客户端是谁
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
//        clients.inMemory()
//                .withClient("client_qhh")
//                .secret("qhh921114")
//                .resourceIds("autodeliver")
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("all");
        clients.withClientDetails(createJdbcClientDetailsService());
    }

    public JdbcClientDetailsService createJdbcClientDetailsService() {

        return new JdbcClientDetailsService(dataSource);
    }


    /**
     * 认证服务器是玩转token的，那么这⾥配置token令牌管理相关（token此时就是⼀个字符
     * 串，当下的token需要在服务器端存储，
     * 那么存储在哪⾥呢？都是在这⾥配置）
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints.tokenStore(tokenStore())
//                .accessTokenConverter(jwtAccessTokenConverter())  配置之后check接口会进行调用
                .tokenServices(authorizationServerTokenServices())
                .authenticationManager(securityConfiger)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(sign_key);//签名密钥
        jwtAccessTokenConverter.setVerifier(new MacSigner(sign_key));
//        myAccessTokenConverter.setUserTokenConverter(myUserAuthenTicationConverter);
        jwtAccessTokenConverter.setAccessTokenConverter(myAccessTokenConverter);
        return jwtAccessTokenConverter;
    }

    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        //使用默认实现
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenStore(tokenStore());

        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());

        defaultTokenServices.setAccessTokenValiditySeconds(600);
        defaultTokenServices.setRefreshTokenValiditySeconds(259200);
        return defaultTokenServices;
    }
}
