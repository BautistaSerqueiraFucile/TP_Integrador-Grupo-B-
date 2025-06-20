package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "reporte", url = "http://localhost:8006")
public interface ReporteClient {

    @GetMapping("/reporte/uso-monopatines")
    List<Map<String, Object>> getReporteUsoMonopatines(@RequestParam("incluirPausas") boolean incluirPausas);

    @GetMapping("/reporte/monopatines-mas-usados")
    List<Map<String, Object>> getMonopatinesFrecuentes(@RequestParam("anio") int anio, @RequestParam("minViajes") int minViajes);

    @GetMapping("/reporte/facturacion-total")
    Map<String, Object> getFacturacionTotal(@RequestParam("anio") int anio, @RequestParam("mesDesde") int desde, @RequestParam("mesHasta") int hasta);

    @GetMapping("/reporte/usuarios-top")
    List<Map<String, Object>> getUsuariosTop(@RequestParam("fechaDesde") String desde, @RequestParam("fechaHasta") String hasta, @RequestParam("tipoUsuario") String tipo);
}
