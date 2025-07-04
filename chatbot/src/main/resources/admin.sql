-- MySQL dump 10.13  Distrib 9.3.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bbdd_msvcAdmin
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
                          `id` binary(16) NOT NULL,
                          `activo` bit(1) NOT NULL,
                          `fecha_asignacion` datetime(6) NOT NULL,
                          `user_id` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `UKpiovo1hsx7hi5f9ax85epqya9` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (_binary 'n��0�@H��\�\Z�\�',_binary '','2025-06-25 21:07:47.000000','4');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditoria_admin`
--

DROP TABLE IF EXISTS `auditoria_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria_admin` (
                                   `id` binary(16) NOT NULL,
                                   `admin_id` binary(16) NOT NULL,
                                   `detalle` varchar(1000) DEFAULT NULL,
                                   `entidad_afectada_id` varchar(255) NOT NULL,
                                   `fecha` datetime(6) NOT NULL,
                                   `tipo_accion` varchar(255) NOT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria_admin`
--

LOCK TABLES `auditoria_admin` WRITE;
/*!40000 ALTER TABLE `auditoria_admin` DISABLE KEYS */;
INSERT INTO `auditoria_admin` VALUES (_binary 'D�R�WB@��\�6ڼv',_binary 'n��0�@H��\�\Z�\�','Reporte de usuarios top desde 2011-11-11 hasta 2044-11-11 (tipo: basica)','-','2025-06-27 15:53:57.561995','consultar_reporte'),(_binary 'T\�S�W�G�a�\�4cȮ',_binary 'n��0�@H��\�\Z�\�','Se eliminó el monopatín con ID 19','19','2025-06-25 21:28:40.009631','eliminar_monopatin'),(_binary ']x=��L\0�g\�y�1,',_binary 'n��0�@H��\�\Z�\�','Admin 4 cambió el estado del scooter a \'ocupado\'','4','2025-06-25 21:57:12.735065','cambiar_estado_scooter'),(_binary 'o�\�~)Cպ�ص˖n',_binary 'n��0�@H��\�\Z�\�','Admin 4 cambió el estado del scooter a \'ocupado\'','4','2025-06-25 21:57:03.822778','cambiar_estado_scooter'),(_binary 'tn\�k��C٘\�F�[�x',_binary 'n��0�@H��\�\Z�\�','Reporte de usuarios top desde 2011-11-11 hasta 2044-11-11 (tipo: basica)','-','2025-06-27 15:54:28.528454','consultar_reporte'),(_binary 'v\�\�#\�KۗUAU)�n�',_binary 'n��0�@H��\�\Z�\�','Admin 4 cambió el estado del scooter a \'feo\'','4','2025-06-25 21:58:07.529935','cambiar_estado_scooter'),(_binary '�Q,5�L\��GmK\�99�',_binary 'n��0�@H��\�\Z�\�','Reporte de usuarios top desde 2011-11-11 hasta 2044-11-11 (tipo: premium)','-','2025-06-27 15:54:35.886199','consultar_reporte'),(_binary '�O�PF�G��dE=.œ',_binary 'n��0�@H��\�\Z�\�','Consulta de facturación total (año=2025, desde=2011-11-11, hasta=2044-11-11)','-','2025-06-26 20:09:25.626912','consultar_reporte'),(_binary '�o��rJW�\�ɿ�#D',_binary 'n��0�@H��\�\Z�\�','Consulta de facturación total (año=2025, desde=2011-11-11, hasta=2044-11-11)','-','2025-06-26 20:07:06.001681','consultar_reporte'),(_binary '\�hԮ\�\�JѺ�\�MC#\�',_binary 'n��0�@H��\�\Z�\�','Reporte de usuarios top desde 2011-11-11 hasta 2044-11-11 (tipo: basica)','-','2025-06-27 15:54:12.057996','consultar_reporte'),(_binary '��p\�	3I��$��,X',_binary 'n��0�@H��\�\Z�\�','Admin 4 cambió el estado del scooter a \'ocupado\'','4','2025-06-25 21:58:01.394075','cambiar_estado_scooter');
/*!40000 ALTER TABLE `auditoria_admin` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-03 19:07:30
