-- MySQL dump 10.13  Distrib 5.6.27, for FreeBSD10.1 (amd64)
--
-- Host: localhost    Database: qifu2
-- ------------------------------------------------------
-- Server version	5.6.27

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
-- Table structure for table `act_evt_log`
--

DROP TABLE IF EXISTS `act_evt_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_evt_log`
--

LOCK TABLES `act_evt_log` WRITE;
/*!40000 ALTER TABLE `act_evt_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_evt_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ge_bytearray`
--

DROP TABLE IF EXISTS `act_ge_bytearray`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ge_bytearray`
--

LOCK TABLES `act_ge_bytearray` WRITE;
/*!40000 ALTER TABLE `act_ge_bytearray` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ge_bytearray` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ge_property`
--

DROP TABLE IF EXISTS `act_ge_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ge_property`
--

LOCK TABLES `act_ge_property` WRITE;
/*!40000 ALTER TABLE `act_ge_property` DISABLE KEYS */;
INSERT INTO `act_ge_property` VALUES ('cfg.execution-related-entities-count','false',1),('next.dbid','1',1),('schema.history','create(6.0.0.4)',1),('schema.version','6.0.0.4',1);
/*!40000 ALTER TABLE `act_ge_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_actinst`
--

DROP TABLE IF EXISTS `act_hi_actinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_actinst`
--

LOCK TABLES `act_hi_actinst` WRITE;
/*!40000 ALTER TABLE `act_hi_actinst` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_actinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_attachment`
--

DROP TABLE IF EXISTS `act_hi_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_attachment`
--

LOCK TABLES `act_hi_attachment` WRITE;
/*!40000 ALTER TABLE `act_hi_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_comment`
--

DROP TABLE IF EXISTS `act_hi_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_comment`
--

LOCK TABLES `act_hi_comment` WRITE;
/*!40000 ALTER TABLE `act_hi_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_detail`
--

DROP TABLE IF EXISTS `act_hi_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_detail`
--

LOCK TABLES `act_hi_detail` WRITE;
/*!40000 ALTER TABLE `act_hi_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_identitylink`
--

DROP TABLE IF EXISTS `act_hi_identitylink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_identitylink`
--

LOCK TABLES `act_hi_identitylink` WRITE;
/*!40000 ALTER TABLE `act_hi_identitylink` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_identitylink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_procinst`
--

DROP TABLE IF EXISTS `act_hi_procinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_procinst`
--

LOCK TABLES `act_hi_procinst` WRITE;
/*!40000 ALTER TABLE `act_hi_procinst` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_procinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_taskinst`
--

DROP TABLE IF EXISTS `act_hi_taskinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_taskinst`
--

LOCK TABLES `act_hi_taskinst` WRITE;
/*!40000 ALTER TABLE `act_hi_taskinst` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_taskinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_varinst`
--

DROP TABLE IF EXISTS `act_hi_varinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_varinst`
--

LOCK TABLES `act_hi_varinst` WRITE;
/*!40000 ALTER TABLE `act_hi_varinst` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_varinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_group`
--

DROP TABLE IF EXISTS `act_id_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_group`
--

LOCK TABLES `act_id_group` WRITE;
/*!40000 ALTER TABLE `act_id_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_info`
--

DROP TABLE IF EXISTS `act_id_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_info`
--

LOCK TABLES `act_id_info` WRITE;
/*!40000 ALTER TABLE `act_id_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_membership`
--

DROP TABLE IF EXISTS `act_id_membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_membership`
--

LOCK TABLES `act_id_membership` WRITE;
/*!40000 ALTER TABLE `act_id_membership` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_membership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_user`
--

DROP TABLE IF EXISTS `act_id_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_user`
--

LOCK TABLES `act_id_user` WRITE;
/*!40000 ALTER TABLE `act_id_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_procdef_info`
--

DROP TABLE IF EXISTS `act_procdef_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_procdef_info`
--

LOCK TABLES `act_procdef_info` WRITE;
/*!40000 ALTER TABLE `act_procdef_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_procdef_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_re_deployment`
--

DROP TABLE IF EXISTS `act_re_deployment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  `ENGINE_VERSION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_re_deployment`
--

LOCK TABLES `act_re_deployment` WRITE;
/*!40000 ALTER TABLE `act_re_deployment` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_re_deployment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_re_model`
--

DROP TABLE IF EXISTS `act_re_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_re_model`
--

LOCK TABLES `act_re_model` WRITE;
/*!40000 ALTER TABLE `act_re_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_re_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_re_procdef`
--

DROP TABLE IF EXISTS `act_re_procdef`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `ENGINE_VERSION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_re_procdef`
--

LOCK TABLES `act_re_procdef` WRITE;
/*!40000 ALTER TABLE `act_re_procdef` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_re_procdef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_deadletter_job`
--

DROP TABLE IF EXISTS `act_ru_deadletter_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_deadletter_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_deadletter_job`
--

LOCK TABLES `act_ru_deadletter_job` WRITE;
/*!40000 ALTER TABLE `act_ru_deadletter_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_deadletter_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_event_subscr`
--

DROP TABLE IF EXISTS `act_ru_event_subscr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_event_subscr`
--

LOCK TABLES `act_ru_event_subscr` WRITE;
/*!40000 ALTER TABLE `act_ru_event_subscr` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_event_subscr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_execution`
--

DROP TABLE IF EXISTS `act_ru_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_MI_ROOT_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint(4) DEFAULT NULL,
  `EVT_SUBSCR_COUNT_` int(11) DEFAULT NULL,
  `TASK_COUNT_` int(11) DEFAULT NULL,
  `JOB_COUNT_` int(11) DEFAULT NULL,
  `TIMER_JOB_COUNT_` int(11) DEFAULT NULL,
  `SUSP_JOB_COUNT_` int(11) DEFAULT NULL,
  `DEADLETTER_JOB_COUNT_` int(11) DEFAULT NULL,
  `VAR_COUNT_` int(11) DEFAULT NULL,
  `ID_LINK_COUNT_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_IDC_EXEC_ROOT` (`ROOT_PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE,
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_execution`
--

LOCK TABLES `act_ru_execution` WRITE;
/*!40000 ALTER TABLE `act_ru_execution` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_execution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_identitylink`
--

DROP TABLE IF EXISTS `act_ru_identitylink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_identitylink`
--

LOCK TABLES `act_ru_identitylink` WRITE;
/*!40000 ALTER TABLE `act_ru_identitylink` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_identitylink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_job`
--

DROP TABLE IF EXISTS `act_ru_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_job`
--

LOCK TABLES `act_ru_job` WRITE;
/*!40000 ALTER TABLE `act_ru_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_suspended_job`
--

DROP TABLE IF EXISTS `act_ru_suspended_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_suspended_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_suspended_job`
--

LOCK TABLES `act_ru_suspended_job` WRITE;
/*!40000 ALTER TABLE `act_ru_suspended_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_suspended_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_task`
--

DROP TABLE IF EXISTS `act_ru_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_task`
--

LOCK TABLES `act_ru_task` WRITE;
/*!40000 ALTER TABLE `act_ru_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_timer_job`
--

DROP TABLE IF EXISTS `act_ru_timer_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_timer_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_TIMER_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_TIMER_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TIMER_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_timer_job`
--

LOCK TABLES `act_ru_timer_job` WRITE;
/*!40000 ALTER TABLE `act_ru_timer_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_timer_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_variable`
--

DROP TABLE IF EXISTS `act_ru_variable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_variable`
--

LOCK TABLES `act_ru_variable` WRITE;
/*!40000 ALTER TABLE `act_ru_variable` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_variable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `comm_qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `comm_qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `comm_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_blob_triggers`
--

LOCK TABLES `comm_qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_calendars`
--

DROP TABLE IF EXISTS `comm_qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_calendars`
--

LOCK TABLES `comm_qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `comm_qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `comm_qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `comm_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_cron_triggers`
--

LOCK TABLES `comm_qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `comm_qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_COMM_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_COMM_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_COMM_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_COMM_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_COMM_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_COMM_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_fired_triggers`
--

LOCK TABLES `comm_qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_job_details`
--

DROP TABLE IF EXISTS `comm_qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_COMM_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_COMM_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_job_details`
--

LOCK TABLES `comm_qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_locks`
--

DROP TABLE IF EXISTS `comm_qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_locks`
--

LOCK TABLES `comm_qrtz_locks` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_locks` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `comm_qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_paused_trigger_grps`
--

LOCK TABLES `comm_qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `comm_qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_scheduler_state`
--

LOCK TABLES `comm_qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `comm_qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `comm_qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `comm_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_simple_triggers`
--

LOCK TABLES `comm_qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `comm_qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `comm_qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `comm_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_simprop_triggers`
--

LOCK TABLES `comm_qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comm_qrtz_triggers`
--

DROP TABLE IF EXISTS `comm_qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comm_qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_COMM_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_COMM_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_COMM_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_COMM_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_COMM_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_COMM_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_COMM_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_COMM_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_COMM_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_COMM_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_COMM_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_COMM_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `comm_qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `comm_qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comm_qrtz_triggers`
--

LOCK TABLES `comm_qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `comm_qrtz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_cron_triggers` VALUES ('scheduler','core.job.ClearTempDataJobCronTrigger','DEFAULT','1 30 03 * * ?','Asia/Taipei'),('scheduler','core.job.SendMailHelperJobCronTrigger','DEFAULT','0 0/2 * * * ?','Asia/Taipei'),('scheduler','core.job.SysExpressionJobCronTrigger','DEFAULT','0 0/1 * * * ?','Asia/Taipei');
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
INSERT INTO `qrtz_job_details` VALUES ('scheduler','core.job.ClearTempDataJob','DEFAULT',NULL,'org.qifu.job.impl.ClearTempDataJobImpl','1','0','0','0',0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800),('scheduler','core.job.SendMailHelperJob','DEFAULT',NULL,'org.qifu.job.impl.SendMailHelperJobImpl','1','0','0','0',0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800),('scheduler','core.job.SysExpressionJob','DEFAULT',NULL,'org.qifu.job.impl.SysExpressionJobImpl','1','0','0','0',0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES ('scheduler','STATE_ACCESS'),('scheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
INSERT INTO `qrtz_scheduler_state` VALUES ('scheduler','localX861496662023015',1496662249643,7500);
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_triggers` VALUES ('scheduler','core.job.ClearTempDataJobCronTrigger','DEFAULT','core.job.ClearTempDataJob','DEFAULT',NULL,1496691001000,-1,0,'WAITING','CRON',1496662022000,0,NULL,0,''),('scheduler','core.job.SendMailHelperJobCronTrigger','DEFAULT','core.job.SendMailHelperJob','DEFAULT',NULL,1496662320000,1496662200000,0,'WAITING','CRON',1496662022000,0,NULL,0,''),('scheduler','core.job.SysExpressionJobCronTrigger','DEFAULT','core.job.SysExpressionJob','DEFAULT',NULL,1496662260000,1496662200000,0,'WAITING','CRON',1496662022000,0,NULL,0,'');
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_account`
--

DROP TABLE IF EXISTS `tb_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_account` (
  `OID` char(36) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ON_JOB` varchar(50) NOT NULL DEFAULT 'Y',
  `CUSERID` varchar(24) DEFAULT NULL,
  `CDATE` datetime DEFAULT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`,`ACCOUNT`),
  UNIQUE KEY `UK_1` (`ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_account`
--

LOCK TABLES `tb_account` WRITE;
/*!40000 ALTER TABLE `tb_account` DISABLE KEYS */;
INSERT INTO `tb_account` VALUES ('0','admin','b7f1acbdc67d3b2a68d36a5d7a29b228','Y','admin','2012-11-11 10:56:23','admin','2014-04-19 11:32:04'),('15822da5-25dc-490c-bdfb-be75f5ff4843','tester','5c06e78482c0a8b9cefb34e5cad64941','Y','admin','2015-04-23 11:26:53','admin','2015-08-29 17:54:08'),('52cb274e-388d-419f-a81e-67ca599bfb63','steven','953703bc964d40ce93dc90e2e012062e','Y','admin','2015-09-11 10:33:53',NULL,NULL),('9c239d19-3646-41db-b394-d34c5bf34671','tiffany','a4c1cfdf17b58a01e778a8b4c5701ea5','Y','admin','2015-09-11 10:15:29',NULL,NULL);
/*!40000 ALTER TABLE `tb_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role` (
  `OID` char(36) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(50) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ROLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES ('4b1796ad-0bb7-4a65-b45e-439540ba5dbd','admin','administrator role!','admin','2014-10-09 15:02:24',NULL,NULL),('58914623-46ea-4797-bbec-2dadc5d0800e','COMMON01','Common role!','admin','2017-05-09 13:31:42',NULL,NULL),('c7c69396-e5e6-48ca-b09c-9445b69e2ad5','*','all role','admin','2014-10-09 15:02:54',NULL,NULL);
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_permission`
--

DROP TABLE IF EXISTS `tb_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role_permission` (
  `OID` char(36) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `PERMISSION` varchar(255) NOT NULL,
  `PERM_TYPE` varchar(15) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(50) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ROLE`,`PERMISSION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_permission`
--

LOCK TABLES `tb_role_permission` WRITE;
/*!40000 ALTER TABLE `tb_role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys`
--

DROP TABLE IF EXISTS `tb_sys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys` (
  `OID` char(36) NOT NULL,
  `SYS_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `HOST` varchar(200) NOT NULL,
  `CONTEXT_PATH` varchar(100) NOT NULL,
  `IS_LOCAL` varchar(1) NOT NULL DEFAULT 'Y',
  `ICON` varchar(20) NOT NULL DEFAULT ' ',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`SYS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys`
--

LOCK TABLES `tb_sys` WRITE;
/*!40000 ALTER TABLE `tb_sys` DISABLE KEYS */;
INSERT INTO `tb_sys` VALUES ('c6643182-85a5-4f91-9e73-10567ebd0dd5','CORE','Core-system','127.0.0.1:8080','core-web','Y','SYSTEM','admin','2017-04-10 20:42:00','admin','2017-04-10 20:42:00');
/*!40000 ALTER TABLE `tb_sys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_bean_help`
--

DROP TABLE IF EXISTS `tb_sys_bean_help`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_bean_help` (
  `OID` char(36) NOT NULL,
  `BEAN_ID` varchar(255) NOT NULL,
  `METHOD` varchar(100) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `ENABLE_FLAG` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`BEAN_ID`,`METHOD`,`SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_bean_help`
--

LOCK TABLES `tb_sys_bean_help` WRITE;
/*!40000 ALTER TABLE `tb_sys_bean_help` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_bean_help` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_bean_help_expr`
--

DROP TABLE IF EXISTS `tb_sys_bean_help_expr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_bean_help_expr` (
  `OID` char(36) NOT NULL,
  `HELP_OID` char(36) NOT NULL,
  `EXPR_ID` varchar(20) NOT NULL,
  `EXPR_SEQ` varchar(10) NOT NULL,
  `RUN_TYPE` varchar(10) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`EXPR_ID`,`HELP_OID`,`RUN_TYPE`),
  KEY `IDX_1` (`HELP_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_bean_help_expr`
--

LOCK TABLES `tb_sys_bean_help_expr` WRITE;
/*!40000 ALTER TABLE `tb_sys_bean_help_expr` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_bean_help_expr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_bean_help_expr_map`
--

DROP TABLE IF EXISTS `tb_sys_bean_help_expr_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_bean_help_expr_map` (
  `OID` char(36) NOT NULL,
  `HELP_EXPR_OID` char(36) NOT NULL,
  `METHOD_RESULT_FLAG` varchar(1) NOT NULL DEFAULT 'N',
  `METHOD_PARAM_CLASS` varchar(255) NOT NULL DEFAULT ' ',
  `METHOD_PARAM_INDEX` int(3) NOT NULL DEFAULT '0',
  `VAR_NAME` varchar(255) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`VAR_NAME`,`HELP_EXPR_OID`),
  KEY `IDX_1` (`HELP_EXPR_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_bean_help_expr_map`
--

LOCK TABLES `tb_sys_bean_help_expr_map` WRITE;
/*!40000 ALTER TABLE `tb_sys_bean_help_expr_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_bean_help_expr_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_bpmn_resource`
--

DROP TABLE IF EXISTS `tb_sys_bpmn_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_bpmn_resource` (
  `OID` char(36) NOT NULL,
  `ID` varchar(100) NOT NULL,
  `DEPLOYMENT_ID` varchar(64) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `CONTENT` mediumblob,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_bpmn_resource`
--

LOCK TABLES `tb_sys_bpmn_resource` WRITE;
/*!40000 ALTER TABLE `tb_sys_bpmn_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_bpmn_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_bpmn_resource_role`
--

DROP TABLE IF EXISTS `tb_sys_bpmn_resource_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_bpmn_resource_role` (
  `OID` char(36) NOT NULL,
  `ID` varchar(100) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `TASK_NAME` varchar(255) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`,`ROLE`,`TASK_NAME`),
  KEY `IDX_1` (`ID`),
  KEY `IDX_2` (`ROLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_bpmn_resource_role`
--

LOCK TABLES `tb_sys_bpmn_resource_role` WRITE;
/*!40000 ALTER TABLE `tb_sys_bpmn_resource_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_bpmn_resource_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_code`
--

DROP TABLE IF EXISTS `tb_sys_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_code` (
  `OID` char(36) NOT NULL,
  `CODE` varchar(25) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `PARAM1` varchar(100) DEFAULT NULL,
  `PARAM2` varchar(100) DEFAULT NULL,
  `PARAM3` varchar(100) DEFAULT NULL,
  `PARAM4` varchar(100) DEFAULT NULL,
  `PARAM5` varchar(100) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_code`
--

LOCK TABLES `tb_sys_code` WRITE;
/*!40000 ALTER TABLE `tb_sys_code` DISABLE KEYS */;
INSERT INTO `tb_sys_code` VALUES ('0badf3df-6382-47f4-921e-ad6b4fe81dc2','CNF_CONF004','CNF','enable system template file re-write always!','Y',NULL,NULL,NULL,NULL,'admin','2015-05-13 10:09:01','admin','2015-05-13 19:24:28'),('10046e27-4c0a-40ed-89c5-6530525b421f','MSG_DOS0006','MSG','insert data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30','admin','2014-07-18 11:41:37'),('2d9c84e4-a956-42ac-96cb-1f6292d182a9','CNF_CONF002','CNF','enable mail sender!','Y',NULL,NULL,NULL,NULL,'admin','2014-12-25 09:09:57','admin','2015-05-13 19:24:28'),('31dfcf46-b6af-4cb7-8b88-f2be48ca4db5','CNF_CONF005','CNF','enable left AccordionContainer','Y',NULL,NULL,NULL,NULL,'admin','2015-05-13 10:09:01','admin','2015-05-13 19:24:28'),('328849f7-446d-415e-a598-9477caa5b69c','MSG_UOS0002','MSG','parameter is incorrect!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('44bf5446-b972-47a2-b0a1-9556861af07e','MSG_DOS0003','MSG','update data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('45cab4b8-add1-44be-8cab-7627403c389c','MSG_DOS0001','MSG','data no exist!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('4df770a6-6a9c-4d25-bdcd-1dee819d2ba6','CNF_CONF001','CNF','default mail from account!','root@localhost',NULL,NULL,NULL,NULL,'admin','2014-12-24 21:51:16','admin','2015-05-13 19:24:28'),('520a61eb-4bb0-4d0f-a0d5-4bbdc56f4202','MSG_DOS0005','MSG','insert data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('6cec1a23-d2b9-48ac-b720-13fcf9f8d39b','MSG_UOS0001','MSG','parameter cann\'t blank!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('6fb72e96-43fe-45b3-8a45-9f5898f61b4f','MSG_DOS0009','MSG','search no data!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('750b16c0-9058-435c-b76f-9cb312d2f383','MSG_STD0004','MSG','Please upload a image file!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('76a4cc16-e324-4a94-8c62-9c78643f074a','MSG_STD0005','MSG','Data errors!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('7e8c56a4-d3dc-4768-9b2b-77dd03554814','MSG_DOS0008','MSG','delete data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('8a2a8d70-99f8-42bd-a531-a9e4350fa753','MSG_UOS0004','MSG','Please try again!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('959f6d8a-cbae-4f52-950d-1c7de962ca36','MSG_DOS0004','MSG','update data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('96c3a7cb-966a-406a-8365-0e79a5862bf2','MSG_DOS0010','MSG','Data to be used, and can not be deleted!',NULL,NULL,NULL,NULL,NULL,'admin','2012-11-12 12:42:16',NULL,NULL),('a5f7ee37-f33f-48a6-b448-92ccb8cdf96a','CNF_CONF003','CNF','first load javascript','/* BSC_PROG001D0007Q_TabShow(); */',NULL,NULL,NULL,NULL,'admin','2014-12-25 09:09:57',NULL,NULL),('a714afb2-e28c-4450-8f16-725ee2a3df22','MSG_UOS0003','MSG','object null!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30','admin','2015-04-27 21:16:19'),('c5a5da5b-27c3-46cc-b5d3-ced1a168df0c','MSG_STD0003','MSG','Please select a file!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('c81bfed6-2814-4ada-82e2-f6d25e065eb0','MSG_STD0002','MSG','Upload a file type error!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('caf00ba5-fe63-4dc4-a1a3-32527f6629b2','CMM_CONF001','CMM','Common role for default user!','COMMON01',NULL,NULL,NULL,NULL,'admin','2017-05-09 12:29:00',NULL,NULL),('cca41808-0781-4d11-8303-93369471e901','MSG_DOS0002','MSG','data is exist!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('cec13700-4687-49c0-a89e-ae444388a318','MSG_STD0001','MSG','Login fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('d1322f32-12a3-4b1b-be98-e01acc6180bc','MSG_DOS0007','MSG','delete data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('dc0f2f81-8f04-47ca-b1fb-7810e4fe9d81','MSG_BSE0002','MSG','No sign-on system access denied!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('f0e2ee47-38df-490c-bbae-aba7b0b61dcc','MSG_BSE0001','MSG','No permission!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('fc3e6505-e0eb-491d-9c79-8d9f18f47e38','MSG_BSE0003','MSG','error. dozer mapper id blank!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ctx_bean`
--

DROP TABLE IF EXISTS `tb_sys_ctx_bean`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ctx_bean` (
  `OID` char(36) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `CLASS_NAME` varchar(255) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`SYSTEM`,`CLASS_NAME`,`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ctx_bean`
--

LOCK TABLES `tb_sys_ctx_bean` WRITE;
/*!40000 ALTER TABLE `tb_sys_ctx_bean` DISABLE KEYS */;
INSERT INTO `tb_sys_ctx_bean` VALUES ('19e69795-46f9-4fb4-8410-b4139239d7a6','CORE','org.qifu.support.ConfigureAppSiteHostForContextInitialized','INITIALIZE','Config modules host.','admin','2017-05-22 19:34:50',NULL,NULL),('39f2ba02-3da2-4599-8a04-02b33f0a9d7e','CORE','org.qifu.support.CleanTempUploadForContextInitAndDestroy','INITIALIZE','Clear temp upload data.','admin','2017-05-22 19:35:28',NULL,NULL),('76a17028-8006-4de9-aea1-2e31a97c8a84','CORE','org.qifu.support.CleanTempUploadForContextInitAndDestroy','DESTROY','Clear temp upload data.','admin','2017-05-22 19:35:42',NULL,NULL),('dcf46122-4ac3-4bc7-a5c9-1003db83a0f2','CORE','org.qifu.support.DeployJreportForContextInitialized','INITIALIZE','Deploy jasperreport source file.','admin','2017-05-22 19:36:39',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_ctx_bean` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_event_log`
--

DROP TABLE IF EXISTS `tb_sys_event_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_event_log` (
  `OID` char(36) NOT NULL,
  `USER` varchar(24) NOT NULL,
  `SYS_ID` varchar(10) NOT NULL,
  `EXECUTE_EVENT` varchar(255) NOT NULL,
  `IS_PERMIT` varchar(1) NOT NULL DEFAULT 'N',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`USER`),
  KEY `IDX_2` (`CDATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_event_log`
--

LOCK TABLES `tb_sys_event_log` WRITE;
/*!40000 ALTER TABLE `tb_sys_event_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_event_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_expr_job`
--

DROP TABLE IF EXISTS `tb_sys_expr_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_expr_job` (
  `OID` char(36) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `ID` varchar(20) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `ACTIVE` varchar(1) NOT NULL DEFAULT 'Y',
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `RUN_STATUS` varchar(1) NOT NULL DEFAULT 'Y',
  `CHECK_FAULT` varchar(1) NOT NULL DEFAULT 'N',
  `EXPR_ID` varchar(20) NOT NULL,
  `RUN_DAY_OF_WEEK` varchar(1) NOT NULL,
  `RUN_HOUR` varchar(2) NOT NULL,
  `RUN_MINUTE` varchar(2) NOT NULL,
  `CONTACT_MODE` varchar(1) NOT NULL DEFAULT '0',
  `CONTACT` varchar(500) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`),
  KEY `IDX_1` (`SYSTEM`,`ACTIVE`,`EXPR_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_expr_job`
--

LOCK TABLES `tb_sys_expr_job` WRITE;
/*!40000 ALTER TABLE `tb_sys_expr_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_expr_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_expr_job_log`
--

DROP TABLE IF EXISTS `tb_sys_expr_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_expr_job_log` (
  `OID` char(36) NOT NULL,
  `ID` varchar(20) NOT NULL,
  `LOG_STATUS` varchar(1) NOT NULL DEFAULT 'N',
  `BEGIN_DATETIME` datetime NOT NULL,
  `END_DATETIME` datetime NOT NULL,
  `FAULT_MSG` varchar(2000) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`ID`,`LOG_STATUS`,`BEGIN_DATETIME`),
  KEY `IDX_2` (`CDATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_expr_job_log`
--

LOCK TABLES `tb_sys_expr_job_log` WRITE;
/*!40000 ALTER TABLE `tb_sys_expr_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_expr_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_expression`
--

DROP TABLE IF EXISTS `tb_sys_expression`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_expression` (
  `OID` char(36) NOT NULL,
  `EXPR_ID` varchar(20) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `CONTENT` varchar(8000) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`EXPR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_expression`
--

LOCK TABLES `tb_sys_expression` WRITE;
/*!40000 ALTER TABLE `tb_sys_expression` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_expression` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ftp`
--

DROP TABLE IF EXISTS `tb_sys_ftp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ftp` (
  `OID` char(36) NOT NULL,
  `TYPE` varchar(5) NOT NULL DEFAULT 'FTP',
  `ID` varchar(10) NOT NULL,
  `ADDRESS` varchar(50) NOT NULL,
  `NAME` varchar(20) NOT NULL,
  `PORT` int(5) NOT NULL DEFAULT '0',
  `USER` varchar(50) NOT NULL,
  `PASS` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`),
  KEY `IDX_1` (`TYPE`,`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ftp`
--

LOCK TABLES `tb_sys_ftp` WRITE;
/*!40000 ALTER TABLE `tb_sys_ftp` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_ftp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ftp_tran`
--

DROP TABLE IF EXISTS `tb_sys_ftp_tran`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ftp_tran` (
  `OID` char(36) NOT NULL,
  `FTP_ID` varchar(10) NOT NULL,
  `TRAN_ID` varchar(10) NOT NULL,
  `TRAN_TYPE` varchar(10) NOT NULL DEFAULT 'GET',
  `CWD` varchar(50) NOT NULL,
  `XML_CLASS_NAME` varchar(255) DEFAULT NULL,
  `USE_SEGM` varchar(1) NOT NULL,
  `SEGM_MODE` varchar(10) NOT NULL DEFAULT 'TXT',
  `SEGM_SYMBOL` varchar(1) NOT NULL DEFAULT ',',
  `ENCODING` varchar(10) NOT NULL DEFAULT 'utf-8',
  `EXPR_TYPE` varchar(10) NOT NULL,
  `NAME_EXPRESSION` varchar(8000) NOT NULL,
  `HELP_EXPRESSION` varchar(8000) NOT NULL,
  `BEGIN_LEN` int(1) NOT NULL DEFAULT '0',
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TRAN_ID`),
  KEY `IDX_1` (`FTP_ID`,`TRAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ftp_tran`
--

LOCK TABLES `tb_sys_ftp_tran` WRITE;
/*!40000 ALTER TABLE `tb_sys_ftp_tran` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_ftp_tran` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ftp_tran_segm`
--

DROP TABLE IF EXISTS `tb_sys_ftp_tran_segm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ftp_tran_segm` (
  `OID` char(36) NOT NULL,
  `FTP_ID` varchar(10) NOT NULL,
  `TRAN_ID` varchar(10) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `BEGIN` int(4) NOT NULL DEFAULT '0',
  `END` int(4) NOT NULL DEFAULT '0',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`FTP_ID`,`TRAN_ID`,`NAME`),
  KEY `IDX_1` (`FTP_ID`,`TRAN_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ftp_tran_segm`
--

LOCK TABLES `tb_sys_ftp_tran_segm` WRITE;
/*!40000 ALTER TABLE `tb_sys_ftp_tran_segm` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_ftp_tran_segm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_icon`
--

DROP TABLE IF EXISTS `tb_sys_icon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_icon` (
  `OID` char(36) NOT NULL,
  `ICON_ID` varchar(20) NOT NULL,
  `FILE_NAME` varchar(200) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ICON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_icon`
--

LOCK TABLES `tb_sys_icon` WRITE;
/*!40000 ALTER TABLE `tb_sys_icon` DISABLE KEYS */;
INSERT INTO `tb_sys_icon` VALUES ('00a11813-0fbd-481f-bab8-11bdf0df1a7e','HELP_ABOUT','help-about.png','admin','2014-09-29 00:00:00',NULL,NULL),('01a5ebc8-b79a-4960-8dac-c6543c7fa56b','REFRESH','view-refresh.png','admin','2014-09-29 00:00:00',NULL,NULL),('035d1d3b-7223-448b-a7e8-660d47c5c1a2','STOCK_HOME','stock_home.png','admin','2014-09-29 00:00:00',NULL,NULL),('140a343b-f276-49c4-9bce-69b6c1b81736','GO_LEFT','stock_left.png','admin','2014-09-29 00:00:00',NULL,NULL),('3ee47be9-dc2d-4c7f-842b-e8f807b96e4b','PDCA','pdca.png','admin','2016-04-07 11:53:39',NULL,NULL),('3f70f89b-6ef9-4112-916f-e9994519557c','SIGNATURE','text_signature.png','admin','2015-02-14 10:17:50',NULL,NULL),('4471df15-4346-4c58-9f19-572ceeca4d9d','IMPORTANT','important.png','admin','2014-09-29 00:00:00',NULL,NULL),('45cd5523-c08e-45bd-9461-67064b41052a','GWENVIEW','gwenview.png','admin','2014-09-29 00:00:00',NULL,NULL),('4b84ee11-4e89-45b0-aca2-04db58ccdfd2','DIAGRAM','x-dia-diagram.png','admin','2014-10-22 12:15:02',NULL,NULL),('4c664a5c-69cd-4e4e-8d55-050423d4e0f1','PROPERTIES','document-properties.png','admin','2014-09-29 00:00:00',NULL,NULL),('4f875ea6-6d30-4e72-ba91-56b07890325d','CHART_AREA','area_chart.png','admin','2015-01-17 12:05:49',NULL,NULL),('547c4780-4c26-4552-b5dd-f41ed3fbf6f1','REMOVE','list-remove.png','admin','2014-09-29 00:00:00',NULL,NULL),('5a5279f9-f2ab-471a-a83d-8e5e8019de90','LOCK','lock.png','admin','2014-09-29 00:00:00',NULL,NULL),('5df6aac2-03b9-45e5-b4f0-b6b549813e7d','GIMP','gimp.png','admin','2014-12-02 10:11:46',NULL,NULL),('65b10fb0-9140-4490-9d15-94148be067a8','FOLDER','folder_home.png','admin','2014-09-29 00:00:00',NULL,NULL),('7492a872-520d-48ef-bfa3-518502d48d3b','CALENDAR','xfcalendar.png','admin','2014-09-29 00:00:00',NULL,NULL),('77c3c562-b85c-4a72-b8ac-c8bc94f58df3','EXCEL','excel.png','admin','2014-09-29 00:00:00',NULL,NULL),('793989f2-4818-49d6-ab2b-44bc9ee75b43','CHART_PIE','chart-pie.png','admin','2015-01-15 22:52:29',NULL,NULL),('80d813f6-0c91-4e0b-95ef-a17bcc02e8ce','GO_FIRST','stock_first.png','admin','2014-09-29 00:00:00',NULL,NULL),('81100942-0cda-43c1-84f9-034d39ac8c58','PERSON','stock_person.png','admin','2014-09-29 00:00:00',NULL,NULL),('81959e3c-7205-4fff-8b2b-6bad5770e8c1','IMPORT','document-import.png','admin','2014-09-29 00:00:00',NULL,NULL),('92f6e3b4-e757-414f-a512-9eb53d7d7b90','INTER_ARCHIVE','internet-archive.png','admin','2014-09-29 00:00:00',NULL,NULL),('966b2e28-2168-4172-ac2d-31b429336c1c','CHART_LINE','charts-line-chart-icon.png','admin','2015-01-16 14:05:52',NULL,NULL),('9eac6e4e-3796-4e0a-b9c4-fa337d01517b','APPLICATION_PDF','application-pdf.png','admin','2014-10-30 14:26:56',NULL,NULL),('9f3b3020-b76c-4af0-ad69-7a15c4e5d022','SHARED','emblem-shared.png','admin','2014-09-29 00:00:00',NULL,NULL),('9fb99e36-6ee9-4e1c-9629-8336ede133da','PEOPLE','emblem-people.png','admin','2014-09-29 00:00:00',NULL,NULL),('a788062a-22da-4734-b63d-087f2aee32b8','FULL_SCREEN','fullscreen.png','admin','2015-08-10 21:31:30',NULL,NULL),('a9fd2f30-4960-42fd-a175-a462c6f281fb','EXPORT','document-export.png','admin','2014-09-29 00:00:00',NULL,NULL),('ad1d0d5e-111d-4020-ade4-6bcf167fed0e','WWW','www.png','admin','2014-10-22 12:14:29',NULL,NULL),('b4f3acb8-72bc-49c1-a7a5-50106181facf','SYSTEM','system-run.png','admin','2014-09-29 00:00:00',NULL,NULL),('b5d46f00-8146-4e0e-8812-6e5b56843e5b','TWITTER','twitter.png','admin','2014-12-18 19:51:48',NULL,NULL),('bbff137e-7be8-4e8f-a1d2-db41444345d3','TEXT_SOURCE','text-x-source.png','admin','2014-10-23 10:08:48',NULL,NULL),('c739a407-82d2-4e2e-be95-3df87e280bfd','COMPUTER','computer.png','admin','2014-09-29 00:00:00',NULL,NULL),('cb219bbc-db12-4765-8922-ffd76773d907','STAR','star.png','admin','2014-09-29 00:00:00',NULL,NULL),('d1727475-258b-44f6-8f4a-f36cff81fb85','GO_LAST','stock_last.png','admin','2014-09-29 00:00:00',NULL,NULL),('d1fb350e-e2f6-439a-9934-65e1b26ada3e','GO_RIGHT','stock_right.png','admin','2014-09-29 00:00:00',NULL,NULL),('d24d1ac9-bdb3-40ef-95ef-9845b47b0182','G_APP_INSTALL','gnome-app-install.png','admin','2014-09-29 00:00:00',NULL,NULL),('e5d8eec7-2063-4806-b401-5d415dbd6c25','TEMPLATE','libreoffice-template.png','admin','2014-10-21 13:40:26',NULL,NULL),('ed735416-7d0e-4df0-aa1c-7787bbc5953e','VIEW_LIST','view-list-icons.png','admin','2014-11-28 08:54:06',NULL,NULL),('f05b7819-b9ee-4409-8d2f-b9e067280acd','USERS','system-users.png','admin','2014-09-29 00:00:00',NULL,NULL),('feddb360-0b6f-4ee2-8206-b961bcb2a76d','CHART_BAR','chart-graph-2d-1.png','admin','2015-01-16 13:27:00',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_icon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_jreport`
--

DROP TABLE IF EXISTS `tb_sys_jreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_jreport` (
  `OID` char(36) NOT NULL,
  `REPORT_ID` varchar(50) NOT NULL,
  `FILE` varchar(100) NOT NULL,
  `IS_COMPILE` varchar(50) NOT NULL DEFAULT 'N',
  `CONTENT` mediumblob NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`REPORT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_jreport`
--

LOCK TABLES `tb_sys_jreport` WRITE;
/*!40000 ALTER TABLE `tb_sys_jreport` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_jreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_jreport_param`
--

DROP TABLE IF EXISTS `tb_sys_jreport_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_jreport_param` (
  `OID` char(36) NOT NULL,
  `REPORT_ID` varchar(50) NOT NULL,
  `URL_PARAM` varchar(100) NOT NULL,
  `RPT_PARAM` varchar(100) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`REPORT_ID`,`RPT_PARAM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_jreport_param`
--

LOCK TABLES `tb_sys_jreport_param` WRITE;
/*!40000 ALTER TABLE `tb_sys_jreport_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_jreport_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_login_log`
--

DROP TABLE IF EXISTS `tb_sys_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_login_log` (
  `OID` char(36) NOT NULL,
  `USER` varchar(24) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`USER`),
  KEY `IDX_2` (`CDATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_login_log`
--

LOCK TABLES `tb_sys_login_log` WRITE;
/*!40000 ALTER TABLE `tb_sys_login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_mail_helper`
--

DROP TABLE IF EXISTS `tb_sys_mail_helper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_mail_helper` (
  `OID` char(36) NOT NULL,
  `MAIL_ID` varchar(17) NOT NULL,
  `SUBJECT` varchar(200) NOT NULL,
  `TEXT` blob,
  `MAIL_FROM` varchar(100) NOT NULL,
  `MAIL_TO` varchar(100) NOT NULL,
  `MAIL_CC` varchar(1000) DEFAULT NULL,
  `MAIL_BCC` varchar(1000) DEFAULT NULL,
  `SUCCESS_FLAG` varchar(1) NOT NULL DEFAULT 'N',
  `SUCCESS_TIME` datetime DEFAULT NULL,
  `RETAIN_FLAG` varchar(1) NOT NULL DEFAULT 'N',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`MAIL_ID`),
  KEY `IDX_1` (`MAIL_ID`),
  KEY `IDX_2` (`SUCCESS_FLAG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_mail_helper`
--

LOCK TABLES `tb_sys_mail_helper` WRITE;
/*!40000 ALTER TABLE `tb_sys_mail_helper` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_mail_helper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_menu`
--

DROP TABLE IF EXISTS `tb_sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_menu` (
  `OID` char(36) NOT NULL,
  `PROG_ID` varchar(50) NOT NULL,
  `PARENT_OID` char(36) NOT NULL,
  `ENABLE_FLAG` varchar(1) NOT NULL DEFAULT 'Y',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`PROG_ID`,`PARENT_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_menu`
--

LOCK TABLES `tb_sys_menu` WRITE;
/*!40000 ALTER TABLE `tb_sys_menu` DISABLE KEYS */;
INSERT INTO `tb_sys_menu` VALUES ('0fd3a30d-d000-4091-a6dd-8079b495977f','CORE_PROG003D0004Q','2eecc8a6-d234-4052-877b-bbab59839123','Y','admin','2017-06-03 08:42:14',NULL,NULL),('24720067-24c3-4527-a993-46710f3de43f','CORE_PROG001D0003Q','7ea68636-c93a-4669-ac42-dafc3770d20d','Y','admin','2017-06-05 09:55:00',NULL,NULL),('254a1a62-a47a-4aab-9c92-1f0fbafa6554','CORE_PROG003D0005Q','2eecc8a6-d234-4052-877b-bbab59839123','Y','admin','2017-06-03 08:42:14',NULL,NULL),('2b629a25-6998-4c77-ab09-4027c27208a6','CORE_PROG001D0006Q','7ea68636-c93a-4669-ac42-dafc3770d20d','Y','admin','2017-06-05 09:55:00',NULL,NULL),('2eecc8a6-d234-4052-877b-bbab59839123','CORE_PROG003D','00000000-0000-0000-0000-000000000000','Y','admin','2017-05-23 08:57:41',NULL,NULL),('392cce75-a34c-4f8d-a16f-4ed63b2d2495','CORE_PROG004D0001Q','5e055f61-bfc5-402c-93b4-f241dc17b00b','Y','admin','2017-06-03 14:23:22',NULL,NULL),('4429a2af-c00c-4202-9642-3525029a5438','CORE_PROG001D0004Q','7ea68636-c93a-4669-ac42-dafc3770d20d','Y','admin','2017-06-05 09:55:00',NULL,NULL),('4589612b-077e-4b11-b192-01ca621391ae','CORE_PROG003D0002Q','2eecc8a6-d234-4052-877b-bbab59839123','Y','admin','2017-06-03 08:42:13',NULL,NULL),('4a8ba269-cd8d-490d-a818-2fb6b1dd31d0','CORE_PROG001D0007Q','7ea68636-c93a-4669-ac42-dafc3770d20d','Y','admin','2017-06-05 09:55:00',NULL,NULL),('4bd4d202-5feb-495b-8c8c-ec6b7f5b8041','CORE_PROG002D0002Q','79e1cf24-2522-4cdf-abcc-6455b47d545b','Y','admin','2017-05-10 14:20:12',NULL,NULL),('4ebeab13-ada4-48c4-a75b-a5ff1c78c22e','CORE_PROG001D0005Q','7ea68636-c93a-4669-ac42-dafc3770d20d','Y','admin','2017-06-05 09:55:00',NULL,NULL),('5e055f61-bfc5-402c-93b4-f241dc17b00b','CORE_PROG004D','00000000-0000-0000-0000-000000000000','Y','admin','2017-06-03 14:23:17',NULL,NULL),('6c7ee268-14ad-408c-ac64-e6790d9f33e3','CORE_PROG004D0003Q','5e055f61-bfc5-402c-93b4-f241dc17b00b','Y','admin','2017-06-03 14:23:22',NULL,NULL),('6ea3d56f-4dd4-494d-a48b-975fd570f807','CORE_PROG003D0003Q','2eecc8a6-d234-4052-877b-bbab59839123','Y','admin','2017-06-03 08:42:13',NULL,NULL),('79e1cf24-2522-4cdf-abcc-6455b47d545b','CORE_PROG002D','00000000-0000-0000-0000-000000000000','Y','admin','2017-05-08 21:32:59',NULL,NULL),('7ea68636-c93a-4669-ac42-dafc3770d20d','CORE_PROG001D','00000000-0000-0000-0000-000000000000','Y','admin','2017-04-20 11:24:53',NULL,NULL),('95826121-fffa-4e08-b276-44285d6668fc','CORE_PROG003D0006Q','2eecc8a6-d234-4052-877b-bbab59839123','Y','admin','2017-06-03 08:42:14',NULL,NULL),('9972c249-2985-49ac-9b8b-f6c25c65fd4e','CORE_PROG002D0003Q','79e1cf24-2522-4cdf-abcc-6455b47d545b','Y','admin','2017-05-10 14:20:12',NULL,NULL),('ab061944-9352-4d7f-af50-a330646f0502','CORE_PROG003D0001Q','2eecc8a6-d234-4052-877b-bbab59839123','Y','admin','2017-06-03 08:42:13',NULL,NULL),('bfda3dfe-5857-4637-bf37-0d1ae00f32ce','CORE_PROG001D0002Q','7ea68636-c93a-4669-ac42-dafc3770d20d','Y','admin','2017-06-05 09:55:00',NULL,NULL),('c5349a26-6d6e-4d94-b817-82be6d14d5ed','CORE_PROG002D0001Q','79e1cf24-2522-4cdf-abcc-6455b47d545b','Y','admin','2017-05-10 14:20:12',NULL,NULL),('efb79ce7-360e-4a73-9c52-ee5f4bc8f4e8','CORE_PROG001D0001Q','7ea68636-c93a-4669-ac42-dafc3770d20d','Y','admin','2017-06-05 09:54:59',NULL,NULL),('f21319e4-c83b-486f-857e-44a1b2baa7b1','CORE_PROG004D0002Q','5e055f61-bfc5-402c-93b4-f241dc17b00b','Y','admin','2017-06-03 14:23:22',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_menu_role`
--

DROP TABLE IF EXISTS `tb_sys_menu_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_menu_role` (
  `OID` char(36) NOT NULL,
  `PROG_ID` varchar(50) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`PROG_ID`,`ROLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_menu_role`
--

LOCK TABLES `tb_sys_menu_role` WRITE;
/*!40000 ALTER TABLE `tb_sys_menu_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_menu_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_prog`
--

DROP TABLE IF EXISTS `tb_sys_prog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_prog` (
  `OID` char(36) NOT NULL,
  `PROG_ID` varchar(50) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `URL` varchar(255) NOT NULL,
  `EDIT_MODE` varchar(1) NOT NULL DEFAULT 'N',
  `IS_DIALOG` varchar(1) NOT NULL DEFAULT 'N',
  `DIALOG_W` int(4) NOT NULL DEFAULT '0',
  `DIALOG_H` int(4) NOT NULL DEFAULT '0',
  `PROG_SYSTEM` varchar(10) NOT NULL,
  `ITEM_TYPE` varchar(10) NOT NULL,
  `ICON` varchar(20) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`PROG_ID`),
  KEY `IDX_1` (`PROG_SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_prog`
--

LOCK TABLES `tb_sys_prog` WRITE;
/*!40000 ALTER TABLE `tb_sys_prog` DISABLE KEYS */;
INSERT INTO `tb_sys_prog` VALUES ('005463fd-a1f4-4e9f-89a7-bd86aedfcbdf','CORE_PROG003D0002E','ZC02 - Expression (Edit)','core.sysExpressionEdit.do','Y','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2017-05-24 11:38:45','admin','2018-03-27 11:16:32'),('186b1fb1-749f-4b6f-97d1-6b7fb8115345','CORE_PROG001D0004E','ZA04 - Template (Edit)','core.templateEdit.do','Y','N',0,0,'CORE','ITEM','TEMPLATE','admin','2017-05-12 10:40:10','admin','2018-03-27 11:10:54'),('1902b119-1719-40c9-899b-14d07a8035d7','CORE_PROG003D0004Q','ZC04 - BPM Resource','core.sysBpmResourceManagement.do','N','N',0,0,'CORE','ITEM','DIAGRAM','admin','2017-06-01 10:45:05','admin','2018-03-27 11:17:23'),('1b11c7eb-6133-48fb-87f0-dfbd098ce914','CORE_PROG001D0001E','ZA01 - System site (Edit)','core.sysSiteEdit.do','Y','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-02 00:00:00','admin','2018-03-27 11:09:43'),('1e393fe3-8bbc-482c-aa23-bbb22a1dbafb','CORE_PROG001D0005A','ZA05 - Report (Create)','core.sysReportCreate.do','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2017-05-18 09:55:46','admin','2018-03-27 11:11:19'),('22560527-90fb-4e5a-a89b-353d2aa1d433','CORE_PROG001D0005E','ZA05 - Report (Edit)','core.sysReportEdit.do','Y','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2017-05-18 09:56:27','admin','2018-03-27 11:11:26'),('23aeeb27-e4ed-498b-a233-526d58d12e88','CORE_PROG003D0006E','ZC06 - Expression Job (Edit)','core.sysExpressionJobEdit.do','Y','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-06-03 08:41:33','admin','2018-03-27 11:17:58'),('28a0b08c-d7df-45ce-90ee-202013895953','CORE_PROG003D0004E','ZC04 - BPM Resource (Edit)','core.sysBpmResourceEdit.do','Y','N',0,0,'CORE','ITEM','DIAGRAM','admin','2017-06-01 10:46:37','admin','2018-03-27 11:17:18'),('317d545c-8024-4de6-9bf9-4d9a2fcd0d0d','CORE_PROG003D0004S01Q','ZC04 - BPM Resource (Process)','core.sysBpmResourceProcessObjectList.do','Y','N',0,0,'CORE','ITEM','DIAGRAM','admin','2017-06-01 10:47:12','admin','2018-03-27 11:17:31'),('329bd834-3e71-47fe-b96f-dead8e2738bd','CORE_PROG003D0001A','ZC01 - WebService (Create)','core.sysWebServiceCreate.do','N','N',0,0,'CORE','ITEM','WWW','admin','2017-05-23 08:56:55','admin','2018-03-27 11:15:53'),('3630ee1b-6169-452f-821f-5c015dfb84d5','CORE_PROG001D','ZA. Config','','N','N',0,0,'CORE','FOLDER','PROPERTIES','admin','2014-10-02 00:00:00','admin','2018-03-27 11:09:07'),('36e73d8d-bb6b-485f-90e3-e46ac9a10ad6','CORE_PROG003D0005A','ZC05 - BPM Resource role (Create)','core.sysBpmResourceRoleCreate.do','N','N',0,0,'CORE','ITEM','PEOPLE','admin','2017-06-01 10:49:09','admin','2018-03-27 11:17:36'),('3862b6d0-0551-45d8-8dd1-cd988a5e8e50','CORE_PROG004D0002Q','ZD02 - Login log','core.sysLoginLogManagement.do','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2017-06-03 14:22:29','admin','2018-03-27 11:18:41'),('41fa29d8-3a53-4fbd-b2b1-cdbfd0729767','CORE_PROG001D0004Q','ZA04 - Template','core.templateManagement.do','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2017-05-12 10:36:41','admin','2018-03-27 11:11:04'),('44d0e6fc-51df-4dd2-b05a-17886b180c35','CORE_PROG003D0005Q','ZC05 - BPM Resource role','core.sysBpmResourceRoleManagement.do','N','N',0,0,'CORE','ITEM','PEOPLE','admin','2017-06-01 10:48:14','admin','2018-03-27 11:17:45'),('5599c3a9-f971-432a-a8e7-807d960ef196','CORE_PROG003D0003E','ZC03 - Bean support (Edit)','core.sysBeanSupportEdit.do','Y','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-05-25 10:39:32','admin','2018-03-27 11:16:48'),('5e082c7c-1730-4176-89c6-93e235707deb','CORE_PROG002D0001A','ZB01 - Role (Create)','core.roleCreate.do','N','N',0,0,'CORE','ITEM','PEOPLE','admin','2017-05-09 11:15:50','admin','2018-03-27 11:14:23'),('61aea7ff-7a42-4a92-9a0b-4a0dfe60858b','CORE_PROG004D0001Q','ZD01 - Event log','core.sysEventLogManagement.do','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2017-06-03 14:22:07','admin','2018-03-27 11:18:35'),('62780575-75e4-4a19-8b3a-849f9aafbc0e','CORE_PROG001D0006E','ZA06 - Web context bean (Edit)','core.sysCtxbeanEdit.do','Y','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-05-22 14:30:59','admin','2018-03-27 11:12:10'),('6a442973-0e0c-4a7a-d546-464f4ff5f7a9','CORE_PROG001D0003Q','ZA03 - Menu settings','core.menuSettingsManagement.do','N','N',0,0,'CORE','ITEM','FOLDER','admin','2014-10-02 00:00:00','admin','2018-03-27 11:10:38'),('6b210525-8975-4fb5-954c-fe349f66d3fe','CORE_PROG002D0001S01Q','ZB01 - Role (permission)','core.rolePermissionManagement.do','Y','N',0,0,'CORE','ITEM','IMPORTANT','admin','2017-05-09 14:32:47','admin','2018-03-27 11:14:49'),('6b481707-31ff-422b-a45c-38724d83772c','CORE_PROG999D9999Q','About','/pages/about.html','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2017-06-05 11:02:04',NULL,NULL),('72e6e0d1-1818-47d3-99f9-5134fb211b79','CORE_PROG002D','ZB. Role authority','','N','N',0,0,'CORE','FOLDER','SHARED','admin','2017-05-08 21:27:52','admin','2018-03-27 11:14:03'),('754325fb-be1a-4474-acbd-e06b8c0ef2a0','CORE_PROG001D0006A','ZA06 - Web context bean (Create)','core.sysCtxbeanCreate.do','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-05-22 14:30:36','admin','2018-03-27 11:12:03'),('7746f746-961f-44c2-9b66-fa43c0f49838','CORE_PROG001D0004S01Q','ZA04 - Template (Parameter)','core.templateParam.do','Y','N',0,0,'CORE','ITEM','TEMPLATE','admin','2017-05-12 10:42:04','admin','2018-03-27 11:11:11'),('7984332c-7963-4a02-9888-75a55b5af9f9','CORE_PROG003D0003S02Q','ZC03 - Bean support (Parameter)','core.sysBeanSupportExpressionParam.do','Y','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-05-25 10:42:08','admin','2018-03-27 11:17:08'),('7c1e2c5e-1d62-432a-8cbb-42f550694b4b','CORE_PROG003D0006Q','ZC06 - Expression Job','core.sysExpressionJobManagement.do','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-06-03 08:40:57','admin','2018-03-27 11:18:12'),('7d9ddc45-3eab-4f61-8c0a-d5505c0cc748','CORE_PROG001D0004A','ZA04 - Template (Create)','core.templateCreate.do','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2017-05-12 10:39:20','admin','2018-03-27 11:10:46'),('7e6dcf7e-959a-43cb-a267-2d0e3629c4df','CORE_PROG001D0006Q','ZA06 - Web context bean','core.sysCtxbeanManagement.do','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-05-22 14:29:11','admin','2018-03-27 11:12:17'),('8499957e-6da9-4160-c2ec-dfb7dbc202fe','CORE_PROG001D0002E','ZA02 - Program (Edit)','core.sysProgramEdit.do','Y','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-02 00:00:00','admin','2018-03-27 11:10:21'),('87ae5519-cab2-49d4-a3a4-eca6a219e2d8','CORE_PROG003D0001Q','ZC01 - WebService','core.sysWebServiceManagement.do','N','N',0,0,'CORE','ITEM','WWW','admin','2017-05-23 08:56:13','admin','2018-03-27 11:16:14'),('9050b7ba-c3ff-4483-a887-4979f969f6c8','CORE_PROG003D0004A','ZC04 - BPM Resource (Create)','core.sysBpmResourceCreate.do','N','N',0,0,'CORE','ITEM','DIAGRAM','admin','2017-06-01 10:45:55','admin','2018-03-27 11:17:14'),('91a2dad4-95d2-45c3-9fda-ae0d69ad6f22','CORE_PROG003D0006A','ZC06 - Expression Job (Create)','core.sysExpressionJobCreate.do','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-06-03 08:41:15','admin','2018-03-27 11:17:54'),('ac5bcfd0-4abd-11e4-916c-0800200c9a66','CORE_PROG001D0001A','ZA01 - System site (Create)','core.sysSiteCreate.do','N','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-02 00:00:00','admin','2018-03-27 11:09:33'),('b39159ad-0707-4515-b78d-e3fc72c53974','CORE_PROG002D0001E','ZB01 - Role (Edit)','core.roleEdit.do','Y','N',0,0,'CORE','ITEM','PEOPLE','admin','2017-05-09 12:11:53','admin','2018-03-27 11:14:30'),('b3f7654a-9b35-4b9d-8cb7-bc2557869a3b','CORE_PROG004D0003Q','ZD03 - Expression Job log','core.sysExpressionJobLogManagement.do','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2017-06-03 14:22:58','admin','2018-03-27 11:18:46'),('b419219f-3014-4589-b93d-46cf00c10c4b','CORE_PROG003D0005E','ZC05 - BPM Resource role (Edit)','core.sysBpmResourceRoleEdit.do','Y','N',0,0,'CORE','ITEM','PEOPLE','admin','2017-06-01 10:49:32','admin','2018-03-27 11:17:40'),('b6b89559-6864-46ab-9ca9-0992dcf238f1','CORE_PROG001D0001Q','ZA01 - System site','core.sysSiteManagement.do','N','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-02 00:00:00','admin','2018-03-27 11:10:01'),('b978f706-4c5f-40f8-83b1-395492f141d4','CORE_PROG002D0001Q','ZB01 - Role','core.roleManagement.do','N','N',0,0,'CORE','ITEM','PEOPLE','admin','2017-05-08 21:32:50','admin','2018-03-27 11:14:36'),('bc97b0d4-516a-4f26-a79a-88ecc65bfe50','CORE_PROG003D0003Q','ZC03 - Bean support','core.sysBeanSupportManagement.do','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-05-25 10:38:42','admin','2018-03-27 11:16:53'),('bffce168-6b55-47ed-9fa3-d37666a1e9c7','CORE_PROG003D0003A','ZC03 - Bean support (Create)','core.sysBeanSupportCreate.do','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-05-25 10:39:07','admin','2018-03-27 11:16:42'),('c96ebde8-7044-4b05-a155-68a0c2605619','CORE_PROG002D0003Q','ZB03 - Role for menu','core.menuRoleManagement.do','N','N',0,0,'CORE','ITEM','FOLDER','admin','2017-05-08 21:37:01','admin','2018-03-27 11:15:12'),('d1254ea1-0f2d-4c49-9e64-d24a8e76d6de','CORE_PROG003D0001E','ZC01 - WebService (Edit)','core.sysWebServiceEdit.do','Y','N',0,0,'CORE','ITEM','WWW','admin','2017-05-23 08:57:23','admin','2018-03-27 11:16:02'),('da0e70df-29b1-4a2e-890c-eab723c86ed6','CORE_PROG002D0001S02Q','ZB01 - Role (copy)','core.roleCopyManagement.do','Y','Y',650,600,'CORE','ITEM','PEOPLE','admin','2017-05-09 20:56:50','admin','2018-03-27 11:14:56'),('da7d969a-5efb-4e84-9eab-4fdae236f28c','CORE_PROG002D0002Q','ZB02 - User role','core.userRoleManagement.do','N','N',0,0,'CORE','ITEM','PERSON','admin','2017-05-08 21:34:39','admin','2018-03-27 11:15:06'),('dda67b1d-e3a2-4534-835a-c62d9e8421f3','CORE_PROG001D0005S01Q','ZA05 - Report (Parameter)','core.sysReportParam.do','Y','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2017-05-18 09:57:26','admin','2018-03-27 11:11:45'),('e32b9329-bb38-46d7-8552-2307bac77724','CORE_PROG001D0002A','ZA02 - Program (Create)','core.sysProgramCreate.do','N','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-02 00:00:00','admin','2018-03-27 11:10:13'),('e42f4e11-ddf2-49f5-9267-7cfed6ab1d02','CORE_PROG001D0005S02Q','ZA05 - Report (Preview)','core.sysReportPreview.do','Y','Y',600,600,'CORE','ITEM','APPLICATION_PDF','admin','2017-05-18 10:00:04','admin','2018-03-27 11:11:52'),('e4e691f8-2cd8-444d-b412-ccdbbec029a1','CORE_PROG003D','ZC. Service','','N','N',0,0,'CORE','FOLDER','DIAGRAM','admin','2017-05-23 08:53:46','admin','2018-03-27 11:15:37'),('e86dbb1b-6870-4827-8039-72f5e15fa4f2','CORE_PROG004D','ZD. Log','','N','N',0,0,'CORE','FOLDER','PROPERTIES','admin','2017-06-03 14:21:03','admin','2018-03-27 11:18:27'),('eb6e199f-c853-4fbf-acf3-0c9c77ba9953','CORE_PROG001D0002Q','ZA02 - Program','core.sysProgramManagement.do','N','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-02 00:00:00','admin','2018-03-27 11:10:28'),('eb786ffd-c7d1-4631-aed2-4d9d7368eb13','CORE_PROG001D0005Q','ZA05 - Report','core.sysReportManagement.do','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2017-05-18 09:54:35','admin','2018-03-27 11:11:37'),('edf4ab8c-df86-4ef0-91f6-c2ddcc6e8f7d','CORE_PROG003D0002A','ZC02 - Expression (Create)','core.sysExpressionCreate.do','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2017-05-24 11:38:27','admin','2018-03-27 11:16:26'),('ef6963fe-171e-4ebe-ae24-aef7388fccce','CORE_PROG001D0007Q','ZA07 - Setting','core.sysSettingManagement.do','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2017-06-05 09:54:48','admin','2018-03-27 11:12:31'),('fa1e4078-638f-41e6-82d4-77ef92e76374','CORE_PROG003D0002Q','ZC02 - Expression','core.sysExpressionManagement.do','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2017-05-24 11:38:04','admin','2018-03-27 11:16:37'),('fee6533d-da1c-444b-aec9-6d4f81fa051d','CORE_PROG003D0003S01Q','ZC03 - Bean support (Expression)','core.sysBeanSupportExpression.do','Y','N',0,0,'CORE','ITEM','SYSTEM','admin','2017-05-25 10:41:46','admin','2018-03-27 11:16:57');
/*!40000 ALTER TABLE `tb_sys_prog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_qfield_log`
--

DROP TABLE IF EXISTS `tb_sys_qfield_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_qfield_log` (
  `OID` char(36) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `PROG_ID` varchar(50) NOT NULL,
  `METHOD_NAME` varchar(255) NOT NULL,
  `FIELD_NAME` varchar(255) NOT NULL,
  `FIELD_VALUE` varchar(500) DEFAULT NULL,
  `QUERY_USER_ID` varchar(24) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`SYSTEM`,`PROG_ID`),
  KEY `IDX_2` (`QUERY_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_qfield_log`
--

LOCK TABLES `tb_sys_qfield_log` WRITE;
/*!40000 ALTER TABLE `tb_sys_qfield_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_qfield_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_template`
--

DROP TABLE IF EXISTS `tb_sys_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_template` (
  `OID` char(36) NOT NULL,
  `TEMPLATE_ID` varchar(10) NOT NULL,
  `TITLE` varchar(200) NOT NULL,
  `MESSAGE` varchar(4000) NOT NULL,
  `DESCRIPTION` varchar(200) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_template`
--

LOCK TABLES `tb_sys_template` WRITE;
/*!40000 ALTER TABLE `tb_sys_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_template_param`
--

DROP TABLE IF EXISTS `tb_sys_template_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_template_param` (
  `OID` char(36) NOT NULL,
  `TEMPLATE_ID` varchar(10) NOT NULL,
  `IS_TITLE` varchar(1) NOT NULL DEFAULT 'N',
  `TEMPLATE_VAR` varchar(100) NOT NULL,
  `OBJECT_VAR` varchar(100) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TEMPLATE_ID`,`TEMPLATE_VAR`,`IS_TITLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_template_param`
--

LOCK TABLES `tb_sys_template_param` WRITE;
/*!40000 ALTER TABLE `tb_sys_template_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_template_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_upload`
--

DROP TABLE IF EXISTS `tb_sys_upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_upload` (
  `OID` char(36) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `SUB_DIR` varchar(4) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `FILE_NAME` varchar(50) NOT NULL,
  `SHOW_NAME` varchar(255) NOT NULL,
  `IS_FILE` varchar(1) NOT NULL DEFAULT 'Y',
  `CONTENT` mediumblob,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`SYSTEM`,`TYPE`,`SUB_DIR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_upload`
--

LOCK TABLES `tb_sys_upload` WRITE;
/*!40000 ALTER TABLE `tb_sys_upload` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_upload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_upload_tran`
--

DROP TABLE IF EXISTS `tb_sys_upload_tran`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_upload_tran` (
  `OID` char(36) NOT NULL,
  `TRAN_ID` varchar(10) NOT NULL,
  `SEGM_MODE` varchar(10) NOT NULL DEFAULT 'BYTE',
  `SEGM_SYMBOL` varchar(1) NOT NULL DEFAULT ',',
  `ENCODING` varchar(10) NOT NULL,
  `EXPR_TYPE` varchar(10) NOT NULL,
  `HELP_EXPRESSION` varchar(8000) NOT NULL,
  `BEGIN_LEN` int(1) NOT NULL DEFAULT '0',
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TRAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_upload_tran`
--

LOCK TABLES `tb_sys_upload_tran` WRITE;
/*!40000 ALTER TABLE `tb_sys_upload_tran` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_upload_tran` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_upload_tran_segm`
--

DROP TABLE IF EXISTS `tb_sys_upload_tran_segm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_upload_tran_segm` (
  `OID` char(36) NOT NULL,
  `TRAN_ID` varchar(10) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `BEGIN` int(4) NOT NULL,
  `END` int(4) NOT NULL,
  `CUSERID` varchar(50) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TRAN_ID`,`NAME`),
  KEY `IDX_1` (`TRAN_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_upload_tran_segm`
--

LOCK TABLES `tb_sys_upload_tran_segm` WRITE;
/*!40000 ALTER TABLE `tb_sys_upload_tran_segm` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_upload_tran_segm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_usess`
--

DROP TABLE IF EXISTS `tb_sys_usess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_usess` (
  `OID` char(36) NOT NULL,
  `SESSION_ID` varchar(64) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `CURRENT_ID` varchar(36) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`,`SESSION_ID`),
  UNIQUE KEY `UK_1` (`ACCOUNT`,`SESSION_ID`),
  KEY `IDX_1` (`CURRENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_usess`
--

LOCK TABLES `tb_sys_usess` WRITE;
/*!40000 ALTER TABLE `tb_sys_usess` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_usess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ws_config`
--

DROP TABLE IF EXISTS `tb_sys_ws_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ws_config` (
  `OID` char(36) NOT NULL,
  `WS_ID` varchar(10) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `TYPE` varchar(4) NOT NULL,
  `BEAN_ID` varchar(255) NOT NULL,
  `PUBLISH_ADDRESS` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`WS_ID`),
  KEY `IDX_1` (`SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ws_config`
--

LOCK TABLES `tb_sys_ws_config` WRITE;
/*!40000 ALTER TABLE `tb_sys_ws_config` DISABLE KEYS */;
INSERT INTO `tb_sys_ws_config` VALUES ('30a1371a-123f-465d-95df-8c295205a35d','CORE-WS004','CORE','REST','core.webservice.ManualJobService','','Expression Job manual service!','admin','2017-06-02 12:26:20',NULL,NULL),('3b01608b-6c8d-4a11-a548-324fb923077e','CORE-WS002','CORE','REST','core.webservice.HelloService','','for TEST hello REST!','admin','2017-05-23 13:51:41',NULL,NULL),('674ba477-4c70-4a3a-86b6-0ba08c608c58','CORE-WS001','CORE','SOAP','core.webservice.HelloService','/hello','for TEST hello SOAP!','admin','2017-05-23 13:51:09',NULL,NULL),('e219c990-223f-43e1-9a56-35b3f4625fc2','CORE-WS003','CORE','SOAP','core.webservice.SendMailService','/mail','for TEST mail SOAP!','admin','2017-05-23 13:52:59',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_ws_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ws_service`
--

DROP TABLE IF EXISTS `tb_sys_ws_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ws_service` (
  `OID` char(36) NOT NULL,
  `ID` varchar(10) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `BEAN_ID` varchar(255) NOT NULL,
  `WSDL_ADDRESS` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`,`SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ws_service`
--

LOCK TABLES `tb_sys_ws_service` WRITE;
/*!40000 ALTER TABLE `tb_sys_ws_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_ws_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_role`
--

DROP TABLE IF EXISTS `tb_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_role` (
  `OID` char(36) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(50) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ROLE`,`ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_role`
--

LOCK TABLES `tb_user_role` WRITE;
/*!40000 ALTER TABLE `tb_user_role` DISABLE KEYS */;
INSERT INTO `tb_user_role` VALUES ('1c62cf70-ca6b-4243-8aa9-49b555024c45','COMMON01','steven','','admin','2017-05-10 14:19:58',NULL,NULL),('9243c7de-43b1-46ef-ac4b-2620697f319e','admin','admin','Administrator','admin','2014-09-23 00:00:00',NULL,NULL),('bd7bf78c-d84b-4524-8273-273f883d30b5','COMMON01','tester','','admin','2017-05-10 11:01:50',NULL,NULL),('da0c0462-4bf7-417b-99da-fc2e378a5ccc','COMMON01','tiffany','','admin','2017-05-10 11:01:43',NULL,NULL);
/*!40000 ALTER TABLE `tb_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-16 19:00:40
