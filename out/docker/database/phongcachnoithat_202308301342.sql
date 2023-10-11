-- MySQL dump 10.13  Distrib 8.0.34, for Linux (x86_64)
--
-- Host: 103.238.234.22    Database: appnoithat
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `username` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
                           `password` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
                           `active` tinyint NOT NULL,
                           `info_id` int NOT NULL,
                           `enabled` tinyint NOT NULL,
                           `expire_date` date DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `username` (`username`),
                           KEY `info_id` (`info_id`),
                           CONSTRAINT `account_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `accountinformation` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'user','{noop}user',1,1,1,'2023-12-31'),(2,'admin','{noop}admin',1,2,1,'2300-12-31'),(4,'sa','{noop}ddas',1,4,1,'2023-12-28'),(5,'huy','{noop}huy',1,5,1,'2023-10-29'),(15,'asdsasd','{noop}asd',0,15,0,'2024-12-29'),(16,'dsad','{noop}sad',0,16,0,'2024-12-29'),(17,'sdfsd','{noop}sdf',0,17,0,'2024-12-29'),(27,'kim','{noop}kim',1,27,1,'2024-04-30'),(30,'thuong11','{noop}123',0,30,0,'2023-10-30'),(31,'adad','{noop}adad',0,31,0,'2023-10-30'),(32,'asdd','{noop}123456',0,32,0,'2024-12-30');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accountinformation`
--

DROP TABLE IF EXISTS `accountinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accountinformation` (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                      `gender` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
                                      `email` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
                                      `address` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
                                      `phone` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountinformation`
--

LOCK TABLES `accountinformation` WRITE;
/*!40000 ALTER TABLE `accountinformation` DISABLE KEYS */;
INSERT INTO `accountinformation` VALUES (1,'John Doe','Male','john.doe@example.com','123 Main St, City','123-456-7890'),(2,'Jane Smith','Female','jane.smith@example.com','456 Elm St, Town','987-654-3210'),(4,'asdsad','Nam','dsa','asddasd','asd'),(5,NULL,NULL,NULL,NULL,NULL),(15,'asdsa','Male','dasdas','dsad','sadssa'),(16,'sad','Female','sadasd','asdasd','adsda'),(17,'sdfds','Male','fds','sdfds','sdf'),(27,'kim','Male','kim','kim','000'),(30,'123','Male','123','123','123123'),(31,'adad','Male','adad','adad','adad'),(32,'asdaasdasd','Male','asdasd','asdasd','asassdsa');
/*!40000 ALTER TABLE `accountinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hangmuc`
--

DROP TABLE IF EXISTS `hangmuc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hangmuc` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `noi_that_id` int DEFAULT NULL,
                           `account_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `noi_that_id` (`noi_that_id`),
                           KEY `account_id` (`account_id`),
                           CONSTRAINT `hangmuc_ibfk_1` FOREIGN KEY (`noi_that_id`) REFERENCES `noithat` (`id`) ON DELETE CASCADE,
                           CONSTRAINT `hangmuc_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hangmuc`
--

LOCK TABLES `hangmuc` WRITE;
/*!40000 ALTER TABLE `hangmuc` DISABLE KEYS */;
INSERT INTO `hangmuc` VALUES (1,'sdfssadkjfhnádjkfnkjshưeui\nlsdkaf;ldjf\nsdfokjsdak\ndsfplksđáklpkj',1,1),(2,'dsfádfjkh	klqkláédfvkolnjm	\nsdàoksdk\nsdfjklsdfa\nsdgjkolsd\nsdfjigko;kl\'sdà[\nàkol;gjhnsdkolfp	\nsdfklsad\nskl;djng',1,1),(3,'<Thêm mới 2>',1,1),(4,'<Thêm mới 3>',1,1),(5,'<Thêm mới 4>',1,1),(6,'<Thêm mới 5>',1,1),(7,'<Thêm mới 0>',6,1),(8,'<Thêm mới 1>',6,1),(9,'<Thêm mới 2>',6,1),(10,'<Thêm mới 3>',6,1),(11,'<Thêm mới 4>',6,1),(12,'<Thêm mới 5>',6,1),(13,'<Thêm mới 6>',6,1),(14,'Tủ bếp dưới ',40,27),(15,'Tủ bếp trên ',40,27),(16,'Tủ bếp kịch trần/ tầng 2',40,27),(17,'Bàn đảo bếp',40,27),(18,'Đợt trang trí',40,27),(28,'Bản lề',50,27),(29,'Ray ngăn kéo',50,27),(30,'Ray lùa tủ áo\n',50,27),(31,'Chống cong vênh cánh lùa tủ áo',50,27),(32,'Giá bát',50,27),(33,'Pittong',50,27),(34,'Tay nâng ',50,27),(35,'Giá xoong nồi',50,27),(36,'Giá dao thớt',50,27),(37,'Khay thìa dĩa',50,27),(38,'Giá gia vị',50,27),(39,'Kính bếp',50,27),(40,'Gạch bông',50,27),(41,'Đá bếp',50,27),(42,'Soi đá bếp',50,27),(43,'Đèn led tủ',50,27),(44,'Đèn led ngăn kéo bếp',50,27),(45,'Thùng gạo',50,27),(46,'Thùng rác',50,27),(47,'Tay nắm cửa\n',50,27),(49,'Tủ kho ',50,27),(50,'Nhựa Picomat chống nước tuyệt đối',52,27),(51,'Nhựa Picomat chống nước tuyệt đối, cánh nhựa phủ phim tạo huỳnh CNC ',52,27),(52,'Nhựa Picomat chống nước tuyệt đối phủ fim\n',52,27),(53,'Vách kính thẳng 180 độ cường lực Hải Long 10 ly, Phụ kiện inox VVP ',53,27),(54,'Vách kính góc 135 độ cường lực Hải Long 10 ly, Phụ kiện inox VVP ',53,27),(55,'Gương lavabo nhà tắm theo mẫu thiết kế',54,27),(56,'Bàn ăn',40,27),(57,'Ghế ăn',40,27),(58,'Tủ trang trí (tủ rượu)',40,27),(61,'Ghế sofa văng',41,27),(62,'Đôn ngồi sofa',41,27),(63,'Ghế ngồi sofa',41,27),(64,'Bàn trà ',41,27),(65,'Ốp vách',41,27),(66,'Kệ tivi gỗ CN',41,27),(67,'Tủ trang trí (tủ rượu)',41,27),(68,'Tủ giày Gỗ CN',41,27),(69,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm',55,27),(70,'Gỗ Gụ Nam Phi \n',55,27),(71,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm',56,27),(72,'Gỗ Gụ Nam Phi',56,27),(73,'Gỗ MDF lõi xanh chống ẩm phủ Veneer sồi \nSơn đồng màu, gỗ chống cong vênh, chống mọt',57,27),(74,'Gỗ MDF lõi xanh chống ẩm phủ Melamin\nSơn đồng màu, gỗ chống cong vênh, chống mọt',57,27),(75,'Gỗ Gõ Nam Phi',56,27),(76,'Gỗ gõ Nam Phi',55,27),(77,'Giường ngủ',42,27),(78,'Tap đầu giường',42,27),(79,'Bàn phấn',42,27),(80,'Ghế trang điểm',42,27),(81,'Gương ',42,27),(82,'Tủ quần áo ',42,27),(83,'Ốp đầu giường',42,27),(84,'Bàn làm việc (Bàn học)',42,27),(85,'Kệ sách',42,27),(86,'Đôn thư giãn\nGhế nghỉ',42,27),(87,'Kệ tivi gỗ CN',42,27),(88,'Tủ trang trí ',42,27),(89,'Tủ bếp dưới ',58,27),(90,'Tủ bếp trên',58,27),(91,'Tủ bếp kịch trần ',58,27),(92,'Bàn đảo bếp',58,27),(93,'Đợt trang trí',58,27),(94,'Bàn ăn',58,27),(95,'Ghế ăn',58,27),(96,'Tủ trang trí (tủ rượu)',58,27),(101,'Sofa tân cổ Italia',59,27),(102,'Đôn ngồi sofa',59,27),(103,'Ghế ngồi sofa',59,27),(104,'Bàn trà ',59,27),(105,'Ốp tường',59,27),(106,'Kệ tivi',59,27),(107,'Tủ trang trí (tủ rượu)',59,27),(108,'Tủ giày Gỗ CN',59,27),(112,'Ốp tường',40,27),(113,'Giường ngủ bọc da nỉ',60,27),(115,'Tap đầu giường',60,27),(116,'Bàn phấn',60,27),(117,'Ghế trang điểm',60,27),(118,'Gương trang điểm',60,27),(119,'Tủ quần áo',60,27),(120,'Ốp đầu giường',60,27),(121,'Bàn làm việc (Bàn học)',60,27),(122,'Kệ sách Gỗ CN',60,27),(123,'Đôn tư giãn',60,27),(124,'Ghế nghỉ',60,27),(125,'Kệ tivi',60,27),(126,'Tủ trang trí (tủ rượu)',60,27),(127,'Tủ bếp dưới',61,27),(128,'Tủ bếp trên ',61,27),(129,'Tủ bếp kịch trần ',61,27),(130,'Bàn đảo bếp',61,27),(131,'Đợt trang trí',61,27),(132,'Bàn ăn',61,27),(133,'Ghế ăn',61,27),(134,'Ốp vách',61,27),(135,'Tủ trang trí (tủ rượu)',61,27),(142,'Ghế sofa',62,27),(143,'Bàn trà ',62,27),(144,'Ốp vách',62,27),(145,'Kệ tivi',62,27),(146,'Tủ trang trí (tủ rượu)',62,27),(147,'Tủ giày',62,27),(150,'Đợt trang trí',62,27),(151,'Giường ngủ',63,27),(152,'Tap đầu giường',63,27),(153,'Bàn phấn',63,27),(154,'Ghế trang điểm',63,27),(155,'Gương trang điểm',63,27),(156,'Tủ áo',63,27),(157,'Ốp đầu giường',63,27),(158,'Bàn làm việc (Bàn học)',63,27),(159,'Đợt trang trí',63,27),(160,'Giá sách',63,27),(161,'Đôn tư giãn',63,27),(162,'Ghế nghỉ',63,27),(163,'Kệ tivi',63,27),(164,'Tủ trang trí',63,27),(165,'Đợt trang trí',59,27),(166,'Đợt trang trí',60,27),(167,'Đợt trang trí\n',41,27),(168,'Đợt trang trí',42,27),(169,'Tủ bếp dưới ',64,27),(170,'Tủ bếp trên ',64,27),(171,'Tủ bếp kịch trần',64,27),(172,'Bàn đảo bếp',64,27),(173,'Đợt trang trí',64,27),(174,'Bàn ăn',64,27),(175,'Ghế ăn',64,27),(176,'Tủ trang trí (tủ rượu)',64,27),(181,'Ghế sofa',65,27),(182,'Bàn trà ',65,27),(183,'Ốp vách',65,27),(184,'Kệ tivi',65,27),(185,'Tủ trang trí (tủ rượu)',65,27),(186,'Tủ giày',65,27),(187,'Đợt trang trí',65,27),(190,'Giường ngủ',66,27),(191,'Tap đầu giường',66,27),(192,'Bàn phấn',66,27),(193,'Ghế trang điểm',66,27),(194,'Gương trang điểm',66,27),(195,'Tủ áo',66,27),(196,'Ốp đầu giường',66,27),(197,'Bàn làm việc (Bàn học)',66,27),(198,'Giá sách',66,27),(199,'Đôn thư giãn',66,27),(200,'Ghế nghỉ',66,27),(201,'Tủ trang trí (tủ rượu)',66,27),(202,'Kệ tivi',66,27),(204,'Đợt trang trí',66,27),(205,'Tủ bếp dưới ',67,27),(206,'Tủ bếp trên ',67,27),(207,'Tủ bếp kịch trần',67,27),(208,'Bàn đảo bếp',67,27),(209,'Đợt trang trí',67,27),(210,'Bàn ăn',67,27),(211,'Ghế ăn',67,27),(212,'Tháo dỡ sàn gõ cũ',68,27),(213,'Tháo dỡ gạch ốp tường, sàn đá hoa cũ',68,27),(214,'Ốp tường, sàn đá hoa',68,27),(215,'Vật liệu ốp sàn',68,27),(216,'Hạng mục đập phá',68,27),(217,'Hạng mục xây mới',68,27),(218,'Trát tường',68,27),(219,'Đổ lanh tô cửa đi mới',68,27),(220,'Đóng bao phế liệu',68,27),(221,'Chống thấm ',68,27),(222,'Cầm cửa, trát cạnh',68,27),(224,'Tháo, lắp điều hòa',69,27),(225,'Đi dây đồng điều hòa',69,27),(226,'Nhân công đi điện, nước',69,27),(227,'Dây điện ống nước, ô cắm, công tắc thay, thêm mới',69,27),(228,'Tháo dỡ phần thạch cao cũ',70,27),(229,'Làm mới thạch cao',70,27),(230,'Phào tường Tân cổ điển',70,27),(231,'Phào trần Tân cổ điển',70,27),(232,'Sơn lại tường nhà',71,27),(233,'Sơn lại trần nhà',71,27),(234,'Sàn gỗ An Cường 8mm',72,27),(235,'Sàn gỗ An Cường 12mm',72,27),(236,'Sàn gỗ An Cường xương cá 12mm kiểu công nghiệp',72,27),(237,'Sàn gỗ An Cường xương cá 12mm kiểu tự nhiên',72,27),(238,'Sàn gỗ Malaisya\nJOHOR; BORNEO; SAMALAY; SEMPORNA; SMATWOOD,...',72,27),(239,'Phào chân tường',72,27),(240,'Lót sàn',72,27),(244,'rrrr',73,5);
/*!40000 ALTER TABLE `hangmuc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `noithat`
--

DROP TABLE IF EXISTS `noithat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `noithat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `phong_cach_id` int DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `phong_cach_id` (`phong_cach_id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `noithat_ibfk_1` FOREIGN KEY (`phong_cach_id`) REFERENCES `phongcachnoithat` (`id`) ON DELETE CASCADE,
  CONSTRAINT `noithat_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `noithat`
--

LOCK TABLES `noithat` WRITE;
/*!40000 ALTER TABLE `noithat` DISABLE KEYS */;
INSERT INTO `noithat` VALUES (1,'<Thêm mới 0>',1,1),(2,'<Thêm mới 1>',1,1),(3,'<Thêm mới 2>',1,1),(4,'<Thêm mới 3>',1,1),(5,'<Thêm mới 4>',1,1),(6,'<Thêm mới 0>',2,1),(7,'<Thêm mới 1>',2,1),(8,'<Thêm mới 2>',2,1),(9,'<Thêm mới 3>',2,1),(10,'<Thêm mới 4>',2,1),(11,'<Thêm mới 5>',2,1),(12,'<Thêm mới 6>',2,1),(13,'<Thêm mới 7>',2,1),(40,'NỘI THẤT PHÒNG BẾP',34,27),(41,'NỘI THẤT PHÒNG KHÁCH',34,27),(42,'NỘI THẤT PHÒNG NGỦ',34,27),(50,'PHỤ KIỆN ',48,27),(52,'Lavabo',50,27),(53,'Vách kính nhà tắm',50,27),(54,'Gương lavabo\n',50,27),(55,'Bàn thờ chính',51,27),(56,'Bàn thờ phụ',51,27),(57,'Vách ốp bàn thờ',51,27),(58,'NỘI THẤT PHÒNG BẾP',35,27),(59,'NỘI THẤT PHÒNG KHÁCH',35,27),(60,'NỘI THẤT PHÒNG NGỦ',35,27),(61,'NỘI THẤT PHÒNG BẾP',36,27),(62,'NỘI THẤT PHÒNG KHÁCH',36,27),(63,'NỘI THẤT PHÒNG NGỦ',36,27),(64,'NỘI THẤT PHÒNG BẾP',37,27),(65,'NỘI THẤT PHÒNG KHÁCH',37,27),(66,'NỘI THẤT PHÒNG NGỦ',37,27),(67,'NỘI THẤT PHÒNG BẾP',38,27),(68,'HẠNG MỤC PHÁ DỠ SỬA CHỮA XÂY MỚI',40,27),(69,'HẠNG MỤC ĐIỆN NƯỚC ĐIỀU HÒA',40,27),(70,'HẠNG MỤC TRẦN',40,27),(71,'HẠNG MỤC SƠN ',40,27),(72,'HẠNG MỤC SÀN',40,27),(73,'yttt',8,5),(105,'<Thêm mới 1>',8,5);
/*!40000 ALTER TABLE `noithat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phongcachnoithat`
--

DROP TABLE IF EXISTS `phongcachnoithat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phongcachnoithat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `phongcachnoithat_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phongcachnoithat`
--

LOCK TABLES `phongcachnoithat` WRITE;
/*!40000 ALTER TABLE `phongcachnoithat` DISABLE KEYS */;
INSERT INTO `phongcachnoithat` VALUES (1,'Phong cách hiện đại 2',1),(2,'Phong cách hiện đại (1)',1),(3,'Phong cách cổ điển',1),(4,'Phong cách TEST',1),(8,'Phong cách hiện đại(1)',5),(34,'NỘI THẤT PHONG CÁCH HIỆN ĐẠI ',27),(35,'NỘI THẤT TÂN CỔ ĐIỂN SANG TRỌNG\n',27),(36,'NỘI THẤT CAO CẤP GỖ ÓC CHÓ',27),(37,'NỘI THẤT GỖ SỒI MỸ ',27),(38,'NỘI THẤT PHÒNG BẾP_ GỖ GÕ',27),(40,'HẠNG MỤC SỬA CHỮA PHẦN THÔ',27),(48,'PHỤ KIỆN',27),(50,'NỘI THẤT WC',27),(51,'PHÒNG THỜ',27),(52,'NỘI THẤT PHONG CÁCH HIỆN ĐẠI ',2),(53,'NỘI THẤT TÂN CỔ ĐIỂN SANG TRỌNG\n',2),(54,'NỘI THẤT CAO CẤP GỖ ÓC CHÓ',2),(55,'NỘI THẤT GỖ SỒI MỸ ',2),(56,'NỘI THẤT PHÒNG BẾP_ GỖ GÕ',2),(57,'HẠNG MỤC SỬA CHỮA PHẦN THÔ',2),(58,'PHỤ KIỆN',2),(59,'NỘI THẤT WC',2),(60,'PHÒNG THỜ',2);
/*!40000 ALTER TABLE `phongcachnoithat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `role` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `roles_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,1,'ROLE_USER'),(2,2,'ROLE_ADMIN'),(4,4,'ROLE_USER'),(5,5,'ROLE_USER'),(14,15,'ROLE_USER'),(15,16,'ROLE_USER'),(16,17,'ROLE_USER'),(22,27,'ROLE_USER'),(25,30,'ROLE_USER'),(26,31,'ROLE_USER'),(27,32,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thongso`
--

DROP TABLE IF EXISTS `thongso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thongso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dai` float DEFAULT NULL,
  `rong` float DEFAULT NULL,
  `cao` float DEFAULT NULL,
  `don_vi` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `don_gia` int DEFAULT NULL,
  `vatlieu_id` int DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vatlieu_id` (`vatlieu_id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `thongso_ibfk_1` FOREIGN KEY (`vatlieu_id`) REFERENCES `vatlieu` (`id`) ON DELETE CASCADE,
  CONSTRAINT `thongso_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thongso`
--

LOCK TABLES `thongso` WRITE;
/*!40000 ALTER TABLE `thongso` DISABLE KEYS */;
INSERT INTO `thongso` VALUES (1,0,100.25,0,'cm',999999,1,1),(2,0,0,0,'',0,7,1),(3,0,0,0,'',0,8,1),(4,0,0,0,'',0,14,27),(5,0,0,0,'',0,41,27),(6,0,0,0,'',0,95,27),(7,0,0,0,'',0,131,27),(8,0,0,0,'',0,133,27),(10,0,0,0,'',0,163,27),(11,0,0,0,'',0,181,27),(12,0,0,0,'',0,212,27),(13,9999,12321,11111,'met',999999,2,1),(14,11,12,13,'met',300,273,5),(15,0,600,870,'Mét dài',2550000,61,27),(16,0,600,870,'Mét dài',3050000,62,27),(17,0,600,870,'Mét dài',3450000,63,27),(18,0,600,870,'Mét dài',3950000,64,27),(19,0,600,870,'Mét dài',6750000,65,27),(20,0,350,800,'Mét dài',1800000,69,27),(21,0,350,800,'Mét dài',23500000,70,27),(22,0,350,800,'Mét dài',2700000,71,27),(23,0,350,870,'Mét dài',6750000,72,27),(24,0,350,400,'Mét dài',1435000,74,27),(25,0,350,400,'Mét dài',1790000,75,27),(26,0,350,400,'Mét dài',1950000,76,27),(27,0,350,400,'Mét dài',4750000,77,27),(28,0,600,870,'Mét dài',3550000,81,27),(29,0,600,870,'Mét dài',4150000,82,27),(30,0,600,870,'Mét dài',5490000,83,27),(31,0,600,870,'Mét dài',10850000,84,27),(32,0,300,36,'Mét dài',350,85,27),(33,1800,800,750,'Bộ',5350000,86,27),(34,500,50,900,'Chiếc',1550000,87,27),(35,0,350,0,'Mét vuông',1950000,88,27),(36,0,8,0,'Mét vuông',1550000,135,27),(37,12.5,9.2,3334.5,'cm',1000,3,1),(38,0,9.2,0,'cm',1234,4,1),(39,0,34,0,'Mét vuông',1100000,136,27),(40,0,34,0,'Mét vuông',1650000,137,27),(41,0,5,0,'Mét vuông',998000,138,27),(42,0,5,0,'Mét vuông',1398000,139,27);
/*!40000 ALTER TABLE `thongso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vatlieu`
--

DROP TABLE IF EXISTS `vatlieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vatlieu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `hang_muc_id` int DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hang_muc_id` (`hang_muc_id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `vatlieu_ibfk_1` FOREIGN KEY (`hang_muc_id`) REFERENCES `hangmuc` (`id`) ON DELETE CASCADE,
  CONSTRAINT `vatlieu_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vatlieu`
--

LOCK TABLES `vatlieu` WRITE;
/*!40000 ALTER TABLE `vatlieu` DISABLE KEYS */;
INSERT INTO `vatlieu` VALUES (1,'ádflkjnklàlkfádlk;jfádlk;fsdlk;fdslkfsdlfkdsjlfkdsjl;jgádkljlskdjflsdkjfpoqưeioprfjnmcvlmncm,nvsdlakj\nádálkdjkl;fád\nừakládfjsaldkfs\nàkp;ladsfkad;klsk',1,1),(2,'sadfsdfágjklhsjdfvjkl;hsadfốidvjknjknsdvkớljdfiohjio	dpikòádj0-dsfjksđấọiádikfgộikj	qkljádfkljsdfksdfjk',1,1),(3,'<Thêm mới 2>',1,1),(4,'<Thêm mới 3>',1,1),(5,'<Thêm mới 4>',1,1),(6,'<Thêm mới 5>',1,1),(7,'<Thêm mới 0>',7,1),(8,'<Thêm mới 1>',7,1),(9,'<Thêm mới 2>',7,1),(10,'<Thêm mới 3>',7,1),(11,'<Thêm mới 4>',7,1),(12,'<Thêm mới 5>',7,1),(13,'<Thêm mới 6>',7,1),(14,'Bản lề chính hãng Eurogold',28,27),(15,'Thiết bị hãng hafele nhập khẩu Đức',28,27),(17,'Ray bi 3 lớp sơn tĩnh điện',29,27),(18,'Ray bi inox 3 lớp Eurogold',29,27),(19,'Ray âm giảm chấn Eurogold',29,27),(20,'Thiết bị hãng hafele nhập khẩu Đức',29,27),(21,'Ray lùa\nBộ gồm 2 bánh xe 2 dẫn hướng, 2 ray trên dưới. Dùng cho 1 cách lùa',30,27),(22,'Ray lùa giảm chấn\nBộ gồm 2 bánh xe 2 dẫn hướng, 2 ray trên dưới, 2 giảm chấn. Dùng cho 1 cách lùa',30,27),(23,'Chống cong vênh\nBộ gồm 2 thanh ren, 2 thanh che ren, 4 đai ốc vuông. 1 lục giác, 1 nắp che đầu kéo, 4 bas nhựa, 1 nối ren ở giữa, 1 hướng dẫn lắp đặp. 1 cánh dùng 2 bộ',31,27),(24,'Giá bát cố định inox 304 A One gia công',32,27),(25,'Giá bát inox cố định Tủ trên Eurogold',32,27),(26,'Giá bát inox cố định Tủ dưới Eurogold',32,27),(27,'Giá bát nâng hạ Eurogold',32,27),(28,'Giá bát nâng hạ Hafele Cucina',32,27),(29,'Pittong nâng cánh Eurogold',33,27),(30,'Pittong nâng cánh Hafele\n',33,27),(31,'Tay nâng giá bát Blum nhập khẩu Áo\nBộ tay nâng aventos hf nắp đạy màu trắng hoặc đen. Tay nâng, chặn góc mở',34,27),(32,'Giá xoong nồi inox 304 A One Gia Công',35,27),(33,'Giá xoong nồi inox chính hãng Eurogold',35,27),(34,'Giá xoong nồi inox chính hãng Hafele',35,27),(35,'Giá dao thớt inox 304 A One Gia Công',36,27),(36,'Giá dao thớt inox chính hãng Eurogold ',36,27),(37,'Giá bát inox cố định Tủ trên Hafele\n',36,27),(38,'Khay thìa dĩa Eurogold ',37,27),(39,'Giá gia vị inox 304 A One Gia Công',38,27),(40,'Giá gia vị inox chính hãng Eurogold ',38,27),(41,'Giá gia vị inox chính hãng Hafele\n',38,27),(43,'Kính bếp Cường lực Hải Long sơn màu đơn sắc',39,27),(44,'Kính bếp Cường lực Hải Long Màu có nhũ ánh kim',39,27),(45,'Kính bếp Cường lực Hải Long in hình ảnh 3D',39,27),(46,'Gạch bông nhân công ốp, vật liệu',40,27),(47,'Gạch bông loại vảy cá',40,27),(48,' Đá bếp kim xa, vân mây,vàng,…',41,27),(49,'Đá Vicostone\nBáo theo mã đá\nCác nhóm A, B, C, D, E, F giá dao động từ 3,000,000 vnđ-10,000,000 vnđ',41,27),(50,'Bo soi biên dạng đá ',42,27),(51,'Đèn led bếp nhập Hàn Quốc, cảm biến vãy mở, bộ đổi nguồn 220V-12V, dây điện, công thợ điện lắp đặt \nĐơn giá tính dưới 3m, Trên 3m mỗi mét tính thêm 220,000 vnđ',43,27),(52,'Đèn led bếp nhập Hàn Quốc, cảm biến vãy mở, bộ đổi nguồn 220V-12V, dây điện, công thợ điện lắp đặt\nNgăn kéo từ 300-900mm',44,27),(53,'Thùng gạo Eurogold',45,27),(54,'Thùng rác Eurogold',46,27),(55,'Tay nắm cửa theo thiết kế/ mẫu đã chọn',47,27),(56,'Tủ kho 2 tầng Eurogold',49,27),(57,'Tủ kho 4 tầng Eurogold',49,27),(58,'Tủ kho 6 tầng Eurogold',49,27),(59,'Tủ kho 6 tầng Hafele',49,27),(60,'Thiết bị hãng hafele nhập khẩu Đức',46,27),(61,'- Thùng: nhựa Picomat 17mm chống nước tuyệt đối. \n- Cánh: MDF chống ẩm 17mm phủ Melamine An Cường.\n- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',14,27),(62,'- Thùng: nhựa Picomat 17mm chống nước tuyệt đối. \n- Cánh: MDF chống ẩm 17mm phủ Laminate An Cường.\n- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',14,27),(63,'- Thùng: nhựa Picomat 17mm chống nước tuyệt đối. \n- Cánh: MDF chống ẩm 17mm phủ Acrylic An Cường.\n- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',14,27),(64,'- Thùng: Inox 304 \n - Cánh: MDF  chống ẩm 17mm phủ Acrylic An Cường.\n- Hậu: tấm inox 304',14,27),(65,'Thùng: INOX 304 dập khung sơn tĩnh điện màu tùy chọn\n Cánh: Khung nhôm nhập khẩu Hàn Quốc, kính cường lực Hải long\nHậu: Tấm Inox 304 sơn tĩnh điện màu tùy chọn\n',14,27),(69,'Thùng và cánh: MDF chống ẩm 17mm phủ Melamine An Cường\n- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',15,27),(70,'Thùng: MDF chống ẩm 17mm phủ melamin An Cường\nCánh: MDF chống ẩm 17mm phủ Laminate An Cường\n- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',15,27),(71,'Thùng: MDF chống ẩm 17mm phủ melamin An Cường\nCánh: MDF chống ẩm 17mm phủ Acrylic An Cường\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối',15,27),(72,'Thùng: INOX 304 dập khung sơn tĩnh điện màu tùy chọn\n Cánh: Khung nhôm nhập khẩu Hàn Quốc, kính cường lực Hải long\nHậu: Tấm Inox 304 sơn tĩnh điện màu tùy chọn\n',15,27),(74,'Thùng và cánh: MDF chống ẩm 17mm phủ Melamine An Cường\n- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',16,27),(75,'Thùng: MDF chống ẩm 17mm phủ melamin An Cường\nCánh: MDF chống ẩm 17mm phủ Laminate An Cường\n- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',16,27),(76,'Thùng: MDF chống ẩm 17mm phủ melamin An Cường\nCánh: MDF chống ẩm 17mm phủ Acrylic An Cường\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối',16,27),(77,'Thùng: INOX 304 dập khung sơn tĩnh điện màu tùy chọn\n Cánh: Khung nhôm nhập khẩu Hàn Quốc, kính cường lực Hải long\nHậu: Tấm Inox 304 sơn tĩnh điện màu tùy chọn\n',16,27),(81,'-Thùng: nhựa Picomat 17mm  chống nước tuyệt đối\n-Cánh: MDF chống ẩm 17mm phủ Melamine An Cường \n- Mặt lộ: MDF chống ẩm 17mm phủ Melamine An Cường ',17,27),(82,'- Thùng: nhựa Picomat 17mm chống nước tuyệt đối. \n- Cánh: MDF chống ẩm 17mm phủ Laminate An Cường.\n- Mặt lộ: MDF chống ẩm 17mm phủ Laminate An Cường.',17,27),(83,'- Thùng: nhựa Picomat 17mm chống nước tuyệt đối. \n- Cánh: MDF chống ẩm 17mm phủ Acrylic An Cường.\n- Mặt lộ:  MDF chống ẩm 17mm phủ Acrylic An Cường.',17,27),(84,'Thùng: INOX 304 dập khung sơn tĩnh điện màu tùy chọn\n Cánh: Khung nhôm nhập khẩu Hàn Quốc, kính cường lực Hải long\nMặt lộ:Khung nhôm nhập khẩu Hàn Quốc, kính cường lực Hải long\n',17,27),(85,'MDF chống ẩm phủ melamin An Cường',18,27),(86,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',56,27),(87,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',57,27),(88,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',58,27),(90,'Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',61,27),(91,'Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',62,27),(92,'Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',63,27),(93,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',64,27),(94,'Vách ốp dạng tấm phẳng \nMDF chống ẩm 34 phủ Melamine An Cường',65,27),(95,'Vách ốp soi CNC, Đan nan\nMDF chống ẩm 34 phủ Melamine An Cường',65,27),(96,'Vách ốp nhựa vân đá ',65,27),(97,'Vách ốp nhựa vân đá in \n( dùng cho các tấm không có sẵn hàng, vân đối xứng,...)',65,27),(98,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',66,27),(100,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',67,27),(101,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',68,27),(102,'MDF chống ẩm 17mm phủ Melamine An Cường\nĐầu giường, đuôi giường, thành giường ghép 2 lân gỗ 34mm',77,27),(108,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',78,27),(109,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',79,27),(110,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',80,27),(111,'Gương trang điểm dây da mặt gương bỉ',81,27),(112,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',82,27),(113,'- Thùng: MDF chống ẩm 17mm phủ Melamine An Cường\n- Cánh: MDF chống ẩm 17mm phủ Acrylic An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',82,27),(114,'- Thùng: MDF chống ẩm 17mm phủ Melamine An Cường\n- Cánh: khung nhôm kính cường lực Hải Long 8 ly\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',82,27),(117,'Vách ốp dạng tấm phẳng \nMDF chống ẩm 34 phủ Melamine An Cường',83,27),(118,'Vách ốp soi CNC, Đan nan\nMDF chống ẩm 34 phủ Melamine An Cường',83,27),(119,'Vách ốp nhựa vân đá ',83,27),(120,'Vách ốp nhựa vân đá in \n( dùng cho các tấm không có sẵn hàng, vân đối xứng,...)',83,27),(121,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',84,27),(122,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',85,27),(123,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường\nMặt ngồi: Mút D40 bọc da nỉ công nghiệp Hàn Quốc',86,27),(124,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',87,27),(125,'Thùng cánh: MDF chống ẩm 17mm phủ Melamine An Cường\nHậu: MDF chống ẩm 6mm phủ Melamin An Cường',88,27),(127,'- Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n- Thùng: nhựa Picomat 17mm chống nước tuyệt đối\n- Hậu: Nhôm Alu 3mm chống nước tuyệt đối',89,27),(128,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp \n- Hậu: Nhôm Alu 3mm chống nước tuyệt đối',90,27),(129,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp \n- Hậu: Nhôm Alu 3mm chống nước tuyệt đối',91,27),(130,'- Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n- Thùng: nhựa Picomat 17mm chống nước tuyệt đối\n- Hậu: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC',92,27),(131,' MDF chống ẩm  An Cường phủ sơn Inchem 5 lớp',93,27),(132,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',94,27),(133,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',95,27),(134,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',96,27),(135,'Gương trang trí nghệ thuật',112,27),(136,'Vách ốp dạng tấm phẳng \nMDF chống ẩm 34 phủ Melamine An Cường',112,27),(137,'Vách ốp soi CNC, Đan nan\nMDF chống ẩm 34 phủ Melamine An Cường',112,27),(138,'Vách ốp nhựa vân đá ',112,27),(139,'Vách ốp nhựa vân đá in \n( dùng cho các tấm không có sẵn hàng, vân đối xứng,...)',112,27),(140,'Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',101,27),(141,'Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',102,27),(142,'Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',103,27),(143,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',104,27),(144,'Gương trang trí nghệ thuật',105,27),(146,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',106,27),(147,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',107,27),(148,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',108,27),(149,'Khung gỗ MDF lõi xanh chống ẩm An Cường, bọc đệm mút D40, bọc da hoặc nỉ theo mẫu chọn',113,27),(151,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',115,27),(152,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',116,27),(153,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',117,27),(154,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',118,27),(155,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',119,27),(157,'MDF chống ẩm An Cường, bọc da nỉ Hàn Quốc cao cấp',120,27),(158,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',121,27),(159,'-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',122,27),(160,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp \nMặt có lớp đệm da hoặc nỉ chọn theo mẫu thực tế',123,27),(161,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp \nMặt có lớp đệm da hoặc nỉ chọn theo mẫu thực tế',124,27),(162,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',125,27),(163,'-Cánh: MDF chống ẩm  An Cường 25mm phủ sơn Inchem 5 lớp, soi họa tiết CNC\n-Thùng: MDF chống ẩm  An Cường 17mm  phủ sơn Inchem 5 lớp\n- Hậu: MDF chống ẩm  An Cường 6mm  phủ sơn Inchem 5 lớp ',126,27),(165,'-Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\n-Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối\n-Sơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',127,27),(172,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',128,27),(173,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',129,27),(174,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nMặt Lộ: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',130,27),(175,'Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',131,27),(176,'Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',132,27),(177,'Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',133,27),(178,'- Veneer: gỗ Óc chó\n- Lõi MDF chống ẩm An Cường (đan nan, cắt CNC,…)',134,27),(179,'MDF chống ẩm phủ Laminate vân đá An Cường',134,27),(180,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',135,27),(181,'Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',142,27),(182,'Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',143,27),(183,'- Veneer: gỗ Óc chó\n- Lõi MDF chống ẩm An Cường (đan nan, cắt CNC,…)',144,27),(184,'MDF chống ẩm phủ Laminate vân đá An Cường',144,27),(185,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',145,27),(186,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',146,27),(187,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',147,27),(188,'Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',150,27),(189,'Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm, Xương đơ 7 hộp kẽm 40*40\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu\nDát giường: Veneer óc chó 18mm\n',151,27),(190,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',152,27),(191,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',153,27),(192,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',154,27),(193,'Gương trang điểm khung gỗ Óc chó nhập khẩu, mặt gương của Bỉ',155,27),(194,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',156,27),(195,'- Veneer: gỗ Óc chó\n- Lõi MDF chống ẩm An Cường (đan nan, cắt CNC,…)',157,27),(196,'MDF chống ẩm phủ Laminate vân đá An Cường',157,27),(197,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',158,27),(198,'Thùng: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',160,27),(199,'Thùng: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu\nMặt: bọc da hoặc nỉ theo mẫu thực tế',161,27),(200,'Thùng: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu\nMặt: bọc da hoặc nỉ theo mẫu thực tế',162,27),(201,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',163,27),(202,'Thùng , cánh: Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer óc chó 6mm\nSơn: Pu 5 lớp, 3 lớp lót, 2 lớp màu',164,27),(203,'Gỗ Óc chó nhập khẩu Châu Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',159,27),(204,' MDF chống ẩm  An Cường phủ sơn Inchem 5 lớp',165,27),(205,' MDF chống ẩm  An Cường phủ sơn Inchem 5 lớp',166,27),(206,'MDF chống ẩm phủ melamin An Cường',167,27),(207,'MDF chống ẩm phủ melamin An Cường',168,27),(208,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',169,27),(209,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',170,27),(210,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',171,27),(211,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nMặt lộ: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',172,27),(212,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',173,27),(213,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',174,27),(214,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',175,27),(215,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',176,27),(216,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',181,27),(217,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',182,27),(218,'- Veneer: gỗ Sồi Mỹ\n- Lõi MDF chống An Cường (đan nan, cắt CNC,…)',183,27),(219,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',184,27),(220,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',185,27),(221,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',186,27),(222,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',187,27),(223,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\ncác thành phần có độ dày lớn hơn 22mm dùng phương pháp ghép tấm, Xương đơ 7 hộp kẽm 40*40\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu\nDát giường: Veneer sồi 18mm',190,27),(224,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',191,27),(225,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',192,27),(226,'Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',193,27),(227,'Gương trang điểm khung gỗ Sồi Mỹ nhập khẩu, mặt gương của Bỉ',194,27),(228,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',195,27),(229,'- Veneer: gỗ Sồi Mỹ\n- Lõi MDF chống An Cường (đan nan, cắt CNC,…)',196,27),(230,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',197,27),(231,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',198,27),(232,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu\nMặt: bọc da hoặc nỉ theo mẫu thực tế',199,27),(233,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu\nMặt: bọc da hoặc nỉ theo mẫu thực tế',200,27),(234,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',201,27),(235,'Thùng, cánh: Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nHậu: Veneer sồi 6mm \nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',202,27),(236,'Gỗ Sồi Mỹ nhập khẩu Bắc Âu, gỗ thô tiêu chuẩn dày 1 Inch, thành phẩm dày 22mm\nSơn: PU 5 lớp, 3 lớp lót, 2 lớp màu',204,27),(237,'Thùng, cánh: Gỗ Sồi gõ đỏ nhập khẩu Nam Phi, Thành phẩm dày 22mm\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: Inchem 5 lớp của Mỹ, 3 lớp lót, 2 lớp màu',205,27),(238,'Thùng, cánh: Gỗ Sồi gõ đỏ nhập khẩu Nam Phi, Thành phẩm dày 22mm\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: Inchem 5 lớp của Mỹ, 3 lớp lót, 2 lớp màu',206,27),(239,'Thùng, cánh: Gỗ Sồi gõ đỏ nhập khẩu Nam Phi, Thành phẩm dày 22mm\nHậu: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: Inchem 5 lớp của Mỹ, 3 lớp lót, 2 lớp màu',207,27),(240,'Thùng, cánh: Gỗ Sồi gõ đỏ nhập khẩu Nam Phi, Thành phẩm dày 22mm\nMặt lộ: Nhôm Alu dày 3mm chống nước tuyệt đối\nSơn: Inchem 5 lớp của Mỹ, 3 lớp lót, 2 lớp màu',208,27),(241,'Gỗ gõ nhập khẩu Nam Phi\nSơn: Inchem 5 lớp của Mỹ, 3 lớp lót, 2 lớp màu',209,27),(242,'Gỗ Sồi gõ đỏ nhập khẩu Nam Phi\nSơn: Inchem 5 lớp của Mỹ, 3 lớp lót, 2 lớp màu\nGiá ước tính, giá thực tế phụ thuộc theo thiết kế',210,27),(243,'Gỗ Sồi gõ đỏ nhập khẩu Nam Phi\nSơn: Inchem 5 lớp của Mỹ, 3 lớp lót, 2 lớp màu\nGiá ước tính, giá thực tế phụ thuộc theo thiết kế',211,27),(244,'Nhân công tháo dỡ',212,27),(245,'Nhân công tháo dỡ',213,27),(246,'Nhân công và chưa gồm vật liệu ',214,27),(247,'gạch viglacera, Giá theo hóa đơn',215,27),(248,'Nhân công đập phá',216,27),(249,'Nhân công xây dụng, gạch, xi, cát',217,27),(250,'Nhân công gạch xi măng, cát',218,27),(251,'Nhân công gạch xi măng, cát',222,27),(253,'Nhân công sỏi, gạch xi măng, cát đổ bê tông',219,27),(254,'Nhân công và vận chuyển',220,27),(255,'Nhân công và vật liệu',221,27),(256,'Tháo lắp và bảo dưỡng cơ bản điều hòa',224,27),(257,'Vật liệu ống đồng điều hòa',225,27),(258,'Nhân công lắp đặt điện nước',226,27),(259,'Dây điện Trần phú, ông nước nhựa PVC',227,27),(260,'Nhân công đập phá',228,27),(261,'Nhân công, xương vĩnh tường, thạch cao',229,27),(262,'Thạch cao, hoàn thiện lắp đặt và sơn',230,27),(263,'Thạch cao, hoàn thiện lắp đặt và sơn',231,27),(264,'Sơn Dulux vật liệu, nhân công sơn bả, hoàn thiện 3 lớp ',232,27),(265,'Sơn Dulux vật liệu, nhân công sơn bả, hoàn thiện 3 lớp ',233,27),(266,'Sàn gỗ 8mm Laminate Flooring',234,27),(267,'Sàn gỗ 12mm Laminate Flooring',235,27),(268,'Sàn gỗ 12mm chevron Laminate Flooring',236,27),(269,'Sàn gỗ 12mm Heringbone Laminate Flooring',237,27),(270,'Sàn gỗ nhập khẩu Malaisya, Sàn HDF siêu chống ẩm bảo hành 10 năm',238,27),(271,'Phào  chân tường gỗ, phào nhựa màu tùy chọn',239,27),(272,'Lót cao su non',240,27),(273,'fasdasd',244,5);
/*!40000 ALTER TABLE `vatlieu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-10  0:17:09