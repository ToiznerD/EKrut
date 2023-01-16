CREATE DATABASE  IF NOT EXISTS `ekrut` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ekrut`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ekrut
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `customer_report`
--

DROP TABLE IF EXISTS `customer_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_report` (
  `s_name` varchar(45) NOT NULL,
  `year` int NOT NULL,
  `month` int NOT NULL,
  `histogram` varchar(45) NOT NULL,
  `min_num_orders` int DEFAULT NULL,
  `max_num_orders` int DEFAULT NULL,
  PRIMARY KEY (`s_name`,`year`,`month`),
  CONSTRAINT `sname3` FOREIGN KEY (`s_name`) REFERENCES `store` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_report`
--

LOCK TABLES `customer_report` WRITE;
/*!40000 ALTER TABLE `customer_report` DISABLE KEYS */;
INSERT INTO `customer_report` VALUES ('Abu Dabi',2022,9,'10,6,7,3,2',2,20),('Abu Dabi',2022,10,'1,5,12,6,8',5,25),('Abu Dabi',2022,11,'5,9,11,6,4',0,18),('Abu Dabi',2022,12,'3,10,12,9,7',3,22),('Ashdod',2022,9,'5,9,11,6,4',0,18),('Ashdod',2022,10,'3,10,12,9,7',3,22),('Ashdod',2022,11,'1,5,12,6,8',5,25),('Ashdod',2022,12,'10,6,7,3,2',2,20),('Ashkelon',2022,9,'5,9,11,6,4',3,22),('Ashkelon',2022,10,'3,10,12,9,7',0,18),('Ashkelon',2022,11,'1,5,12,6,8',2,20),('Ashkelon',2022,12,'10,6,7,3,2',5,25),('Dubai',2022,9,'5,9,11,6,4',3,22),('Dubai',2022,10,'1,5,12,6,8',2,20),('Dubai',2022,11,'10,6,7,3,2',5,25),('Dubai',2022,12,'3,10,12,9,7',0,18),('Haifa',2022,9,'10,6,7,3,2',0,18),('Haifa',2022,10,'1,5,12,6,8',3,22),('Haifa',2022,11,'3,10,12,9,7',5,25),('Haifa',2022,12,'5,9,11,6,4',2,20),('Karmiel',2022,9,'1,5,12,6,8',3,22),('Karmiel',2022,10,'3,10,12,9,7',0,18),('Karmiel',2022,11,'10,6,7,3,2',2,20),('Karmiel',2022,12,'5,9,11,6,4',5,25);
/*!40000 ALTER TABLE `customer_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveries`
--

DROP TABLE IF EXISTS `deliveries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliveries` (
  `oid` int NOT NULL,
  `status` enum('Pending','In Progress','Completed') NOT NULL DEFAULT 'Pending',
  `estimated_date` date DEFAULT NULL,
  `estimated_time` time DEFAULT NULL,
  `shipping_address` varchar(45) NOT NULL,
  PRIMARY KEY (`oid`),
  CONSTRAINT `oid1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveries`
--

LOCK TABLES `deliveries` WRITE;
/*!40000 ALTER TABLE `deliveries` DISABLE KEYS */;
INSERT INTO `deliveries` VALUES (19,'Pending',NULL,NULL,'Haifa 4'),(40,'Pending',NULL,NULL,'kiryat ata 4'),(47,'In Progress','2023-01-13','10:00:00','beer sheva 7'),(62,'In Progress','2023-01-14','10:00:00','karmiel 5'),(71,'Pending',NULL,NULL,'qiryat haim 4'),(80,'In Progress','2023-01-14','10:00:00','sdot yam 1'),(86,'In Progress','2023-01-14','10:00:00','herzeliya 5');
/*!40000 ALTER TABLE `deliveries` ENABLE KEYS */;
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
  `total_price` double DEFAULT NULL,
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
  `s_name` varchar(45) NOT NULL,
  `month` int NOT NULL,
  `year` int NOT NULL,
  `num_orders` int NOT NULL,
  `total_profit` int NOT NULL,
  PRIMARY KEY (`s_name`,`month`,`year`),
  CONSTRAINT `sname` FOREIGN KEY (`s_name`) REFERENCES `store` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_report`
--

LOCK TABLES `order_report` WRITE;
/*!40000 ALTER TABLE `order_report` DISABLE KEYS */;
INSERT INTO `order_report` VALUES ('Abu Dabi',9,2022,45,450),('Abu Dabi',10,2022,35,350),('Abu Dabi',11,2022,80,800),('Abu Dabi',12,2022,70,750),('Ashdod',9,2022,50,500),('Ashdod',10,2022,60,600),('Ashdod',11,2022,70,700),('Ashdod',12,2022,40,400),('Ashkelon',1,2023,1,100),('Ashkelon',9,2022,80,850),('Ashkelon',10,2022,30,350),('Ashkelon',11,2022,20,200),('Ashkelon',12,2022,50,550),('Delivery Warehouse',9,2022,80,800),('Delivery Warehouse',10,2022,90,900),('Delivery Warehouse',11,2022,100,1050),('Delivery Warehouse',12,2022,95,950),('Dubai',9,2022,80,850),('Dubai',10,2022,70,750),('Dubai',11,2022,90,900),('Dubai',12,2022,20,250),('Haifa',9,2022,60,600),('Haifa',10,2022,50,500),('Haifa',11,2022,40,400),('Haifa',12,2022,30,300),('Karmiel',9,2022,20,200),('Karmiel',10,2022,80,800),('Karmiel',11,2022,70,700),('Karmiel',12,2022,60,600);
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
  `total_price` double DEFAULT NULL,
  `ord_date` date DEFAULT NULL,
  `ord_time` time DEFAULT NULL,
  `method` enum('Delivery','Local','Pickup') DEFAULT NULL,
  `ord_status` enum('In Progress','Completed') DEFAULT 'In Progress',
  PRIMARY KEY (`oid`),
  KEY `cid` (`cid`),
  KEY `sid` (`sid`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `store` (`sid`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (7,1,1,100,'2023-01-01','10:00:00','Pickup','Completed'),(8,1,1,200,'2023-01-01','11:00:00','Local','Completed'),(9,2,2,100,'2023-01-01','10:00:00','Pickup','Completed'),(10,2,2,300,'2023-01-01','11:00:00','Pickup','Completed'),(11,1,3,100,'2023-01-01','12:00:00','Local','Completed'),(12,1,4,100,'2023-01-01','14:00:00','Pickup','Completed'),(13,1,0,100,'2023-01-02','10:00:00','Delivery','Completed'),(14,1,1,150,'2023-01-03','10:15:00','Pickup','Completed'),(15,1,2,200,'2023-01-02','10:00:00','Pickup','Completed'),(16,1,0,100,'2023-01-03','10:15:00','Delivery','Completed'),(17,1,3,150,'2023-01-02','10:00:00','Pickup','Completed'),(18,1,3,200,'2023-01-03','10:15:00','Pickup','Completed'),(19,1,0,250,'2023-01-02','10:00:00','Delivery','In Progress'),(20,1,4,150,'2023-01-03','10:15:00','Pickup','Completed'),(21,1,5,300,'2023-01-02','10:00:00','Pickup','Completed'),(22,1,5,250,'2023-01-03','10:15:00','Pickup','Completed'),(34,2,0,350,'2023-01-02','13:00:00','Delivery','Completed'),(35,2,1,450,'2023-01-03','13:15:00','Pickup','Completed'),(36,2,2,200,'2023-01-02','10:00:00','Pickup','Completed'),(37,2,0,150,'2023-01-03','12:15:00','Delivery','Completed'),(38,2,3,150,'2023-01-02','14:00:00','Pickup','Completed'),(39,2,3,200,'2023-01-03','15:15:00','Pickup','Completed'),(40,2,0,100,'2023-01-02','15:00:00','Delivery','In Progress'),(41,2,4,150,'2023-01-03','16:15:00','Pickup','Completed'),(42,2,5,250,'2023-01-02','22:00:00','Pickup','Completed'),(43,2,5,250,'2023-01-03','21:15:00','Pickup','Completed'),(44,3,0,350,'2023-01-02','13:00:00','Delivery','Completed'),(45,3,1,450,'2023-01-03','13:15:00','Pickup','Completed'),(46,3,2,200,'2023-01-02','10:00:00','Pickup','Completed'),(47,3,0,150,'2023-01-03','12:15:00','Delivery','In Progress'),(48,3,3,150,'2023-01-02','14:00:00','Pickup','Completed'),(49,3,3,200,'2023-01-03','15:15:00','Pickup','Completed'),(50,3,0,100,'2023-01-02','15:00:00','Delivery','Completed'),(51,3,4,150,'2023-01-03','16:15:00','Pickup','Completed'),(52,3,5,250,'2023-01-02','22:00:00','Pickup','Completed'),(53,3,5,250,'2023-01-03','21:15:00','Pickup','Completed'),(54,3,6,150,'2023-01-02','14:00:00','Local','Completed'),(55,3,6,150,'2023-01-05','18:00:00','Pickup','Completed'),(56,4,0,350,'2023-01-04','13:00:00','Delivery','Completed'),(57,4,1,450,'2023-01-03','13:15:00','Pickup','Completed'),(58,4,2,200,'2023-01-04','10:00:00','Pickup','Completed'),(59,4,0,150,'2023-01-03','12:15:00','Delivery','Completed'),(60,4,3,150,'2023-01-04','14:00:00','Pickup','Completed'),(61,4,3,200,'2023-01-03','15:15:00','Pickup','Completed'),(62,4,0,100,'2023-01-04','15:00:00','Delivery','In Progress'),(63,4,4,150,'2023-01-03','16:15:00','Pickup','Completed'),(64,4,5,250,'2023-01-04','22:00:00','Pickup','Completed'),(65,4,5,250,'2023-01-03','21:15:00','Pickup','Completed'),(66,4,6,150,'2023-01-04','14:00:00','Local','Completed'),(67,4,6,150,'2023-01-05','18:00:00','Pickup','Completed'),(68,5,0,350,'2023-01-04','20:00:00','Delivery','Completed'),(69,5,1,450,'2023-01-06','20:18:00','Pickup','Completed'),(70,5,2,200,'2023-01-04','10:00:00','Pickup','Completed'),(71,5,0,180,'2023-01-06','12:18:00','Delivery','In Progress'),(72,5,3,180,'2023-01-04','14:00:00','Pickup','Completed'),(73,5,3,200,'2023-01-06','18:18:00','Pickup','Completed'),(74,5,0,100,'2023-01-04','18:00:00','Delivery','Completed'),(75,5,4,180,'2023-01-06','16:18:00','Pickup','Completed'),(76,5,5,250,'2023-01-04','22:00:00','Pickup','Completed'),(77,5,5,250,'2023-01-06','21:18:00','Pickup','Completed'),(78,5,6,180,'2023-01-04','14:00:00','Local','Completed'),(79,5,6,180,'2023-01-05','18:00:00','Pickup','Completed'),(80,6,0,350,'2023-01-04','20:00:00','Delivery','In Progress'),(81,6,1,450,'2023-01-06','20:18:00','Pickup','Completed'),(82,6,2,200,'2023-01-04','10:00:00','Pickup','Completed'),(83,6,0,180,'2023-01-06','12:18:00','Delivery','Completed'),(84,6,3,180,'2023-01-04','14:00:00','Pickup','Completed'),(85,6,3,200,'2023-01-06','18:18:00','Pickup','Completed'),(86,6,0,100,'2023-01-04','18:00:00','Delivery','In Progress'),(87,6,4,180,'2023-01-06','16:18:00','Pickup','Completed'),(88,6,5,250,'2023-01-04','22:00:00','Pickup','Completed'),(89,6,5,250,'2023-01-06','21:18:00','Pickup','Completed'),(90,6,6,180,'2023-01-04','14:00:00','Local','Completed'),(91,6,6,180,'2023-01-05','18:00:00','Pickup','Completed');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pickup`
--

DROP TABLE IF EXISTS `pickup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pickup` (
  `oid` int NOT NULL,
  `sid` int NOT NULL,
  `orderCode` varchar(50) NOT NULL,
  PRIMARY KEY (`oid`),
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
INSERT INTO `pickup` VALUES (7,1,'1061435'),(9,2,'1812643'),(10,2,'2609513'),(12,4,'1993857'),(14,1,'739198'),(15,2,'345765'),(17,3,'2510884'),(18,3,'458066'),(20,4,'2310987'),(21,5,'2557233'),(22,5,'498752'),(35,1,'2267236'),(36,2,'1018885'),(38,3,'1228715'),(39,3,'2725305'),(41,4,'3096457'),(42,5,'2171445'),(43,5,'653487'),(45,1,'2662850'),(46,2,'2562958'),(48,3,'381553'),(49,3,'106503'),(51,4,'2498540'),(52,5,'1056666'),(53,5,'2046746'),(55,6,'1833869'),(57,1,'826703'),(58,2,'2199940'),(60,3,'1626810'),(61,3,'552513'),(63,4,'2118946'),(64,5,'1918496'),(65,5,'1612738'),(67,6,'1445967'),(69,1,'3107659'),(70,2,'2091945'),(72,3,'2159049'),(73,3,'2765310'),(75,4,'2538532'),(76,5,'753808'),(77,5,'480296'),(79,6,'567529'),(81,1,'793506'),(82,2,'1208375'),(84,3,'726218'),(85,3,'999381'),(87,4,'786021'),(88,5,'373268'),(89,5,'1055807'),(91,6,'333545');
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
INSERT INTO `region_employee` VALUES (7,1),(8,1),(13,2),(16,2),(14,3),(17,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regions`
--

LOCK TABLES `regions` WRITE;
/*!40000 ALTER TABLE `regions` DISABLE KEYS */;
INSERT INTO `regions` VALUES (0,'global'),(2,'north'),(1,'south'),(3,'uae');
/*!40000 ALTER TABLE `regions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resupply_request`
--

DROP TABLE IF EXISTS `resupply_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resupply_request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
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
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resupply_request`
--

LOCK TABLES `resupply_request` WRITE;
/*!40000 ALTER TABLE `resupply_request` DISABLE KEYS */;
INSERT INTO `resupply_request` VALUES (34,3,3,10,15,'Pending'),(35,3,4,15,15,'Pending'),(36,1,5,10,11,'Pending'),(37,4,2,15,20,'Pending'),(38,4,2,10,13,'Pending'),(39,6,3,15,18,'Pending'),(40,5,8,10,20,'Pending'),(41,1,9,15,10,'Pending'),(42,1,5,10,11,'Done'),(43,4,2,15,20,'Done'),(44,3,8,10,11,'Done'),(45,5,2,15,20,'Done'),(46,4,10,10,11,'Done'),(47,6,9,15,20,'Done');
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
  `s_name` varchar(45) NOT NULL,
  `stock_data` text NOT NULL,
  `month` int NOT NULL,
  `year` int NOT NULL,
  PRIMARY KEY (`s_name`,`month`,`year`),
  CONSTRAINT `sname2` FOREIGN KEY (`s_name`) REFERENCES `store` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_report`
--

LOCK TABLES `stock_report` WRITE;
/*!40000 ALTER TABLE `stock_report` DISABLE KEYS */;
INSERT INTO `stock_report` VALUES ('Abu Dabi','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',9,2022),('Abu Dabi','Cola,15,Bamba,5,Snickers,10,Oreo,15,Bisli,0,Pringles,5,Cheetos,20,Doritos,20,White Kinder Bueno,40,Black Kinder Bueno,30',10,2022),('Abu Dabi','Cola,5,Bamba,10,Snickers,5,Oreo,10,Bisli,15,Pringles,15,Cheetos,10,Doritos,10,White Kinder Bueno,10,Black Kinder Bueno,25',11,2022),('Abu Dabi','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',12,2022),('Ashdod','Cola,15,Bamba,5,Snickers,10,Oreo,15,Bisli,0,Pringles,5,Cheetos,20,Doritos,20,White Kinder Bueno,40,Black Kinder Bueno,30',9,2022),('Ashdod','Cola,5,Bamba,10,Snickers,5,Oreo,10,Bisli,15,Pringles,15,Cheetos,10,Doritos,10,White Kinder Bueno,10,Black Kinder Bueno,25',10,2022),('Ashdod','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',11,2022),('Ashdod','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',12,2022),('Ashkelon','Cola,15,Bamba,5,Snickers,10,Oreo,15,Bisli,0,Pringles,5,Cheetos,20,Doritos,20,White Kinder Bueno,40,Black Kinder Bueno,30',9,2022),('Ashkelon','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',10,2022),('Ashkelon','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',11,2022),('Ashkelon','Cola,5,Bamba,10,Snickers,5,Oreo,10,Bisli,15,Pringles,15,Cheetos,10,Doritos,10,White Kinder Bueno,10,Black Kinder Bueno,25',12,2022),('Dubai','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',9,2022),('Dubai','Cola,5,Bamba,10,Snickers,5,Oreo,10,Bisli,15,Pringles,15,Cheetos,10,Doritos,10,White Kinder Bueno,10,Black Kinder Bueno,25',10,2022),('Dubai','Cola,15,Bamba,5,Snickers,10,Oreo,15,Bisli,0,Pringles,5,Cheetos,20,Doritos,20,White Kinder Bueno,40,Black Kinder Bueno,30',11,2022),('Dubai','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',12,2022),('Haifa','Cola,15,Bamba,5,Snickers,10,Oreo,15,Bisli,0,Pringles,5,Cheetos,20,Doritos,20,White Kinder Bueno,40,Black Kinder Bueno,30',9,2022),('Haifa','Cola,5,Bamba,10,Snickers,5,Oreo,10,Bisli,15,Pringles,15,Cheetos,10,Doritos,10,White Kinder Bueno,10,Black Kinder Bueno,25',10,2022),('Haifa','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',11,2022),('Haifa','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',12,2022),('Karmiel','Cola,15,Bamba,5,Snickers,10,Oreo,15,Bisli,0,Pringles,5,Cheetos,20,Doritos,20,White Kinder Bueno,40,Black Kinder Bueno,30',9,2022),('Karmiel','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',10,2022),('Karmiel','Cola,10,Bamba,15,Snickers,5,Oreo,25,Bisli,20,Pringles,10,Cheetos,5,Doritos,15,White Kinder Bueno,10,Black Kinder Bueno,20',11,2022),('Karmiel','Cola,5,Bamba,10,Snickers,5,Oreo,10,Bisli,15,Pringles,15,Cheetos,10,Doritos,10,White Kinder Bueno,10,Black Kinder Bueno,25',12,2022);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (0,0,'Delivery Warehouse','Everywhere'),(1,1,'Haifa','Yafo 82'),(2,1,'Karmiel','Kineret 33'),(3,2,'Ashdod','Nahal 2'),(4,2,'Ashkelon','Remze 14'),(5,3,'Abu Dabi','Abu dabi Highway'),(6,3,'Dubai','Emirates 611');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
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
INSERT INTO `store_product` VALUES (1,0,2147483647,0),(1,1,20,5),(1,2,20,5),(1,3,20,5),(1,4,20,5),(1,5,20,5),(1,6,20,5),(2,0,2147483647,0),(2,1,20,5),(2,2,20,20),(2,3,20,5),(2,4,20,5),(2,5,20,5),(2,6,20,5),(3,0,2147483647,0),(3,1,20,5),(3,2,20,5),(3,3,20,5),(3,4,20,5),(3,5,20,5),(3,6,20,5),(4,0,2147483647,0),(4,1,20,5),(4,2,20,5),(4,3,20,5),(4,4,20,5),(4,5,20,5),(4,6,20,5),(5,0,2147483647,0),(5,1,20,5),(5,2,20,5),(5,3,20,5),(5,4,20,5),(5,5,20,5),(5,6,20,5),(6,0,2147483647,0),(6,1,20,5),(6,2,20,5),(6,3,20,5),(6,4,20,5),(6,5,20,5),(6,6,20,5),(7,0,2147483647,0),(7,1,20,5),(7,2,20,5),(7,3,20,5),(7,4,20,5),(7,5,20,5),(7,6,20,5),(8,0,2147483647,0),(8,1,20,5),(8,2,20,5),(8,3,20,5),(8,4,20,5),(8,5,20,5),(8,6,20,5),(9,0,2147483647,0),(9,1,20,5),(9,2,20,5),(9,3,20,5),(9,4,20,5),(9,5,20,5),(9,6,20,5),(10,0,2147483647,0),(10,1,20,5),(10,2,20,5),(10,3,20,5),(10,4,20,5),(10,5,20,5),(10,6,20,5);
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

------------------------ table `users`

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


-- Dump completed on 2023-01-15 20:50:16

