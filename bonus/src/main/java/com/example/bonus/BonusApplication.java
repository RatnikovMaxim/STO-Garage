package com.example.bonus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BonusApplication {
    public static void main(String[] args) {
        SpringApplication.run(BonusApplication.class, args);
    }
}
