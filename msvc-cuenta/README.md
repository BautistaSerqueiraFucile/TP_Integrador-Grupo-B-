
# 💳 Microservicio de Cuenta

Gestiona las cuentas asociadas a los usuarios del sistema de monopatines eléctricos.

---

## 🚀 Funcionalidades

- **ABM de cuentas**: alta, baja, modificación y consulta
- **Recarga de saldo** y consulta de saldo
- **Anulación y activación** de cuentas
- **Cambio de tipo de cuenta**: BÁSICA / PREMIUM
- **Historial de viajes** y cálculo de distancias
- **API RESTful**: endpoints claros y documentados

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

| Método | Endpoint                                      | Descripción                        |
|--------|-----------------------------------------------|------------------------------------|
| GET    | `/cuentas/`                                   | Listar cuentas                     |
| GET    | `/cuentas/{id}`                               | Buscar cuenta por ID               |
| POST   | `/cuentas/`                                   | Crear cuenta                       |
| PUT    | `/cuentas/{id}`                               | Actualizar cuenta                  |
| DELETE | `/cuentas/{id}`                               | Eliminar cuenta                    |
| PATCH  | `/cuentas/anular/{id}`                        | Anular cuenta                      |
| PATCH  | `/cuentas/activar/{id}`                       | Activar cuenta                     |
| PATCH  | `/cuentas/{id}/set-plan/{tipo}`               | Cambiar tipo de cuenta             |
| PATCH  | `/cuentas/recargar/{id}/monto/{monto}`        | Recargar saldo                     |
| GET    | `/cuentas/saldo/{id}`                         | Consultar saldo                    |
| GET    | `/cuentas/viajes/{id}`                        | Historial de viajes                |
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

```bash
mvn spring-boot:run
