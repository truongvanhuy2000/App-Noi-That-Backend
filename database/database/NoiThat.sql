CREATE TABLE `accountEntity` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`username` varchar(50) UNIQUE NOT NULL,
`password` varchar(50) NOT NULL,
`active` tinyint NOT NULL,
`info_id` integer NOT NULL,
`enabled` tinyint NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `accountinformation` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255) NOT NULL,
`gender` varchar(255) NOT NULL,
`email` varchar(255) UNIQUE NOT NULL,
`address` varchar(255) NOT NULL,
`phone` varchar(255) UNIQUE NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `roles` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`username` varchar(50) NOT NULL,
`role` varchar(50) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `phongcachnoithat` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `noithat` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255)  NOT NULL,
`phong_cach_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `hangmuc` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255)  NOT NULL,
`noi_that_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `vatlieu` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(500)  NOT NULL,
`hang_muc_id` integer NOT NULL,
`thong_so_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `thongso` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`dai` float,
`rong` float,
`cao` float,
`don_vi` nvarchar(100) NOT NULL,
`don_gia` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

ALTER TABLE `noithat` ADD FOREIGN KEY (`phong_cach_id`) REFERENCES `phongcachnoithat` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `hangmuc` ADD FOREIGN KEY (`noi_that_id`) REFERENCES `noithat` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `vatlieu` ADD FOREIGN KEY (`hang_muc_id`) REFERENCES `hangmuc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `vatlieu` ADD FOREIGN KEY (`thong_so_id`) REFERENCES `thongso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `roles` ADD FOREIGN KEY (`username`) REFERENCES `accountEntity` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `accountEntity` ADD FOREIGN KEY (`info_id`) REFERENCES `accountinformation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
