
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

| Campo                  | Tipo        | Descripción                          |
|------------------------|-------------|--------------------------------------|
| id                     | Long        | Identificador único                  |
| fechaAlta              | LocalDate   | Fecha de alta de la cuenta           |
| tipoCuenta             | Enum        | BÁSICA / PREMIUM                     |
| saldo                  | Double      | Saldo disponible                     |
| mercadoPagoId          | String      | ID de MercadoPago                    |
| estadoCuenta           | Enum        | ACTIVA / ANULADA                     |
| kmRecorridosMesPremium | Double      | Km recorridos (solo PREMIUM)         |
| usuariosId             | Set\<Long\> | IDs de usuarios asociados            |

---

## 📡 Endpoints principales
## Endpoints de la API

A continuación se detallan todos los endpoints disponibles en esta API.

### Gestión de Cuentas (CRUD)

| Método HTTP | Endpoint                  | Descripción                                                                                             | Ejemplo de Uso                               |
| :---------- | :------------------------ | :------------------------------------------------------------------------------------------------------ | :------------------------------------------- |
| `GET`       | `/cuentas`                | Devuelve una lista con todas las cuentas existentes.                                                    | `GET /cuentas`                               |
| `GET`       | `/cuentas/{id}`           | Busca y devuelve los detalles de una cuenta específica por su ID.                                       | `GET /cuentas/2001`                          |
| `POST`      | `/cuentas`                | Crea una nueva cuenta. Requiere un cuerpo (body) en formato JSON con los datos de la cuenta.            | `POST /cuentas`                              |
| `PUT`       | `/cuentas/{id}`           | Actualiza los datos de una cuenta existente. Requiere un cuerpo (body) en formato JSON.                 | `PUT /cuentas/2001`                          |
| `DELETE`    | `/cuentas/{id}`           | Elimina permanentemente una cuenta del sistema.                                                         | `DELETE /cuentas/2001`                       |

### Operaciones de Estado y Plan

| Método HTTP | Endpoint                  | Descripción                                                                                             | Ejemplo de Uso                               |
| :---------- | :------------------------ | :------------------------------------------------------------------------------------------------------ | :------------------------------------------- |
| `PUT`       | `/cuentas/anular/{id}`    | Cambia el estado de una cuenta a `ANULADA`.                                                             | `PUT /cuentas/anular/2001`                   |
| `PUT`       | `/cuentas/activar/{id}`   | Cambia el estado de una cuenta a `ACTIVA`.                                                              | `PUT /cuentas/activar/2001`                  |
| `PUT`       | `/cuentas/{id}/set-plan/{tipo}` | Establece el tipo de plan de una cuenta. `tipo` puede ser `BASICA` o `PREMIUM`.                     | `PUT /cuentas/2001/set-plan/PREMIUM`         |

### Operaciones de Saldo y Consultas

| Método HTTP | Endpoint                  | Descripción                                                                                             | Ejemplo de Uso                               |
| :---------- | :------------------------ | :------------------------------------------------------------------------------------------------------ | :------------------------------------------- |
| `GET`       | `/cuentas/saldo/{id}`     | Devuelve el saldo actual de una cuenta específica.                                                       | `GET /cuentas/saldo/2001`                    |
| `PUT`       | `/cuentas/recargar/{id}/monto/{monto}` | Añade un monto específico al saldo de una cuenta. No aplica a cuentas PREMIUM.              | `PUT /cuentas/recargar/2001/monto/500.0`     |
| `GET`       | `/cuentas/tipo/{tipo}`    | Devuelve una lista de cuentas que coinciden con el tipo especificado (`BASICA` o `PREMIUM`).              | `GET /cuentas/tipo/BASICA`                   |

### Funcionalidades de Negocio (Orquestación)

Estos endpoints interactúan con otros microservicios para proveer funcionalidades complejas.

| Método HTTP | Endpoint                                          | Descripción                                                                                                                                                                                                                         | Ejemplos de Uso                                                                                             |
| :---------- |:--------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| :---------------------------------------------------------------------------------------------------------- |
| `GET`       | `/cuentas/{idCuenta}/distancia-parada/{idParada}` | Calcula la **distancia Euclidiana** desde un usuario a una parada. **Acepta un parámetro opcional `idUsuario`**. Si no se provee, usa el primer usuario de la cuenta.                                                                    | `GET /cuentas/2001/distancia-parada/1`<br>`GET /cuentas/2001/distancia-parada/1?idUsuario=2003`               |
| `GET`       | `/cuentas/{idCuenta}/paradas-cercanas`            | Devuelve una lista de paradas **ordenadas por cercanía (distancia Euclidiana)**. **Acepta un parámetro opcional `idUsuario`**. Si no se provee, usa el primer usuario de la cuenta.                                                                                 | `GET /cuentas/2001/paradas-cercanas`<br>`GET /cuentas/2001/paradas-cercanas?idUsuario=2003`                      |
| `GET`       | `/cuentas/viajes/{idCuenta}`                      | Obtiene el historial de viajes de una cuenta. **Acepta un parámetro opcional `idUsuario`** para filtrar los viajes de un usuario específico que pertenezca a la cuenta. Si no se provee, devuelve los viajes de todos los usuarios. | `GET /cuentas/viajes/2001`<br>`GET /cuentas/viajes/2001?idUsuario=2003`                                      |


### Notas sobre los Parámetros

*   **Variables de Ruta (`{id}`)**: Son obligatorias y forman parte de la URL. Por ejemplo, en `/cuentas/{id}`, el `{id}` debe ser reemplazado por un número como `2001`.
*   **Parámetros de Consulta (`?idUsuario=...`)**: Son opcionales y se añaden al final de la URL después de un `?`. Se usan para filtrar o modificar el comportamiento de la consulta.
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
