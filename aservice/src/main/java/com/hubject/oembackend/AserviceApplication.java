package com.hubject.oembackend;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringCloudApplication
@ComponentScan(basePackages = "com.hubject")
public class AserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AserviceApplication.class, args);
    }
}
