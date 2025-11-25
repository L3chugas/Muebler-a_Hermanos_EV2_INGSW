# 🪑 Sistema de Gestión - Mueblería "Los Muebles Hermanos S.A."

## 📄 Descripción del Proyecto
Este proyecto es un Backend desarrollado en **Spring Boot** para gestionar el inventario y las ventas de una mueblería. El sistema permite administrar un catálogo de muebles con variantes (materiales, tamaños), crear cotizaciones (carrito de compras) y procesar ventas finales controlando estrictamente el stock disponible.

El objetivo principal de esta evaluación fue conectar una API REST con **MySQL**, implementar patrones de diseño de software y realizar pruebas unitarias con **JUnit**.

---

## 🛠️ Stack Tecnológico y Dependencias
El proyecto utiliza las siguientes tecnologías y dependencias clave:

* **Java 21**: Lenguaje de programación principal.
* **Spring Boot 3.x**: Framework base.
    * `Spring Web`: Para la creación de la API REST y los controladores.
    * `Spring Data JPA`: Para la persistencia de datos y el patrón Repositorio (ORM).
    * `Spring Boot DevTools`: Para facilitar el desarrollo con reinicio automático.
* **MySQL Driver**: Conector JDBC para la comunicación con la base de datos MySQL (XAMPP).
* **JUnit 5**: Framework para la ejecución de pruebas unitarias y de integración.
* **Maven**: Gestor de dependencias y construcción del proyecto.

---

## 🚀 Guía de Instalación y Ejecución

### 1. Requisitos Previos
Asegúrese de tener instalado:
* Java JDK 17 o 21.
* MySQL (XAMPP, Workbench o Docker) corriendo en el puerto `3306`.
* Postman (para probar los endpoints).

### 2. Configuración de Base de Datos
1.  Abra XAMPP y activar los modulos de Apache y MySQL
2.  Ingrese a phpMyAdmin de forma local
3.  Importe el archivo “mebles_hermanos.sql” con tal de obtener la base de datos

### 3. Ejecutar la Aplicación
1.  Extraiga el archivo “Muebleria_Hermanos_EV2_INGSW.rar”
2.  Abra en un IDE la carpeta obtenida anteriormente (opcional)
3.  Ingresese a la ruta “Muebleria_Hermanos_EV2_INGSW\mueblería\src\main\java\com\ev2\muebleria” y ejecutar y/o compilar el archivo “MuebleriaApplication,java”
4.  Al correr el programa, ponga atención al puerto en donde se ejecutó el proyecto, este número se encuentra en las últimas líneas de la consola

### 4. Utilizar el programa
1.  Abra Postman
2.  En la barra de direcciones ingrese "http://localhost:"puerto"/api/" y puede comenzar a interactuar con el programa mediante muebles y cotizaciones

### 5. Comandos importantes
1.  GET   http://localhost:"puerto"/api/muebles
2.  POST  http://localhost:"puerto"/api/muebles
3.  Se ingresan muebles con el formato {
  "nombre": "mesedora marca acme",
  "tipo": "Mesedora",
  "precio_base": 60000.0,
  "stock": 5,
  "estado_activo": true,
  "dimension": "GRANDE",
  "material": "Madera"
}
5. POST  http://localhost:"puerto"/api/variantes 
   {
  "nombre_variante": "Premium",
  "precio_adicional": 25.0
}
7. GET   http://localhost:"puerto"/api/variantes  
8. GET   http://localhost:"puerto"/api/cotizaciones
9. POST  http://localhost:"puerto"/api/cotizaciones
    {
  "detalles": [
    {
      "mueble": { "id_mueble": 1 },
      "variante": { "id_variante": 1 },
      "cantidad": 2
    },
    {
      "mueble": { "id_mueble": 2 },
      "cantidad": 1
    }
]

10. POST  http://localhost:51246/api/cotizaciones/1/confirmar para marcar una cotización como pagada
