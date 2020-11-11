package com.study.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:22 上午
 **/
@SpringBootApplication
@EntityScan("com.study.netfliex.pojo")
public class NetfilxOrderApplication {


    public static void main(String[] args) {
        SpringApplication.run(NetfilxOrderApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
