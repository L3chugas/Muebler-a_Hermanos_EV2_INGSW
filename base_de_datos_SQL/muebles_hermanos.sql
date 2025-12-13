-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-12-2025 a las 01:22:55
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `muebles_hermanos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cotizacion`
--

CREATE TABLE `cotizacion` (
  `id_contizacion` bigint(20) NOT NULL,
  `fecha_cotizacion` datetime DEFAULT current_timestamp(),
  `calculo_total` double DEFAULT 0,
  `estado_cotizacion` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cotizacion`
--

INSERT INTO `cotizacion` (`id_contizacion`, `fecha_cotizacion`, `calculo_total`, `estado_cotizacion`) VALUES
(2, '2025-11-24 12:36:51', 0, 'PAGADA'),
(10, '2025-11-24 13:37:27', 657000, 'PAGADA'),
(15, '2025-11-24 23:13:35', 135000, 'PENDIENTE'),
(17, '2025-12-12 20:24:51', 450000, 'PENDIENTE'),
(18, '2025-12-12 20:27:25', 95000, 'PAGADA'),
(19, '2025-12-12 20:37:53', 45000, 'PAGADA'),
(20, '2025-12-12 20:41:41', 360000, 'PAGADA'),
(21, '2025-12-12 21:13:05', 30000, 'PAGADA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_cotizacion`
--

CREATE TABLE `detalle_cotizacion` (
  `id_detalle_cotiz` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `subtotal` double NOT NULL,
  `cotizacion_id` bigint(20) DEFAULT NULL,
  `mueble_id` bigint(20) DEFAULT NULL,
  `variante_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_cotizacion`
--

INSERT INTO `detalle_cotizacion` (`id_detalle_cotiz`, `cantidad`, `subtotal`, `cotizacion_id`, `mueble_id`, `variante_id`) VALUES
(2, 2, 240000, 2, 1, 1),
(11, 1, 395000, 10, 2, 5),
(12, 1, 262000, 10, 2, 7),
(17, 2, 30000, 15, 1, 1),
(18, 1, 105000, 15, 2, 3),
(20, 2, 30000, 17, 1, NULL),
(21, 28, 420000, 17, 1, NULL),
(22, 1, 95000, 18, 2, 2),
(23, 3, 45000, 19, 1, 1),
(24, 5, 360000, 20, 3, 7),
(25, 2, 30000, 21, 1, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mueble`
--

CREATE TABLE `mueble` (
  `id_mueble` bigint(20) NOT NULL,
  `nombre_mueble` varchar(255) NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `precio_base` double NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0,
  `estado_activo` tinyint(1) DEFAULT 1,
  `tamano` varchar(20) DEFAULT NULL,
  `material` varchar(255) DEFAULT NULL,
  `tipo_mueble` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mueble`
--

INSERT INTO `mueble` (`id_mueble`, `nombre_mueble`, `tipo`, `precio_base`, `stock`, `estado_activo`, `tamano`, `material`, `tipo_mueble`) VALUES
(1, 'Silla Clásica', 'Silla', 15000, 3, NULL, NULL, 'Madera', ''),
(2, 'Mesa Comedor', 'Mesa', 80000, 0, NULL, NULL, 'Roble', ''),
(3, 'mesedora marca acme', NULL, 60000, 0, 1, 'GRANDE', 'Madera', 'Mesedora'),
(8, 'Silla de Comedor Clásica', NULL, 45000, 15, 1, 'MEDIANO', 'Madera de Roble', 'Silla'),
(10, 'silla de test', NULL, 155065, 50, NULL, NULL, 'Madera', 'silla'),
(11, 'Gamer Escritorio', NULL, 99999, 1, NULL, NULL, 'pulento', 'Escritorio'),
(12, 'silla de 4 patas', NULL, 5, 5, NULL, NULL, 'madera', 'silla');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `variante`
--

CREATE TABLE `variante` (
  `id_variante` bigint(20) NOT NULL,
  `nombre_variante` varchar(255) DEFAULT NULL,
  `precio_adicional` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `variante`
--

INSERT INTO `variante` (`id_variante`, `nombre_variante`, `precio_adicional`) VALUES
(1, 'Sin Variantes', 0),
(2, 'Barniz Premium', 15000),
(3, 'Cojines de Seda', 25000),
(4, 'Ruedas de Alto Tráfico', 5000),
(5, 'Tapiz de Cuero', 45000),
(6, 'Manillas Doradas', 3500),
(7, 'Vidrio Templado', 12000),
(8, 'Color Premium - Gris Oscuro', 15000);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  ADD PRIMARY KEY (`id_contizacion`);

--
-- Indices de la tabla `detalle_cotizacion`
--
ALTER TABLE `detalle_cotizacion`
  ADD PRIMARY KEY (`id_detalle_cotiz`),
  ADD KEY `fk_detalle_cotizacion` (`cotizacion_id`),
  ADD KEY `fk_detalle_mueble` (`mueble_id`),
  ADD KEY `fk_detalle_variante` (`variante_id`);

--
-- Indices de la tabla `mueble`
--
ALTER TABLE `mueble`
  ADD PRIMARY KEY (`id_mueble`);

--
-- Indices de la tabla `variante`
--
ALTER TABLE `variante`
  ADD PRIMARY KEY (`id_variante`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  MODIFY `id_contizacion` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `detalle_cotizacion`
--
ALTER TABLE `detalle_cotizacion`
  MODIFY `id_detalle_cotiz` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `mueble`
--
ALTER TABLE `mueble`
  MODIFY `id_mueble` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `variante`
--
ALTER TABLE `variante`
  MODIFY `id_variante` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_cotizacion`
--
ALTER TABLE `detalle_cotizacion`
  ADD CONSTRAINT `fk_detalle_cotizacion` FOREIGN KEY (`cotizacion_id`) REFERENCES `cotizacion` (`id_contizacion`),
  ADD CONSTRAINT `fk_detalle_mueble` FOREIGN KEY (`mueble_id`) REFERENCES `mueble` (`id_mueble`),
  ADD CONSTRAINT `fk_detalle_variante` FOREIGN KEY (`variante_id`) REFERENCES `variante` (`id_variante`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
