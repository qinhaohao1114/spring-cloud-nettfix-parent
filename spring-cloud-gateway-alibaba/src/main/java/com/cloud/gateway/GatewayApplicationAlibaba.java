package com.cloud.gateway;

import com.cloud.gateway.authentication.JWTAuthenticationGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplicationAlibaba {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplicationAlibaba.class, args);
    }

//    @Bean
//    public JWTAuthenticationGatewayFilterFactory jwtAuthenticationGatewayFilterFactory(){
//        return new JWTAuthenticationGatewayFilterFactory();
//    }
}
