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