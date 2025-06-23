package org.example.msvcadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "org.example.msvcadmin.clients")
@SpringBootApplication
public class MsvcAdminApplication {

    public static void main(String[] args) {

        SpringApplication.run(MsvcAdminApplication.class, args);
    }

}
