package com.study.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:22 上午
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class NetfilxBussinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetfilxBussinessApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}
