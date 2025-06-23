package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de reportes (puerto 8006).
 * Permite obtener diferentes tipos de reportes relacionados al uso de monopatines, usuarios y facturación.
 */
@FeignClient(name = "reporte", url = "http://localhost:8006")
public interface ReporteClient {

    /**
     * Obtiene un reporte de uso de monopatines.
     *
     * @param incluirPausas Indica si el reporte debe incluir también los tiempos de pausa en los viajes.
     * @return Lista de mapas con datos estadísticos por monopatín.
     */
    @GetMapping("/reporte/uso-monopatines")
    List<Map<String, Object>> getReporteUsoMonopatines(@RequestParam("incluirPausas") boolean incluirPausas);

    /**
     * Obtiene los monopatines más usados durante un año determinado.
     *
     * @param anio Año a consultar.
     * @param minViajes Cantidad mínima de viajes para considerar en el reporte.
     * @return Lista de monopatines con su cantidad de usos.
     */
    @GetMapping("/reporte/monopatines-mas-usados")
    List<Map<String, Object>> getMonopatinesFrecuentes(@RequestParam("anio") int anio, @RequestParam("minViajes") int minViajes);

    /**
     * Obtiene el total de facturación entre dos meses de un año dado.
     *
     * @param anio Año a consultar.
     * @param desde Mes de inicio (inclusive).
     * @param hasta Mes de fin (inclusive).
     * @return Mapa con el total facturado y datos relacionados.
     */
    @GetMapping("/reporte/facturacion-total")
    Map<String, Object> getFacturacionTotal(@RequestParam("anio") int anio,
                                            @RequestParam("mesDesde") int desde,
                                            @RequestParam("mesHasta") int hasta);

    /**
     * Obtiene los usuarios más activos o destacados en un rango de fechas.
     *
     * @param desde Fecha de inicio del período (formato: yyyy-MM-dd).
     * @param hasta Fecha de fin del período (formato: yyyy-MM-dd).
     * @param tipo Tipo de usuario (por ejemplo: "regular", "premium").
     * @return Lista de usuarios top con sus estadísticas.
     */
    @GetMapping("/reporte/usuarios-top")
    List<Map<String, Object>> getUsuariosTop(@RequestParam("fechaDesde") String desde,
                                             @RequestParam("fechaHasta") String hasta,
                                             @RequestParam("tipoUsuario") String tipo);
}
