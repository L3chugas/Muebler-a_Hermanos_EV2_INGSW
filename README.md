# 🪑 Mueblería Hermanos S.A. - Sistema de Gestión Full Stack

> **Evaluación 3 - Ingeniería de Software**
> Universidad del Bío-Bío | Facultad de Ciencias Empresariales

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue)
![Frontend](https://img.shields.io/badge/Frontend-Vanilla_JS-yellow)

## 📖 Descripción

Sistema integral para la gestión comercial de una mueblería. El proyecto evoluciona una API REST tradicional hacia una arquitectura **Full Stack Containerizada**. Permite la administración de inventario (muebles y variantes), la generación dinámica de cotizaciones y la confirmación de ventas con control estricto de stock en tiempo real.

El despliegue está orquestado completamente con **Docker**, separando el Frontend (Nginx), Backend (Spring Boot) y Base de Datos (MySQL) en microservicios aislados.

---

## 🚀 Características Principales

* **Gestión de Catálogo:** CRUD para muebles con atributos específicos (Material, Tamaño, Stock).
* **Sistema de Variantes:** Manejo de modificaciones de productos (ej. Barnices, Cojines) que alteran el precio final dinámicamente.
* **Cotizador Interactivo:** Interfaz gráfica para generar cotizaciones con múltiples ítems y cálculos automáticos.
* **Control de Stock Transaccional:** Validación lógica que impide la venta si el stock es insuficiente al momento de confirmar la cotización.
* **Arquitectura SPA:** Frontend ligero construido con Vanilla JS, sin frameworks pesados, consumiendo la API de forma asíncrona.
* **Dockerizado:** Entorno reproducible con `docker-compose`.

---

## 🛠️ Stack Tecnológico

### Backend
* **Lenguaje:** Java 21 (JDK)
* **Framework:** Spring Boot 3.x (Web, Data JPA)
* **Testing:** JUnit 5 & Mockito
* **Build Tool:** Gradle

### Frontend
* **Core:** HTML5, CSS3, JavaScript (ES6+)
* **Servidor Web:** Nginx (Alpine Linux)

### Infraestructura & Datos
* **Base de Datos:** MySQL 8.0
* **Contenedores:** Docker & Docker Compose
* **Herramientas Extra:** PhpMyAdmin (Gestión BD), MailHog (SMTP Mock)

---

## 🏗️ Arquitectura

El sistema utiliza una arquitectura de microservicios simplificada:

```mermaid
graph LR
    A[Cliente Web] -- Puerto 3000 --> B[Nginx Frontend]
    B -- Fetch API / JSON --> C[Spring Boot Backend]
    C -- Puerto 3306 --> D[(MySQL Database)]
``````

## 📖 Guía de Uso

Siga estos pasos para ejecutar el proyecto correctamente en su entorno local.

### 1. Configuración de Base de Datos
Para asegurar la persistencia y carga inicial de datos:

1.  Extraiga el archivo `Muebleria_Hermanos_EV2_INGSW.rar`.
2.  Abra **XAMPP** y active los módulos de **Apache** y **MySQL**.
3.  Ingrese a **phpMyAdmin** de forma local (usualmente `http://localhost/phpmyadmin`).
4.  Importe el archivo `muebles_hermanos.sql` (ubicado en la carpeta del proyecto) para generar la base de datos y sus tablas.

### 2. Ejecutar la Aplicación
El despliegue de la aplicación se realiza mediante contenedores:

1.  Abra el programa **Docker Desktop** y asegúrese de que el motor esté corriendo.
2.  Abra su terminal o consola de comandos.
3.  Navegue hasta la ruta raíz del backend donde se encuentra el archivo `docker-compose.yml`.
    ```bash
    cd "ruta/a/Muebleria_Hermanos_EV2_INGSW/muebleria"
    ```

### 3. Utilizar el Programa
Una vez situado en la carpeta correcta:

1.  Ejecute el siguiente comando para levantar los servicios:
    ```bash
    docker compose up -d
    ```
    *(Espere unos instantes a que los contenedores se inicien correctamente)*.

2.  Abra el Frontend ingresando a la siguiente dirección en su navegador (testeado en Firefox):
    * 👉 **http://localhost:3000/**

¡Listo! Ya puede empezar a ejecutar y probar el programa.

---

### 🛑 Cerrar la Aplicación

Para detener y eliminar los contenedores creados, ejecute en la terminal:

```bash
docker compose down
```

### Otros accesos:
* 👉 **Frontend http://localhost:3000 Interfaz de Usuario (Cliente y Admin)**
* 👉 **API Backend http://localhost:8090/api/muebles Endpoints REST directos**
* 👉 **PhpMyAdmin http://localhost:8081 Gestión visual de la BD**
