-- MySQL dump 10.13  Distrib 9.3.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: viaje
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
-- Table structure for table `viaje`
--

DROP TABLE IF EXISTS `viaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `viaje` (
                         `fecha` date NOT NULL,
                         `hora_fin` time(6) DEFAULT NULL,
                         `hora_inicio` time(6) NOT NULL,
                         `kilometros` double NOT NULL,
                         `tiempo_pausa` double DEFAULT NULL,
                         `id_parada_fin` bigint NOT NULL,
                         `id_parada_inicio` bigint NOT NULL,
                         `id_usuario` bigint NOT NULL,
                         `id_viaje` bigint NOT NULL AUTO_INCREMENT,
                         `id_monopatin` varchar(255) NOT NULL,
                         `estado` enum('activo','finalizado','pausado') NOT NULL,
                         PRIMARY KEY (`id_viaje`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viaje`
--

LOCK TABLES `viaje` WRITE;
/*!40000 ALTER TABLE `viaje` DISABLE KEYS */;
INSERT INTO `viaje` VALUES ('2025-01-05','08:35:00.000000','08:00:00.000000',3.2,2,3,1,2001,1,'2','finalizado'),('2025-01-15','09:50:00.000000','09:15:00.000000',2.1,4,2,3,2001,2,'2','finalizado'),('2025-02-10','11:10:00.000000','10:30:00.000000',2.7,1,4,2,2001,3,'2','finalizado'),('2025-02-22','12:35:00.000000','12:00:00.000000',3,3,1,4,2001,4,'2','finalizado'),('2025-03-05','08:20:00.000000','07:45:00.000000',2.9,2.5,5,1,2005,5,'2','finalizado'),('2025-03-18','13:45:00.000000','13:00:00.000000',3.4,2,3,5,2006,6,'2','finalizado'),('2025-04-01','15:10:00.000000','14:30:00.000000',2.6,1.5,1,3,2007,7,'2','finalizado'),('2025-04-15','08:50:00.000000','08:15:00.000000',2.8,3,2,1,2008,8,'5','finalizado'),('2025-04-25','10:55:00.000000','10:10:00.000000',3.1,2,3,2,2008,9,'5','finalizado'),('2025-05-02','12:30:00.000000','11:45:00.000000',3,2.5,5,4,2008,10,'5','finalizado'),('2025-05-11','14:05:00.000000','13:20:00.000000',2.3,1,4,5,2008,11,'5','finalizado'),('2025-05-20','15:50:00.000000','15:10:00.000000',3.2,4,1,3,2009,12,'5','finalizado'),('2025-06-01','09:40:00.000000','09:00:00.000000',3.5,3,4,1,2009,13,'5','finalizado'),('2025-06-05','09:05:00.000000','08:25:00.000000',3.3,2.5,3,1,2002,14,'9','finalizado'),('2025-06-10','10:50:00.000000','10:15:00.000000',2.6,3,2,4,2002,15,'9','finalizado'),('2025-06-15','12:10:00.000000','11:30:00.000000',2.9,1,5,2,2003,16,'9','finalizado'),('2025-06-20','13:50:00.000000','13:10:00.000000',2.7,2,1,5,2003,17,'9','finalizado'),('2025-06-25','15:20:00.000000','14:40:00.000000',3.1,1.5,4,3,2003,18,'9','finalizado'),('2025-06-28','16:30:00.000000','15:50:00.000000',2.4,2,2,1,2003,19,'9','finalizado'),('2025-06-30','17:45:00.000000','17:10:00.000000',2.8,2,3,2,2003,20,'9','finalizado');
/*!40000 ALTER TABLE `viaje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-03 19:03:58
