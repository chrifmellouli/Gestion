-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: gestion
-- ------------------------------------------------------
-- Server version	8.0.22-0ubuntu0.20.04.3

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
-- Table structure for table `AUTHORIZATIONS`
--

DROP TABLE IF EXISTS `AUTHORIZATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AUTHORIZATIONS` (
  `ID_ROLE` int NOT NULL,
  `ID_PRIVILEGE` int NOT NULL,
  `PERMISSION` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_ROLE`,`ID_PRIVILEGE`),
  KEY `AUTHORIZATIONS_PRIVILEGE_ID_PRIVILEGE_fk` (`ID_PRIVILEGE`),
  CONSTRAINT `AUTHORIZATIONS_PRIVILEGE_ID_PRIVILEGE_fk` FOREIGN KEY (`ID_PRIVILEGE`) REFERENCES `PRIVILEGE` (`ID_PRIVILEGE`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `AUTHORIZATIONS_ROLES_ID_ROLE_fk` FOREIGN KEY (`ID_ROLE`) REFERENCES `ROLES` (`ID_ROLE`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHORIZATIONS`
--

LOCK TABLES `AUTHORIZATIONS` WRITE;
/*!40000 ALTER TABLE `AUTHORIZATIONS` DISABLE KEYS */;
INSERT INTO `AUTHORIZATIONS` VALUES (1,1,1),(1,2,1),(1,3,1),(1,4,1),(1,5,1),(1,6,1),(1,7,1),(1,8,1),(1,9,1),(1,10,1),(1,11,1),(1,12,1),(1,13,1),(1,14,1),(2,1,1),(2,2,1),(2,3,1),(2,4,1),(2,5,1),(2,6,1),(2,7,1),(2,8,1),(2,9,1),(2,10,1),(2,11,1),(2,12,0),(2,13,0),(2,14,0),(3,1,0),(3,2,0),(3,3,0),(3,4,0),(3,5,1),(3,6,0),(3,7,1),(3,8,0),(3,9,1),(3,10,0),(3,11,0),(3,12,0),(3,13,0),(3,14,0);
/*!40000 ALTER TABLE `AUTHORIZATIONS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CATEGORY`
--

DROP TABLE IF EXISTS `CATEGORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CATEGORY` (
  `ID_CATEGORY` int NOT NULL AUTO_INCREMENT,
  `CODE` varchar(6) DEFAULT NULL,
  `DESIGNATION` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_CATEGORY`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CATEGORY`
--

LOCK TABLES `CATEGORY` WRITE;
/*!40000 ALTER TABLE `CATEGORY` DISABLE KEYS */;
INSERT INTO `CATEGORY` VALUES (1,'C001','Infomration Technologie'),(2,'C002','Electronic');
/*!40000 ALTER TABLE `CATEGORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CUSTOMER`
--

DROP TABLE IF EXISTS `CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CUSTOMER` (
  `ID_CUSTOMER` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) DEFAULT NULL,
  `PHONE_NUMBER` varchar(50) DEFAULT NULL,
  `ADRESS` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_CUSTOMER`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CUSTOMER`
--

LOCK TABLES `CUSTOMER` WRITE;
/*!40000 ALTER TABLE `CUSTOMER` DISABLE KEYS */;
INSERT INTO `CUSTOMER` VALUES (1,'Chrif','MELLOULI','94562002','Sfax'),(2,'Mohamed','AJIMI','22901773','Monastir');
/*!40000 ALTER TABLE `CUSTOMER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRIVILEGE`
--

DROP TABLE IF EXISTS `PRIVILEGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PRIVILEGE` (
  `ID_PRIVILEGE` int NOT NULL AUTO_INCREMENT,
  `DESIGNATION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_PRIVILEGE`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRIVILEGE`
--

LOCK TABLES `PRIVILEGE` WRITE;
/*!40000 ALTER TABLE `PRIVILEGE` DISABLE KEYS */;
INSERT INTO `PRIVILEGE` VALUES (1,'view user'),(2,'manage user'),(3,'view role'),(4,'manage role'),(5,'view customer'),(6,'manage customer'),(7,'view product'),(8,'manage product'),(9,'view category'),(10,'manage category'),(11,'view privilege'),(12,'manage privilege'),(13,'view authorization'),(14,'manage authorization');
/*!40000 ALTER TABLE `PRIVILEGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCT`
--

DROP TABLE IF EXISTS `PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PRODUCT` (
  `ID_PRODUCT` int NOT NULL AUTO_INCREMENT,
  `CODE` varchar(6) DEFAULT NULL,
  `DESIGNATION` varchar(50) DEFAULT NULL,
  `PRICE_EXCLUDING_VAT` float DEFAULT NULL,
  `VAT` float DEFAULT NULL,
  `UNIT_OF_MEASURE` varchar(6) DEFAULT NULL,
  `ID_CATEGORY` int NOT NULL,
  PRIMARY KEY (`ID_PRODUCT`),
  KEY `PRODUCT_CATEGORY_ID_CATEGORY_fk` (`ID_CATEGORY`),
  CONSTRAINT `PRODUCT_CATEGORY_ID_CATEGORY_fk` FOREIGN KEY (`ID_CATEGORY`) REFERENCES `CATEGORY` (`ID_CATEGORY`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCT`
--

LOCK TABLES `PRODUCT` WRITE;
/*!40000 ALTER TABLE `PRODUCT` DISABLE KEYS */;
INSERT INTO `PRODUCT` VALUES (1,'P001','NoteBook X10',10000,18,'unit',1),(2,'P002','NoteBook X11',21050,18,'unit',1),(3,'P003','NoteBook X12',32099,18,'unit',1),(4,'P004','Arduino uno',64.5,12,'unit',2),(5,'P005','Arduino mega',115.7,12,'unit',2),(6,'P006','Arduino mini',50.4,12,'unit',2);
/*!40000 ALTER TABLE `PRODUCT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ROLES`
--

DROP TABLE IF EXISTS `ROLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ROLES` (
  `ID_ROLE` int NOT NULL AUTO_INCREMENT,
  `ROLE_USER` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_ROLE`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ROLES`
--

LOCK TABLES `ROLES` WRITE;
/*!40000 ALTER TABLE `ROLES` DISABLE KEYS */;
INSERT INTO `ROLES` VALUES (1,'Root'),(2,'Admin'),(3,'Manager');
/*!40000 ALTER TABLE `ROLES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USERS` (
  `ID_USER` int NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(50) DEFAULT NULL,
  `ISCONN` int DEFAULT NULL,
  `ID_ROLE` int NOT NULL,
  PRIMARY KEY (`ID_USER`),
  KEY `USERS_ROLES_ID_ROLE_fk` (`ID_ROLE`),
  CONSTRAINT `USERS_ROLES_ID_ROLE_fk` FOREIGN KEY (`ID_ROLE`) REFERENCES `ROLES` (`ID_ROLE`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` VALUES (1,'Root','Root',0,1),(2,'Admin','Admin',0,2),(3,'Manager','Manager',0,3);
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-04  6:11:27
