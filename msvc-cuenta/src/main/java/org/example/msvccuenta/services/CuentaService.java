package org.example.msvccuenta.services;

import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.entities.EstadoCuenta;
import org.example.msvccuenta.entities.TipoCuenta;
import org.example.msvccuenta.entities.dto.ParadaConDistanciaDto;
import org.example.msvccuenta.entities.dto.ParadaDto;
import org.example.msvccuenta.entities.dto.UsuarioDto;
import org.example.msvccuenta.entities.dto.ViajeDto;
import org.example.msvccuenta.exceptions.CuentaNoEncontradaException;
import org.example.msvccuenta.feignClients.ParadaFeignClient;
import org.example.msvccuenta.feignClients.UsuarioFeignClient;
import org.example.msvccuenta.feignClients.ViajeFeignClient;
import org.example.msvccuenta.repositories.CuentaRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio que encapsula la lógica de negocio para la gestión de Cuentas.
 * Interactúa con el repositorio de cuentas y clientes Feign para comunicarse
 * con otros microservicios.
 */
@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final ParadaFeignClient paradaFeignClient;
    private final UsuarioFeignClient usuarioFeignClient;
    private final ViajeFeignClient viajeFeignClient;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param cuentaRepository  Repositorio para el acceso a datos de cuentas.
     * @param paradaFeignClient Cliente Feign para el microservicio de paradas.
     * @param usuarioFeignClient Cliente Feign para el microservicio de usuarios.
     * @param viajeFeignClient   Cliente Feign para el microservicio de viajes.
     */
    public CuentaService(CuentaRepository cuentaRepository,
                         ParadaFeignClient paradaFeignClient,
                         UsuarioFeignClient usuarioFeignClient,
                         ViajeFeignClient viajeFeignClient) {
        this.cuentaRepository = cuentaRepository;
        this.paradaFeignClient = paradaFeignClient;
        this.usuarioFeignClient = usuarioFeignClient;
        this.viajeFeignClient = viajeFeignClient;
    }

    /**
     * Devuelve una lista de todas las cuentas.
     *
     * @return Una lista de {@link Cuenta}.
     */
    @Transactional(readOnly = true)
    public List<Cuenta> listar() {
        return cuentaRepository.findAll();
    }

    /**
     * Busca una cuenta por su ID.
     *
     * @param id El ID de la cuenta a buscar.
     * @return La entidad {@link Cuenta} encontrada.
     * @throws IllegalArgumentException si el ID es nulo o no positivo.
     * @throws CuentaNoEncontradaException si no se encuentra ninguna cuenta con ese ID.
     */
    @Transactional(readOnly = true)
    public Cuenta buscarPorId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada con ID: " + id));
    }

    /**
     * Guarda una nueva cuenta en la base de datos.
     *
     * @param cuenta La entidad {@link Cuenta} a crear.
     * @return La cuenta guardada con su ID asignado.
     */
    @Transactional
    public Cuenta crear(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    /**
     * Actualiza una cuenta existente con nuevos datos.
     *
     * @param id     El ID de la cuenta a actualizar.
     * @param cuenta La entidad {@link Cuenta} con los datos actualizados.
     * @return La cuenta actualizada.
     * @throws CuentaNoEncontradaException si no se encuentra la cuenta a actualizar.
     */
    @Transactional
    public Cuenta actualizar(Long id, Cuenta cuenta) {
        return cuentaRepository.findById(id).map(cuentaDB -> {
            cuentaDB.setFechaAlta(cuenta.getFechaAlta());
            cuentaDB.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDB.setSaldo(cuenta.getSaldo());
            cuentaDB.setMercadoPagoId(cuenta.getMercadoPagoId());
            cuentaDB.setEstadoCuenta(cuenta.getEstadoCuenta());
            cuentaDB.setKmRecorridosMesPremium(cuenta.getKmRecorridosMesPremium());
            cuentaDB.setUsuariosId(cuenta.getUsuariosId());

            return cuentaRepository.save(cuentaDB);
        }).orElseThrow(() -> new CuentaNoEncontradaException("No se pudo actualizar. Cuenta no encontrada con ID: " + id));
    }

    /**
     * Elimina una cuenta por su ID.
     *
     * @param id El ID de la cuenta a eliminar.
     * @throws CuentaNoEncontradaException si no se encuentra la cuenta a eliminar.
     */
    @Transactional
    public void eliminar(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new CuentaNoEncontradaException("No se pudo eliminar. Cuenta no encontrada con ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    /**
     * Cambia el estado de una cuenta a ANULADA.
     *
     * @param id El ID de la cuenta a anular.
     * @return La cuenta con el estado actualizado.
     * @throws CuentaNoEncontradaException si no se encuentra la cuenta.
     */
    @Transactional
    public Cuenta anularCuenta(Long id) {
        Cuenta cuenta = this.buscarPorId(id);
        cuenta.setEstadoCuenta(EstadoCuenta.ANULADA);
        return cuentaRepository.save(cuenta);
    }

    /**
     * Cambia el estado de una cuenta a ACTIVA.
     *
     * @param id El ID de la cuenta a activar.
     * @return La cuenta con el estado actualizado.
     * @throws CuentaNoEncontradaException si no se encuentra la cuenta.
     */
    @Transactional
    public Cuenta activarCuenta(Long id) {
        Cuenta cuenta = this.buscarPorId(id);

        cuenta.setEstadoCuenta(EstadoCuenta.ACTIVA);
        return cuentaRepository.save(cuenta);
    }


    /**
     * Calcula la distancia Euclidiana entre la ubicación de un usuario y una parada.
     *
     * @param idCuenta El ID de la cuenta.
     * @param idParada El ID de la parada.
     * @param idUsuario (Opcional) El ID del usuario específico. Si es nulo, se usa el primer usuario de la cuenta.
     * @return La distancia Euclidiana calculada.
     * @throws CuentaNoEncontradaException si la cuenta no existe.
     * @throws IllegalStateException si la cuenta no tiene usuarios, o si no se pueden obtener las coordenadas.
     * @throws IllegalArgumentException si el idUsuario especificado no pertenece a la cuenta.
     */
    @Transactional(readOnly = true)
    public Double calcularDistanciaAParada(Long idCuenta, Long idParada, Long idUsuario) {
        UsuarioDto usuario = obtenerUsuarioVerificado(idCuenta, idUsuario);
        ParadaDto parada = paradaFeignClient.getParadaById(idParada);
        if (parada == null || parada.getPosX() == null || parada.getPosY() == null) {
            throw new IllegalStateException("No se pudo obtener la ubicación para la parada con ID " + idParada + ".");
        }
        double distancia = calcularDistanciaEuclidiana(usuario.getLatitud(), usuario.getLongitud(), parada.getPosY(), parada.getPosX());
        return Math.round(distancia * 100.0) / 100.0;
    }
    /**
     * Función de utilidad privada para calcular la distancia Euclidiana.
     * @return distancia Euclidiana.
     */
    private double calcularDistanciaEuclidiana(double lat1, double lon1, Double lat2, Double lon2) {
        if (lat2 == null || lon2 == null) {
            return Double.MAX_VALUE;
        }

        return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));
    }
    /**
     * Obtiene el saldo de una cuenta.
     *
     * @param id El ID de la cuenta.
     * @return El saldo de la cuenta como {@link Double}.
     * @throws CuentaNoEncontradaException si no se encuentra la cuenta.
     */
    @Transactional(readOnly = true)
    public Double obtenerSaldo(Long id) {
        return this.buscarPorId(id).getSaldo();
    }

    /**
     * Actualiza el tipo de plan de una cuenta.
     *
     * @param id   El ID de la cuenta.
     * @param tipo El nuevo {@link TipoCuenta}.
     * @return La cuenta con el tipo de plan actualizado.
     * @throws CuentaNoEncontradaException si no se encuentra la cuenta.
     */
    @Transactional
    public Cuenta actualizarTipoCuenta(Long id, TipoCuenta tipo) {
        Cuenta cuenta = this.buscarPorId(id);
        cuenta.setTipoCuenta(tipo);
        return cuentaRepository.save(cuenta);
    }

    /**
     * Añade un monto al saldo de una cuenta.
     *
     * @param id    El ID de la cuenta a recargar.
     * @param monto El monto a añadir.
     * @return La cuenta con el saldo actualizado.
     * @throws CuentaNoEncontradaException si no se encuentra la cuenta.
     * @throws IllegalStateException si la cuenta está ANULADA.
     * @throws IllegalArgumentException si el monto es negativo o cero.
     * @throws RuntimeException si se intenta recargar una cuenta PREMIUM.
     */
    @Transactional
    public Cuenta recargarSaldo(Long id, Double monto) {
        Cuenta cuenta = this.buscarPorId(id);
        if (cuenta.getEstadoCuenta() == EstadoCuenta.ANULADA) {
            throw new IllegalStateException("No se puede recargar saldo en una cuenta ANULADA.");
        }
        if (cuenta.getTipoCuenta() == TipoCuenta.PREMIUM) {
            throw new RuntimeException("No se puede recargar saldo en cuentas PREMIUM. Estas pagan una tarifa fija mensual.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto de recarga debe ser positivo.");
        }
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        return cuentaRepository.save(cuenta);
    }


    /**
     * Obtiene el historial de viajes de una cuenta a través del microservicio de viajes.
     *
     * @param idCuenta El ID del usuario de la cuenta para la cual se solicita el historial.
     * @return Una lista de {@link ViajeDto}.
     */
    @Transactional(readOnly = true)
    public List<ViajeDto> getViajesPorUsuarioYPeriodo(Long idCuenta, Long idUsuario) {
        Cuenta cuenta = this.buscarPorId(idCuenta);
        Set<Long> userIds = cuenta.getUsuariosId();

        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }

        if (idUsuario != null) {
            if (!userIds.contains(idUsuario)) {
                throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no pertenece a la cuenta con ID " + idCuenta);
            }
            return viajeFeignClient.getViajesPorUsuarioYPeriodo(idUsuario, null, null);
        } else {
            return userIds.stream()
                    .flatMap(uid -> {
                        try {
                            return viajeFeignClient.getViajesPorUsuarioYPeriodo(uid, null, null).stream();
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(java.util.Objects::nonNull)
                    .sorted(Comparator.comparing(ViajeDto::getFecha).reversed())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Obtiene todas las cuentas de un tipo específico.
     *
     * @param tipo El tipo de cuenta a buscar (String, ej. "BASICA").
     * @return Una lista de {@link Cuenta} del tipo especificado.
     */
    @Transactional(readOnly = true)
    public List<Cuenta> obtenerPorTipo(String tipo) {
        return cuentaRepository.findAllByTipoCuenta(TipoCuenta.valueOf(tipo));
    }



    /**
     * Encuentra y devuelve una lista de todas las paradas, ordenadas por cercanía a un usuario,
     *
     * @param idCuenta El ID de la cuenta del usuario.
     * @param idUsuario (Opcional) El ID del usuario específico. Si es nulo, se usa el primer usuario de la cuenta.
     * @return Una lista de {@link ParadaDto} ordenada por distancia ascendente.
     * @throws IllegalStateException si no se puede obtener la ubicación del usuario.
     * @throws IllegalArgumentException si el idUsuario especificado no pertenece a la cuenta.
     */
    @Transactional(readOnly = true)
    public List<ParadaConDistanciaDto> paradasCercanas(Long idCuenta, Long idUsuario) {
        UsuarioDto usuario = obtenerUsuarioVerificado(idCuenta, idUsuario);
        List<ParadaDto> paradas = paradaFeignClient.listarParadas();
        if (paradas == null || paradas.isEmpty()) {
            return Collections.emptyList();
        }
        return paradas.stream()
                .map(parada -> {
                    double distancia = calcularDistanciaEuclidiana(
                            usuario.getLatitud(),
                            usuario.getLongitud(),
                            parada.getPosY(),
                            parada.getPosX()
                    );
                    double distanciaRedondeada = Math.round(distancia * 100.0) / 100.0;
                    return new ParadaConDistanciaDto(parada, distanciaRedondeada);
                })
                .sorted(Comparator.comparing(ParadaConDistanciaDto::getDistancia))
                .collect(Collectors.toList());
    }

    /**
     * Métdo de utilidad para obtener un usuario validado, asegurando que pertenece a la cuenta
     * y que su ubicación es válida. Centraliza la lógica repetida.
     *
     * @param idCuenta ID de la cuenta.
     * @param idUsuario ID opcional del usuario.
     * @return El DTO del usuario con su ubicación.
     */
    private UsuarioDto obtenerUsuarioVerificado(Long idCuenta, Long idUsuario) {
        Cuenta cuenta = this.buscarPorId(idCuenta);
        Hibernate.initialize(cuenta.getUsuariosId());
        Set<Long> usuariosDeLaCuenta = cuenta.getUsuariosId();

        if (usuariosDeLaCuenta == null || usuariosDeLaCuenta.isEmpty()) {
            throw new IllegalStateException("La cuenta con ID " + idCuenta + " no tiene usuarios asociados.");
        }

        Long idUsuarioAUtilizar;
        if (idUsuario != null) {
            if (!usuariosDeLaCuenta.contains(idUsuario)) {
                throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no pertenece a la cuenta con ID " + idCuenta);
            }
            idUsuarioAUtilizar = idUsuario;
        } else {
            idUsuarioAUtilizar = usuariosDeLaCuenta.iterator().next();
        }

        UsuarioDto usuario = usuarioFeignClient.getUsuarioById(idUsuarioAUtilizar);
        if (usuario == null || usuario.getLatitud() == null || usuario.getLongitud() == null) {
            throw new IllegalStateException("No se pudo obtener la ubicación para el usuario con ID " + idUsuarioAUtilizar + ".");
        }
        return usuario;
    }

}