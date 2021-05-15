package com.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.baizhi.dao")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudEmsEmpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudEmsEmpsApplication.class, args);
    }

}
