-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2026 a las 04:25:53
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
-- Base de datos: `bd_inventario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `insumo`
--

CREATE TABLE `insumo` (
  `id_insumo` bigint(20) NOT NULL,
  `nom_insumo` varchar(50) NOT NULL,
  `precio_compra` int(11) NOT NULL,
  `stock_actual` int(11) NOT NULL,
  `id_proveedor` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `insumo`
--

INSERT INTO `insumo` (`id_insumo`, `nom_insumo`, `precio_compra`, `stock_actual`, `id_proveedor`) VALUES
(1, 'Harina', 1200, 50, 1),
(2, 'Azúcar', 1200, 50, 1),
(3, 'Huevos', 3500, 100, 2),
(4, 'Huevos', 3500, 100, 1),
(5, 'Chocolate Chips', 5500, 120, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id_proveedor` bigint(20) NOT NULL,
  `nom_proveedor` varchar(60) NOT NULL,
  `rut_proveedor` varchar(12) NOT NULL,
  `telefono` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id_proveedor`, `nom_proveedor`, `rut_proveedor`, `telefono`) VALUES
(1, 'Distribuidora Campo Lindo S.A.', '78654345-9', '987654321'),
(2, 'Avícola Santa Marta S.A.', '75678908-5', '987654356'),
(3, 'Molinera La Africana', '79876543-5', '944078901'),
(5, 'Confitería Galicia', '73456789-6', '978084567');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta`
--

CREATE TABLE `receta` (
  `id_receta` bigint(20) NOT NULL,
  `cantidad_neta` double NOT NULL,
  `id_donut` bigint(20) NOT NULL,
  `unidad_medida` varchar(50) NOT NULL,
  `id_insumo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `receta`
--

INSERT INTO `receta` (`id_receta`, `cantidad_neta`, `id_donut`, `unidad_medida`, `id_insumo`) VALUES
(1, 250, 4, 'gramos', 1),
(2, 350, 5, 'gramos', 1),
(3, 500, 6, 'gramos', 3),
(4, 20, 7, 'kilos', 1),
(5, 200, 8, 'gramos', 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `insumo`
--
ALTER TABLE `insumo`
  ADD PRIMARY KEY (`id_insumo`),
  ADD KEY `FK80jl7u5isc90w5f9sun0qvqxf` (`id_proveedor`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id_proveedor`),
  ADD UNIQUE KEY `UKhj8h1wqt99t2ftcj9q82uaqh6` (`rut_proveedor`);

--
-- Indices de la tabla `receta`
--
ALTER TABLE `receta`
  ADD PRIMARY KEY (`id_receta`),
  ADD KEY `FKpd02lyc1yjv18cxwn14wdjcmq` (`id_insumo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `insumo`
--
ALTER TABLE `insumo`
  MODIFY `id_insumo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id_proveedor` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `receta`
--
ALTER TABLE `receta`
  MODIFY `id_receta` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `insumo`
--
ALTER TABLE `insumo`
  ADD CONSTRAINT `FK80jl7u5isc90w5f9sun0qvqxf` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`);

--
-- Filtros para la tabla `receta`
--
ALTER TABLE `receta`
  ADD CONSTRAINT `FKpd02lyc1yjv18cxwn14wdjcmq` FOREIGN KEY (`id_insumo`) REFERENCES `insumo` (`id_insumo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
