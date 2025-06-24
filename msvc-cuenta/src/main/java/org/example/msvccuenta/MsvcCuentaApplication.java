package org.example.msvccuenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcCuentaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcCuentaApplication.class, args);
    }

}
