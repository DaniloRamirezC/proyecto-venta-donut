-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-05-2026 a las 07:31:48
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
-- Base de datos: `bd_catalogo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `nombre`) VALUES
(1, 'Vegano'),
(2, 'Sin lactosa'),
(3, 'Sin azúcar'),
(4, 'Premium'),
(5, 'Vegano'),
(6, 'Sin gluten');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `donut`
--

CREATE TABLE `donut` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `nombre_donut` varchar(100) NOT NULL,
  `precio_unitario` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `categoria_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `donut`
--

INSERT INTO `donut` (`id`, `descripcion`, `nombre_donut`, `precio_unitario`, `stock`, `categoria_id`) VALUES
(2, 'Brocheta Donutin con 4 deliciosas mini donuts', 'Brocheta Donutin', 1000, 40, 1),
(3, 'Vaso Donutin con 8 deliciosas mini donuts', 'Vaso Donutin Peque', 2000, 100, 2),
(4, 'Vaso Donutin con 12 deliciosas mini donuts', 'Vaso Donutin Max', 3000, 20, 3),
(5, 'Caja de 16 deliciosos donutines más 3 cuchuflíes', 'Caja Donutin Premium', 6500, 100, 4),
(6, 'Torre temática montada con 50 mini donuts', 'Torre Fiesta Donutin', 16500, 20, 5),
(7, 'Caja ecológica con 8 mini donuts', 'Caja Eco Felicidades', 2500, 30, 6);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `donut`
--
ALTER TABLE `donut`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK29sqmd6rj8aqsfxomr2cbsogc` (`categoria_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `donut`
--
ALTER TABLE `donut`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `donut`
--
ALTER TABLE `donut`
  ADD CONSTRAINT `FK29sqmd6rj8aqsfxomr2cbsogc` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id_categoria`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
