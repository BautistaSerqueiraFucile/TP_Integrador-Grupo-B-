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

    @Column(name = "tarifa_basica", nullable = false)
    private double tarifaBasica;

    @Column(name = "tarifa_premium", nullable = false)
    private double tarifaPremium;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "fecha", nullable = false)
    private Date fecha; // o popdria ser Solo la fecha con LocalDate (yyyy-MM-dd)

    public Factura(FacturaRequestDTO entity) {
        this.idUsuario = entity.getIdUsuario();
        this.idViaje = entity.getIdViaje();
        this.tarifaBasica = entity.getTarifaBasica();
        this.tarifaPremium = entity.getTarifaPremium();
        this.precio = entity.getPrecio();
        this.fecha = transformarFecha(entity.getFecha(), entity.getHora());
    }

    private Date transformarFecha(String fecha, String hora){
        LocalDate localDate = LocalDate.parse(fecha);
        LocalTime localTime = LocalTime.parse(hora);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
