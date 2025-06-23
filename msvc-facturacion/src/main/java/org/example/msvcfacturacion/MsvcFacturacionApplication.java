package org.example.msvcfacturacion;

import org.example.msvcfacturacion.entities.Tarifa;
import org.example.msvcfacturacion.services.TarifaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcFacturacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcFacturacionApplication.class, args);
    }

}
