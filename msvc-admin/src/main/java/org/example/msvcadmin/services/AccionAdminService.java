package org.example.msvcadmin.services;

import org.example.msvcadmin.clients.*;
import org.example.msvcadmin.entities.Admin;
import org.example.msvcadmin.entities.AuditoriaAdmin;
import org.example.msvcadmin.repositories.AdminRepository;
import org.example.msvcadmin.repositories.AuditoriaAdminRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AccionAdminService {

    private final CuentaClient cuentaClient;
    private final AuditoriaAdminRepository auditoriaRepo;
    private final AdminRepository adminRepo;
    private final ScooterClient scooterClient;
    private final ParadaClient paradaClient;
    private final FacturacionClient facturacionClient;
    private final ReporteClient reporteClient;

    public AccionAdminService(CuentaClient cuentaClient,
                              AuditoriaAdminRepository auditoriaRepo,
                              AdminRepository adminRepo, ScooterClient scooterClient, ParadaClient paradaClient, FacturacionClient facturacionClient, ReporteClient reporteClient) {
        this.cuentaClient = cuentaClient;
        this.auditoriaRepo = auditoriaRepo;
        this.adminRepo = adminRepo;
        this.scooterClient = scooterClient;
        this.paradaClient = paradaClient;
        this.facturacionClient = facturacionClient;
        this.reporteClient = reporteClient;
    }

    public void bloquearCuenta(Long cuentaId, String userIdAdmin) {
        // 1. Ejecutar acción externa
        cuentaClient.anularCuenta(cuentaId);

        // 2. Registrar auditoría
        registrarAuditoria(
                "bloquear_cuenta",
                cuentaId.toString(),
                userIdAdmin,
                "Cuenta bloqueada por admin " + userIdAdmin
        );
    }

    public void reactivarCuenta(Long cuentaId, String userIdAdmin) {
        // 1. Llamada al microservicio cuenta
        cuentaClient.activarCuenta(cuentaId);

        // 2. Registrar acción en auditoría
        registrarAuditoria(
                "reactivar_cuenta",
                cuentaId.toString(),
                userIdAdmin,
                "Cuenta reactivada por admin " + userIdAdmin
        );
    }

    public void cambiarEstadoScooter(Long scooterId, String nuevoEstado, String userIdAdmin) {
        // 1. Armar body del request con el estado
        Map<String, Object> body = Map.of("estado", nuevoEstado);

        // 2. Llamar al scooter-service para cambiar estado
        scooterClient.cambiarEstado(scooterId, body);

        // 3. Si vuelve a estar disponible, reiniciar km
        if (nuevoEstado.equalsIgnoreCase("disponible")) {
            scooterClient.resetearKilometraje(scooterId);
        }

        // 4. Registrar auditoría
        registrarAuditoria(
                "cambiar_estado_scooter",
                scooterId.toString(),
                userIdAdmin,
                "Admin " + userIdAdmin + " cambió el estado del scooter a '" + nuevoEstado + "'"
        );
    }

    public void agregarMonopatin(Map<String, Object> datos, String userIdAdmin) {
        scooterClient.agregarMonopatin(datos);

        registrarAuditoria(
                "crear_monopatin",
                datos.get("id").toString(),
                userIdAdmin,
                "Se creó un nuevo monopatín con datos: " + datos.toString()
        );
    }

    public void eliminarMonopatin(Long id, String userIdAdmin) {
        scooterClient.eliminarMonopatin(id);

        registrarAuditoria(
                "eliminar_monopatin",
                id.toString(),
                userIdAdmin,
                "Se eliminó el monopatín con ID " + id
        );
    }

    public void crearParada(Map<String, Object> datos, String userIdAdmin) {
        paradaClient.crearParada(datos);
        registrarAuditoria("crear_parada", null, userIdAdmin, "Parada creada: " + datos.toString());
    }

    public void editarParada(Long paradaId, Map<String, Object> datos, String userIdAdmin) {
        paradaClient.editarParada(paradaId, datos);
        registrarAuditoria("editar_parada", paradaId.toString(), userIdAdmin, "Parada editada: " + datos.toString());
    }

    public void eliminarParada(Long paradaId, String userIdAdmin) {
        paradaClient.eliminarParada(paradaId);
        registrarAuditoria("eliminar_parada", paradaId.toString(), userIdAdmin, "Parada eliminada");
    }

    public void crearTarifa(Map<String, Object> datos, String userIdAdmin) {
        facturacionClient.crearTarifa(datos);
        registrarAuditoria("crear_tarifa", null, userIdAdmin, "Tarifa creada: " + datos.toString());
    }

    public void modificarTarifa(Long tarifaId, Map<String, Object> datos, String userIdAdmin) {
        facturacionClient.modificarTarifa(tarifaId, datos);
        registrarAuditoria("modificar_tarifa", tarifaId.toString(), userIdAdmin, "Tarifa modificada: " + datos.toString());
    }
    /*
    agregar a partir de tal fecha cambiar tarifa
     */

    public List<Map<String, Object>> consultarUsuariosTop(String desde, String hasta, String tipoUsuario, String userIdAdmin) {
        List<Map<String, Object>> resultado = reporteClient.getUsuariosTop(desde, hasta, tipoUsuario);

        registrarAuditoria("consultar_reporte", "-", userIdAdmin,
                "Reporte de usuarios top desde " + desde + " hasta " + hasta + " (tipo: " + tipoUsuario + ")");

        return resultado;
    }

    public List<Map<String, Object>> consultarUsoMonopatines(boolean incluirPausas, String userIdAdmin) {
        List<Map<String, Object>> resultado = reporteClient.getReporteUsoMonopatines(incluirPausas);

        registrarAuditoria("consultar_reporte", "-", userIdAdmin,
                "Consulta de reporte de uso de monopatines (incluirPausas=" + incluirPausas + ")");

        return resultado;
    }

    public List<Map<String, Object>> consultarMonopatinesFrecuentes(int anio, int minViajes, String userIdAdmin) {
        List<Map<String, Object>> resultado = reporteClient.getMonopatinesFrecuentes(anio, minViajes);

        registrarAuditoria("consultar_reporte", "-", userIdAdmin,
                "Consulta de monopatines frecuentes (año=" + anio + ", minViajes=" + minViajes + ")");

        return resultado;
    }

    public Map<String, Object> consultarFacturacionTotal(int anio, int mesDesde, int mesHasta, String userIdAdmin) {
        Map<String, Object> resultado = reporteClient.getFacturacionTotal(anio, mesDesde, mesHasta);

        registrarAuditoria("consultar_reporte", "-", userIdAdmin,
                "Consulta de facturación total (año=" + anio + ", desde=" + mesDesde + ", hasta=" + mesHasta + ")");

        return resultado;
    }

    private void registrarAuditoria(String tipo, String entidadId, String userIdAdmin, String detalle) {
        Admin admin = adminRepo.findByUserId(userIdAdmin)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));

        AuditoriaAdmin log = new AuditoriaAdmin();
        log.setAdminId(admin.getId());
        log.setFecha(LocalDateTime.now());
        log.setTipoAccion(tipo);
        log.setEntidadAfectadaId(entidadId != null ? entidadId : "-");
        log.setDetalle(detalle);

        auditoriaRepo.save(log);
    }
}
