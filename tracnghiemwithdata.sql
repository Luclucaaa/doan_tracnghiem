CREATE DATABASE  IF NOT EXISTS `tracnghiem` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `tracnghiem`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: tracnghiem
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `awID` int(11) NOT NULL AUTO_INCREMENT,
  `qID` int(11) NOT NULL COMMENT 'id câu hỏi',
  `awContent` text NOT NULL,
  `awPictures` text NOT NULL COMMENT 'url ảnh',
  `isRight` tinyint(4) NOT NULL COMMENT '1: đúng; 0: Sai',
  `awStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden',
  PRIMARY KEY (`awID`),
  KEY `qID` (`qID`),
  CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`qID`) REFERENCES `questions` (`qID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exams` (
  `testCode` varchar(20) NOT NULL,
  `exOrder` varchar(1) NOT NULL COMMENT 'A;B;C;D;E;F',
  `exCode` varchar(20) NOT NULL COMMENT '=testCode + exOrder',
  `ex_quesIDs` text NOT NULL COMMENT 'mảng các id câu hỏi',
  PRIMARY KEY (`testCode`,`exOrder`),
  KEY `testCode` (`testCode`),
  KEY `exCode` (`exCode`),
  CONSTRAINT `exams_ibfk_1` FOREIGN KEY (`exCode`) REFERENCES `result` (`exCode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs` (
  `logID` int(11) NOT NULL AUTO_INCREMENT,
  `logContent` text NOT NULL,
  `logUserID` int(11) NOT NULL,
  `logExCode` varchar(20) NOT NULL,
  `logDate` datetime NOT NULL,
  PRIMARY KEY (`logID`),
  KEY `logUserID` (`logUserID`),
  CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`logUserID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `qID` int(11) NOT NULL AUTO_INCREMENT,
  `qContent` text NOT NULL COMMENT 'nội dung câu hỏi',
  `qPictures` text NOT NULL COMMENT 'url hình đính kèm',
  `qTopicID` int(11) NOT NULL,
  `qLevel` varchar(10) NOT NULL COMMENT 'easy, meidum, diff',
  `qStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden',
  PRIMARY KEY (`qID`),
  KEY `qTopicID` (`qTopicID`),
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`qTopicID`) REFERENCES `topics` (`tpID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `result` (
  `rs_num` tinyint(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `exCode` varchar(20) NOT NULL,
  `rs_anwsers` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'các đáp án đã chọn' CHECK (json_valid(`rs_anwsers`)),
  `rs_mark` decimal(10,0) NOT NULL,
  `rs_date` datetime NOT NULL,
  PRIMARY KEY (`rs_num`,`userID`,`exCode`),
  KEY `userID` (`userID`),
  KEY `exID` (`exCode`),
  KEY `exCode` (`exCode`),
  CONSTRAINT `result_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `result`
--

LOCK TABLES `result` WRITE;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
/*!40000 ALTER TABLE `result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `testID` int(11) NOT NULL,
  `testCode` varchar(20) NOT NULL COMMENT 'mã bài thi',
  `testTilte` text NOT NULL,
  `testTime` int(11) NOT NULL COMMENT 'thời gian làm bài (phút)',
  `tpID` int(11) NOT NULL COMMENT 'id của chủ đề/bài học',
  `num_easy` int(11) NOT NULL COMMENT 'số lượng câu dễ',
  `num_medium` int(11) NOT NULL COMMENT 'số lượng câu trung bình',
  `num_diff` int(11) NOT NULL COMMENT 'số lượng câu khó',
  `testLimit` tinyint(4) NOT NULL COMMENT 'số lần thi',
  `testDate` date NOT NULL,
  `testStatus` int(11) NOT NULL,
  PRIMARY KEY (`testID`,`tpID`) USING BTREE,
  KEY `tpID` (`tpID`),
  KEY `testCode` (`testCode`),
  CONSTRAINT `test_ibfk_1` FOREIGN KEY (`tpID`) REFERENCES `topics` (`tpID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `test_ibfk_2` FOREIGN KEY (`testCode`) REFERENCES `exams` (`testCode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics` (
  `tpID` int(11) NOT NULL AUTO_INCREMENT,
  `tpTitle` text NOT NULL COMMENT 'tên topic',
  `tpParent` int(11) NOT NULL COMMENT 'id của topic cha',
  `tpStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden',
  PRIMARY KEY (`tpID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (1,'window',2,0),(2,'window',2,0),(3,'window',2,0),(4,'vv',0,0),(5,'luc',0,0),(6,'ff',0,0),(7,'lucdeptrai',0,0),(8,'luc handsome',0,0),(9,'1',0,0),(10,'abc',1,0),(11,'window',1,1),(12,'netbeans',1,1);
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(40) NOT NULL COMMENT 'login = userName',
  `userEmail` varchar(20) NOT NULL,
  `userPassword` varchar(40) NOT NULL COMMENT 'mã hóa dùng md5',
  `userFullName` varchar(40) NOT NULL,
  `isAdmin` tinyint(4) NOT NULL COMMENT '1: admin; 0: user',
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin@example.com','e10adc3949ba59abbe56e057f20f883e','Administrator',1),(2,'lucluc','lucvothe12@gmail.com','e10adc3949ba59abbe56e057f20f883e','Võ Thế Lực',0),(4,'lucluc1','abc@gmail.com','123','lucluc1',0),(5,'votheluc1211','votheluc@gmail.com','202cb962ac59075b964b07152d234b70','votheluc1211',0),(6,'vothe','vothe@gmail.com','202cb962ac59075b964b07152d234b70','vothe',1);
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

-- Dump completed on 2025-03-06 18:22:55
