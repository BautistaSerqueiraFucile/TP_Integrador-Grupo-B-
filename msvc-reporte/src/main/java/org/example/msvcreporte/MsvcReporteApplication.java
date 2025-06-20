package org.example.msvcreporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients(basePackages = "org.example.msvcreporte.clients")
@SpringBootApplication
public class MsvcReporteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcReporteApplication.class, args);
    }

}
