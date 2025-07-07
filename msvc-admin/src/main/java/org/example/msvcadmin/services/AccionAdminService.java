package org.example.msvcadmin.services;

import org.example.msvcadmin.clients.*;
import org.example.msvcadmin.entities.Admin;
import org.example.msvcadmin.entities.AuditoriaAdmin;
import org.example.msvcadmin.models.*;
import org.example.msvcadmin.repositories.AdminRepository;
import org.example.msvcadmin.repositories.AuditoriaAdminRepository;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        // 1. Llamar al scooter-service para cambiar estado
        scooterClient.cambiarEstado(scooterId, nuevoEstado);

        // 2. Si vuelve a estar disponible, reiniciar km
        if (nuevoEstado.equalsIgnoreCase("disponible")) {
            scooterClient.resetearKilometraje(scooterId);
        }

        // 3. Registrar auditoría
        registrarAuditoria(
                "cambiar_estado_scooter",
                scooterId.toString(),
                userIdAdmin,
                "Admin " + userIdAdmin + " cambió el estado del scooter a '" + nuevoEstado + "'"
        );
    }


    public void agregarMonopatin(Monopatin datos, String userIdAdmin) {
        scooterClient.agregarMonopatin(datos);

        registrarAuditoria(
                "crear_monopatin",
                datos.getId().toString(),
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

    public void crearParada(Parada datos, String userIdAdmin) {
        paradaClient.crearParada(datos);
        registrarAuditoria("crear_parada", null, userIdAdmin, "Parada creada: " + datos.toString());
    }

    public void editarParada(Long paradaId, Parada datos, String userIdAdmin) {
        paradaClient.editarParada(paradaId, datos);
        registrarAuditoria("editar_parada", paradaId.toString(), userIdAdmin, "Parada editada: " + datos.toString());
    }

    public void eliminarParada(Long paradaId, String userIdAdmin) {
        paradaClient.eliminarParada(paradaId);
        registrarAuditoria("eliminar_parada", paradaId.toString(), userIdAdmin, "Parada eliminada");
    }



    public void modificarTarifa(Tarifa tarifa, String userIdAdmin) {
        facturacionClient.modificarTarifa(tarifa);
        registrarAuditoria("modificar_tarifa", "tarifa", userIdAdmin, "Tarifa modificada " + " ");
    }



    /*
    agregar a partir de tal fecha cambiar tarifa
     */

    public ResponseEntity<List<ReporteUsuarioActivoDTO>> consultarUsuariosTop(String desde, String hasta, String tipoUsuario, String userIdAdmin) {
        ResponseEntity<List<ReporteUsuarioActivoDTO>> resultado = reporteClient.getUsuariosTop(desde, hasta, tipoUsuario);

        registrarAuditoria("consultar_reporte", "-", userIdAdmin,
                "Reporte de usuarios top desde " + desde + " hasta " + hasta + " (tipo: " + tipoUsuario + ")");

        return resultado;
    }

    public ResponseEntity<List<ReporteUsoMonopatinDTO>> consultarUsoMonopatines( String userIdAdmin) {
        ResponseEntity<List<ReporteUsoMonopatinDTO>> resultado = reporteClient.getReporteUsoMonopatines();

        registrarAuditoria("consultar_reporte", "-", userIdAdmin,
                "Consulta de reporte de uso de monopatines (incluirPausas=" + ")");

        return resultado;
    }

    public ResponseEntity<List<MonopatinViajeDTO>> consultarMonopatinesFrecuentes(int anio, Long minViajes, String userIdAdmin) {
        ResponseEntity<List<MonopatinViajeDTO>> resultado = reporteClient.getMonopatinesFrecuentes(anio, minViajes);

        registrarAuditoria("consultar_reporte", "-", userIdAdmin,
                "Consulta de monopatines frecuentes (año=" + anio + ", minViajes=" + minViajes + ")");

        return resultado;
    }

    public double consultarFacturacionTotal(LocalDate fechaDesde, LocalDate fechaHasta, String userIdAdmin) {
        double resultado = reporteClient.getFacturacionTotal(fechaDesde, fechaHasta);

        registrarAuditoria("consultar_reporte", "-", userIdAdmin,
                "Consulta de facturación total (año=" + LocalDate.now().getYear() + ", desde=" + fechaDesde + ", hasta=" + fechaHasta + ")");

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
