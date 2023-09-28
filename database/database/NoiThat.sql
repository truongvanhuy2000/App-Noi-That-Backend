CREATE TABLE `account`
(
    `id`          INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `username`    VARCHAR(50) UNIQUE  NOT NULL,
    `password`    VARCHAR(50)         NOT NULL,
    `active`      TINYINT             NOT NULL,
    `info_id`     INTEGER             NOT NULL,
    `enabled`     TINYINT             NOT NULL,
    `expire_date` DATE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `accountinformation`
(
    `id`      INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`    NVARCHAR(255),
    `gender`  VARCHAR(255),
    `email`   VARCHAR(255),
    `address` VARCHAR(255),
    `phone`   VARCHAR(255)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `roles`
(
    `id`         INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `account_id` INTEGER             NOT NULL,
    `role`       VARCHAR(50)         NOT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `phongcachnoithat`
(
    `id`         INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`       NVARCHAR(255),
    `account_id` INTEGER
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `noithat`
(
    `id`            INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`          NVARCHAR(255),
    `phong_cach_id` INTEGER,
    `account_id`    INTEGER
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `hangmuc`
(
    `id`          INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`        NVARCHAR(255),
    `noi_that_id` INTEGER,
    `account_id`  INTEGER
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `vatlieu`
(
    `id`          INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`        NVARCHAR(500),
    `hang_muc_id` INTEGER,
    `account_id`  INTEGER
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_520_ci;

CREATE TABLE `thongso`
(
    `id`         INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `dai`        FLOAT,
    `rong`       FLOAT,
    `cao`        FLOAT,
    `don_vi`     NVARCHAR(100),
    `don_gia`    INTEGER,
    `vatlieu_id` integer,
    `account_id` INTEGER
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_520_ci;

-- Nested ref
ALTER TABLE `noithat`
    ADD FOREIGN KEY (`phong_cach_id`) REFERENCES `phongcachnoithat` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `hangmuc`
    ADD FOREIGN KEY (`noi_that_id`) REFERENCES `noithat` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `vatlieu`
    ADD FOREIGN KEY (`hang_muc_id`) REFERENCES `hangmuc` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `thongso`
    ADD FOREIGN KEY (`vatlieu_id`) REFERENCES `vatlieu` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `roles`
    ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `account`
    ADD FOREIGN KEY (`info_id`) REFERENCES `accountinformation` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

-- Ref to account id
ALTER TABLE `phongcachnoithat`
    ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `hangmuc`
    ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `vatlieu`
    ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `thongso`
    ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `noithat`
    ADD FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;