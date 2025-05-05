# EXP3_S8_VIAJEMASCOTAS

# Microservicio de gestión de información de usuarios
Este es un microservicio simple desarrollado con Spring Boot, que permite interactuar con una base de datos Oracle para gestionar información de usuarios en una aplicación de viajes con mascotas.

## Características

- Base de datos en **Oracle**.
- **CRUD** de películas: almacenamiento y consulta de viajes con mascotas.
- **Rutas REST** disponibles:
  - `GET /usuarios/{id}`: Obtiene los detalles de un usuario por su ID.
  - `GET /usuarios`: Obtiene la lista de todas los usuarios.
  - `POST /usuarios`: Crea un nuevo usuario.
  - `PUT /usuarios/{id}`: Actualiza los detalles de un usuario por su ID.
  - `DELETE /usuarios/{id}`: Elimina un usuario por su ID.

## Requisitos

- **Java 17** o superior.
- **Spring Boot 2.x** o superior.
- **Oracle Database** (con Wallet configurado).
- **Maven** como gestor de dependencias.

## Configuración de la Base de Datos

### Configuración de Oracle Wallet

1. Descarga el Oracle Wallet desde la consola de Oracle Cloud.
2. Coloca el archivo `tnsnames.ora` y las credenciales del Wallet en la carpeta especificada en la configuración de la base de datos, como se muestra a continuación:
   
   ```properties
   spring.datasource.url=jdbc:oracle:thin:@bbddfs_tp?TNS_ADMIN=/path/to/Wallet

### El microservicio estará disponible en http://localhost:8080.

## Uso

1. Obtener usuarios por ID:
Realiza una solicitud GET a la siguiente URL para obtener los detalles de un usuario usando su ID:

GET http://localhost:8080/usuarios/{id}

Ejemplo:

GET http://localhost:8080/usuarios/1

3. Obtener todos los usuarios:
Realiza una solicitud GET a la siguiente URL para obtener la lista de todos los usuarios registrados en la base de datos:

GET http://localhost:8080/usuarios

5. Crear un nuevo usuario

POST http://localhost:8080/usuarios

8. Actualizar un usuario existente:

PUT http://localhost:8080/usuarios/{id}

Ejemplo:

PUT http://localhost:8080/usuarios/1

10. Eliminar un usuario:
    
DELETE http://localhost:8080/usuarios/{id}

Ejemplo:

DELETE http://localhost:8080/usuarios/1
