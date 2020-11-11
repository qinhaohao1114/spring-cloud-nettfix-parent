package com.study.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:22 上午
 **/
@SpringBootApplication
@EntityScan("com.study.netfliex.pojo")
public class NetfilxAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetfilxAccountApplication.class, args);
    }
}
