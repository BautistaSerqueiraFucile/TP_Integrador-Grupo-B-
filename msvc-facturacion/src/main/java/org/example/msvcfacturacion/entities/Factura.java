package org.example.msvcfacturacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.msvcfacturacion.dtos.FacturaRequestDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "facturacion")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_viaje", nullable = false)
    private Long idViaje;

    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta = null;

    @Column(name = "costo_tarifa", nullable = false)
    private double costoTarifa = 0;

    @Column(name = "tarifa_pausa", nullable = false)
    private double tarifaPausa = 0;

    @Column(name = "precio_viaje", nullable = false)
    private double precioViaje = 0;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha; // o popdria ser Solo la fecha con LocalDate (yyyy-MM-dd)

    public Factura(FacturaRequestDTO entity) {
        this.idUsuario = entity.getIdUsuario();
        this.idViaje = entity.getIdViaje();
        this.fecha = transformarFecha(entity.getFecha(), entity.getHora());
    }

    private LocalDate transformarFecha(String fecha, String hora){
        LocalDate localDate = LocalDate.parse(fecha);
        LocalTime localTime = LocalTime.parse(hora);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return LocalDate.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
