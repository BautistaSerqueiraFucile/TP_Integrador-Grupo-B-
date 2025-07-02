package org.example.msvcadmin.clients;

import org.example.msvcadmin.models.Factura;
import org.example.msvcadmin.models.MonopatinViajeDTO;
import org.example.msvcadmin.models.ReporteUsoMonopatinDTO;
import org.example.msvcadmin.models.ReporteUsuarioActivoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de reportes (puerto 8006).
 * Permite obtener diferentes tipos de reportes relacionados al uso de monopatines, usuarios y facturación.
 */
@FeignClient(name = "msvc-reporte", url = "http://localhost:8006")
public interface ReporteClient {


    @GetMapping("/reporte/uso-monopatines")
    ResponseEntity<List<ReporteUsoMonopatinDTO>> getReporteUsoMonopatines();

    /**
     * Obtiene los monopatines más usados durante un año determinado.
     *
     * @param anio Año a consultar.
     * @param minViajes Cantidad mínima de viajes para considerar en el reporte.
     * @return Lista de monopatines con su cantidad de usos.
     */
    @GetMapping("/reporte/monopatines-mas-usados")
    ResponseEntity<List<MonopatinViajeDTO>> getMonopatinesFrecuentes(@RequestParam("anio") int anio, @RequestParam("minViajes") Long minViajes);


    @GetMapping("reporte/facturacion-total")
    double getFacturacionTotal(
            @RequestParam("fechaDesde") LocalDate fechaDesde,
            @RequestParam("fechaHasta") LocalDate fechaHasta
    );


    /**
     * Obtiene los usuarios más activos o destacados en un rango de fechas.
     *
     * @param desde Fecha de inicio del período (formato: yyyy-MM-dd).
     * @param hasta Fecha de fin del período (formato: yyyy-MM-dd).
     * @param tipo Tipo de usuario (por ejemplo: "regular", "premium").
     * @return Lista de usuarios top con sus estadísticas.
     */
    @GetMapping("/reporte/usuarios-top")
    ResponseEntity<List<ReporteUsuarioActivoDTO>> getUsuariosTop(@RequestParam("fechaDesde") String desde,
                                                                 @RequestParam("fechaHasta") String hasta,
                                                                 @RequestParam("tipoUsuario") String tipo);
}
