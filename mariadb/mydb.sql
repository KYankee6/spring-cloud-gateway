-- MariaDB dump 10.19-11.3.2-MariaDB, for osx10.19 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	11.3.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `my_topic_users`
--

DROP TABLE IF EXISTS `my_topic_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `my_topic_users` (
  `id` int(11) NOT NULL,
  `user_id` text DEFAULT NULL,
  `pwd` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `created_at` datetime(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_topic_users`
--

LOCK TABLES `my_topic_users` WRITE;
/*!40000 ALTER TABLE `my_topic_users` DISABLE KEYS */;
INSERT INTO `my_topic_users` VALUES
(1,'user1','test1111','user name','2024-03-02 21:17:02.000'),
(2,'admin','admin1111','administratore','2024-03-02 21:18:34.000'),
(3,'user4','tes4','user name4','2024-03-02 21:44:13.000'),
(4,'admin22','admin2','super user','2024-03-02 21:44:25.000'),
(15,'amqwe confused1234','r3eallY?','reall23y2','2024-03-02 22:45:26.000'),
(16,'amqwe confused1234','r3eallY?','reall23y2','2024-03-02 22:46:04.000'),
(17,'lie','rlieeee?','realerwerqwerl23y2','2024-03-02 22:46:26.000'),
(18,'sinket','sinktest?','sinktest','2024-03-02 22:46:26.001');
/*!40000 ALTER TABLE `my_topic_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_topic_users1`
--

DROP TABLE IF EXISTS `my_topic_users1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `my_topic_users1` (
  `id` int(11) NOT NULL,
  `user_id` text DEFAULT NULL,
  `pwd` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `created_at` datetime(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_topic_users1`
--

LOCK TABLES `my_topic_users1` WRITE;
/*!40000 ALTER TABLE `my_topic_users1` DISABLE KEYS */;
INSERT INTO `my_topic_users1` VALUES
(9,'admin24','admin5','super3 user','2024-03-02 21:44:25.002'),
(10,'wow','wow','wow user','2024-03-02 21:44:25.002');
/*!40000 ALTER TABLE `my_topic_users1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `product_id` varchar(20) NOT NULL,
  `order_id` varchar(50) NOT NULL,
  `qty` int(11) DEFAULT 0,
  `unit_price` int(11) DEFAULT 0,
  `total_price` int(11) DEFAULT 0,
  `created_at` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES
(1,'89d022cd-189a-4769-a4da-e31f47a6a111','CATALOG-001','2a2bb028-83b6-49b4-b973-b9f573b8126a',55,1500,82500,'2024-03-02 23:57:48'),
(2,'89d022cd-189a-4769-a4da-e31f47a6a111','CATALOG-001','e3cb353d-8f1b-4325-9a82-507130f934db',55,1500,82500,'2024-03-03 10:46:53'),
(3,'89d022cd-189a-4769-a4da-e31f47a6a111','CATALOG-002','cb40922a-3ac6-4609-988b-72e8aba20a6f',11,1500,16500,'2024-03-03 10:48:26'),
(4,'89d022cd-189a-4769-a4da-e31f47a6a111','CATALOG-003','36cd3f85-734e-4633-a8e9-7daaf032b5ad',11,1500,16500,'2024-03-03 10:48:48');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
(1,'user1','test1111','user name','2024-03-02 21:17:02'),
(2,'admin','admin1111','administratore','2024-03-02 21:18:34'),
(3,'user4','tes4','user name4','2024-03-02 21:44:13'),
(4,'admin22','admin2','super user','2024-03-02 21:44:25'),
(5,'am confused','reallY?','really','2024-03-02 22:14:03'),
(6,'amqwe confused','reallY?','really2','2024-03-02 22:18:09'),
(7,'amqwe confused','reallY?','really2','2024-03-02 22:19:42'),
(8,'amqwe confused','reallY?','really2','2024-03-02 22:25:03'),
(9,'amqwe confused','reallY?','really2','2024-03-02 22:25:14'),
(10,'amqwe confused','reallY?','really2','2024-03-02 22:26:04'),
(11,'amqwe confused','reallY?','really2','2024-03-02 22:30:03'),
(12,'amqwe confused123','r3eallY?','reall23y2','2024-03-02 22:31:36'),
(13,'amqwe confused1234','r3eallY?','reall23y2','2024-03-02 22:33:42'),
(14,'amqwe confused1234','r3eallY?','reall23y2','2024-03-02 22:36:09'),
(15,'amqwe confused1234','r3eallY?','reall23y2','2024-03-02 22:45:26'),
(16,'amqwe confused1234','r3eallY?','reall23y2','2024-03-02 22:46:04'),
(17,'lie','rlieeee?','realerwerqwerl23y2','2024-03-02 22:46:26');
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

-- Dump completed on 2024-03-07 19:55:13
