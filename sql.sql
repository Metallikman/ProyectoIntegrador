CREATE DATABASE  IF NOT EXISTS `rebuc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rebuc`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rebuc
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.28-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dependencias`
--

DROP TABLE IF EXISTS `dependencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dependencias` (
  `de_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `de_dependencia` varchar(45) NOT NULL,
  PRIMARY KEY (`de_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dependencias`
--

LOCK TABLES `dependencias` WRITE;
/*!40000 ALTER TABLE `dependencias` DISABLE KEYS */;
INSERT INTO `dependencias` VALUES (1,'Facultad de Telemática');
/*!40000 ALTER TABLE `dependencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos`
--

DROP TABLE IF EXISTS `movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos` (
  `mo_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mo_idUsuario` int(10) unsigned NOT NULL,
  `mo_idTicket` int(10) unsigned NOT NULL,
  `mo_fechaMovimiento` datetime NOT NULL,
  `mo_comentario` text NOT NULL,
  PRIMARY KEY (`mo_id`),
  KEY `fk_idUsuario_movimientos_idx` (`mo_idUsuario`),
  KEY `fk_idTicket_movimientos_idx` (`mo_idTicket`),
  CONSTRAINT `fk_idTicket_movimientos` FOREIGN KEY (`mo_idTicket`) REFERENCES `tickets` (`ti_folio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_idUsuario_movimientos` FOREIGN KEY (`mo_idUsuario`) REFERENCES `usuarios` (`us_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos`
--

LOCK TABLES `movimientos` WRITE;
/*!40000 ALTER TABLE `movimientos` DISABLE KEYS */;
INSERT INTO `movimientos` VALUES (1,10,2,'2017-10-27 05:51:39','sgkegk'),(2,10,2,'2017-10-27 05:51:46','Gjsgk'),(3,10,2,'2017-10-27 06:23:12','dd'),(4,10,2,'2017-10-27 06:24:13','f'),(5,10,2,'2017-10-27 06:25:05','u'),(6,10,2,'2017-10-27 06:25:47','test'),(7,10,3,'2017-10-27 06:27:27','u'),(8,10,3,'2017-10-27 06:27:31','u fjj'),(9,10,3,'2017-10-27 06:27:55','u fjj'),(10,10,3,'2017-10-27 06:28:01','u fjj');
/*!40000 ALTER TABLE `movimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `ro_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ro_rol` varchar(45) NOT NULL,
  PRIMARY KEY (`ro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrador'),(2,'Bibliotecario'),(3,'Universitario');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `st_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `st_status` varchar(45) NOT NULL,
  PRIMARY KEY (`st_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Activo'),(2,'Inactivo'),(3,'Nuevo'),(4,'En proceso'),(5,'Asignado'),(6,'Tomado'),(7,'Respondido'),(8,'Transferido'),(9,'Reasignado'),(10,'Reabierto'),(11,'Cerrado');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tickets` (
  `ti_folio` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ti_fechaAlta` datetime NOT NULL,
  `ti_fechaCierre` datetime DEFAULT NULL,
  `ti_peticion` varchar(45) NOT NULL,
  `ti_calificacion` varchar(45) DEFAULT NULL,
  `ti_solicitante` int(10) unsigned NOT NULL,
  `ti_status` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ti_folio`),
  KEY `fk_solicitante_tickets_idx` (`ti_solicitante`),
  KEY `fk_status_tickets_idx` (`ti_status`),
  CONSTRAINT `fk_solicitante_tickets` FOREIGN KEY (`ti_solicitante`) REFERENCES `usuarios` (`us_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_status_tickets` FOREIGN KEY (`ti_status`) REFERENCES `status` (`st_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,'2017-10-27 01:55:30',NULL,'Primer intento',NULL,10,3),(2,'2017-10-27 04:10:51',NULL,'sfj',NULL,10,7),(3,'2017-10-27 06:27:06',NULL,'ez pz',NULL,10,7);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `us_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `us_nombre` varchar(45) NOT NULL,
  `us_apellido` varchar(45) NOT NULL,
  `us_correo` varchar(45) NOT NULL,
  `us_pass` varchar(45) NOT NULL,
  `us_dependencia` int(10) unsigned NOT NULL,
  `us_rol` int(10) unsigned NOT NULL,
  `us_status` int(10) unsigned NOT NULL,
  PRIMARY KEY (`us_id`),
  UNIQUE KEY `us_correo_UNIQUE` (`us_correo`),
  KEY `fk_depencia_Usuarios_idx` (`us_dependencia`),
  KEY `fk_rol_Usuarios_idx` (`us_rol`),
  KEY `fk_status_usuarios_idx` (`us_status`),
  CONSTRAINT `fk_depencia_Usuarios` FOREIGN KEY (`us_dependencia`) REFERENCES `dependencias` (`de_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rol_Usuarios` FOREIGN KEY (`us_rol`) REFERENCES `roles` (`ro_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_status_usuarios` FOREIGN KEY (`us_status`) REFERENCES `status` (`st_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (10,'Ubaldo','Torres Juárez','utorres0@ucol.mx','3983',1,3,1),(11,'ahaj','jaja','jj@ucol.mx','3983',1,3,2),(12,'Jose','Mendez','jmendez@ucol.mx','3983',1,2,1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_comentarios`
--

DROP TABLE IF EXISTS `vw_comentarios`;
/*!50001 DROP VIEW IF EXISTS `vw_comentarios`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vw_comentarios` AS SELECT 
 1 AS `fecha`,
 1 AS `comentario`,
 1 AS `idTicket`,
 1 AS `nombreCompleto`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_tickets`
--

DROP TABLE IF EXISTS `vw_tickets`;
/*!50001 DROP VIEW IF EXISTS `vw_tickets`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vw_tickets` AS SELECT 
 1 AS `folio`,
 1 AS `fechaAlta`,
 1 AS `fechaCierre`,
 1 AS `peticion`,
 1 AS `calificacion`,
 1 AS `status`,
 1 AS `idSolicitante`,
 1 AS `nombreSolicitante`,
 1 AS `apellidoSolicitante`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'rebuc'
--

--
-- Dumping routines for database 'rebuc'
--

--
-- Final view structure for view `vw_comentarios`
--

/*!50001 DROP VIEW IF EXISTS `vw_comentarios`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_comentarios` AS select `movimientos`.`mo_fechaMovimiento` AS `fecha`,`movimientos`.`mo_comentario` AS `comentario`,`movimientos`.`mo_idTicket` AS `idTicket`,concat(`usuarios`.`us_nombre`,' ',`usuarios`.`us_apellido`) AS `nombreCompleto` from (`movimientos` join `usuarios` on((`movimientos`.`mo_idUsuario` = `usuarios`.`us_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_tickets`
--

/*!50001 DROP VIEW IF EXISTS `vw_tickets`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_tickets` AS select `tickets`.`ti_folio` AS `folio`,`tickets`.`ti_fechaAlta` AS `fechaAlta`,`tickets`.`ti_fechaCierre` AS `fechaCierre`,`tickets`.`ti_peticion` AS `peticion`,`tickets`.`ti_calificacion` AS `calificacion`,`tickets`.`ti_status` AS `status`,`tickets`.`ti_solicitante` AS `idSolicitante`,`usuarios`.`us_nombre` AS `nombreSolicitante`,`usuarios`.`us_apellido` AS `apellidoSolicitante` from (`tickets` join `usuarios` on((`tickets`.`ti_solicitante` = `usuarios`.`us_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-27  7:07:28
