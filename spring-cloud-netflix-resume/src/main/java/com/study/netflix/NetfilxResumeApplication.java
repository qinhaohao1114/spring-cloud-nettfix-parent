package com.study.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:22 上午
 **/
@SpringBootApplication
@EntityScan("com.study.netfliex.pojo")
//@EnableDiscoveryClient
public class NetfilxResumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetfilxResumeApplication.class, args);
    }
}
