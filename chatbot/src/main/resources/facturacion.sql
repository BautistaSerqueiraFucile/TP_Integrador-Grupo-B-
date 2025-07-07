-- MySQL dump 10.13  Distrib 9.3.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: facturacion
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
-- Table structure for table `facturacion`
--

DROP TABLE IF EXISTS `facturacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facturacion` (
                               `costo_tarifa` double NOT NULL,
                               `fecha` date NOT NULL,
                               `precio_viaje` double NOT NULL,
                               `tarifa_pausa` double NOT NULL,
                               `id_factura` bigint NOT NULL AUTO_INCREMENT,
                               `id_usuario` bigint NOT NULL,
                               `id_viaje` bigint NOT NULL,
                               `tipo_cuenta` varchar(255) NOT NULL,
                               PRIMARY KEY (`id_factura`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturacion`
--

LOCK TABLES `facturacion` WRITE;
/*!40000 ALTER TABLE `facturacion` DISABLE KEYS */;
INSERT INTO `facturacion` VALUES (130,'2022-03-15',130,0,1,1001,11,'basica'),(150,'2023-06-05',165,15,2,1001,14,'basica'),(90,'2024-07-13',100,10,3,1001,17,'basica'),(150,'2025-06-01',160,10,4,1001,1,'basica'),(0,'2025-06-02',0,0,5,1001,2,'premium'),(120,'2025-06-03',125,5,6,1001,3,'basica'),(50,'2025-06-04',65,15,7,1001,4,'premium'),(140,'2025-06-10',145,5,8,1001,20,'basica'),(0,'2022-08-20',10,10,9,1002,12,'premium'),(20,'2023-11-11',20,0,10,1002,15,'premium'),(0,'2024-12-25',0,0,11,1002,18,'premium'),(110,'2023-01-10',115,5,12,1003,13,'basica'),(170,'2024-02-28',170,0,13,1003,16,'basica'),(160,'2025-03-05',168,8,14,1003,19,'basica'),(180,'2025-06-05',180,0,15,1005,5,'basica'),(0,'2025-06-06',20,20,16,1005,6,'premium'),(140,'2025-06-07',150,10,17,1005,7,'basica'),(30,'2025-06-08',35,5,18,1005,8,'premium'),(160,'2025-06-09',160,0,19,1009,9,'basica'),(0,'2025-06-10',0,0,20,1009,10,'premium');
/*!40000 ALTER TABLE `facturacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarifa`
--

DROP TABLE IF EXISTS `tarifa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarifa` (
                          `fecha_aumento` date DEFAULT NULL,
                          `porcentaje_aumento` double DEFAULT NULL,
                          `tarifa_basica` double DEFAULT NULL,
                          `tarifa_pausa` double DEFAULT NULL,
                          `tarifa_premium` double DEFAULT NULL,
                          `id_tarifa` bigint NOT NULL AUTO_INCREMENT,
                          PRIMARY KEY (`id_tarifa`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarifa`
--

LOCK TABLES `tarifa` WRITE;
/*!40000 ALTER TABLE `tarifa` DISABLE KEYS */;
INSERT INTO `tarifa` VALUES ('2124-07-07',0.22,34234,324324,124235,1);
/*!40000 ALTER TABLE `tarifa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-03 19:02:26
