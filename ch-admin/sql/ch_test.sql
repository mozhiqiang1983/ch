/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.6.21 : Database - ch_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ch_test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ch_test`;

/*Table structure for table `online` */

DROP TABLE IF EXISTS `online`;

CREATE TABLE `online` (
  `ID` varchar(36) NOT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `LOGINNAME` varchar(100) DEFAULT NULL,
  `TYPE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `online` */

insert  into `online`(`ID`,`CREATEDATETIME`,`IP`,`LOGINNAME`,`TYPE`) values ('02486014-5625-441f-b190-f885e43bd3b0','2014-12-31 00:05:16','本地','admin','1'),('04e3b2fd-d6c8-4d2e-b248-f79c603eb02e','2014-12-31 00:32:55','本地','admin','1'),('0527fb0c-5107-4c5f-bb78-c7fbb4712211','2014-12-31 03:30:02','本地','admin','1'),('0751048b-7dde-45b1-8ff8-495ad6c05e42','2014-12-31 06:47:44','本地','admin','0'),('08f91d93-9271-4999-96a1-58144e91627a','2014-12-30 23:19:56','本地','admin','1'),('13683a82-8270-4428-baec-84cba782ae13','2014-12-30 06:56:51','本地','admin','0'),('15bfc1bd-ef3e-45c3-9230-a863b023c9a5','2014-12-31 03:35:47','本地','admin','1'),('1661f9f7-256e-408b-bc68-ebf646101ee8','2014-12-31 19:17:19','本地','admin','0'),('21a3cc74-dd0f-4e93-b6c6-4b7a1e0589d0','2014-12-30 22:56:17','本地','admin','1'),('22ceebf0-36c0-4939-9c09-f68eae9697bd','2014-12-30 23:22:51','本地','admin','1'),('2b77bda6-025e-4069-b05d-f9585d5f37af','2014-12-31 19:06:53','本地','admin','1'),('2e9a1730-3220-437c-ae9e-0784ae5b58a1','2014-12-31 02:55:46','本地','admin','1'),('4ef3ad1b-2629-4a1a-b6f1-511d34f45ebe','2014-12-30 23:42:38','本地','admin','1'),('57bb2278-edf5-428b-be88-d9780c8d8489','2014-12-31 19:03:55','本地','admin','1'),('58b4bf2d-f585-4f57-814c-7c257ab21d47','2014-12-31 19:25:24','本地','admin','1'),('5cf230af-6817-455c-b731-89fa0df6b2a9','2014-12-31 19:09:55','本地','admin','1'),('6dcbb5d5-ebfd-48da-a4ef-ea8519e7fbce','2014-12-30 23:16:36','本地','admin','1'),('7007957a-014d-42b1-a655-0d9c8f5b352d','2014-12-31 19:20:06','本地','admin','1'),('78deef2e-9d09-42af-9dc8-ac4592eb7dd7','2014-12-31 18:31:04','本地','admin','1'),('899d36f5-97a5-4cc0-8c79-392a3070bf4c','2014-12-30 23:50:22','本地','admin','1'),('8b40159d-c344-4e64-bc8b-fdd44f7776a8','2014-12-31 19:18:10','本地','admin','1'),('94d1b8f2-4ebe-4e1e-a337-a62aa1f17c0b','2014-12-30 23:54:39','本地','admin','1'),('9d8f77bb-159a-4d5c-a388-b38c132211d0','2014-12-31 03:10:52','本地','admin','0'),('a387da4e-aee6-41bd-a9f1-b3c6aa6659d5','2014-12-31 00:52:40','本地','admin','1'),('a54dc1b6-0f41-4b6e-a43e-6fe996f3cfc2','2014-12-31 19:41:41','本地','admin','0'),('b75927fb-b788-4142-b0fc-d0197c1b3069','2014-12-30 03:49:40','本地','admin','1'),('bc71ece8-28e9-40f4-bde2-ab52e0fb639e','2014-12-31 18:16:11','本地','admin','1'),('bf37901e-2e70-4752-b99f-8b24ddeef6e0','2014-12-31 01:16:06','本地','admin','1'),('c3e7bca0-1636-4f64-a51d-f3d836557834','2014-12-31 18:50:07','本地','admin','1'),('c659c9b1-574f-494f-a667-276062aea1eb','2014-12-31 19:14:07','本地','admin','1'),('d9cd4759-b3ea-4705-b3c0-3bcfb4fbf637','2014-12-31 02:26:41','本地','admin','1'),('db6838fa-2a0d-4507-a2fa-9db97d1ba64e','2014-12-31 19:24:52','本地','admin','1'),('e579f21d-bbc6-4bb6-976a-a66c1a58cf16','2014-12-31 02:31:35','本地','admin','1'),('e64bf7f1-94b0-4962-b10b-9409e1525e16','2014-12-31 00:27:46','本地','admin','1');

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `ID` varchar(36) NOT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `ICONCLS` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `SEQ` int(11) DEFAULT NULL,
  `TARGET` varchar(100) DEFAULT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `RESOURCE_ID` varchar(36) DEFAULT NULL,
  `RESOURCETYPE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_d87p0u38tfkcd35oaf004k5vg` (`RESOURCE_ID`),
  KEY `FK_42om89ea3u9fv7pmhstdnw1mw` (`RESOURCETYPE_ID`),
  CONSTRAINT `FK_42om89ea3u9fv7pmhstdnw1mw` FOREIGN KEY (`RESOURCETYPE_ID`) REFERENCES `resourcetype` (`ID`),
  CONSTRAINT `FK_d87p0u38tfkcd35oaf004k5vg` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `resource` */

insert  into `resource`(`ID`,`CREATEDATETIME`,`DESCRIPTION`,`ICONCLS`,`NAME`,`SEQ`,`TARGET`,`UPDATEDATETIME`,`URL`,`RESOURCE_ID`,`RESOURCETYPE_ID`) values ('jsbj','2014-12-30 03:49:33','编辑角色','ext-icon-bullet_wrench','编辑角色',2,'','2014-12-30 03:49:33','/auth/role/update','jsgl','1'),('jsck','2014-12-30 03:49:33','查看角色','ext-icon-bullet_wrench','查看角色',4,NULL,'2014-12-31 03:41:56','/auth/role/get','jsgl','1'),('jsgl','2014-12-30 03:49:33','管理系统中用户的角色','ext-icon-tux','角色管理',2,'','2014-12-30 03:49:33','/page/auth/role/list','xtgl','0'),('jslb','2014-12-30 03:49:33','查询角色列表','ext-icon-bullet_wrench','角色列表',0,'','2014-12-30 03:49:33','/auth/role/grid','jsgl','1'),('jssc','2014-12-30 03:49:33','删除角色','ext-icon-bullet_wrench','删除角色',3,'','2014-12-30 03:49:33','/auth/role/delete','jsgl','1'),('jssq','2014-12-30 03:49:33','角色授权','ext-icon-bullet_wrench','角色授权',5,'','2014-12-30 03:49:33','/auth/role/grant','jsgl','1'),('jstj','2014-12-30 03:49:33','添加角色','ext-icon-bullet_wrench','添加角色',1,'','2014-12-30 03:49:33','/auth/role/save','jsgl','1'),('xtgl','2014-12-30 03:49:33','管理系统的资源、角色、机构、用户等信息','ext-icon-application_view_tile','系统管理',0,'','2014-12-30 03:49:33','/welcome.jsp',NULL,'0'),('yhbj','2014-12-30 03:49:33','编辑用户','ext-icon-bullet_wrench','编辑用户',2,'','2014-12-30 03:49:33','/auth/user/update','yhgl','1'),('yhck','2014-12-30 03:49:33','查看用户','ext-icon-bullet_wrench','查看用户',4,'','2014-12-30 03:49:33','/auth/user/get','yhgl','1'),('yhgl','2014-12-30 03:49:33','管理系统中用户的用户','ext-icon-user_suit','用户管理',4,'','2014-12-30 03:49:33','/page/auth/user/list','xtgl','0'),('yhjs','2014-12-30 03:49:33','编辑用户角色','ext-icon-bullet_wrench','用户角色',5,'','2014-12-30 03:49:33','/auth/user/grantRole','yhgl','1'),('yhlb','2014-12-30 03:49:33','查询用户列表','ext-icon-bullet_wrench','用户列表',0,'','2014-12-30 03:49:33','/auth/user/grid','yhgl','1'),('yhsc','2014-12-30 03:49:33','删除用户','ext-icon-bullet_wrench','删除用户',3,'','2014-12-30 03:49:33','/auth/user/delete','yhgl','1'),('yhtj','2014-12-30 03:49:33','添加用户','ext-icon-bullet_wrench','添加用户',1,'','2014-12-30 03:49:33','/auth/user/save','yhgl','1'),('zybj','2014-12-30 03:49:33','编辑资源','ext-icon-bullet_wrench','编辑资源',2,'','2014-12-30 03:49:33','/auth/resource/update','zygl','1'),('zyck','2014-12-30 03:49:33','查看资源','ext-icon-bullet_wrench','查看资源',4,'','2014-12-30 03:49:33','/auth/resource/get','zygl','1'),('zygl','2014-12-30 03:49:33','管理系统的资源','ext-icon-newspaper_link','资源管理',1,'','2014-12-30 03:49:33','/page/auth/resource/list','xtgl','0'),('zylb','2014-12-30 03:49:33','查询资源','ext-icon-bullet_wrench','资源列表',0,'','2014-12-30 03:49:33','/auth/resource/treeGrid','zygl','1'),('zysc','2014-12-30 03:49:33','删除资源','ext-icon-bullet_wrench','删除资源',3,'','2014-12-30 03:49:33','/auth/resource/delete','zygl','1'),('zytj','2014-12-30 03:49:33','添加资源','ext-icon-bullet_wrench','添加资源',1,'','2014-12-30 03:49:33','/auth/resource/save','zygl','1');

/*Table structure for table `resourcetype` */

DROP TABLE IF EXISTS `resourcetype`;

CREATE TABLE `resourcetype` (
  `ID` varchar(36) NOT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `resourcetype` */

insert  into `resourcetype`(`ID`,`CREATEDATETIME`,`DESCRIPTION`,`NAME`,`UPDATEDATETIME`) values ('0','2014-12-30 03:49:33','菜单类型会显示在系统首页左侧菜单中','菜单','2014-12-30 03:49:33'),('1','2014-12-30 03:49:33','功能类型不会显示在系统首页左侧菜单中','功能','2014-12-30 03:49:33');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ID` varchar(36) NOT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `ICONCLS` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `SEQ` int(11) DEFAULT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`ID`,`CREATEDATETIME`,`DESCRIPTION`,`ICONCLS`,`NAME`,`SEQ`,`UPDATEDATETIME`) values ('0','2014-12-30 03:49:33','拥有系统所有权限',NULL,'超管',0,'2014-12-31 03:44:22'),('1','2014-12-30 03:49:33','只有查看权限',NULL,'Guest',1,'2014-12-31 03:44:05'),('67159187-d9d2-48f5-9553-9b8adf062436','2014-12-31 19:26:26','44',NULL,'44',100,'2014-12-31 19:26:26');

/*Table structure for table `role_resource` */

DROP TABLE IF EXISTS `role_resource`;

CREATE TABLE `role_resource` (
  `ROLE_ID` varchar(36) NOT NULL,
  `RESOURCE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`RESOURCE_ID`,`ROLE_ID`),
  KEY `FK_eqr4p02sn1k7vmqm098qj9qs6` (`RESOURCE_ID`),
  KEY `FK_ibofjsb1jt3hktpl47r1i64v2` (`ROLE_ID`),
  CONSTRAINT `FK_eqr4p02sn1k7vmqm098qj9qs6` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `resource` (`ID`),
  CONSTRAINT `FK_ibofjsb1jt3hktpl47r1i64v2` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_resource` */

insert  into `role_resource`(`ROLE_ID`,`RESOURCE_ID`) values ('0','jsbj'),('0','jsck'),('1','jsck'),('0','jsgl'),('1','jsgl'),('0','jslb'),('1','jslb'),('0','jssc'),('0','jssq'),('0','jstj'),('0','xtgl'),('1','xtgl'),('0','yhbj'),('0','yhck'),('1','yhck'),('0','yhgl'),('1','yhgl'),('0','yhjs'),('0','yhlb'),('1','yhlb'),('0','yhsc'),('0','yhtj'),('0','zybj'),('0','zyck'),('1','zyck'),('0','zygl'),('1','zygl'),('0','zylb'),('1','zylb'),('0','zysc'),('0','zytj');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `ID` varchar(36) NOT NULL,
  `AGE` int(11) DEFAULT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `LOGINNAME` varchar(100) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `PHOTO` varchar(200) DEFAULT NULL,
  `PWD` varchar(100) DEFAULT NULL,
  `SEX` varchar(1) DEFAULT NULL,
  `UPDATEDATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`ID`,`AGE`,`CREATEDATETIME`,`LOGINNAME`,`NAME`,`PHOTO`,`PWD`,`SEX`,`UPDATEDATETIME`) values ('0',30,'2014-12-30 03:49:33','admin','admin',NULL,'e10adc3949ba59abbe56e057f20f883e','1','2014-12-30 03:49:33'),('1',30,'2014-12-30 03:49:33','guest','guest',NULL,'e10adc3949ba59abbe56e057f20f883e','1','2014-12-30 03:49:33');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `USER_ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `FK_oqmdk7xj0ainhxpvi79fkaq3y` (`ROLE_ID`),
  KEY `FK_j2j8kpywaghe8i36swcxv8784` (`USER_ID`),
  CONSTRAINT `FK_j2j8kpywaghe8i36swcxv8784` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`),
  CONSTRAINT `FK_oqmdk7xj0ainhxpvi79fkaq3y` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`USER_ID`,`ROLE_ID`) values ('0','0'),('0','1'),('1','1'),('0','67159187-d9d2-48f5-9553-9b8adf062436');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
