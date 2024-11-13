# Proyecto Final Servidor1: API REST para Gestión de Proyectos

Este proyecto consiste en una API REST para gestionar información de proyectos, desarrolladores y tecnologías. Fue desarrollado en Java utilizando Spring Boot y sigue el patrón Modelo-Vista-Controlador (MVC), usando también Git como sistema de control de versiones.

## Tabla de Contenidos
- [Descripción General](#descripción-general)
- [Requisitos](#requisitos)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Instalación y Configuración](#instalación-y-configuración)
- [Endpoints de la API](#endpoints-de-la-api)
- [Pruebas](#pruebas)
- [Documentación Swagger](#Swagger)


---

## Descripción General

La API permite a los usuarios:
- Gestionar proyectos, desarrolladores y tecnologías en una base de datos.
- Realizar operaciones CRUD sobre las diferentes tablas.
- Filtrar proyectos por nombre o tecnología.
- Cambiar el estado de un proyecto de **"Development"** a **"Testing"** y de **"Testing"** a **"Production"**.

Este proyecto fue desarrollado como parte de la evaluación de un módulo de servidor y cumple con criterios como paginación en las consultas y manejo de errores con `ResponseEntity`.

---

## Requisitos

Para ejecutar la aplicación, se necesitan las siguientes herramientas:
- **Java 17** o superior
- **Maven** o **Gradle** para gestionar dependencias
- **MySQL** (u otra base de datos compatible)
- **Postman** o cualquier herramienta para probar los endpoints
- **Git** para el control de versiones

---

## Estructura del Proyecto

La aplicación sigue el patrón Modelo-Vista-Controlador:
- **Modelo**: Entidades que representan las tablas en la base de datos.
- **Controladores**: Gestionan las peticiones y retornan `ResponseEntity` con códigos de respuesta adecuados.
- **Servicios**: Implementan la lógica de negocio.
- **Repositorios**: Interactúan con la base de datos mediante JPA.


---

## Instalación y Configuración

1. **Clonar el repositorio**:
git clone https://github.com/josevazquez99/ProyectoFinalServidor1.git

## Swagger
localhost:8080/swagger-ui.html
