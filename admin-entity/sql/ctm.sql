-- MySQL dump 10.13  Distrib 5.6.14, for osx10.7 (x86_64)
--
-- Host: localhost    Database: ctm
-- ------------------------------------------------------
-- Server version	5.6.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `a_resource`
--

DROP TABLE IF EXISTS `a_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `a_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL,
  `identifier` varchar(32) DEFAULT NULL,
  `is_end` tinyint(2) NOT NULL DEFAULT '0',
  `memo` varchar(512) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `order_field` varchar(128) NOT NULL DEFAULT '',
  `priority` int(11) NOT NULL DEFAULT '0',
  `tree_level` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `updated_time` datetime DEFAULT NULL,
  `url` varchar(512) DEFAULT NULL,
  `valid` int(11) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_created_time` (`created_time`),
  KEY `idx_valid` (`valid`),
  KEY `FK_hq7yxyx4c7e2fs0ku5lw3dq1k` (`parent_id`),
  CONSTRAINT `FK_hq7yxyx4c7e2fs0ku5lw3dq1k` FOREIGN KEY (`parent_id`) REFERENCES `a_resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_role`
--

DROP TABLE IF EXISTS `a_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `a_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `identifier` varchar(32) NOT NULL DEFAULT '',
  `is_default` int(11) NOT NULL,
  `name` varchar(32) NOT NULL DEFAULT '',
  `updated_time` datetime DEFAULT NULL,
  `valid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_identifier` (`identifier`),
  KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_role_resource`
--

DROP TABLE IF EXISTS `a_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `a_role_resource` (
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  KEY `FK_tbjqss72n39mp0rafrmgm7gsq` (`resource_id`),
  KEY `FK_se02ydixmytir1k0roug6144r` (`role_id`),
  CONSTRAINT `FK_se02ydixmytir1k0roug6144r` FOREIGN KEY (`role_id`) REFERENCES `a_role` (`id`),
  CONSTRAINT `FK_tbjqss72n39mp0rafrmgm7gsq` FOREIGN KEY (`resource_id`) REFERENCES `a_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_user`
--

DROP TABLE IF EXISTS `a_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `a_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL,
  `email` varchar(128) NOT NULL,
  `email_valid` int(11) NOT NULL,
  `mobile` varchar(32) DEFAULT NULL,
  `mobile_valid` int(11) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `updated_time` datetime DEFAULT NULL,
  `username` varchar(20) NOT NULL,
  `valid` int(11) NOT NULL,
  `root` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_created_time` (`created_time`),
  KEY `idx_valid` (`valid`),
  KEY `idx_valid_email_mobile` (`email_valid`,`mobile_valid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_user_role`
--

DROP TABLE IF EXISTS `a_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `a_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_iiwagecyg3i395876hrjmddp0` (`role_id`),
  CONSTRAINT `FK_i0eyspudq2c39c9e8qss1l7s5` FOREIGN KEY (`user_id`) REFERENCES `a_user` (`id`),
  CONSTRAINT `FK_iiwagecyg3i395876hrjmddp0` FOREIGN KEY (`role_id`) REFERENCES `a_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-21 17:18:17
