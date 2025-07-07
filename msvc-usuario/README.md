# 👤 Microservicio de Usuario

Gestiona los usuarios del sistema de alquiler de monopatines eléctricos.

---

## 🚀 Funcionalidades

- **ABM de usuarios**: alta, baja, modificación y consulta
- **Validación avanzada**: email, número de celular, password, etc.
- **Gestión de roles**: USUARIO y ADMIN
- **API RESTful**: endpoints claros y documentados

---

## 🗂️ Entidad principal

| Campo           | Tipo     | Descripción                        |
|-----------------|----------|------------------------------------|
| id              | Long     | Identificador único                |
| nombre          | String   | Nombre del usuario                 |
| apellido        | String   | Apellido del usuario               |
| numeroCelular   | String   | Celular único                      |
| email           | String   | Email único                        |
| password        | String   | Contraseña (encriptada)            |
| rol             | Enum     | USUARIO / ADMIN                    |
| latitud         | Double   | Ubicación actual (latitud)         |
| longitud        | Double   | Ubicación actual (longitud)        |

---

## 📡 Endpoints principales

| Método | Endpoint                      | Descripción                    |
|--------|-------------------------------|--------------------------------|
| GET    | `/usuarios`                   | Listar usuarios                |
| GET    | `/usuarios/{id}`              | Buscar usuario por ID          |
| POST   | `/usuarios`                   | Crear usuario                  |
| PUT    | `/usuarios/{id}`              | Actualizar usuario             |
| DELETE | `/usuarios/{id}`              | Eliminar usuario               |
| PUT    | `/usuarios/{id}/set-admin`    | Asignar rol ADMIN              |
| PUT    | `/usuarios/{id}/set-usuario`  | Asignar rol USUARIO            |

---
---

### 🛡️ Notas de Seguridad sobre la Actualización de Usuarios

Es importante destacar que, por diseño y para garantizar la integridad del sistema, ciertas operaciones en la actualización de usuarios están restringidas:

-   **Actualización de `rol` y `password`**: El endpoint `PUT /usuarios/{id}` está diseñado para actualizar datos del perfil del usuario (como nombre, email, etc.). Sin embargo, **ignora intencionadamente** los campos `rol` y `password` aunque se envíen en el cuerpo de la petición.

    -   **Motivo**: Esta es una medida de seguridad crucial para prevenir que un usuario pueda asignarse a sí mismo el rol de `ADMIN` o que se cambie una contraseña sin seguir el flujo de seguridad adecuado (que podría implicar, por ejemplo, la verificación de la contraseña anterior).

    -   **Operaciones correctas**: La gestión de roles de administrador y el cambio de contraseñas deben realizarse a través de endpoints dedicados y protegidos, diseñados específicamente para esas tareas.

## 📖 Documentación de la API

La API está documentada utilizando Swagger/OpenAPI. Una vez que el microservicio está en ejecución, puedes acceder a la documentación interactiva en la siguiente URL:

http://localhost:8002/swagger-ui.html

---

## ✅ Validaciones

- Email y número de celular únicos y con formato válido
- Password con longitud mínima
- Campos obligatorios no nulos

---

## ⚙️ Configuración

- **Puerto:** `8002`
- **Base de datos:** MySQL (`usuario`)
- Variables en `application.properties`

---

## ▶️ Ejecución

```bash
mvn spring-boot:run