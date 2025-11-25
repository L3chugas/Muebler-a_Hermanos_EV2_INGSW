UNIVERSIDAD DEL BÍO-BÍO
FACULTAD DE CIENCIAS EMPRESARIALES
DEPARTAMENTO DE CIENCIAS DE LA COMPUTACIÓN Y TECNOLOGÍA DE LA INFORMACIÓN
INGENIERÍA CIVIL EN INFORMÁTICA



Evaluación 2 Ing. Software
“Patrones de diseño y test unitarios”



Nombre Estudiante: Carlos Orellana Fajardo  
Carrera: Ingeniería Civil en Informática  
Nombre Docentes: Roberto Anabalón, César Aguilera  
Fecha de realización: Lunes 24 de noviembre de 2025

# ÍNDICE

- INTRODUCCIÓN
- OBJETIVOS
- REQUERIMIENTOS
- ARQUITECTURA
- BASE DE DATOS
- IMPLEMENTACIÓN
  - Clase principal
  - Entidades y Repositorios
  - Servicios principales
  - Controladores (REST)
- GUÍA DE USO (Ejecución & Postman)
- PRUEBAS Y RESULTADOS (unitarias & integración)
- CONCLUSIONES

---

# INTRODUCCIÓN

Dentro del contexto de la asignatura “Patrones de diseño y test unitarios”, el proyecto "Mueblería Hermanos" propone una pequeña aplicación de gestión de muebles y cotizaciones (ventas/solicitudes). El objetivo es integrar prácticas de diseño por capas (Controlador, Servicio, Repositorio, Entidad), persistencia con JPA/Hibernate, pruebas unitarias con Mockito/JUnit y pruebas de integración. Se utilizó Gradle, Spring Boot, Java 21 y MySQL (H2 para pruebas).

# OBJETIVOS

**Objetivo general:**
- Implementar un CRUD y lógica de negocio para la gestión de muebles y procesamiento de cotizaciones con pruebas unitarias e integración.

**Objetivos específicos:**
- Crear entidades JPA para `Mueble`, `Cotizacion`, `DetalleCotizacion` y `Variante`.
- Implementar servicios para lógica de negocio (`CotizacionService`, `MuebleService`, `PrecioService`).
- Desarrollar controladores REST para exponer operaciones (GET/POST/PUT/DELETE).
- Implementar pruebas unitarias (Mockito) y tests de integración (SpringBootTest).
- Alinear el mapping de entidades con la DB real y manejar excepciones específicas del dominio (Stock insuficiente).

# REQUERIMIENTOS

**Software:**
- Java 21
- Gradle (wrapper incluido)
- MySQL (o MariaDB)
- IDE opcional
- Postman
- Pandoc + LaTeX (opcional, para generar PDF)

**Dependencias** (incluídas en Gradle):
- spring-boot-starter
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- mysql-connector-java
- h2 (para tests)
- junit-jupiter, mockito (para pruebas)

# ARQUITECTURA

- Paquete principal: `com.ev2.muebleria`
  - `Controladores` — clases REST (e.g., `CotizacionController`, `MuebleController`)
  - `Servicios` — lógica de negocio (e.g., `CotizacionService`, `PrecioService`, `MuebleService`)
  - `Modelos` — entidades JPA (e.g., `Cotizacion`, `DetalleCotizacion`, `Mueble`, `Variante`)
  - `Repositorios` — interfaces `JpaRepository`

Diseño: Controller -> Service -> Repository -> DB

# BASE DE DATOS

**Dump (resumen)** (local: `base_de_datos_SQL/muebles_hermanos.sql`)
- `cotizacion`:
  - `id_contizacion` bigint (PK) AUTO_INCREMENT
  - `fecha` datetime
  - `total` double
  - `estado` varchar(20)
- `detalle_cotizacion`:
  - `id_detalle_cotiz` bigint (PK)
  - `cantidad` int
  - `subtotal` double
  - `cotizacion_id` bigint (FK)
  - `mueble_id` bigint (FK)
  - `variante_id` bigint (FK)
- `mueble`:
  - `id_mueble` bigint (PK)
  - `nombre_mueble` varchar(100) NOT NULL
  - `tipo` varchar(50)
  - `precio_base` double
  - `stock` int DEFAULT 0
  - `estado_activo` tinyint
  - `tamano` varchar(20) (columna que representa la dimensión)
  - `material` varchar(50)
- `variante`:
  - `id_variante` bigint, `nombre_variante`, `precio_adicional`

**Nota:** Se corrigieron mapeos de columnas en las entidades para reflejar las columnas reales del dump (ej. `id_contizacion`, `id_detalle_cotiz`, `nombre_mueble`, `tamano`).

# IMPLEMENTACIÓN

**Clase principal**
- `com.ev2.muebleria.MuebleriaApplication`
  - `@SpringBootApplication`
  - `@EnableJpaRepositories` y `@EntityScan` cuando fue necesario.

**Entidades y Repositorios**
- `Cotizacion`, `DetalleCotizacion`, `Mueble`, `Variante` (entidades JPA).
- Repositorios: `CotizacionRepository`, `MuebleRepository`, `VarianteRepository`.

**Servicios**
- `MuebleService` — CRUD de muebles.
- `CotizacionService` — Lógica de confirmación de venta:
  - Recupera la cotización.
  - Valida que no esté pagada.
  - Recorre `DetalleCotizacion`, recupera `Mueble` desde repo o usa el embebido, valida stock y decrementa, guarda.
  - Cambia estado de la cotización a `PAGADA` y la guarda.
  - Lanza `StockInsuficienteException` cuando procede (unchecked).
- `PrecioService` — Calcula precio final incluyendo variante si existe.

**Controladores (REST)**
- `MuebleController` — `/api/muebles` (GET, POST, PUT, DELETE)
- `CotizacionController` — `/api/cotizaciones` (GET, POST, confirmación: `POST /{id}/confirmar`)
  - `@PathVariable("id")` fue usado explícitamente para evitar errores sin la flag `-parameters`.

**Excepciones**
- `StockInsuficienteException` como `RuntimeException`.

# GUÍA DE USO

**Preparación**
1. Importar `muebles_hermanos.sql` en MySQL (use phpMyAdmin o CLI).
2. Configurar `src/main/resources/application.properties` con URL y credenciales de DB.

**Build & Run**
```powershell
.\gradlew.bat clean build
.\gradlew.bat bootRun
```
El servidor por defecto se ejecutará en `http://localhost:8080` (si no se cambió `server.port`).

**Pruebas**
- Ejecutar tests: `.\gradlew.bat test` (usa H2 para pruebas si `src/test/resources/application.properties` está configurado).

**Ejemplos con Postman**
- Crear mueble (POST /api/muebles) — JSON body con `nombre`, `tipo`, `precio_base`, `stock`, `material`, `dimension`.
- Listar muebles (GET /api/muebles)
- Crear cotización (POST /api/cotizaciones) — un JSON con `detalles` que referencien `mueble` y `variante`.
- Confirmar venta (POST /api/cotizaciones/{id}/confirmar) — responderá 200 o 400 si stock insuficiente.

# PRUEBAS Y RESULTADOS

**Unit tests**
- `CotizacionServiceUnitTest`:
  - Verifica que `confirmarVenta` lanza `StockInsuficienteException` cuando stock insuficiente.
  - Verifica decremento y guardado cuando stock suficiente.

**Integration tests**
- `MuebleriaApplicationTests`: integra Spring y la DB (en H2 para tests o MySQL si está configurado). Se detectaron y corrigieron problemas de mapeo de columnas.

**Problemas detectados (durante desarrollo)**
- Error SQL por nombres de columnas distintos entre entidad y la DB (p. ej. `id_cotizacion` vs `id_contizacion`): Se corrigieron con `@Table` y `@Column`.
- Error en serialización de `detalle_cotizacion` por seleccionar columnas inexistentes — solución: mapear columas y usar `@JsonIgnore` en refererencias cíclicas cuando sea necesario.

# CONCLUSIONES

- Se finalizó la funcionalidad principal y se aplicaron buenas prácticas: separación en capas y pruebas unitarias.
- Para pruebas de integración, usar H2 es muy útil para evitar problemas de esquema con MySQL.
- Se recomienda agregar control central de excepciones con `@ControllerAdvice`, validaciones, y generar documentación OpenAPI/Swagger.

---

# Generar PDF (instrucciones)

1. Instalar `pandoc` y una distribución TeX (MiKTeX o TeXLive) si quieres generar PDF con calidad.
2. Ejecutar:

```powershell
# Generar PDF (si tienes LaTeX instalado)
pandoc REPORT.md -o report/REPORT.pdf --pdf-engine=pdflatex --metadata title="Informe - Mueblería"
# Generar HTML si no tienes LaTeX
pandoc REPORT.md -o report/REPORT.html
```

# Capturas y anexos
- Si deseas, añade capturas en `report/images/` y referencia las imágenes con Markdown.

# Notas finales
- Adjunté también un script de ejemplo para generar el PDF (ver `scripts/generate_report_pdf.ps1`).
- Si lo deseas, puedo generar un PDF aquí (si el entorno lo permite), o hacerlo en tu máquina con Pandoc.

---

Si quieres que:
1) genere un `REPORT.md` en el repositorio (ya lo creé),
2) cree y ejecute la conversión a PDF en el workspace (necesita Pandoc/LaTeX en la máquina),
3) o que cree un `report/` con imágenes y ejemplo de comandos,

indica cuál prefieres y lo hago. También puedo incluir capturas de pantalla simuladas o instrucciones para producirlas.

---

Carlos, dime si quieres que además: generar el PDF (si Pandoc está disponible aquí), o que añada capturas y un `README` con instrucciones de entrega. ¡Listo para seguir!