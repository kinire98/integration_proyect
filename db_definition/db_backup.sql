/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.6.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: tpv_test
-- ------------------------------------------------------
-- Server version	11.6.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES
(1,'Electronics'),
(2,'Clothing'),
(3,'Books'),
(4,'Toys'),
(5,'Home Appliances'),
(6,'Furniture'),
(7,'Kitchenware'),
(8,'Sports'),
(9,'Gardening'),
(10,'Automotive'),
(11,'Health & Beauty'),
(12,'Electronics Accessories'),
(13,'Footwear'),
(14,'Beverages'),
(15,'Snacks'),
(16,'Office Supplies'),
(17,'Pet Supplies'),
(18,'Gaming'),
(19,'Music'),
(20,'Groceries'),
(21,'Baby Products');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `image_path` varchar(100) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `last_modification` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES
(1,'Smartphone',699.99,'./img/test.png',1,'2025-01-11'),
(2,'Laptop',1199.99,'./img/test.png',1,'2025-01-10'),
(3,'T-shirt',19.99,'./img/test.png',2,'2025-01-09'),
(4,'Jeans',39.99,'./img/test.png',2,'2025-01-08'),
(5,'Fiction Book',14.99,'./img/test.png',3,'2025-01-07'),
(6,'Non-Fiction Book',24.99,'./img/test.png',3,'2025-01-06'),
(7,'Action Figure',24.99,'./img/test.png',4,'2025-01-05'),
(8,'Board Game',34.99,'./img/test.png',4,'2025-01-04'),
(9,'Blender',49.99,'./img/test.png',5,'2025-01-03'),
(10,'Microwave',89.99,'./img/test.png',5,'2025-01-02'),
(11,'Chair',59.99,'./img/test.png',6,'2025-01-01'),
(12,'Table',129.99,'./img/test.png',6,'2024-12-31'),
(13,'Knife Set',79.99,'./img/test.png',7,'2024-12-30'),
(14,'Cooking Pan',34.99,'./img/test.png',7,'2024-12-29'),
(15,'Soccer Ball',24.99,'./img/test.png',8,'2024-12-28'),
(16,'Tennis Racket',69.99,'./img/test.png',8,'2024-12-27'),
(17,'Garden Hose',29.99,'./img/test.png',9,'2024-12-26'),
(18,'Lawn Mower',199.99,'./img/test.png',9,'2024-12-25'),
(19,'Car Battery',149.99,'./img/test.png',10,'2024-12-24'),
(20,'Air Freshener',9.99,'./img/test.png',10,'2024-12-23'),
(21,'Skincare Kit',49.99,'./img/test.png',11,'2024-12-22'),
(22,'Hair Dryer',59.99,'./img/test.png',11,'2024-12-21'),
(23,'Headphones',49.99,'./img/test.png',12,'2024-12-20'),
(24,'Wireless Mouse',29.99,'./img/test.png',12,'2024-12-19'),
(25,'Running Shoes',89.99,'./img/test.png',13,'2024-12-18'),
(26,'Sandals',19.99,'./img/test.png',13,'2024-12-17'),
(27,'Coffee',5.99,'./img/test.png',14,'2024-12-16'),
(28,'Energy Drink',2.99,'./img/test.png',14,'2024-12-15'),
(29,'Potato Chips',3.49,'./img/test.png',15,'2024-12-14'),
(30,'Chocolate Bar',1.49,'./img/test.png',15,'2024-12-13'),
(31,'Notebook',2.99,'./img/test.png',16,'2024-12-12'),
(32,'Ballpoint Pen',0.99,'./img/test.png',16,'2024-12-11'),
(33,'Dog Food',19.99,'./img/test.png',17,'2024-12-10'),
(34,'Cat Toy',9.99,'./img/test.png',17,'2024-12-09'),
(35,'Gaming Console',499.99,'./img/test.png',18,'2024-12-08'),
(36,'Gaming Chair',199.99,'./img/test.png',18,'2024-12-07'),
(37,'Electric Guitar',799.99,'./img/test.png',19,'2024-12-06'),
(38,'Headphone Amplifier',149.99,'./img/test.png',19,'2024-12-05'),
(39,'Rice',1.99,'./img/test.png',20,'2024-12-04'),
(40,'Pasta',0.99,'./img/test.png',20,'2024-12-03'),
(41,'Baby Stroller',129.99,'./img/test.png',21,'2024-12-02'),
(42,'Baby Monitor',89.99,'./img/test.png',21,'2024-12-01');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchased_products`
--

DROP TABLE IF EXISTS `purchased_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchased_products` (
  `purchase_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`purchase_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `purchased_products_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `purchased_products_ibfk_2` FOREIGN KEY (`purchase_id`) REFERENCES `purchases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchased_products`
--

LOCK TABLES `purchased_products` WRITE;
/*!40000 ALTER TABLE `purchased_products` DISABLE KEYS */;
INSERT INTO `purchased_products` VALUES
(1,1,1),
(1,5,2),
(2,3,3),
(2,6,1),
(3,2,1),
(3,4,2),
(4,7,1),
(4,10,1),
(5,2,1),
(5,8,2),
(6,4,1),
(6,9,1),
(7,6,2),
(7,11,1),
(8,3,4),
(8,5,3),
(9,13,1),
(9,17,1),
(10,14,2),
(10,20,1),
(11,15,1),
(11,18,1),
(12,16,3),
(12,19,1),
(13,7,1),
(13,21,2),
(14,22,2),
(14,26,3),
(15,23,1),
(15,29,10),
(16,24,2),
(16,28,5),
(17,25,1),
(17,30,20),
(18,24,1),
(18,27,6),
(19,21,4),
(19,26,8),
(20,7,1),
(20,10,1),
(21,8,2),
(21,11,1),
(22,15,3),
(22,28,2),
(23,18,1),
(23,30,50),
(24,31,3),
(24,33,1),
(25,32,2),
(25,34,1),
(26,35,1),
(26,37,10),
(27,36,2),
(27,38,15),
(28,39,1),
(28,40,1),
(29,8,5),
(29,24,2),
(30,9,3),
(30,26,6),
(31,18,1),
(31,21,1),
(32,23,2),
(32,31,3),
(33,22,4),
(33,39,1),
(34,12,3);
/*!40000 ALTER TABLE `purchased_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  CONSTRAINT `purchases_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES
(1,'alice','2025-01-10'),
(2,'bob','2025-01-09'),
(3,'charlie','2025-01-08'),
(4,'dave','2025-01-09'),
(5,'eve','2025-01-08'),
(6,'frank','2025-01-07'),
(7,'grace','2025-01-06'),
(8,'heidi','2025-01-05'),
(9,'ivan','2025-01-04'),
(10,'judy','2025-01-03'),
(11,'karen','2025-01-02'),
(12,'louis','2025-01-01'),
(13,'mike','2024-12-31'),
(14,'nancy','2024-12-30'),
(15,'oscar','2024-12-29'),
(16,'paul','2024-12-28'),
(17,'quincy','2024-12-27'),
(18,'rachel','2024-12-26'),
(19,'steve','2024-12-25'),
(20,'tracy','2024-12-24'),
(21,'uma','2024-12-23'),
(22,'victor','2024-12-22'),
(23,'wendy','2024-12-21'),
(24,'xander','2024-12-20'),
(25,'yolanda','2024-12-19'),
(26,'zack','2024-12-18'),
(27,'aaron','2024-12-17'),
(28,'bella','2024-12-16'),
(29,'carla','2024-12-15'),
(30,'derek','2024-12-14'),
(31,'emma','2024-12-13'),
(32,'felix','2024-12-12'),
(33,'gina','2024-12-11'),
(34,'aaron','2025-01-15');
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
('aaron','*CEC667F066484CD88C2AB5C19B6A1742C5569A5D'),
('alice','*A0F874BC7F54EE086FCE60A37CE7887D8B31086B'),
('bella','*AD56F428956D2A8CE156A5F1FB4DBC6CC083E48D'),
('bob','*4994A78AFED55B0F529C11C436F85458C1F8D4C2'),
('carla','*25670EE1A61B0086D6EE7E5FAB5DE24A7292739C'),
('charlie','*FABE5482D5AADF36D028AC443D117BE1180B9725'),
('dave','*FB6E1F205D675BC29B052DB14CCEFE7759C5FF7E'),
('derek','*24ED314BD50C826E40F7B8BA21DABD93D19CFC63'),
('emma','*9515A8A0A92343E5D6A12CA4797C6152ED93CD54'),
('eve','*ABD2ADA71F0819472E3F4892C3F3FCD6F58F04A5'),
('felix','*ECFCE9E70BF9B2465A39CC53083E2DC0777F008B'),
('frank','*D37C49F9CBEFBF8B6F4B165AC703AA271E079004'),
('gina','*B61AC472F05BA3B501761D6B37E8207AA81B045A'),
('grace','*3C636F43ED0ECE3F2C179E89F83D78F3642172BE'),
('heidi','*6C47D9CD3A183D230B04FE7F38D7D313E2B4B5AE'),
('ivan','*E1859B64FEBEE4C4D6B0F5625A365CA515D1CBD9'),
('judy','*72B97D7059CCF5BD8C021A28B2BF060133F79004'),
('karen','*9A9A8DF73F6431CD3B43F2EE3309A01592D8ADFA'),
('louis','*58815970BE77B3720276F63DB198B1FA42E5CC02'),
('mike','*F1B7BAF42778762AA81241C0A4192FD54C892738'),
('nancy','*E2F9B692062264291197270A103846A7D63D710B'),
('oscar','*EAF9BD916B49817418C0B12CDF8C53738E79EB0C'),
('paul','*13E0C49E45699422E815512B516BA22B4F6A1686'),
('quincy','*AA1420F182E88B9E5F874F6FBE7459291E8F4601'),
('rachel','*AAEFA175814902B793E043417B05E5CDFCFE145A'),
('steve','*335472077703FF62AE8011E15696B29A1930AEA0'),
('tracy','*6175BD9CE3C9DEB5BBFC5860EB89A85B1F273BC5'),
('uma','*BAF727BE855C10F608DF7B09BA53060034861C4B'),
('victor','*A70DA56210E5DD9E97C307E01AC714208C51459E'),
('wendy','*4AC58FD1C3FD083280533E8FDB85D9ADCF3F53E4'),
('xander','*D29674845F45E66C92B30F1BF13DA6AE5DECD3A7'),
('yolanda','*58DB3960E47CD8817AA7592D31DBEB537C98C2BD'),
('zack','*7D3876676B275875EC82D7F0A5160CB234233AE8');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-01-18 19:10:56
