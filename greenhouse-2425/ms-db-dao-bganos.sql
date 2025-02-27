-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-11-2024 a las 22:59:28
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
-- Base de datos: `ms-db-dao-bganos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrada`
--

CREATE TABLE `entrada` (
  `id` int(11) NOT NULL,
  `id_invernadero` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `precio` int(11) NOT NULL,
  `stock_entradas` int(11) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fabricante`
--

CREATE TABLE `fabricante` (
  `id` int(11) NOT NULL,
  `cod_fabricante` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fabricante_extranjero`
--

CREATE TABLE `fabricante_extranjero` (
  `id_fabricante` int(11) NOT NULL,
  `aranceles` int(11) NOT NULL,
  `pais_origen` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fabricante_local`
--

CREATE TABLE `fabricante_local` (
  `id_fabricante` int(11) NOT NULL,
  `impuestos` int(11) NOT NULL,
  `subvenciones` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id` int(11) NOT NULL,
  `precio_total` double NOT NULL,
  `fecha_compra` date NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `invernadero`
--

CREATE TABLE `invernadero` (
  `id` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `sustrato` varchar(20) NOT NULL,
  `tipo_iluminacion` varchar(20) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_factura`
--

CREATE TABLE `linea_factura` (
  `id` int(11) NOT NULL,
  `id_factura` int(11) NOT NULL,
  `id_entrada` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `planta`
--

CREATE TABLE `planta` (
  `id` int(11) NOT NULL,
  `id_invernadero` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `nombre_cientifico` varchar(255) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `planta_frutal`
--

CREATE TABLE `planta_frutal` (
  `id_planta` int(11) NOT NULL,
  `nombre_fruta` varchar(255) NOT NULL,
  `maduracion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `planta_no_frutal`
--

CREATE TABLE `planta_no_frutal` (
  `id` int(11) NOT NULL,
  `tipo_hoja` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sistemas_riego`
--

CREATE TABLE `sistemas_riego` (
  `id` int(11) NOT NULL,
  `id_fabricante` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `potencia_riego` int(11) NOT NULL,
  `frecuencia` int(11) NOT NULL,
  `cantidad_agua` int(11) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sistemas_riego_de_invernadero`
--

CREATE TABLE `sistemas_riego_de_invernadero` (
  `id_invernadero` int(11) NOT NULL,
  `id_sistema_riego` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `entrada`
--
ALTER TABLE `entrada`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_invernadero` (`id_invernadero`,`fecha`);

--
-- Indices de la tabla `fabricante`
--
ALTER TABLE `fabricante`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cod_fabricante` (`cod_fabricante`);

--
-- Indices de la tabla `fabricante_extranjero`
--
ALTER TABLE `fabricante_extranjero`
  ADD PRIMARY KEY (`id_fabricante`);

--
-- Indices de la tabla `fabricante_local`
--
ALTER TABLE `fabricante_local`
  ADD PRIMARY KEY (`id_fabricante`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `invernadero`
--
ALTER TABLE `invernadero`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `linea_factura`
--
ALTER TABLE `linea_factura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_factura` (`id_factura`,`id_entrada`),
  ADD KEY `id_entrada` (`id_entrada`);

--
-- Indices de la tabla `planta`
--
ALTER TABLE `planta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_invernadero` (`id_invernadero`);

--
-- Indices de la tabla `planta_frutal`
--
ALTER TABLE `planta_frutal`
  ADD PRIMARY KEY (`id_planta`);

--
-- Indices de la tabla `planta_no_frutal`
--
ALTER TABLE `planta_no_frutal`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sistemas_riego`
--
ALTER TABLE `sistemas_riego`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `id_fabricante` (`id_fabricante`);

--
-- Indices de la tabla `sistemas_riego_de_invernadero`
--
ALTER TABLE `sistemas_riego_de_invernadero`
  ADD PRIMARY KEY (`id_invernadero`,`id_sistema_riego`),
  ADD KEY `id_invernadero` (`id_invernadero`,`id_sistema_riego`),
  ADD KEY `id_sistema_riego` (`id_sistema_riego`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `entrada`
--
ALTER TABLE `entrada`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `fabricante`
--
ALTER TABLE `fabricante`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `invernadero`
--
ALTER TABLE `invernadero`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `linea_factura`
--
ALTER TABLE `linea_factura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `planta`
--
ALTER TABLE `planta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sistemas_riego`
--
ALTER TABLE `sistemas_riego`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `entrada`
--
ALTER TABLE `entrada`
  ADD CONSTRAINT `entrada_ibfk_1` FOREIGN KEY (`id_invernadero`) REFERENCES `invernadero` (`id`);

--
-- Filtros para la tabla `fabricante_extranjero`
--
ALTER TABLE `fabricante_extranjero`
  ADD CONSTRAINT `fabricante_ibfk_1` FOREIGN KEY (`id_fabricante`) REFERENCES `fabricante` (`id`);

--
-- Filtros para la tabla `fabricante_local`
--
ALTER TABLE `fabricante_local`
  ADD CONSTRAINT `fabricante_local_ibfk_1` FOREIGN KEY (`id_fabricante`) REFERENCES `fabricante` (`id`);

--
-- Filtros para la tabla `linea_factura`
--
ALTER TABLE `linea_factura`
  ADD CONSTRAINT `linea_factura_ibfk_1` FOREIGN KEY (`id_factura`) REFERENCES `factura` (`id`),
  ADD CONSTRAINT `linea_factura_ibfk_2` FOREIGN KEY (`id_entrada`) REFERENCES `entrada` (`id`);

--
-- Filtros para la tabla `planta`
--
ALTER TABLE `planta`
  ADD CONSTRAINT `planta_ibfk_1` FOREIGN KEY (`id_invernadero`) REFERENCES `invernadero` (`id`);

--
-- Filtros para la tabla `planta_frutal`
--
ALTER TABLE `planta_frutal`
  ADD CONSTRAINT `planta_frutal_ibfk_1` FOREIGN KEY (`id_planta`) REFERENCES `planta` (`id`);

--
-- Filtros para la tabla `planta_no_frutal`
--
ALTER TABLE `planta_no_frutal`
  ADD CONSTRAINT `planta_no_frutal_ibfk_1` FOREIGN KEY (`id`) REFERENCES `planta` (`id`);

--
-- Filtros para la tabla `sistemas_riego`
--
ALTER TABLE `sistemas_riego`
  ADD CONSTRAINT `sistemas_riego_ibfk_1` FOREIGN KEY (`id_fabricante`) REFERENCES `fabricante` (`id`);

--
-- Filtros para la tabla `sistemas_riego_de_invernadero`
--
ALTER TABLE `sistemas_riego_de_invernadero`
  ADD CONSTRAINT `sistemas_riego_de_invernadero_ibfk_1` FOREIGN KEY (`id_sistema_riego`) REFERENCES `sistemas_riego` (`id`),
  ADD CONSTRAINT `sistemas_riego_de_invernadero_ibfk_2` FOREIGN KEY (`id_invernadero`) REFERENCES `invernadero` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
