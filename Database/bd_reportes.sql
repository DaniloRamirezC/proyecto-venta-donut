-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2026 a las 04:26:57
-- Versión del servidor: 10.1.25-MariaDB
-- Versión de PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_reportes`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte_ventas`
--

CREATE TABLE `reporte_ventas` (
  `id` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `total_pedidos` int(11) DEFAULT NULL,
  `total_recaudado` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `reporte_ventas`
--

INSERT INTO `reporte_ventas` (`id`, `fecha`, `total_pedidos`, `total_recaudado`) VALUES
(1, '2026-06-04', 0, 0),
(2, '2026-06-06', 0, 0),
(3, '2026-08-24', 0, 0),
(4, '2026-06-09', 0, 46500),
(5, '2026-06-21', 3, 46500);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `reporte_ventas`
--
ALTER TABLE `reporte_ventas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKoh6opxx9yntl2ohb1g1dg5yl` (`fecha`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `reporte_ventas`
--
ALTER TABLE `reporte_ventas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
