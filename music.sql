-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 34.124.163.67    Database: music_db
-- ------------------------------------------------------
-- Server version	5.7.36-google

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
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `release_date` date DEFAULT NULL,
  `total_listen` int(11) DEFAULT '0',
  `total_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `artist_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmwc4fyyxb6tfi0qba26gcf8s1` (`artist_id`),
  CONSTRAINT `FKmwc4fyyxb6tfi0qba26gcf8s1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (1,'2022-03-07 00:03:12',_binary '\0','2022-03-23 14:10:10','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/25_image_cover1646669111319?generation=1646669111943227&alt=media','25','2020-12-16',112,'0h 43m 16s',1),(2,'2022-03-07 00:09:50',_binary '\0','2022-03-25 03:11:22','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/seesingshare2_image_cover1646669099579?generation=1646669099522473&alt=media','SEE SING SHARE 2','2020-11-13',79,'0h 54m 26s',2),(3,'2022-03-07 00:31:46',_binary '\0','2022-03-23 13:56:14','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/buocquanhau_image_cover1646669087834?generation=1646669087850712&alt=media','B?????c Qua Nhau','2021-11-11',39,'0h 8m 56s',3),(4,'2022-03-07 00:48:46',_binary '\0','2022-03-23 13:56:51','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/cuchillthoi_image_cover1646669054721?generation=1646669059376939&alt=media','C??? Chill Th??i','2020-10-07',19,'0h 4m 30s',4),(5,'2022-03-17 16:01:48',_binary '\0','2022-03-28 02:54:22','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/hospitalplaylistseason2ost_image_cover1647532907555?generation=1647532908308364&alt=media','Hospital Playlist Season 2 OST','2021-09-24',16,'',10),(6,'2022-03-17 16:20:20',_binary '\0','2022-03-28 02:31:14','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/3_image_cover1647534019032?generation=1647534019679563&alt=media','3','2019-01-06',24,'',13),(7,'2022-03-17 16:26:06',_binary '\0','2022-03-28 03:43:16','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media','Qua Khung C???a S???','2021-05-05',74,'',4),(8,'2022-03-23 17:14:44',_binary '\0','2022-03-28 03:06:21','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/loinho_image_cover1648055683139?generation=1648055683688935&alt=media','L???i nh???','2019-10-21',13,'',14),(9,'2022-03-23 17:20:19',_binary '\0','2022-03-28 03:02:08','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/bainaychillphet_image_cover1648056018729?generation=1648056019320268&alt=media','B??i N??y Chill Ph???t','2019-05-21',3,'',14),(10,'2022-03-23 17:25:17',_binary '\0','2022-03-28 02:57:32','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/lamgiphaihot_image_cover1648056315935?generation=1648056316613672&alt=media','L??m G?? Ph???i H???t','2020-01-05',4,'',16),(11,'2022-03-25 11:49:57',_binary '\0','2022-03-25 11:49:57','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/thattinhcucmanh_image_cover1648208995806?generation=1648208996475628&alt=media','Th???t T??nh C???c M???nh','2022-03-25',0,'',18);
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birthday` date DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `gender` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nationality` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (1,'2022-03-06 23:56:24',_binary '\0','2022-03-07 23:03:19','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/hoangdung_image_cover1646668996937?generation=1646668997027664&alt=media','1995-11-04','Nguy???n Ho??ng D??ng, hay c??n ???????c bi???t ?????n v???i ngh??? danh Ho??ng D??ng, l?? m???t nam ca s??, nh???c s?? t???i Vi???t Nam. Anh t???ng ?????t danh hi???u ?? qu??n cu???c thi Gi???ng H??t Vi???t n??m 2015 v?? l???t top 10 B??i h??t hay nh???t n??m 2016.','Male','Ho??ng D??ng','Viet Nam'),(2,'2022-03-06 23:58:10',_binary '\0','2022-03-07 23:03:03','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/haanhtuan_image_cover1646668980562?generation=1646668981605478&alt=media','1984-12-17','H?? Anh Tu???n l?? m???t nam ca s?? ng?????i Vi???t Nam. Anh t???ng gi??nh ???????c 13 ????? c??? v?? ??o???t gi???i 2 l???n cho gi???i C???ng hi???n. Anh b???t ?????u n???i ti???ng t??? khi l???t v??o top 3 cu???c thi Sao Mai ??i???m h???n.','Male','H?? Anh Tu???n','Viet Nam'),(3,'2022-03-07 00:29:34',_binary '\0','2022-03-07 23:02:51','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/vu_image_cover1646668969184?generation=1646668969229798&alt=media','1995-10-03','V??, ???????c vi???t c??ch ??i???u l?? V?? t??n ?????y ????? l?? Ho??ng Th??i V?? sinh t???i H?? N???i, l?? ca s?? ki??m s??ng t??c nh???c ng?????i Vi???t Nam. Sinh ra trong gia ????nh c?? b??? l?? qu??n nh??n v?? m??? l?? gi??o vi??n, V?? th?????ng ????ng t???i c??c s??ng t??c c???a m??nh tr??n Soundcloud. Th??? lo???i c???a anh theo ??u???i l?? nh???c indie pop, acoustic, rock... .','Male','V??','Viet Nam'),(4,'2022-03-07 00:42:26',_binary '\0','2022-03-07 23:02:15','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/chillies_image_cover1646668932271?generation=1646668933317201&alt=media',NULL,'Chillies l?? m???t ban nh???c pop rock ng?????i Vi???t Nam g???m 5 th??nh vi??n: Khang, Nh??m, Ph?????c, ?????c v?? Ph??. Nh??m ???????c th??nh l???p v??o th??ng 10 n??m 2018 v?? ???????c qu???n l?? b???i h??ng ????a Warner Music Vietnam.','','Chillies','Viet Nam'),(5,'2022-03-07 00:43:50',_binary '\0','2022-03-07 23:01:56','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/sunihalinh_image_cover1646668913973?generation=1646668914253058&alt=media','1993-09-06','Ng?? ?????ng Thu Giang, th?????ng ???????c bi???t ?????n v???i ngh??? danh Suni H??? Linh l?? m???t n??? ca s??, v?? c??ng ng?????i Vi???t Nam. C?? ???? gi??nh gi???i ?? qu??n trong cu???c thi Kpop Star Hunt season 2, gi???i ba trong cu???c thi Ng??i sao Vi???t.','Female','Suni H??? Linh','Viet Nam'),(6,'2022-03-07 00:45:06',_binary '\0','2022-03-07 23:01:44','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/rhymastic_image_cover1646668902445?generation=1646668902361163&alt=media','1991-04-08','V?? ?????c Thi???n, ???????c bi???t ?????n v???i ngh??? danh Rhymastic ho???c Young Crizzal, l?? m???t rapper, nh???c s??, ca s??, nh?? s???n xu???t ??m nh???c ng?????i Vi???t Nam v?? l?? th??nh vi??n c???a nh??m SpaceSpeakers','Male','Rhymastic','Viet Nam'),(7,'2022-03-07 20:48:51',_binary '\0','2022-03-07 23:01:21','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/coldplay_image_cover1646668879050?generation=1646668879158844&alt=media',NULL,'Coldplay l?? m???t ban nh???c rock ng?????i Anh ???????c th??nh l???p t???i London v??o n??m 1996. Ban nh???c bao g???m gi???ng ca ch??nh, tay guitar ?????m v?? piano Chris Martin, tay guitar ch??nh Jonny Buckland, tay bass Guy Berryman, tay tr???ng Will Champion v?? gi??m ?????c s??ng t???o Phil Harvey.','','Coldplay','United Kingdom'),(8,'2022-03-07 20:58:55',_binary '\0','2022-03-07 20:58:55','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/edsheeran_image_cover1646661471927?generation=1646661534112145&alt=media','1991-02-17','Edward Christopher \"Ed\" Sheeran hay c??n ???????c bi???t ?????n v???i ngh??? danh Ed Sheeran l?? m???t nam ca s??-nh???c s?? ng?????i Anh. Ed ???????c sinh ra t???i Hebden Bridge, Yorkshire v?? l???n l??n t???i Framlingham, Suffolk. ?????u n??m 2011, anh ?????c l???p cho ra m???t EP th??? t??m, No. 5 Collaborations Project.','Male','Ed Sheeran','United Kingdom'),(9,'2022-03-07 21:01:49',_binary '\0','2022-03-07 21:01:49','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/soobinhoangson_image_cover1646661707161?generation=1646661707885115&alt=media','1992-09-10','Nguy???n Ho??ng S??n, th?????ng ???????c bi???t ?????n v???i ngh??? danh SOOBIN ho???c Soobin Ho??ng S??n l?? nam ca s??, nh???c s??, rapper, nh?? s???n xu???t ??m nh???c thu???c c??ng ty SpaceSpeakers Group t???i Vi???t Nam. Anh l?? m???t trong s??? ??t nam ca s?? t???i Vi???t Nam c?? kh??? n??ng th??? hi???n ???????c nhi???u d??ng nh???c v?? k?? n??ng tr??nh di???n s??n kh???u n???i b???t. ','Male','Soobin Ho??ng S??n','Viet Nam'),(10,'2022-03-17 16:00:30',_binary '\0','2022-03-17 16:00:30','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/midoandfalasol_image_cover1647532829104?generation=1647532830280288&alt=media','2021-07-29','V??o ng??y 29/7, nh??m nh???c Kpop Mido And Falasol quy t??? d??n di???n vi??n c???c x???n c???a drama Chuy???n ?????i B??c S?? ???? ch??nh th???c tung MV ?????u tay c?? t???a ????? \"Superstar\"','','Mido and Falasol','Korea, Republic of'),(11,'2022-03-17 16:07:06',_binary '\0','2022-03-17 16:07:06','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/jeonmi-do_image_cover1647533225661?generation=1647533226347847&alt=media','1982-08-04','Jeon Mi-do l?? n??? ca s??, di???n vi??n s??n kh???u v?? truy???n h??nh ng?????i H??n Qu???c. C?? ???????c bi???t ?????n v???i vai di???n b??c s?? Chae Song-hwa trong Nh???ng b??c s?? t??i hoa.','Female','Jeon Mi-do','Korea, Republic of'),(12,'2022-03-17 16:12:25',_binary '\0','2022-03-17 16:12:25','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/yooyeon-seok_image_cover1647533544275?generation=1647533544888103&alt=media','1984-04-11','Ahn Yeon Seok, th?????ng ???????c bi???t ?????n v???i ngh??? danh Yoo Yeon Seok, l?? m???t nam di???n vi??n ng?????i H??n Qu???c. Sau khi ra m???t v??o n??m 2003 v???i m???t vai nh??? trong Old boy, anh ???? ti???p t???c s??? nghi???p di???n xu???t c???a m??nh v??o n??m 2008. ','Male','Yoo Yeon-seok','Korea, Republic of'),(13,'2022-03-17 16:15:12',_binary '\0','2022-03-17 16:15:12','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/ngot_image_cover1647533711364?generation=1647533712015582&alt=media',NULL,'Ng???t l?? m???t ban nh???c indie pop Vi???t Nam g???m 3 th??nh vi??n V?? ??inh Tr???ng Th???ng, Phan Vi???t Ho??ng v?? Nguy???n H??ng Nam Anh','','Ng???t','Viet Nam'),(14,'2022-03-23 17:12:14',_binary '\0','2022-03-23 17:12:14','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/den_image_cover1648055533168?generation=1648055533780883&alt=media','1989-12-13','Nguy???n ?????c C?????ng, th?????ng ???????c bi???t ?????n v???i ngh??? danh ??en V??u hay ??en, l?? m???t nam rapper v?? nh???c s?? ng?????i Vi???t Nam. ??en V??u t???ng gi??nh ???????c 1 gi???i C???ng hi???n v?? l?? \"m???t trong s??? ??t ngh??? s?? th??nh c??ng t??? l??n s??ng underground v?? ??m nh???c indie\" c???a Vi???t Nam','Male','??en','Viet Nam'),(15,'2022-03-23 17:19:41',_binary '\0','2022-03-23 17:19:41','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/min_image_cover1648055980802?generation=1648055981415403&alt=media','1988-12-07','Nguy???n Minh H???ng, th?????ng ???????c bi???t ?????n v???i ngh??? danh Min l?? m???t n??? ca s?? v?? v?? c??ng ng?????i Vi???t Nam. Kh???i ?????u l?? th??nh vi??n c???a nh??m nh???y St.319, c?? ra m???t v???i t?? c??ch ca s?? solo v??o n??m 2013 v???i ????a ????n ?????u tay \"T??m\"','Female','Min','Viet Nam'),(16,'2022-03-23 17:22:34',_binary '\0','2022-03-23 17:22:34','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/justatee_image_cover1648056153031?generation=1648056153655013&alt=media','1990-11-01','Nguy???n Thanh Tu???n, th?????ng ???????c bi???t ?????n v???i ngh??? danh JustaTee hay JayTee l?? m???t nam rapper R&B, ca s?? ki??m s??ng t??c nh???c ng?????i Vi???t Nam. Anh t???ng gi??nh ???????c 1 gi???i C???ng hi???n v?? l?? m???t trong nh???ng rapper r???t th??nh c??ng v?? ???????c ????ng ?????o kh??n gi??? trong th??? tr?????ng ??m nh???c Vi???t Nam bi???t ?????n v?? y??u m???n.','Male','JustaTee','Viet Nam'),(17,'2022-03-23 17:23:22',_binary '\0','2022-03-23 17:23:22','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/hoangthuylinh_image_cover1648056201795?generation=1648056202410310&alt=media','1988-08-11','Ho??ng Th??y Linh l?? m???t n??? ca s??, di???n vi??n, ng?????i m???u ???nh v?? ng?????i d???n ch????ng tr??nh ng?????i Vi???t Nam. C?? t???ng gi??nh 4 gi???i C???ng hi???n trong t???ng s??? 6 ????? c???.','Female','Ho??ng Th??y Linh','Viet Nam'),(18,'2022-03-25 11:48:50',_binary '\0','2022-03-25 11:48:50','https://storage.googleapis.com/download/storage/v1/b/artist-upload/o/dinhdung_image_cover1648208928470?generation=1648208929512866&alt=media','2000-09-11','R???t ?????p trai','Male','????nh D??ng','Viet Nam');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'2022-03-06 23:54:16',_binary '\0','2022-03-19 03:20:10','https://storage.googleapis.com/download/storage/v1/b/genre-upload/o/pop_image_cover1646668848366?generation=1646668848456622&alt=media','Pop'),(2,'2022-03-06 23:54:37',_binary '\0','2022-03-07 23:00:38','https://storage.googleapis.com/download/storage/v1/b/genre-upload/o/ballad_image_cover1646668834891?generation=1646668835017190&alt=media','Ballad'),(3,'2022-03-06 23:54:53',_binary '\0','2022-03-07 23:00:18','https://storage.googleapis.com/download/storage/v1/b/genre-upload/o/r&b_image_cover1646668813078?generation=1646668816825620&alt=media','R&B'),(4,'2022-03-07 00:39:48',_binary '\0','2022-03-07 22:59:56','https://storage.googleapis.com/download/storage/v1/b/genre-upload/o/rock_image_cover1646668791938?generation=1646668794605833&alt=media','Rock'),(5,'2022-03-23 17:09:35',_binary '\0','2022-03-23 17:09:35','https://storage.googleapis.com/download/storage/v1/b/genre-upload/o/rap_image_cover1648055373623?generation=1648055374642094&alt=media','Rap');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listen_count`
--

DROP TABLE IF EXISTS `listen_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listen_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `week` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listen_count`
--

LOCK TABLES `listen_count` WRITE;
/*!40000 ALTER TABLE `listen_count` DISABLE KEYS */;
/*!40000 ALTER TABLE `listen_count` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `expire_date` date NOT NULL,
  `payment_method` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` int(11) NOT NULL,
  `transaction_completed` bit(1) NOT NULL,
  `transaction_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `txn_ref_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `actual_price` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4spfnm9si9dowsatcqs5or42i` (`user_id`),
  CONSTRAINT `FK4spfnm9si9dowsatcqs5or42i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,'2022-03-09 11:25:57',_binary '\0','2022-03-09 11:26:33',0,'2022-06-09',NULL,200000,_binary '','13702049','35015713',1,200000),(2,'2021-12-09 11:25:57',_binary '\0','2021-12-09 11:26:33',0,'2022-03-09',NULL,200000,_binary '','13702048','35015712',1,200000),(3,'2021-09-09 11:25:57',_binary '\0','2021-09-09 11:26:33',0,'2021-12-09',NULL,200000,_binary '','13702047','35015711',1,200000),(4,'2022-03-09 11:25:57',_binary '\0','2022-03-09 11:26:33',0,'2022-04-09',NULL,200000,_binary '','13702050','35015714',2,200000),(5,'2022-03-22 15:07:06',_binary '\0','2022-03-22 15:07:06',0,'2022-06-22',NULL,2000000,_binary '\0',NULL,'01741911',1,2000000),(6,'2022-03-23 01:47:02',_binary '\0','2022-03-23 01:47:02',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'71136652',4,50000),(7,'2022-03-23 01:47:03',_binary '\0','2022-03-23 01:47:03',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'38340647',4,50000),(8,'2022-03-23 13:37:44',_binary '\0','2022-03-23 13:37:44',0,'2022-06-23',NULL,2000000,_binary '\0',NULL,'67548031',4,2000000),(9,'2022-03-23 13:38:09',_binary '\0','2022-03-23 13:38:09',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'32924232',4,50000),(10,'2022-03-23 13:45:05',_binary '\0','2022-03-23 13:45:05',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'34310991',4,50000),(11,'2022-03-23 13:45:27',_binary '\0','2022-03-23 13:45:27',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'44086472',4,50000),(12,'2022-03-23 13:46:08',_binary '\0','2022-03-23 13:46:08',0,'2022-06-23',NULL,2000000,_binary '\0',NULL,'70153556',4,2000000),(13,'2022-03-23 13:46:21',_binary '\0','2022-03-23 13:46:21',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'38156108',4,50000),(14,'2022-03-23 13:46:22',_binary '\0','2022-03-23 13:46:22',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'66484682',4,50000),(15,'2022-03-23 13:46:27',_binary '\0','2022-03-23 13:46:27',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'14969516',4,50000),(16,'2022-03-23 13:46:28',_binary '\0','2022-03-23 13:46:28',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'63100588',4,50000),(17,'2022-03-23 13:46:29',_binary '\0','2022-03-23 13:46:29',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'24485641',4,50000),(18,'2022-03-23 13:46:30',_binary '\0','2022-03-23 13:46:30',0,'2022-04-23',NULL,50000,_binary '\0',NULL,'89568039',4,50000),(19,'2022-03-23 14:20:55',_binary '\0','2022-03-23 14:23:18',0,'2022-04-23',NULL,50000,_binary '','13711471','97593622',4,50000),(20,'2022-03-23 14:55:05',_binary '\0','2022-03-23 14:55:05',0,'2022-06-23',NULL,2000000,_binary '\0',NULL,'91120608',6,2000000),(21,'2022-03-23 14:56:00',_binary '\0','2022-03-23 14:57:41',0,'2022-06-23',NULL,2000000,_binary '','13711475','14913516',6,2000000),(22,'2022-03-25 03:02:14',_binary '\0','2022-03-25 03:02:14',0,'2022-06-25',NULL,2000000,_binary '\0',NULL,'07570336',5,2000000),(23,'2022-03-25 03:31:41',_binary '\0','2022-03-25 03:31:41',0,'2022-06-25',NULL,2000000,_binary '\0',NULL,'61559233',7,2000000),(24,'2022-03-25 03:32:36',_binary '\0','2022-03-25 03:32:36',0,'2022-06-25',NULL,2000000,_binary '\0',NULL,'34753957',7,2000000),(25,'2022-03-25 03:38:40',_binary '\0','2022-03-25 03:38:40',0,'2022-06-25',NULL,2000000,_binary '\0',NULL,'90725347',7,2000000),(26,'2022-03-25 04:04:32',_binary '\0','2022-03-25 04:05:01',0,'2022-06-25',NULL,2000000,_binary '','13712497','37427996',7,2000000),(27,'2022-03-25 04:06:51',_binary '\0','2022-03-25 04:06:51',0,'2022-04-25',NULL,50000,_binary '\0',NULL,'28558407',8,50000),(28,'2022-03-25 04:06:52',_binary '\0','2022-03-25 04:07:11',0,'2022-04-25',NULL,50000,_binary '','13712500','16116762',8,50000),(29,'2022-03-25 04:23:43',_binary '\0','2022-03-25 04:24:04',0,'2022-06-25',NULL,2000000,_binary '','13712521','61520406',9,2000000),(30,'2022-03-25 04:41:47',_binary '\0','2022-03-25 04:42:11',0,'2022-04-25',NULL,50000,_binary '','13712540','78568477',11,50000),(31,'2022-03-25 11:42:45',_binary '\0','2022-03-25 11:42:45',0,'2022-06-25',NULL,2000000,_binary '\0',NULL,'32137439',5,2000000),(32,'2022-03-25 11:43:48',_binary '\0','2022-03-25 11:45:26',0,'2022-06-25',NULL,2000000,_binary '','13712978','40880194',5,2000000),(33,'2022-03-25 12:19:09',_binary '\0','2022-03-25 12:19:46',0,'2022-06-25',NULL,2000000,_binary '','13712988','33383680',13,2000000);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_param`
--

DROP TABLE IF EXISTS `payment_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `price` int(11) NOT NULL,
  `time_expire` int(11) NOT NULL,
  `unit` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `actual_price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_param`
--

LOCK TABLES `payment_param` WRITE;
/*!40000 ALTER TABLE `payment_param` DISABLE KEYS */;
INSERT INTO `payment_param` VALUES (1,'2022-03-21 03:11:22',_binary '\0','2022-03-21 03:11:22',0,2000000,3,'Month',2000000),(2,'2022-03-21 03:11:22',_binary '\0','2022-03-21 03:11:22',0,50000,1,'Month',50000);
/*!40000 ALTER TABLE `payment_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlbi6wsq41356go2ki0yirfixo` (`user_id`),
  CONSTRAINT `FKlbi6wsq41356go2ki0yirfixo` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES (1,'2022-03-07 02:28:59',_binary '\0','2022-03-07 02:28:59','playlist-like',2),(14,'2022-03-13 08:08:46',_binary '\0','2022-03-13 08:08:46','Chill',2),(16,'2022-03-14 04:13:15',_binary '\0','2022-03-14 04:13:15','Night',2),(28,'2022-03-21 12:46:15',_binary '\0','2022-03-21 12:46:15','playlist_HD',5),(29,'2022-03-23 14:02:09',_binary '\0','2022-03-23 14:02:09','playlist-like',4),(45,'2022-03-25 04:21:20',_binary '\0','2022-03-25 04:21:20','d',9),(48,'2022-03-25 04:40:23',_binary '\0','2022-03-25 04:40:23','test',11),(49,'2022-03-25 12:12:09',_binary '\0','2022-03-25 12:12:09','playlist-like',5);
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist_song`
--

DROP TABLE IF EXISTS `playlist_song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlist_song` (
  `playlist_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  KEY `FK8l4jevlmxwsdm3ppymxm56gh2` (`song_id`),
  KEY `FKji5gt6i2hcwyt9x1fcfndclva` (`playlist_id`),
  CONSTRAINT `FK8l4jevlmxwsdm3ppymxm56gh2` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`),
  CONSTRAINT `FKji5gt6i2hcwyt9x1fcfndclva` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist_song`
--

LOCK TABLES `playlist_song` WRITE;
/*!40000 ALTER TABLE `playlist_song` DISABLE KEYS */;
INSERT INTO `playlist_song` VALUES (14,10),(14,12),(14,14),(14,2),(16,11),(16,1),(16,4),(1,10),(1,9),(1,8),(1,7),(1,11),(1,1),(29,28),(48,14),(28,32),(28,13),(28,5),(49,5);
/*!40000 ALTER TABLE `playlist_song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song`
--

DROP TABLE IF EXISTS `song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `song` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `composer` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `listen_count` int(11) DEFAULT NULL,
  `listen_count_reset` int(11) DEFAULT NULL,
  `media_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time_play` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vip_only` bit(1) NOT NULL,
  `album_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrcjmk41yqj3pl3iyii40niab0` (`album_id`),
  CONSTRAINT `FKrcjmk41yqj3pl3iyii40niab0` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song`
--

LOCK TABLES `song` WRITE;
/*!40000 ALTER TABLE `song` DISABLE KEYS */;
INSERT INTO `song` VALUES (1,'2022-03-07 00:11:58',_binary '\0','2022-03-25 03:11:22','?????c Tr??','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/seesingshare2_image_cover1646669099579?generation=1646669099522473&alt=media',15,15,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/giacmochilagiacmo_audio-file1646586692246?generation=1646586717371710&alt=media','05:40','Gi???c m?? ch??? l?? gi???c m??',_binary '\0',2),(2,'2022-03-07 00:14:18',_binary '\0','2022-03-23 13:52:12','Vi???t Anh','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/seesingshare2_image_cover1646669099579?generation=1646669099522473&alt=media',18,18,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/chuabaogio_audio-file1646586849131?generation=1646586857356366&alt=media','05:02','Ch??a bao gi???',_binary '\0',2),(3,'2022-03-07 00:15:22',_binary '\0','2022-03-23 14:00:15','Anh B???ng','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/seesingshare2_image_cover1646669099579?generation=1646669099522473&alt=media',13,13,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/nguoitinhmuadong_audio-file1646586917020?generation=1646586921913655&alt=media','04:16','Ng?????i t??nh m??a ????ng',_binary '\0',2),(4,'2022-03-07 00:16:38',_binary '\0','2022-03-23 13:55:56','B???o Ch???n','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/seesingshare2_image_cover1646669099579?generation=1646669099522473&alt=media',10,10,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/noiaybinhyen_audio-file1646586990452?generation=1646586998176844&alt=media','04:53','N??i ???y b??nh y??n',_binary '',2),(5,'2022-03-07 00:17:57',_binary '\0','2022-03-23 13:58:17','B???o Ch???n','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/seesingshare2_image_cover1646669099579?generation=1646669099522473&alt=media',10,10,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/phomuadong_audio-file1646587060842?generation=1646587076917279&alt=media','06:52','Ph??? m??a ????ng',_binary '',2),(6,'2022-03-07 00:19:38',_binary '\0','2022-03-23 14:00:28','Mr.Siro','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/seesingshare2_image_cover1646669099579?generation=1646669099522473&alt=media',13,13,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/traitimemcungbietdau_audio-file1646587166257?generation=1646587177207787&alt=media','05:26','Tr??i Tim Em C??ng Bi???t ??au',_binary '\0',2),(7,'2022-03-07 00:21:35',_binary '\0','2022-03-23 14:10:10','Ho??ng D??ng','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/25_image_cover1646669111319?generation=1646669111943227&alt=media',64,64,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/nangtho_audio-file1646587281997?generation=1646587293401772&alt=media','04:15','N??ng Th??',_binary '\0',1),(8,'2022-03-07 00:23:00',_binary '\0','2022-03-23 13:54:59','Ho??ng D??ng','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/25_image_cover1646669111319?generation=1646669111943227&alt=media',14,14,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/nuathapky_audio-file1646587362642?generation=1646587380074980&alt=media','05:20','N???a Th???p K???',_binary '\0',1),(9,'2022-03-07 00:24:06',_binary '\0','2022-03-23 13:54:39','Ho??ng D??ng','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/25_image_cover1646669111319?generation=1646669111943227&alt=media',15,15,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/thoiquen_audio-file1646587438001?generation=1646587445271810&alt=media','04:31','Th??i Quen',_binary '\0',1),(10,'2022-03-07 00:26:13',_binary '\0','2022-03-23 13:58:48','Ho??ng D??ng','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/25_image_cover1646669111319?generation=1646669111943227&alt=media',8,8,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/venha_audio-file1646587568318?generation=1646587572655533&alt=media','04:58','V??? Nh??',_binary '\0',1),(11,'2022-03-07 00:26:58',_binary '\0','2022-03-23 13:59:04','Ho??ng D??ng','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/25_image_cover1646669111319?generation=1646669111943227&alt=media',11,11,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/vimotcaunoi_audio-file1646587610533?generation=1646587616914534&alt=media','04:54','V?? M???t C??u N??i',_binary '\0',1),(12,'2022-03-07 00:33:07',_binary '\0','2022-03-23 13:56:14','V??','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/buocquanhau_image_cover1646669087834?generation=1646669087850712&alt=media',15,15,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/buocquanhau_audio-file1646587980508?generation=1646587986660483&alt=media','04:17','B?????c Qua Nhau',_binary '',3),(13,'2022-03-07 00:35:25',_binary '\0','2022-03-23 13:50:45','V??','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/buocquanhau_image_cover1646669087834?generation=1646669087850712&alt=media',24,24,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/buocquamuacodon_audio-file1646588113006?generation=1646588124155846&alt=media','04:38','B?????c Qua M??a C?? ????n',_binary '\0',3),(14,'2022-03-07 00:53:06',_binary '\0','2022-03-23 13:56:51','Tr???n Duy Khang & Rhymastic','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/cuchillthoi_image_cover1646669054721?generation=1646669059376939&alt=media',19,19,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/cuchillthoi_audio-file1646589163878?generation=1646589185441597&alt=media','04:30','C??? Chill Th??i',_binary '',4),(15,'2022-03-17 16:03:27',_binary '\0','2022-03-17 16:03:27','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/hospitalplaylistseason2ost_image_cover1647532907555?generation=1647532908308364&alt=media',0,0,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/alreadyoneyear(dramaversion)_audio-file1647533006201?generation=1647533007331998&alt=media','3:52','Already One Year (Drama Version)',_binary '\0',5),(16,'2022-03-17 16:04:19',_binary '\0','2022-03-28 02:54:22','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/hospitalplaylistseason2ost_image_cover1647532907555?generation=1647532908308364&alt=media',3,3,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/butterfly_audio-file1647533057073?generation=1647533058968332&alt=media','3:28','Butterfly ',_binary '\0',5),(17,'2022-03-17 16:05:15',_binary '\0','2022-03-28 02:50:53','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/hospitalplaylistseason2ost_image_cover1647532907555?generation=1647532908308364&alt=media',2,2,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/ilikeyou(dramaversion)_audio-file1647533113692?generation=1647533114749458&alt=media','3:35','I Like You (Drama Version)',_binary '\0',5),(18,'2022-03-17 16:08:15',_binary '\0','2022-03-28 02:47:17','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/hospitalplaylistseason2ost_image_cover1647532907555?generation=1647532908308364&alt=media',2,2,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/let%E2%80%B2sforgetit(dramaversion)_audio-file1647533293280?generation=1647533294665523&alt=media','4:35','Let???s Forget It (Drama Version)',_binary '\0',5),(19,'2022-03-17 16:08:48',_binary '\0','2022-03-28 02:42:41','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/hospitalplaylistseason2ost_image_cover1647532907555?generation=1647532908308364&alt=media',1,1,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/rainandyou(dramaversion)_audio-file1647533326732?generation=1647533328101882&alt=media','4:6','Rain And You (Drama Version)',_binary '\0',5),(20,'2022-03-17 16:10:57',_binary '\0','2022-03-28 02:38:35','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/hospitalplaylistseason2ost_image_cover1647532907555?generation=1647532908308364&alt=media',7,7,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/superstar_audio-file1647533456272?generation=1647533457384314&alt=media','3:34','Superstar',_binary '\0',5),(21,'2022-03-17 16:12:53',_binary '\0','2022-03-28 02:35:01','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/hospitalplaylistseason2ost_image_cover1647532907555?generation=1647532908308364&alt=media',1,1,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/toyou_audio-file1647533571782?generation=1647533572776322&alt=media','3:46','To You',_binary '\0',5),(22,'2022-03-17 16:21:16',_binary '\0','2022-03-28 02:31:14','Ng???t','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/3_image_cover1647534019032?generation=1647534019679563&alt=media',12,12,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/lancuoi(dibenemxotxanguoioi)_audio-file1647534074637?generation=1647534075606501&alt=media','3:43','L???n cu???i (??i b??n em x??t xa ng?????i ??i)',_binary '\0',6),(23,'2022-03-17 16:21:55',_binary '\0','2022-03-28 02:27:31','Ng???t','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/3_image_cover1647534019032?generation=1647534019679563&alt=media',12,12,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/chuongbaothuc(sangroi)_audio-file1647534114363?generation=1647534115374072&alt=media','4:8','Chu??ng b??o th???c (S??ng r???i)',_binary '\0',6),(24,'2022-03-17 16:28:59',_binary '\0','2022-03-28 02:23:23','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',1,1,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/baonhieu_audio-file1647534538222?generation=1647534539203601&alt=media','3:50','Bao Nhi??u',_binary '\0',7),(25,'2022-03-17 16:29:40',_binary '\0','2022-03-28 03:43:16','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',2,2,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/emdungkhoc_audio-file1647534578621?generation=1647534579679077&alt=media','4:27','Em ?????ng Kh??c',_binary '\0',7),(26,'2022-03-17 16:30:19',_binary '\0','2022-03-28 03:38:48','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',15,15,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/gianhu_audio-file1647534617692?generation=1647534618795319&alt=media','4:57','Gi?? Nh??',_binary '\0',7),(27,'2022-03-17 16:31:01',_binary '\0','2022-03-28 03:33:51','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',2,2,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/ms.may_audio-file1647534659377?generation=1647534660555718&alt=media','3:24','Ms. May',_binary '\0',7),(28,'2022-03-17 16:31:57',_binary '\0','2022-03-28 03:30:27','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',18,18,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/vungkyuc_audio-file1647534715777?generation=1647534717144900&alt=media','4:58','V??ng K?? ???c',_binary '\0',7),(29,'2022-03-17 16:32:27',_binary '\0','2022-03-28 03:25:28','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',2,2,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/mongdu_audio-file1647534745804?generation=1647534746822756&alt=media','4:0','M???ng Du',_binary '\0',7),(30,'2022-03-17 16:33:05',_binary '\0','2022-03-28 03:21:28','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',2,2,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/quakhungcuaso_audio-file1647534784352?generation=1647534785344362&alt=media','4:59','Qua Khung C???a S???',_binary '\0',7),(31,'2022-03-17 16:33:43',_binary '\0','2022-03-28 03:16:28','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',19,19,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/mascara_audio-file1647534822290?generation=1647534823470213&alt=media','4:56','Mascara',_binary '\0',7),(32,'2022-03-17 16:34:14',_binary '\0','2022-03-28 03:11:32','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/quakhungcuaso_image_cover1647534364923?generation=1647534365646699&alt=media',13,13,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/duongchantroi_audio-file1647534852611?generation=1647534853675079&alt=media','5:10','???????ng Ch??n Tr???i',_binary '\0',7),(33,'2022-03-23 17:15:17',_binary '\0','2022-03-28 03:06:21','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/loinho_image_cover1648055683139?generation=1648055683688935&alt=media',13,13,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/loinho_audio-file1648055715700?generation=1648055716695977&alt=media','4:13','L???i nh???',_binary '\0',8),(34,'2022-03-23 17:20:49',_binary '\0','2022-03-28 03:02:08','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/bainaychillphet_image_cover1648056018729?generation=1648056019320268&alt=media',3,3,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/bainaychillphet_audio-file1648056047192?generation=1648056048537349&alt=media','4:36','B??i N??y Chill Ph???t',_binary '\0',9),(35,'2022-03-23 17:25:56',_binary '\0','2022-03-28 02:57:32','','https://storage.googleapis.com/download/storage/v1/b/album-upload/o/lamgiphaihot_image_cover1648056315935?generation=1648056316613672&alt=media',4,4,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/lamgiphaihot_audio-file1648056354671?generation=1648056355604119&alt=media','3:9','L??m G?? Ph???i H???t',_binary '\0',10),(36,'2022-03-25 12:23:26',_binary '\0','2022-03-25 12:23:26','????nh D??ng','https://storage.googleapis.com/download/storage/v1/b/song-upload/o/saotanguocloi_image_cover1648211000356?generation=1648211003067969&alt=media',0,0,'https://storage.googleapis.com/download/storage/v1/b/music-upload/o/saotanguocloi_audio-file1648211003085?generation=1648211005678747&alt=media','5:12','Sao Ta Ng?????c L???i',_binary '',11);
/*!40000 ALTER TABLE `song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song_artist`
--

DROP TABLE IF EXISTS `song_artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `song_artist` (
  `song_id` int(11) NOT NULL,
  `artist_id` int(11) NOT NULL,
  KEY `FK9tevojs24wnwin3di24wlao1m` (`artist_id`),
  KEY `FKa29cre1dfpdj3gek88ukv43cc` (`song_id`),
  CONSTRAINT `FK9tevojs24wnwin3di24wlao1m` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`id`),
  CONSTRAINT `FKa29cre1dfpdj3gek88ukv43cc` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song_artist`
--

LOCK TABLES `song_artist` WRITE;
/*!40000 ALTER TABLE `song_artist` DISABLE KEYS */;
INSERT INTO `song_artist` VALUES (1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,1),(8,1),(9,1),(10,1),(11,1),(12,3),(13,3),(14,4),(14,5),(14,6),(15,10),(17,10),(16,11),(18,10),(19,10),(20,10),(21,12),(22,13),(23,13),(24,4),(25,4),(26,4),(27,4),(28,4),(29,4),(30,4),(31,4),(32,4),(33,14),(34,14),(34,15),(35,16),(35,14),(35,17),(36,18);
/*!40000 ALTER TABLE `song_artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song_genre`
--

DROP TABLE IF EXISTS `song_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `song_genre` (
  `song_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  KEY `FKmpuht870e976moxtxywrfngcr` (`genre_id`),
  KEY `FK1ssu87dg5vsdxpmyjqqc42if3` (`song_id`),
  CONSTRAINT `FK1ssu87dg5vsdxpmyjqqc42if3` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`),
  CONSTRAINT `FKmpuht870e976moxtxywrfngcr` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song_genre`
--

LOCK TABLES `song_genre` WRITE;
/*!40000 ALTER TABLE `song_genre` DISABLE KEYS */;
INSERT INTO `song_genre` VALUES (1,1),(1,2),(2,1),(2,2),(3,2),(4,2),(5,2),(6,1),(7,2),(8,1),(9,1),(10,1),(11,1),(11,2),(12,2),(13,2),(14,3),(14,4),(15,3),(15,1),(17,4),(17,3),(16,3),(18,1),(19,1),(20,3),(21,1),(22,4),(23,4),(24,3),(24,4),(25,3),(26,3),(26,4),(27,3),(28,3),(28,4),(29,3),(29,4),(30,3),(30,1),(31,1),(32,3),(33,5),(34,5),(35,5),(35,3),(36,2);
/*!40000 ALTER TABLE `song_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_param`
--

DROP TABLE IF EXISTS `system_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `param_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `param_value` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_param`
--

LOCK TABLES `system_param` WRITE;
/*!40000 ALTER TABLE `system_param` DISABLE KEYS */;
INSERT INTO `system_param` VALUES (1,'2022-03-07 00:06:32',_binary '\0','2022-03-07 00:06:32','user-image-default','https://storage.googleapis.com/genre-upload/avatar.jpg'),(2,'2022-03-07 00:07:22',_binary '\0','2022-03-07 00:07:22','artist-image-default','https://storage.googleapis.com/genre-upload/avatars-pXAmgKVNbQvrXCST-MAaq0g-t500x500.jpg'),(3,'2022-03-07 00:07:52',_binary '\0','2022-03-07 00:07:52','album-image-default','https://storage.googleapis.com/genre-upload/avatars-pXAmgKVNbQvrXCST-MAaq0g-t500x500.jpg');
/*!40000 ALTER TABLE `system_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createddate` datetime NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `token_expire` datetime DEFAULT NULL,
  `user_token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vip_expire_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2022-02-21 20:20:34',_binary '\0',NULL,'https://drive.google.com/uc?id=1U4vbYboDf9Cb2RQ36syj1XWEw3GEHrqH',NULL,'bachcvth1911012@fpt.edu.vn',NULL,NULL,NULL,'$2a$10$6b28h12Lru7tGeHNUQGjEeq6pLQG16tcwFWDm4pj5bqDJJZDgmv.6','0332677240',NULL,NULL,'cvb1995','2022-06-09'),(2,'2022-03-07 01:25:43',_binary '\0','2022-03-20 14:08:13','https://drive.google.com/file/d/13BHu4Pu0wwv9552A3k03LzdbNaMG8lY6',NULL,'tuanvm@gmail.com',NULL,NULL,NULL,'$2a$10$jslRTL.rpF/07chqctUGxOTsD.EwC3NEMat4Now9rwnuYA0lEjRRi','0327439204','2022-03-21 14:08:13','qiKfpPQUabruwmv','tuanvm','2022-04-09'),(3,'2022-03-20 14:10:51',_binary '\0','2022-03-20 14:10:51','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'bach1994@gmail.com',NULL,NULL,NULL,'$2a$10$VZwULEa/QxGqSPGz4l4J9.tAD2sgjcvJbRIY3SLAIHtdx0ypqM43u','0332733416',NULL,NULL,'cvb1994',NULL),(4,'2022-03-20 14:13:57',_binary '\0','2022-03-23 14:23:18','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'test@gmail.com',NULL,NULL,NULL,'$2a$10$xznql9V912pHFbCDGyu96.IXigVJz6DAprBW3VmVV4dzgn53Nt2DW','09856224445',NULL,NULL,'test','2022-04-23'),(5,'2022-03-21 12:43:39',_binary '\0','2022-03-25 11:45:26','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'huyducle1109@gmail.com','Le',NULL,'Duc','$2a$10$5MyWMqGEx6anslAvM6Sp2uqJOTmjkpiiC0FqHFevXh6i4i9bXUkLO','0961503893',NULL,NULL,'huyduc','2022-06-25'),(6,'2022-03-23 14:54:12',_binary '\0','2022-03-23 14:57:41','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'test2@gmail..com',NULL,NULL,NULL,'$2a$10$.ntFtOgRniThzWXEKd2N5OD0.olYE7USvg9TE3Me2z4FQ8WhJdiZ2','0982747373',NULL,NULL,'test2','2022-06-23'),(7,'2022-03-25 03:31:12',_binary '\0','2022-03-25 04:05:01','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'test10@gmail.com',NULL,NULL,NULL,'$2a$10$rDkcjHGKLMY/aKuFnlGR0e3hvrSt0FoBKaKFcwHGX7vT4/KVzKUM.','0983746738',NULL,NULL,'test10','2022-06-25'),(8,'2022-03-25 04:06:39',_binary '\0','2022-03-25 04:07:11','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'test11@gmai.com',NULL,NULL,NULL,'$2a$10$n4N.YFHHA1GNAn0bVpeLRuRWudpXsMQpIR2PIt0/mU4TL2gMpdgw2','09837463736',NULL,NULL,'test11','2022-04-25'),(9,'2022-03-25 04:16:33',_binary '\0','2022-03-25 04:24:04','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'test12@gmail.com',NULL,NULL,NULL,'$2a$10$55LBpIckwA1b2nHaFQvmFuEinEMdYZNgGeV/fMJoAYl9k..dTtS5e','0948573847',NULL,NULL,'test12','2022-06-25'),(10,'2022-03-25 04:25:47',_binary '\0','2022-03-25 04:25:47','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'test13@gmail.com',NULL,NULL,NULL,'$2a$10$77oNlYnJfr/w0.UVCuxqAOmjJ2Z1OC7p2L0CFx38E6HvKvTe113S2','0987465747',NULL,NULL,'test13',NULL),(11,'2022-03-25 04:40:10',_binary '\0','2022-03-25 04:42:11','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'test14@gmail.com',NULL,NULL,NULL,'$2a$10$mIGcM6ZAxyImFVYbOqJYbOtGwBFldz29a8kfCd3ewYJfC8jymkjxW','09876457652',NULL,NULL,'test14','2022-04-25'),(12,'2022-03-25 12:11:03',_binary '\0','2022-03-25 12:11:03','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'hd123@gmail.com',NULL,NULL,NULL,'$2a$10$/hx5uIFtyj29hBkdOZ47iu8Ckjp/8bPKgKZ5f37DjX9WN41Tt71bW','0961503893',NULL,NULL,'hd123',NULL),(13,'2022-03-25 12:17:04',_binary '\0','2022-03-25 12:19:46','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'huyduc123@gmail.com',NULL,NULL,NULL,'$2a$10$mQvqDMDUmFqvRkFLYHaj3OxExzXxfdE3gpoddHKl4gBFTzr/t5LRu','123456',NULL,NULL,'123455','2022-06-25'),(14,'2022-03-27 10:06:54',_binary '\0','2022-03-27 10:06:54','https://storage.googleapis.com/genre-upload/avatar.jpg',NULL,'test15@gmail.com',NULL,NULL,NULL,'$2a$10$QF2yDT/ewhsg.BxjnVJhveFU6bu82wpKeO4gCwp8a6OnO2aOrYPoe','0948573847',NULL,NULL,'test15',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-28 10:45:38
