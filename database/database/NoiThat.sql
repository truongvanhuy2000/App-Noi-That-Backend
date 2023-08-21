CREATE TABLE `account` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `username` varchar(50) UNIQUE NOT NULL,
  `password` varchar(50) NOT NULL,
  `active` tinyint NOT NULL,
  `info_id` integer NOT NULL,
  `enabled` tinyint NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `accountinformation` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` nvarchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(255) UNIQUE NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `roles` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `phongcachnoithat` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` nvarchar(255) UNIQUE NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `noithat` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` nvarchar(255) UNIQUE NOT NULL,
  `phong_cach_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `hangmuc` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` nvarchar(255) UNIQUE NOT NULL,
  `noi_that_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `vatlieu` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` nvarchar(500) UNIQUE NOT NULL,
  `hang_muc_id` integer NOT NULL,
  `thong_so_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `thongso` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `dai` float,
  `rong` float,
  `cao` float,
  `don_vi` nvarchar(100) NOT NULL,
  `don_gia` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE `noithat` ADD FOREIGN KEY (`phong_cach_id`) REFERENCES `phongcachnoithat` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `hangmuc` ADD FOREIGN KEY (`noi_that_id`) REFERENCES `noithat` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `vatlieu` ADD FOREIGN KEY (`hang_muc_id`) REFERENCES `hangmuc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `vatlieu` ADD FOREIGN KEY (`thong_so_id`) REFERENCES `thongso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `roles` ADD FOREIGN KEY (`username`) REFERENCES `account` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `account` ADD FOREIGN KEY (`info_id`) REFERENCES `accountinformation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

INSERT INTO `accountinformation` (`name`, `gender`, `email`, `address`, `phone`)
VALUES
  ('John Doe', 'Male', 'john.doe@example.com', '123 Main St, City', '123-456-7890'),
  ('Jane Smith', 'Female', 'jane.smith@example.com', '456 Elm St, Town', '987-654-3210');

-- Account
INSERT INTO `account` (`username`, `password`, `active`, `info_id`, `enabled`)
VALUES
  ('john_doe', '{noop}123456', 1, 1, 1),
  ('admin', '{noop}admin', 1, 2, 1);

-- Roles
INSERT INTO `roles` (`username`, `role`)
VALUES
  ('john_doe', 'ROLE_USER'),
  ('admin', 'ROLE_ADMIN');

