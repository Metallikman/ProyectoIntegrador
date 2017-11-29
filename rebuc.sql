-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 29, 2017 at 02:36 PM
-- Server version: 10.1.24-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id3295737_rebuc`
--

-- --------------------------------------------------------

--
-- Table structure for table `dependencias`
--

CREATE TABLE `dependencias` (
  `de_id` int(10) UNSIGNED NOT NULL,
  `de_dependencia` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dependencias`
--

INSERT INTO `dependencias` (`de_id`, `de_dependencia`) VALUES
(1, 'Facultad de Telemática'),
(2, 'Facultad de Ingenieria Civil'),
(3, 'Bachillerato Técnico N° 4');

-- --------------------------------------------------------

--
-- Table structure for table `etiquetamovimientos`
--

CREATE TABLE `etiquetamovimientos` (
  `em_id` int(10) UNSIGNED NOT NULL,
  `em_etiquetaMovimiento` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `etiquetamovimientos`
--

INSERT INTO `etiquetamovimientos` (`em_id`, `em_etiquetaMovimiento`) VALUES
(1, 'Respondido por el bibliotecario.'),
(2, 'Respondido por el usuario.'),
(3, 'Tomado por el bibliotecario.'),
(4, 'Transferido');

-- --------------------------------------------------------

--
-- Table structure for table `movimientos`
--

CREATE TABLE `movimientos` (
  `mo_id` int(10) UNSIGNED NOT NULL,
  `mo_idUsuario` int(10) UNSIGNED NOT NULL,
  `mo_idTicket` int(10) UNSIGNED NOT NULL,
  `mo_fechaMovimiento` datetime NOT NULL,
  `mo_comentario` text NOT NULL,
  `mo_etiquetaMovimiento` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `ro_id` int(10) UNSIGNED NOT NULL,
  `ro_rol` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`ro_id`, `ro_rol`) VALUES
(1, 'Administrador'),
(2, 'Bibliotecario'),
(3, 'Universitario'),
(4, 'Responsable');

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `st_id` int(10) UNSIGNED NOT NULL,
  `st_status` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`st_id`, `st_status`) VALUES
(1, 'Activo'),
(2, 'Inactivo'),
(3, 'Nuevo'),
(4, 'En proceso'),
(5, 'Reabierto'),
(6, 'Cerrado');

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE `tickets` (
  `ti_folio` int(10) UNSIGNED NOT NULL,
  `ti_fechaAlta` datetime NOT NULL,
  `ti_fechaCierre` datetime DEFAULT NULL,
  `ti_peticion` varchar(45) NOT NULL,
  `ti_detalles` text NOT NULL,
  `ti_calificacion` int(10) DEFAULT NULL,
  `ti_solicitante` int(10) UNSIGNED NOT NULL,
  `ti_status` int(10) UNSIGNED NOT NULL,
  `ti_bibliotecario` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`ti_folio`, `ti_fechaAlta`, `ti_fechaCierre`, `ti_peticion`, `ti_detalles`, `ti_calificacion`, `ti_solicitante`, `ti_status`, `ti_bibliotecario`) VALUES
(14, '2017-11-29 07:30:37', NULL, 'Hola', 'Buenaz', NULL, 19, 3, NULL),
(15, '2017-11-29 07:59:58', NULL, 'Telematica', '123', NULL, 10, 3, 12);

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `us_id` int(10) UNSIGNED NOT NULL,
  `us_nombre` varchar(45) NOT NULL,
  `us_apellido` varchar(45) NOT NULL,
  `us_correo` varchar(45) NOT NULL,
  `us_pass` varchar(45) NOT NULL,
  `us_dependencia` int(10) UNSIGNED NOT NULL,
  `us_rol` int(10) UNSIGNED NOT NULL,
  `us_status` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`us_id`, `us_nombre`, `us_apellido`, `us_correo`, `us_pass`, `us_dependencia`, `us_rol`, `us_status`) VALUES
(10, 'Ubaldo', 'Torres Juárez', 'utorres0@ucol.mx', '3983', 1, 3, 1),
(11, 'ahaj', 'jaja', 'jj@ucol.mx', '3983', 1, 3, 1),
(12, 'Jose', 'Mendez', 'jmendez@ucol.mx', '3983', 1, 2, 1),
(13, 'Roberto', 'Martinez', 'rmartinez@ucol.mx', '1234', 1, 2, 1),
(14, 'Fernando', 'Ceballos como el hotel', 'cerealdantes@ucol.mx', 'q', 3, 2, 1),
(15, 'german eduardo', 'mendez hernandez', 'gmendez2@ucol.mx', 'german18!', 1, 3, 1),
(16, 'Ubaldo', 'Torres', 'cuantotequiero@ucol.mx', '123', 1, 3, 1),
(17, 'Torombolo', 'Derecha Izquierda', 'encargado@ucol.mx', '12345', 1, 4, 1),
(18, 'José', 'José', 'jose@ucol.mx', '123', 1, 2, 1),
(19, 'Bachi', 'Cuarto', 'bachi@ucol.mx', '123', 3, 3, 1),
(20, 'Civil', 'Topo', 'civil@ucol.mx', '123', 3, 4, 1),
(21, 'Prueba', 'bonitas', 'zz@ucol.mx', '123', 1, 1, 1),
(22, 'Preuba', 'Bibliotecario', 'prueba@ucol.mx', '123', 1, 2, 1);

-- --------------------------------------------------------

--
-- Stand-in structure for view `vw_tickets`
-- (See below for the actual view)
--
CREATE TABLE `vw_tickets` (
`folio` int(10) unsigned
,`fechaAlta` datetime
,`fechaCierre` datetime
,`peticion` varchar(45)
,`calificacion` int(10)
,`status` int(10) unsigned
,`idSolicitante` int(10) unsigned
,`idBibliotecario` int(10) unsigned
,`nombreSolicitante` varchar(45)
,`apellidoSolicitante` varchar(45)
,`dependenciaSolicitante` int(10) unsigned
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `vw_tickets_completos`
-- (See below for the actual view)
--
CREATE TABLE `vw_tickets_completos` (
`folio` int(10) unsigned
,`fechaAlta` datetime
,`fechaCierre` datetime
,`peticion` varchar(45)
,`calificacion` int(10)
,`status` int(10) unsigned
,`idSolicitante` int(10) unsigned
,`nombreSolicitante` varchar(45)
,`apellidoSolicitante` varchar(45)
,`dependenciaSolicitante` int(10) unsigned
,`nombreBibliotecario` varchar(45)
,`apellidoBibliotecario` varchar(45)
,`idBibliotecario` int(10) unsigned
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `vw_usuarios`
-- (See below for the actual view)
--
CREATE TABLE `vw_usuarios` (
`idUsuario` int(10) unsigned
,`nombre` varchar(45)
,`apellido` varchar(45)
,`correo` varchar(45)
,`estado` int(10) unsigned
,`idDependencia` int(10) unsigned
,`dependencia` varchar(45)
,`rol` int(10) unsigned
,`nombreRol` varchar(45)
,`nombreStatus` varchar(45)
);

-- --------------------------------------------------------

--
-- Structure for view `vw_tickets`
--
DROP TABLE IF EXISTS `vw_tickets`;

CREATE ALGORITHM=UNDEFINED DEFINER=`id3295737_utorres0`@`%` SQL SECURITY DEFINER VIEW `vw_tickets`  AS  select `tickets`.`ti_folio` AS `folio`,`tickets`.`ti_fechaAlta` AS `fechaAlta`,`tickets`.`ti_fechaCierre` AS `fechaCierre`,`tickets`.`ti_peticion` AS `peticion`,`tickets`.`ti_calificacion` AS `calificacion`,`tickets`.`ti_status` AS `status`,`tickets`.`ti_solicitante` AS `idSolicitante`,`tickets`.`ti_bibliotecario` AS `idBibliotecario`,`usuarios`.`us_nombre` AS `nombreSolicitante`,`usuarios`.`us_apellido` AS `apellidoSolicitante`,`usuarios`.`us_dependencia` AS `dependenciaSolicitante` from (`tickets` join `usuarios` on((`tickets`.`ti_solicitante` = `usuarios`.`us_id`))) ;

-- --------------------------------------------------------

--
-- Structure for view `vw_tickets_completos`
--
DROP TABLE IF EXISTS `vw_tickets_completos`;

CREATE ALGORITHM=UNDEFINED DEFINER=`id3295737_utorres0`@`%` SQL SECURITY DEFINER VIEW `vw_tickets_completos`  AS  select `vw_tickets`.`folio` AS `folio`,`vw_tickets`.`fechaAlta` AS `fechaAlta`,`vw_tickets`.`fechaCierre` AS `fechaCierre`,`vw_tickets`.`peticion` AS `peticion`,`vw_tickets`.`calificacion` AS `calificacion`,`vw_tickets`.`status` AS `status`,`vw_tickets`.`idSolicitante` AS `idSolicitante`,`vw_tickets`.`nombreSolicitante` AS `nombreSolicitante`,`vw_tickets`.`apellidoSolicitante` AS `apellidoSolicitante`,`vw_tickets`.`dependenciaSolicitante` AS `dependenciaSolicitante`,`bibliotecario`.`us_nombre` AS `nombreBibliotecario`,`bibliotecario`.`us_apellido` AS `apellidoBibliotecario`,`bibliotecario`.`us_id` AS `idBibliotecario` from (`vw_tickets` left join `usuarios` `bibliotecario` on((`bibliotecario`.`us_id` = `vw_tickets`.`idBibliotecario`))) ;

-- --------------------------------------------------------

--
-- Structure for view `vw_usuarios`
--
DROP TABLE IF EXISTS `vw_usuarios`;

CREATE ALGORITHM=UNDEFINED DEFINER=`id3295737_utorres0`@`%` SQL SECURITY DEFINER VIEW `vw_usuarios`  AS  select `usuarios`.`us_id` AS `idUsuario`,`usuarios`.`us_nombre` AS `nombre`,`usuarios`.`us_apellido` AS `apellido`,`usuarios`.`us_correo` AS `correo`,`usuarios`.`us_status` AS `estado`,`usuarios`.`us_dependencia` AS `idDependencia`,`dependencias`.`de_dependencia` AS `dependencia`,`usuarios`.`us_rol` AS `rol`,`roles`.`ro_rol` AS `nombreRol`,`status`.`st_status` AS `nombreStatus` from (((`usuarios` join `status` on((`usuarios`.`us_status` = `status`.`st_id`))) join `dependencias` on((`usuarios`.`us_dependencia` = `dependencias`.`de_id`))) join `roles` on((`usuarios`.`us_rol` = `roles`.`ro_id`))) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dependencias`
--
ALTER TABLE `dependencias`
  ADD PRIMARY KEY (`de_id`);

--
-- Indexes for table `etiquetamovimientos`
--
ALTER TABLE `etiquetamovimientos`
  ADD PRIMARY KEY (`em_id`);

--
-- Indexes for table `movimientos`
--
ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`mo_id`),
  ADD KEY `fk_idUsuario_movimientos_idx` (`mo_idUsuario`),
  ADD KEY `fk_idTicket_movimientos_idx` (`mo_idTicket`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`ro_id`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`st_id`);

--
-- Indexes for table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ti_folio`),
  ADD KEY `fk_solicitante_tickets_idx` (`ti_solicitante`),
  ADD KEY `fk_status_tickets_idx` (`ti_status`),
  ADD KEY `fk_bibliotecario_tickets_idx` (`ti_bibliotecario`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`us_id`),
  ADD UNIQUE KEY `us_correo_UNIQUE` (`us_correo`),
  ADD KEY `fk_depencia_Usuarios_idx` (`us_dependencia`),
  ADD KEY `fk_rol_Usuarios_idx` (`us_rol`),
  ADD KEY `fk_status_usuarios_idx` (`us_status`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dependencias`
--
ALTER TABLE `dependencias`
  MODIFY `de_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `etiquetamovimientos`
--
ALTER TABLE `etiquetamovimientos`
  MODIFY `em_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `mo_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `ro_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `st_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `tickets`
--
ALTER TABLE `tickets`
  MODIFY `ti_folio` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `us_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `movimientos`
--
ALTER TABLE `movimientos`
  ADD CONSTRAINT `fk_idTicket_movimientos` FOREIGN KEY (`mo_idTicket`) REFERENCES `tickets` (`ti_folio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_idUsuario_movimientos` FOREIGN KEY (`mo_idUsuario`) REFERENCES `usuarios` (`us_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `fk_solicitante_tickets` FOREIGN KEY (`ti_solicitante`) REFERENCES `usuarios` (`us_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_status_tickets` FOREIGN KEY (`ti_status`) REFERENCES `status` (`st_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_depencia_Usuarios` FOREIGN KEY (`us_dependencia`) REFERENCES `dependencias` (`de_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_rol_Usuarios` FOREIGN KEY (`us_rol`) REFERENCES `roles` (`ro_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_status_usuarios` FOREIGN KEY (`us_status`) REFERENCES `status` (`st_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
