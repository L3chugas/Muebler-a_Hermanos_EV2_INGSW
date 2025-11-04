-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-11-2025 a las 18:21:57
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
-- Base de datos: `muebles_hermanossa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cotizacion`
--

CREATE TABLE `cotizacion` (
  `ID_cotizacion` int(11) NOT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `precio_total_calculado` decimal(10,2) NOT NULL,
  `estado` enum('pendiente','confirmada','cancelada') NOT NULL DEFAULT 'pendiente'
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_cotizacion`
--

CREATE TABLE `detalle_cotizacion` (
  `ID_detalle_cotizacion` int(11) NOT NULL,
  `ID_cotizacion_fk` int(11) NOT NULL,
  `ID_mueble_fk` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario_calculado` decimal(10,2) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_variante`
--

CREATE TABLE `detalle_variante` (
  `ID_detalle_cotizacion_fk` int(11) NOT NULL,
  `ID_variante_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mueble`
--

CREATE TABLE `mueble` (
  `ID_mueble` int(11) NOT NULL,
  `nombre_mueble` varchar(100) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `precio_base` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0,
  `estado` enum('activo','inactivo') NOT NULL DEFAULT 'activo',
  `tamaño` enum('Grande','Mediano','Pequeño') NOT NULL,
  `material` varchar(50) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `variante`
--

CREATE TABLE `variante` (
  `ID_variante` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio_adicional` decimal(10,2) NOT NULL
) ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  ADD PRIMARY KEY (`ID_cotizacion`);

--
-- Indices de la tabla `detalle_cotizacion`
--
ALTER TABLE `detalle_cotizacion`
  ADD PRIMARY KEY (`ID_detalle_cotizacion`),
  ADD KEY `ID_cotizacion_fk` (`ID_cotizacion_fk`),
  ADD KEY `ID_mueble_fk` (`ID_mueble_fk`);

--
-- Indices de la tabla `detalle_variante`
--
ALTER TABLE `detalle_variante`
  ADD PRIMARY KEY (`ID_detalle_cotizacion_fk`,`ID_variante_fk`),
  ADD KEY `ID_variante_fk` (`ID_variante_fk`);

--
-- Indices de la tabla `mueble`
--
ALTER TABLE `mueble`
  ADD PRIMARY KEY (`ID_mueble`);

--
-- Indices de la tabla `variante`
--
ALTER TABLE `variante`
  ADD PRIMARY KEY (`ID_variante`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  MODIFY `ID_cotizacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_cotizacion`
--
ALTER TABLE `detalle_cotizacion`
  MODIFY `ID_detalle_cotizacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `mueble`
--
ALTER TABLE `mueble`
  MODIFY `ID_mueble` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `variante`
--
ALTER TABLE `variante`
  MODIFY `ID_variante` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_cotizacion`
--
ALTER TABLE `detalle_cotizacion`
  ADD CONSTRAINT `detalle_cotizacion_ibfk_1` FOREIGN KEY (`ID_cotizacion_fk`) REFERENCES `cotizacion` (`ID_cotizacion`) ON DELETE CASCADE,
  ADD CONSTRAINT `detalle_cotizacion_ibfk_2` FOREIGN KEY (`ID_mueble_fk`) REFERENCES `mueble` (`ID_mueble`);

--
-- Filtros para la tabla `detalle_variante`
--
ALTER TABLE `detalle_variante`
  ADD CONSTRAINT `detalle_variante_ibfk_1` FOREIGN KEY (`ID_detalle_cotizacion_fk`) REFERENCES `detalle_cotizacion` (`ID_detalle_cotizacion`) ON DELETE CASCADE,
  ADD CONSTRAINT `detalle_variante_ibfk_2` FOREIGN KEY (`ID_variante_fk`) REFERENCES `variante` (`ID_variante`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
