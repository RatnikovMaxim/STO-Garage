package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class CatalogStoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogStoApplication.class, args);
    }

}
