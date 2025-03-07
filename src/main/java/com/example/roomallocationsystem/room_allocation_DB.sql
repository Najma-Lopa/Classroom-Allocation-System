CREATE DATABASE  IF NOT EXISTS `project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `project`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: project
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `cr_info`
--

DROP TABLE IF EXISTS `cr_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cr_info` (
  `cr_id` int DEFAULT NULL,
  `cr_name` varchar(80) DEFAULT NULL,
  `f_name` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cr_info`
--

LOCK TABLES `cr_info` WRITE;
/*!40000 ALTER TABLE `cr_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `cr_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `f_name` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES ('Computer Science and Engineering'),('Fisheries'),('Science'),('Bussiness Studies'),('Social Science and Humanities');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_info`
--

DROP TABLE IF EXISTS `room_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_info` (
  `f_name` varchar(80) DEFAULT NULL,
  `room_number` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_info`
--

LOCK TABLES `room_info` WRITE;
/*!40000 ALTER TABLE `room_info` DISABLE KEYS */;
INSERT INTO `room_info` VALUES ('Computer Science and Engineering',235),('Computer Science and Engineering',236),('Computer Science and Engineering',237),('Computer Science and Engineering',209),('Computer Science and Engineering',210),('Computer Science and Engineering',243),('Fisheries',103),('Fisheries',104),('Fisheries',143),('Fisheries',142),('Fisheries',141),('Fisheries',136),('Fisheries',137),('Fisheries',135),('Bussiness Studies',301),('Bussiness Studies',302),('Bussiness Studies',303),('Bussiness Studies',304),('Bussiness Studies',305),('Bussiness Studies',309),('Bussiness Studies',310),('Bussiness Studies',342),('Bussiness Studies',343),('Bussiness Studies',341),('Bussiness Studies',335),('Social Science and Humanities',541),('Social Science and Humanities',542),('Social Science and Humanities',543),('Social Science and Humanities',536),('Social Science and Humanities',537),('Social Science and Humanities',534),('Social Science and Humanities',501),('Social Science and Humanities',502),('Social Science and Humanities',503),('Social Science and Humanities',504),('Social Science and Humanities',505),('Social Science and Humanities',510);
/*!40000 ALTER TABLE `room_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-22  7:07:53
