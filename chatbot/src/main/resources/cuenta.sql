-- MySQL dump 10.13  Distrib 9.3.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cuenta
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
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
                          `fecha_alta` date NOT NULL,
                          `km_recorridos_mes_premium` double DEFAULT NULL,
                          `saldo` double NOT NULL,
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `mercado_pago_id` varchar(255) NOT NULL,
                          `estado_cuenta` enum('ACTIVA','ANULADA') NOT NULL,
                          `tipo_cuenta` enum('BASICA','PREMIUM') NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2012 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES ('2023-01-01',NULL,15000,2001,'mp_2001','ACTIVA','BASICA'),('2023-02-01',5,30000,2002,'mp_2002','ACTIVA','PREMIUM'),('2023-03-01',NULL,18000,2003,'mp_2003','ACTIVA','BASICA'),('2023-04-01',10,25000,2004,'mp_2004','ACTIVA','PREMIUM'),('2023-05-01',NULL,10000,2005,'mp_2005','ACTIVA','BASICA'),('2023-06-01',7,20000,2006,'mp_2006','ACTIVA','PREMIUM'),('2023-07-01',NULL,12000,2007,'mp_2007','ACTIVA','BASICA'),('2023-08-01',3.5,22000,2008,'mp_2008','ACTIVA','PREMIUM'),('2023-09-01',NULL,11000,2009,'mp_2009','ACTIVA','BASICA'),('2023-10-01',12,27000,2010,'mp_2010','ACTIVA','PREMIUM'),('2023-11-01',NULL,13000,2011,'mp_2011','ACTIVA','BASICA');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_usuarios_id`
--

DROP TABLE IF EXISTS `cuenta_usuarios_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta_usuarios_id` (
                                      `cuenta_id` bigint NOT NULL,
                                      `usuarios` bigint DEFAULT NULL,
                                      KEY `FKagj5lwuikgxn19b9cgrd5p76p` (`cuenta_id`),
                                      CONSTRAINT `FKagj5lwuikgxn19b9cgrd5p76p` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_usuarios_id`
--

LOCK TABLES `cuenta_usuarios_id` WRITE;
/*!40000 ALTER TABLE `cuenta_usuarios_id` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuenta_usuarios_id` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-03 19:01:21
