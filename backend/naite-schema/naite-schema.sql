CREATE DATABASE  IF NOT EXISTS `naite` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `naite`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: naite
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `auth_key`
--

DROP TABLE IF EXISTS `auth_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_key` (
  `auth_no` int NOT NULL AUTO_INCREMENT,
  `user_no` int NOT NULL,
  `auth_key` varchar(200) NOT NULL,
  `auth_type` int NOT NULL COMMENT '0:회원가입 / 1:아이디 찾기/2:비밀번호 찾기',
  PRIMARY KEY (`auth_no`,`user_no`),
  KEY `FK_user_TO_auth_key_1` (`user_no`),
  CONSTRAINT `FK_user_TO_auth_key_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_key`
--

LOCK TABLES `auth_key` WRITE;
/*!40000 ALTER TABLE `auth_key` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auto_login`
--

DROP TABLE IF EXISTS `auto_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auto_login` (
  `token_no` int NOT NULL AUTO_INCREMENT,
  `user_no` int NOT NULL,
  `refresh_token` varchar(200) NOT NULL,
  PRIMARY KEY (`token_no`,`user_no`),
  KEY `FK_user_TO_auto_login_1` (`user_no`),
  CONSTRAINT `FK_user_TO_auto_login_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auto_login`
--

LOCK TABLES `auto_login` WRITE;
/*!40000 ALTER TABLE `auto_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `auto_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `big_category`
--

DROP TABLE IF EXISTS `big_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `big_category` (
  `big_category_no` int NOT NULL,
  `big_category_name` varchar(20) NOT NULL COMMENT '1:자유 / 2:질문 / 3:리뷰 / 4:핫이슈 / 5:장터 / 6:공지',
  PRIMARY KEY (`big_category_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `big_category`
--

LOCK TABLES `big_category` WRITE;
/*!40000 ALTER TABLE `big_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `big_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `board_no` int NOT NULL AUTO_INCREMENT,
  `user_no` int NOT NULL,
  `big_category_no` int NOT NULL,
  `board_title` varchar(30) NOT NULL,
  `board_content` text NOT NULL,
  `board_pic` varchar(100) DEFAULT NULL,
  `board_created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `board_updated_at` datetime DEFAULT NULL,
  `board_like_cnt` int DEFAULT NULL,
  `unknown_flag` tinyint(1) NOT NULL,
  `board_report_cnt` int DEFAULT '0',
  `open_flag` tinyint(1) NOT NULL COMMENT '0: 공개, 1:비공개',
  `board_is_deleted` tinyint(1) NOT NULL COMMENT '0:활성1:삭제',
  PRIMARY KEY (`board_no`),
  KEY `FK_user_TO_board_1` (`user_no`),
  KEY `FK_big_category_TO_board_1` (`big_category_no`),
  CONSTRAINT `FK_big_category_TO_board_1` FOREIGN KEY (`big_category_no`) REFERENCES `big_category` (`big_category_no`),
  CONSTRAINT `FK_user_TO_board_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_no` int NOT NULL AUTO_INCREMENT,
  `board_no` int NOT NULL,
  `user_no` int NOT NULL,
  `comment_content` varchar(200) NOT NULL,
  `comment_created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `comment_updated_at` datetime DEFAULT NULL,
  `comment_is_deleted` tinyint(1) NOT NULL COMMENT '0:활성1:삭제',
  `comment_report_cnt` int DEFAULT '0',
  `comment_parent_id` int NOT NULL COMMENT '0:댓글, 그외: 답글',
  PRIMARY KEY (`comment_no`),
  KEY `FK_board_TO_comment_1` (`board_no`),
  KEY `FK_user_TO_comment_1` (`user_no`),
  CONSTRAINT `FK_board_TO_comment_1` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`),
  CONSTRAINT `FK_user_TO_comment_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluation` (
  `eval_no` int NOT NULL AUTO_INCREMENT,
  `user_no` int NOT NULL,
  `market_no` int NOT NULL,
  `eval_score` int NOT NULL,
  `eval_comment` text NOT NULL,
  PRIMARY KEY (`eval_no`),
  KEY `FK_user_TO_evaluation_1` (`user_no`),
  KEY `FK_market_TO_evaluation_1` (`market_no`),
  CONSTRAINT `FK_market_TO_evaluation_1` FOREIGN KEY (`market_no`) REFERENCES `market` (`market_no`),
  CONSTRAINT `FK_user_TO_evaluation_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation`
--

LOCK TABLES `evaluation` WRITE;
/*!40000 ALTER TABLE `evaluation` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like`
--

DROP TABLE IF EXISTS `like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like` (
  `user_no` int NOT NULL,
  `board_no` int NOT NULL,
  PRIMARY KEY (`user_no`,`board_no`),
  KEY `FK_board_TO_like_1` (`board_no`),
  CONSTRAINT `FK_board_TO_like_1` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`),
  CONSTRAINT `FK_user_TO_like_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like`
--

LOCK TABLES `like` WRITE;
/*!40000 ALTER TABLE `like` DISABLE KEYS */;
/*!40000 ALTER TABLE `like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `local`
--

DROP TABLE IF EXISTS `local`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `local` (
  `local_no` int NOT NULL AUTO_INCREMENT,
  `city` varchar(30) DEFAULT NULL,
  `gugun` varchar(30) DEFAULT NULL,
  `dong` varchar(30) DEFAULT NULL,
  `code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`local_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `local`
--

LOCK TABLES `local` WRITE;
/*!40000 ALTER TABLE `local` DISABLE KEYS */;
/*!40000 ALTER TABLE `local` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `market`
--

DROP TABLE IF EXISTS `market`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `market` (
  `market_no` int NOT NULL AUTO_INCREMENT,
  `board_no` int NOT NULL,
  `small_category_no` int NOT NULL,
  `market_cost` int NOT NULL,
  `market_place` varchar(50) DEFAULT NULL,
  `market_is_completed` tinyint(1) NOT NULL DEFAULT '0',
  `market_person` int DEFAULT NULL,
  `market_end_time` datetime DEFAULT NULL,
  `market_category` int NOT NULL COMMENT '1:공구 2:중고거래',
  PRIMARY KEY (`market_no`),
  KEY `FK_board_TO_market_1` (`board_no`),
  KEY `FK_small_category_TO_market_1` (`small_category_no`),
  CONSTRAINT `FK_board_TO_market_1` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`),
  CONSTRAINT `FK_small_category_TO_market_1` FOREIGN KEY (`small_category_no`) REFERENCES `small_category` (`small_category_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `market`
--

LOCK TABLES `market` WRITE;
/*!40000 ALTER TABLE `market` DISABLE KEYS */;
/*!40000 ALTER TABLE `market` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `report_no` int NOT NULL AUTO_INCREMENT,
  `board_no` int DEFAULT NULL,
  `comment_no` int DEFAULT NULL,
  `user_no` int NOT NULL,
  `report_type` int NOT NULL COMMENT '0:게시글, 1:댓글',
  `report_target_no` int NOT NULL,
  `report_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`report_no`),
  KEY `FK_board_TO_report_1` (`board_no`),
  KEY `FK_comment_TO_report_1` (`comment_no`),
  KEY `FK_user_TO_report_1` (`user_no`),
  CONSTRAINT `FK_board_TO_report_1` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`),
  CONSTRAINT `FK_comment_TO_report_1` FOREIGN KEY (`comment_no`) REFERENCES `comment` (`comment_no`),
  CONSTRAINT `FK_user_TO_report_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_no` int NOT NULL AUTO_INCREMENT,
  `small_category_no` int NOT NULL,
  `board_no` int NOT NULL,
  `review_star` int NOT NULL DEFAULT '5' COMMENT '만점: 5점, 단위: 1점',
  PRIMARY KEY (`review_no`),
  KEY `FK_small_category_TO_review_1` (`small_category_no`),
  KEY `FK_board_TO_review_1` (`board_no`),
  CONSTRAINT `FK_board_TO_review_1` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`),
  CONSTRAINT `FK_small_category_TO_review_1` FOREIGN KEY (`small_category_no`) REFERENCES `small_category` (`small_category_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `small_category`
--

DROP TABLE IF EXISTS `small_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `small_category` (
  `small_category_no` int NOT NULL,
  `big_category_no` int NOT NULL,
  `small_category_name` varchar(20) NOT NULL COMMENT '1:식당 / 2:의료 / 3:체육시설 / 4:미용 / 5:기타',
  PRIMARY KEY (`small_category_no`),
  KEY `FK_big_category_TO_small_category_1` (`big_category_no`),
  CONSTRAINT `FK_big_category_TO_small_category_1` FOREIGN KEY (`big_category_no`) REFERENCES `big_category` (`big_category_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `small_category`
--

LOCK TABLES `small_category` WRITE;
/*!40000 ALTER TABLE `small_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `small_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(15) NOT NULL,
  `user_name` varchar(10) NOT NULL,
  `user_email` varchar(30) NOT NULL,
  `user_pw` varchar(20) NOT NULL,
  `user_age` int DEFAULT NULL,
  `user_basic_address` varchar(30) NOT NULL COMMENT 'Ex) 경기 성남시 분당구 판교역로 235',
  `user_detail_address` varchar(30) DEFAULT NULL COMMENT '상세 주소(~동~호)',
  `user_nick` varchar(20) NOT NULL,
  `user_score` int NOT NULL DEFAULT '42' COMMENT '범위:1~100',
  `user_report_cnt` int DEFAULT '0',
  `user_pic` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `village`
--

DROP TABLE IF EXISTS `village`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `village` (
  `user_no` int NOT NULL,
  `village_name` varchar(30) DEFAULT NULL,
  `local_no` int NOT NULL,
  PRIMARY KEY (`user_no`),
  KEY `FK_local_TO_village_1` (`local_no`),
  CONSTRAINT `FK_local_TO_village_1` FOREIGN KEY (`local_no`) REFERENCES `local` (`local_no`),
  CONSTRAINT `FK_user_TO_village_1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `village`
--

LOCK TABLES `village` WRITE;
/*!40000 ALTER TABLE `village` DISABLE KEYS */;
/*!40000 ALTER TABLE `village` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-20 14:41:03
