
# 💳 Microservicio de Cuenta

Gestiona las cuentas asociadas a los usuarios del sistema de monopatines eléctricos.

---

## 🚀 Funcionalidades

- **CRUD de cuentas**: alta, baja, modificación y consulta
- **Recarga de saldo** y consulta de saldo
- **Anulación y activación** de cuentas
- **Cambio de tipo de cuenta**: BÁSICA / PREMIUM
- **Integración con otros microservicios** para obtener historiales de viaje, datos de usuarios y paradas.
- **API RESTful**: endpoints claros y documentados con Swagger

---

## 📖 Documentación de la API (Swagger)

La documentación completa, interactiva y actualizada de todos los endpoints está disponible a través de Swagger UI. Una vez que la aplicación esté en ejecución, puedes acceder a ella desde tu navegador:

**http://localhost:8001/swagger-ui/index.html#/**

---

## 🗂️ Entidad principal

| Campo                  | Tipo         | Descripción                          |
|------------------------|--------------|--------------------------------------|
| id                     | Long         | Identificador único                  |
| fechaAlta              | LocalDate    | Fecha de alta de la cuenta           |
| tipoCuenta             | Enum         | BÁSICA / PREMIUM                     |
| saldo                  | BigDecimal   | Saldo disponible                     |
| mercadoPagoId          | String       | ID de MercadoPago                    |
| estadoCuenta           | Enum         | ACTIVA / ANULADA                     |
| kmRecorridosMesPremium | BigDecimal   | Km recorridos (solo PREMIUM)         |
| usuariosId             | Set\<Long\>  | IDs de usuarios asociados            |

---

## 📡 Endpoints principales

| Método | Endpoint                                    | Descripción                        |
|--------|---------------------------------------------|------------------------------------|
| GET    | `/cuentas`                                  | Listar cuentas                     |
| GET    | `/cuentas/{id}`                             | Buscar cuenta por ID               |
| POST   | `/cuentas`                                  | Crear cuenta                       |
| PUT    | `/cuentas/{id}`                             | Actualizar cuenta                  |
| DELETE | `/cuentas/{id}`                             | Eliminar cuenta                    |
| PUT    | `/cuentas/anular/{id}`                      | Anular cuenta                      |
| PUT    | `/cuentas/activar/{id}`                     | Activar cuenta                     |
| PUT    | `/cuentas/{id}/set-plan/{tipo}`             | Cambiar tipo de cuenta             |
| PUT     | `/cuentas/recargar/{id}/monto/{monto}`      | Recargar saldo                     |
| GET    | `/cuentas/saldo/{id}`                       | Consultar saldo                    |
| GET    | `/cuentas/viajes/{id}`                      | Historial de viajes                |
| GET    | `/cuentas/{idCuenta}/distancia-parada/{idParada}` | Calcular distancia a una parada |

---

## ✅ Validaciones

- Saldo no negativo
- `mercadoPagoId` obligatorio
- Tipo y estado de cuenta obligatorios

---

## ⚙️ Configuración

- **Puerto:** configurable
- **Base de datos:** MySQL (`cuenta`)
- Variables en `application.properties`

---

## ▶️ Ejecución

## ⚙️ Configuración y Ejecución

1.  **Base de Datos**: Asegúrate de tener MySQL corriendo. La base de datos `cuenta` se creará automáticamente si no existe.
2.  **Variables de Entorno**: Puedes configurar el puerto y los datos de la base de datos en el archivo `src/main/resources/application.properties`.
3.  **Ejecutar la aplicación**:

```bash
mvn spring-boot:run
