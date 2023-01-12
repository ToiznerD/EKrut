CREATE DATABASE  IF NOT EXISTS `ekrut` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ekrut`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: ekrut
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL,
  `status` enum('Approved','Not Approved','Pending') DEFAULT 'Pending',
  `subscriber` tinyint NOT NULL,
  `credit_card` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Approved',1,'1234123412341234'),(3,'Not Approved',0,'1234123412341234'),(4,'Approved',0,'1234123412341234'),(5,'Approved',1,'1234123412341234'),(6,'Not Approved',0,'1231231231231234');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_report`
--

DROP TABLE IF EXISTS `customer_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_report` (
  `crp_id` int NOT NULL AUTO_INCREMENT,
  `sid` int NOT NULL,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `histogram` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`crp_id`),
  KEY `sid` (`sid`),
  CONSTRAINT `customer_report_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `store` (`sid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_report`
--

LOCK TABLES `customer_report` WRITE;
/*!40000 ALTER TABLE `customer_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `oid` int NOT NULL,
  `pid` int NOT NULL,
  `quantity` int NOT NULL,
  `total_price` int DEFAULT NULL,
  PRIMARY KEY (`oid`,`pid`),
  KEY `pid` (`pid`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON UPDATE CASCADE,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_report`
--

DROP TABLE IF EXISTS `order_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_report` (
  `orp_id` int NOT NULL AUTO_INCREMENT,
  `oid` int NOT NULL,
  `shipping_address` varchar(50) NOT NULL,
  PRIMARY KEY (`orp_id`),
  KEY `oid` (`oid`),
  CONSTRAINT `order_report_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_report`
--

LOCK TABLES `order_report` WRITE;
/*!40000 ALTER TABLE `order_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `oid` int NOT NULL AUTO_INCREMENT,
  `cid` int NOT NULL,
  `sid` int NOT NULL,
  `total_price` int DEFAULT NULL,
  `ord_date` date DEFAULT NULL,
  `ord_time` time DEFAULT NULL,
  `method` enum('Delivery','Not Approved') DEFAULT NULL,
  `ord_status` enum('In Progress','Completed') DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `cid` (`cid`),
  KEY `sid` (`sid`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `customer` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `store` (`sid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pickup`
--

DROP TABLE IF EXISTS `pickup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pickup` (
  `pickId` int NOT NULL AUTO_INCREMENT,
  `sid` int NOT NULL,
  `oid` int NOT NULL,
  `orderCode` varchar(50) NOT NULL,
  PRIMARY KEY (`pickId`),
  UNIQUE KEY `orderCode` (`orderCode`),
  KEY `sid` (`sid`),
  KEY `oid` (`oid`),
  CONSTRAINT `pickup_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `store` (`sid`) ON UPDATE CASCADE,
  CONSTRAINT `pickup_ibfk_2` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pickup`
--

LOCK TABLES `pickup` WRITE;
/*!40000 ALTER TABLE `pickup` DISABLE KEYS */;
/*!40000 ALTER TABLE `pickup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `pname` varchar(50) NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pname` (`pname`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Cola',8),(2,'Bamba',6),(3,'Snickers',4),(4,'Oreo',5),(5,'Bisli',6),(6,'Pringles',6),(7,'Cheetos',8),(8,'Doritos',5),(9,'White Kinder Bueno',5),(10,'Black Kinder Bueno',5);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region_employee`
--

DROP TABLE IF EXISTS `region_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region_employee` (
  `uid` int NOT NULL,
  `rid` int NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `rid_idx` (`rid`),
  CONSTRAINT `regionEmpToUsers` FOREIGN KEY (`uid`) REFERENCES `users` (`id`),
  CONSTRAINT `rid` FOREIGN KEY (`rid`) REFERENCES `regions` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region_employee`
--

LOCK TABLES `region_employee` WRITE;
/*!40000 ALTER TABLE `region_employee` DISABLE KEYS */;
INSERT INTO `region_employee` VALUES (7,1),(13,2),(14,3);
/*!40000 ALTER TABLE `region_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regions`
--

DROP TABLE IF EXISTS `regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regions` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`rid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regions`
--

LOCK TABLES `regions` WRITE;
/*!40000 ALTER TABLE `regions` DISABLE KEYS */;
INSERT INTO `regions` VALUES (2,'north'),(1,'south'),(3,'uae');
/*!40000 ALTER TABLE `regions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resupply_request`
--

DROP TABLE IF EXISTS `resupply_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resupply_request` (
  `request_id` int NOT NULL,
  `sid` int NOT NULL,
  `pid` int NOT NULL,
  `uid` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `status` enum('Pending','Done') NOT NULL DEFAULT 'Pending',
  PRIMARY KEY (`request_id`),
  KEY `pid_idx` (`pid`),
  KEY `sid_idx` (`sid`),
  KEY `uid_idx` (`uid`),
  CONSTRAINT `pid` FOREIGN KEY (`pid`) REFERENCES `store_product` (`pid`),
  CONSTRAINT `sid` FOREIGN KEY (`sid`) REFERENCES `store_product` (`sid`),
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `store_employees` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resupply_request`
--

LOCK TABLES `resupply_request` WRITE;
/*!40000 ALTER TABLE `resupply_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `resupply_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_initiate`
--

DROP TABLE IF EXISTS `sale_initiate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_initiate` (
  `templateId` int NOT NULL,
  `saleName` varchar(50) NOT NULL,
  `rid` int NOT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `startHour` time DEFAULT NULL,
  `endHour` time DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`templateId`),
  KEY `rid` (`rid`),
  CONSTRAINT `sale_initiate_ibfk_1` FOREIGN KEY (`templateId`) REFERENCES `sale_template` (`templateId`) ON UPDATE CASCADE,
  CONSTRAINT `sale_initiate_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `regions` (`rid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_initiate`
--

LOCK TABLES `sale_initiate` WRITE;
/*!40000 ALTER TABLE `sale_initiate` DISABLE KEYS */;
INSERT INTO `sale_initiate` VALUES (1,'purim sale',1,'2023-01-10','2023-01-20','10:00:00','20:00:00',1),(2,'spring sale',1,'2023-01-25','2023-02-15','10:00:00','20:00:00',0),(3,'summer sale',1,'2023-03-10','2023-03-20','10:00:00','20:00:00',1);
/*!40000 ALTER TABLE `sale_initiate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_template`
--

DROP TABLE IF EXISTS `sale_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_template` (
  `templateId` int NOT NULL AUTO_INCREMENT,
  `templateName` varchar(50) NOT NULL,
  `discount` double DEFAULT NULL,
  PRIMARY KEY (`templateId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_template`
--

LOCK TABLES `sale_template` WRITE;
/*!40000 ALTER TABLE `sale_template` DISABLE KEYS */;
INSERT INTO `sale_template` VALUES (1,'50 precent',0.5),(2,'10%',0.1),(3,'20%',0.2),(4,'30%',0.3),(5,'40%',0.4),(6,'50%',0.5),(7,'60%',0.6),(8,'70%',0.7),(9,'80%',0.8),(10,'90%',0.9),(11,'10%',0.1),(12,'20%',0.2),(13,'30%',0.3),(14,'40%',0.4),(15,'50%',0.5),(16,'60%',0.6),(17,'70%',0.7),(18,'80%',0.8),(19,'90%',0.9);
/*!40000 ALTER TABLE `sale_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_report`
--

DROP TABLE IF EXISTS `stock_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_report` (
  `srp_id` int NOT NULL AUTO_INCREMENT,
  `rep_date` date DEFAULT NULL,
  PRIMARY KEY (`srp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_report`
--

LOCK TABLES `stock_report` WRITE;
/*!40000 ALTER TABLE `stock_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `rid` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `name` (`name`),
  KEY `rid` (`rid`),
  CONSTRAINT `store_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `regions` (`rid`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (1,1,'Haifa','Yafo 82'),(2,1,'Karmiel','Kineret 33'),(3,2,'Ashdod','Nahal 2'),(4,2,'Ashkelon','Remze 14'),(5,3,'Abu Dabi ','Abu dabi Highway'),(6,3,'Dubai','Emirates 611');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_employees`
--

DROP TABLE IF EXISTS `store_employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_employees` (
  `uid` int NOT NULL,
  `sid` int DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `sid` (`sid`),
  CONSTRAINT `store_employees_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `store` (`sid`) ON UPDATE CASCADE,
  CONSTRAINT `store_employees_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `users` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_employees`
--

LOCK TABLES `store_employees` WRITE;
/*!40000 ALTER TABLE `store_employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `store_employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_product`
--

DROP TABLE IF EXISTS `store_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_product` (
  `pid` int NOT NULL,
  `sid` int NOT NULL,
  `quantity` int NOT NULL,
  `lim` int DEFAULT NULL,
  PRIMARY KEY (`pid`,`sid`),
  KEY `sid` (`sid`),
  CONSTRAINT `store_product_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `store_product_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `store` (`sid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_product`
--

LOCK TABLES `store_product` WRITE;
/*!40000 ALTER TABLE `store_product` DISABLE KEYS */;
INSERT INTO `store_product` VALUES (1,1,20,5),(1,2,20,5),(1,3,20,5),(1,4,20,5),(1,5,20,5),(1,6,20,5),(2,1,20,5),(2,2,20,5),(2,3,20,5),(2,4,20,5),(2,5,20,5),(2,6,20,5),(3,1,20,5),(3,2,20,5),(3,3,20,5),(3,4,20,5),(3,5,20,5),(3,6,20,5),(4,1,20,5),(4,2,20,5),(4,3,20,5),(4,4,20,5),(4,5,20,5),(4,6,20,5),(5,1,20,5),(5,2,20,5),(5,3,20,5),(5,4,20,5),(5,5,20,5),(5,6,20,5),(6,1,20,5),(6,2,20,5),(6,3,20,5),(6,4,20,5),(6,5,20,5),(6,6,20,5),(7,1,20,5),(7,2,20,5),(7,3,20,5),(7,4,20,5),(7,5,20,5),(7,6,20,5),(8,1,20,5),(8,2,20,5),(8,3,20,5),(8,4,20,5),(8,5,20,5),(8,6,20,5),(9,1,20,5),(9,2,20,5),(9,3,20,5),(9,4,20,5),(9,5,20,5),(9,6,20,5),(10,1,20,5),(10,2,20,5),(10,3,20,5),(10,4,20,5),(10,5,20,5),(10,6,20,5);
/*!40000 ALTER TABLE `store_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `isLogged` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'service','123','service','danny','0554334123','palmach','service@gmail.com',0),(2,'not_customer','123','new_user','user1','0500000000','Derek hay','user1@gmail.com',0),(3,'na_customer','123','customer','user2','0501111111','zalafim 5','user2@gmail.com',0),(4,'a_customer','123','customer','user3','0502222222','keren hayseed','user3@gmail.com',0),(5,'as_customer','123','customer','user4','0503333333','karma 2','user4@gmail.com',0),(6,'nans_customer','123','customer','user5','0504444444','harel 31','user5@gmail.com',0),(7,'region_manager1','123','region_manager','yaniv','0505555555','radof 2','rm@gmail.com',0),(8,'market_employee','123','marketing_employee','shaul','0506666666','ilanot 34','me@gmail.com',0),(9,'market_manager','123','marketing_manager','eliyahu','0507777777','keren hayesod 91','mm@gmail.com',0),(10,'operation_emp1','123','operation_employee','nave','0501231234','jerusalem 29','nave@gmail.com',0),(11,'ceo','123','ceo','eliya','0501231231','karmiel 3','eliya@gmail.com',0),(12,'deliveryman','123','delivery','erik','0525381648','kiryat ata 1','erik@gmail.com',0),(13,'region_manager2','123','region_manager','aviel','0524213141','kiryat bialik 1','aviel@gmail.com',0),(14,'region_manager3','123','region_manager','yaron','0543215644','kiryat motzkin 2','yaron@gmail.com',0),(15,'operation_emp2','123','operation_employee','nati','0501231232','jerusalem 21','nati@gmail.com',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-10 15:12:04
