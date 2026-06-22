-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2026 a las 04:25:08
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
-- Base de datos: `bd_clientes`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` bigint(20) NOT NULL,
  `apellido_cliente` varchar(100) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre_cliente` varchar(100) NOT NULL,
  `rut_cliente` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `apellido_cliente`, `direccion`, `email`, `nombre_cliente`, `rut_cliente`) VALUES
(1, 'Perez', 'Avda Pajaritos 6350', 'jperez@gmail.com', 'Juan', '12345678-9'),
(2, 'Solar', 'Calle Campanario 640', 'asolar@gmail.com', 'Ana', '9876543-2'),
(3, 'Soto', 'Avda Esquina Blanca', 'danisoto@gmail.com', 'Daniela', '789456-2'),
(4, 'Jiménez', 'Avda Senadora María de la Cruz 650', 'manujimenez@gmail.com', 'Manuel', '11234567-8'),
(5, 'Mandel', 'Primera Transversal 1450', 'olimandel@gmail.com', 'Olivia', '9876345-2'),
(6, 'Montenegro', 'Avda El Conquistador 3800', 'sabi.montenegro@gmail.com', 'Sabina', '12765890-4'),
(7, 'Donoso Cáceres', 'Avenida Las Lilas 123', 'jpadonocaceres@gmail.com', 'Juan Pablo', '18543987-6');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
