-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2026 a las 04:26:19
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
-- Base de datos: `bd_pagos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comprobante_pago`
--

CREATE TABLE `comprobante_pago` (
  `id_comprobante` bigint(20) NOT NULL,
  `detalle` varchar(50) NOT NULL,
  `id_pago` bigint(20) DEFAULT NULL,
  `monto_iva` int(11) NOT NULL,
  `monto_neto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `comprobante_pago`
--

INSERT INTO `comprobante_pago` (`id_comprobante`, `detalle`, `id_pago`, `monto_iva`, `monto_neto`) VALUES
(1, 'Compra Brocheta Donutin', 1, 160, 840),
(2, 'Compra vaso Donutin', 2, 319, 1681),
(3, 'Compra Vaso Donutin Max', 3, 479, 2521),
(4, 'Compra caja Donutin Premium', 4, 1038, 5462),
(5, 'Compra Torre Fiesta Donutin', 5, 2634, 13866),
(6, 'Compra Caja Eco Felicidades', 6, 2634, 13866),
(7, 'Compra Brocheta Donutin', 7, 160, 840);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `id_pago` bigint(20) NOT NULL,
  `estado_transaccion` bit(1) NOT NULL,
  `metodo_pago` varchar(25) NOT NULL,
  `monto` int(11) NOT NULL,
  `pedido_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`id_pago`, `estado_transaccion`, `metodo_pago`, `monto`, `pedido_id`) VALUES
(1, b'1', 'Tarjeta débito', 1000, 5),
(2, b'1', 'Webpay', 2000, 6),
(3, b'1', 'Tarjeta débito', 3000, 7),
(4, b'1', 'Tarjeta crédito', 6500, 8),
(5, b'1', 'Webpay', 16500, 9),
(6, b'1', 'Efectivo', 16500, 10),
(7, b'1', 'Tarjeta débito', 1000, 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comprobante_pago`
--
ALTER TABLE `comprobante_pago`
  ADD PRIMARY KEY (`id_comprobante`),
  ADD UNIQUE KEY `UKmpx7xr2gpff7mj1792x2aopi3` (`id_pago`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`id_pago`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comprobante_pago`
--
ALTER TABLE `comprobante_pago`
  MODIFY `id_comprobante` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `id_pago` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comprobante_pago`
--
ALTER TABLE `comprobante_pago`
  ADD CONSTRAINT `FKq3re245vvc1l803brhl7taig0` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id_pago`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
