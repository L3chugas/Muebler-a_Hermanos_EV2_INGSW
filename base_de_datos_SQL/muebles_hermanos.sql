-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-11-2025 a las 21:33:17
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
  `fecha` datetime DEFAULT current_timestamp(),
  `total` double DEFAULT 0,
  `estado` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mueble`
--

CREATE TABLE `mueble` (
  `id_mueble` bigint(20) NOT NULL,
  `nombre_mueble` varchar(100) NOT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `precio_base` double NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0,
  `estado_activo` tinyint(1) DEFAULT 1,
  `tamano` varchar(20) DEFAULT NULL,
  `material` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `variante`
--

CREATE TABLE `variante` (
  `id_variante` bigint(20) NOT NULL,
  `nombre_variante` varchar(100) NOT NULL,
  `precio_adicional` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  MODIFY `id_contizacion` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_cotizacion`
--
ALTER TABLE `detalle_cotizacion`
  MODIFY `id_detalle_cotiz` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `mueble`
--
ALTER TABLE `mueble`
  MODIFY `id_mueble` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `variante`
--
ALTER TABLE `variante`
  MODIFY `id_variante` bigint(20) NOT NULL AUTO_INCREMENT;

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
