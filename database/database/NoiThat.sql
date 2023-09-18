CREATE TABLE `account` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`username` varchar(50) UNIQUE NOT NULL,
`password` varchar(50) NOT NULL,
`active` tinyint NOT NULL,
`info_id` integer NOT NULL,
`enabled` tinyint NOT NULL,
`expire_date` DATE
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `accountinformation` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255) ,
`gender` varchar(255) ,
`email` varchar(255) ,
`address` varchar(255) ,
`phone` varchar(255) 
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `roles` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`account_id` integer NOT NULL,
`role` varchar(50) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `phongcachnoithat` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255) 
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `noithat` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255)  ,
`phong_cach_id` integer 
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `hangmuc` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255)  ,
`noi_that_id` integer 
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `vatlieu` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(500)  ,
`hang_muc_id` integer ,
`thong_so_id` integer 
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `thongso` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`dai` float,
`rong` float,
`cao` float,
`don_vi` nvarchar(100),
`don_gia` integer
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

ALTER TABLE `noithat` ADD FOREIGN KEY (`phong_cach_id`) REFERENCES `phongcachnoithat` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `hangmuc` ADD FOREIGN KEY (`noi_that_id`) REFERENCES `noithat` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `vatlieu` ADD FOREIGN KEY (`hang_muc_id`) REFERENCES `hangmuc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `vatlieu` ADD FOREIGN KEY (`thong_so_id`) REFERENCES `thongso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `roles` ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `account` ADD FOREIGN KEY (`info_id`) REFERENCES `accountinformation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
