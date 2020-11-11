package com.cloud.gateway.config;


import com.cloud.gateway.filter.authentication.AuthInfoCarryStrategy;
import com.cloud.gateway.filter.authentication.AuthWriteToHeaderStrategy;
import com.cloud.gateway.filter.authentication.JWTAuthenticationGatewayFilterFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebExceptionHandler;

@Configuration
@EnableWebFlux
public class GatewayConfig implements WebFluxConfigurer {

    @Bean
    public ObjectMapper om() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JWTAuthenticationGatewayFilterFactory checkJWTAuthenticationGatewayFilterFactory() {
        return new JWTAuthenticationGatewayFilterFactory();
    }

    @Bean("AuthWriteToHeader")
    public AuthInfoCarryStrategy writeToHeaderStrategy() {
        return new AuthWriteToHeaderStrategy();
    }
}