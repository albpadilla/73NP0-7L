package com.apadilla.tenpo.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableRetry
@EnableAsync
public class TenpoDesafioApp {

    public static void main(String[] args) {
        SpringApplication.run(TenpoDesafioApp.class, args);
    }

}
