CREATE TABLE `account` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`username` varchar(50) UNIQUE NOT NULL,
`password` varchar(50) NOT NULL,
`active` tinyint NOT NULL,
`info_id` integer NOT NULL,
`enabled` tinyint NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `accountinformation` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255) NOT NULL,
`gender` varchar(255) NOT NULL,
`email` varchar(255) UNIQUE NOT NULL,
`address` varchar(255) NOT NULL,
`phone` varchar(255) UNIQUE NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `roles` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`username` varchar(50) NOT NULL,
`role` varchar(50) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `phongcachnoithat` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `noithat` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255)  NOT NULL,
`phong_cach_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `hangmuc` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(255)  NOT NULL,
`noi_that_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `vatlieu` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`name` nvarchar(500)  NOT NULL,
`hang_muc_id` integer NOT NULL,
`thong_so_id` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `thongso` (
`id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
`dai` float,
`rong` float,
`cao` float,
`don_vi` nvarchar(100) NOT NULL,
`don_gia` integer NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

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
INSERT INTO phongcachnoithat (name) VALUES
('HẠNG MỤC BỔ XUNG'),
('HẠNG MỤC SỬA CHỮA PHẦN THÔ'),
('NỘI THẤT CAO CẤP GỖ ÓC CHÓ PHIÊN BẢN GIỚI HẠN - WALNUT LIMITED EDITION'),
('NỘI THẤT GỖ SỒI MỸ - AMERICAN OAK EDITION'),
('NỘI THẤT PHONG CÁCH HIỆN ĐẠI - CONTEMPORARY STYLE'),
('NỘI THẤT TÂN CỔ ĐIỂN SANG TRỌNG - CONTEMPORATY CLASSIC');

INSERT INTO noithat (name,phong_cach_id) VALUES
('NỘI THẤT PHÒNG BẾP',1),
('NỘI THẤT PHÒNG KHÁCH',1),
('NỘI THẤT PHÒNG NGỦ',1),
('PHỤ KIỆN EUROGOLD',1),
('PHỤ KIỆN HAFELE',1),
('NỘI THẤT WC',1),
('NỘI THẤT PHÒNG BẾP',2),
('NỘI THẤT PHÒNG KHÁCH',2),
('NỘI THẤT PHÒNG NGỦ',2),
('NỘI THẤT WC',2),
('NỘI THẤT PHÒNG BẾP',3),
('NỘI THẤT PHÒNG KHÁCH',3),
('NỘI THẤT PHÒNG NGỦ',3),
('NỘI THẤT PHÒNG BẾP',4),
('NỘI THẤT PHÒNG KHÁCH',4),
('NỘI THẤT PHÒNG NGỦ',4),
('NỘI THẤT PHÒNG BẾP_ GỖ GÕ_ TƯƠNG ĐƯƠNG GỖ ÓC CHÓ',4),
('NỘI THẤT PHÒNG BẾP_ INOX NHÔM KÍNH',5),
('HẠNG MỤC PHÁ DỠ SỬA CHỮA XÂY MỚI',5),
('HẠNG MỤC ĐIỆN NƯỚC ĐIỀU HÒA',6),
('HẠNG MỤC TRẦN',6),
('HẠNG MỤC SƠN ',6),
('HẠNG MỤC SÀN',6);


INSERT INTO hangmuc (name,noi_that_id) VALUES
  ('Tủ bếp dưới ',1),
  ('Tủ bếp trên ',1),
  ('Tủ bếp kịch trần ',1),
  ('Bàn đảo bếp',1),
  ('Đợt trang trí',1),
  ('Bàn ăn',1),
  ('Ghế ăn',1),
  ('Ghế sofa văng',2),
  ('Đôn ngồi sofa',2),
  ('Ghế ngồi sofa',2),
  ('Bàn trà ',2),
  ('Ốp vách',2),
  ('Kệ tivi gỗ CN',2),
  ('Tủ trang trí (tủ rượu)',2),
  ('Tủ giày Gỗ CN',2),
  ('Giường ngủ',3),
  ('Tap đầu giường',3),
  ('Bàn phấn',3),
  ('Ghế trang điểm',3),
  ('Gương ',3),
  ('Tủ quần áo ',3),
  ('Ốp đầu giường',3),
  ('Bàn làm việc (Bàn học)',3),
  ('Kệ sách',3),
  ('Đôn thư giãn Ghế nghỉ',3),
  ('Bản lề',4),
  ('Ray ngăn kéo',4),
  ('Ray lùa tủ áo',4),
  ('Chống cong vênh cánh lùa tủ áo',4),
  ('Giá bát',4),
  ('Pittong',4),
  ('Tay nâng ',4),
  ('Giá xoong nồi',4),
  ('Giá dao thớt',4),
  ('Khay thìa dĩa',4),
  ('Giá gia vị',4),
  ('Kính bếp',4),
  ('Gạch bông',4),
  ('Đá bếp',4),
  ('Soi đá bếp',4),
  ('Đèn led tủ',4),
  ('Đèn led ngăn kéo bếp',4),
  ('Thùng rác',4),
  ('Tay nắm cửa',4),
  ('Bản lề',5),
  ('Ray ngăn kéo',5),
  ('Giá xoong nồi',5),
  ('Giá để dao thớt',5),
  ('Giá để gia vị',5),
  ('Pittong nâng cánh',5),
  ('Tủ kho 6 tầng',5),
  ('Thùng rác',5),
  ('Lavabo',6),
  ('Vách kính nhà tắm',6),
  ('Tủ bếp dưới ',7),
  ('Tủ bếp trên',7),
  ('Tủ bếp kịch trần ',7),
  ('Bàn đảo bếp',7),
  ('Đợt trang trí',7),
  ('Bàn ăn',7),
  ('Ghế ăn',7),
  ('Sofa tân cổ Italia',8),
  ('Đôn ngồi sofa',8),
  ('Ghế ngồi sofa',8),
  ('Bàn trà ',8),
  ('Ốp gương tường',8),
  ('Kệ tivi',8),
  ('Tủ trang trí (tủ rượu)',8),
  ('Tủ giày Gỗ CN',8),
  ('Giường ngủ bọc da nỉ',9),
  ('Giường ngủ khung gỗ kết hợp bọc da nỉ',9),
  ('Tap đầu giường',9),
  ('Bàn phấn',9),
  ('Ghế trang điểm',9),
  ('Gương trang điểm',9),
  ('Tủ quần áo',9),
  ('Ốp đầu giường',9),
  ('Bàn làm việc (Bàn học)',9),
  ('Đôn tư giãn',9),
  ('Ghế nghỉ',9),
  ('Lavabo',10),
  ('Vách kính nhà tắm',10),
  ('Tủ bếp dưới',11),
  ('Tủ bếp trên ',11),
  ('Tủ bếp kịch trần ',11),
  ('Bàn đảo bếp',11),
  ('Đợt trang trí',11),
  ('Bàn ăn',11),
  ('Ghế ăn',11),
  ('Ghế sofa',12),
  ('Bàn trà ',12),
  ('Ốp vách',12),
  ('Kệ tivi',12),
  ('Tủ trang trí (tủ rượu)',12),
  ('Tủ giày',12),
  ('Giường ngủ',13),
  ('Tap đầu giường',13),
  ('Bàn phấn',13),
  ('Ghế trang điểm',13),
  ('Gương trang điểm',13),
  ('Tủ áo',13),
  ('Ốp đầu giường',13),
  ('Bàn làm việc (Bàn học)',13),
  ('Đôn tư giãn',13),
  ('Ghế nghỉ',13),
  ('Tủ bếp dưới ',14),
  ('Tủ bếp trên ',14),
  ('Tủ bếp kịch trần',14),
  ('Bàn đảo bếp',14),
  ('Đợt trang trí',14),
  ('Bàn ăn',14),
  ('Ghế ăn',14),
  ('Ghế sofa',15),
  ('Bàn trà ',15),
  ('Ốp vách',15),
  ('Kệ tivi',15),
  ('Tủ trang trí (tủ rượu)',15),
  ('Tủ giày',15),
  ('Giường ngủ',16),
  ('Tap đầu giường',16),
  ('Bàn phấn',16),
  ('Ghế trang điểm',16),
  ('Gương trang điểm',16),
  ('Tủ áo',16),
  ('Ốp đầu giường',16),
  ('Bàn làm việc (Bàn học)',16),
  ('Giá sách',16),
  ('Đôn thư giãn',16),
  ('Ghế nghỉ',16),
  ('Tủ bếp dưới ',17),
  ('Tủ bếp trên ',17),
  ('Tủ bếp kịch trần',17),
  ('Bàn đảo bếp',17),
  ('Tủ bếp dưới ',18),
  ('Tủ bếp trên ',18),
  ('Tủ bếp tầng 2',18),
  ('Bàn đảo bếp',18),
  ('Phát sinh ',18),
  ('Nẹp xung quanh ',18),
  ('Tháo dỡ sàn gõ cũ',19),
  ('Tháo dỡ gạch ốp tường, sàn đá hoa cũ',19),
  ('Ốp tường, sàn đá hoa',19),
  ('Vật liệu ốp sàn bếp',19),
  ('Hạng mục đập phá',19),
  ('Hạng mục xây mới',19),
  ('Trát tường',19),
  ('Cầm cửa, trát cạnh',19),
  ('Đổ lanh tô cửa đi mới',19),
  ('Đóng bao phế liệu',19),
  ('Chống thấm ',19),
  ('Tháo, lắp điều hòa',20),
  ('Đi dây đồngđiều hòa',20),
  ('Nhân công đi điện, nước',20),
  ('Dây điện ống nước, ô cắm, công tắc thay, thêm mới',20),
  ('Tháo dỡ phần thạch cao cũ',21),
  ('Làm mới thạch cao',21),
  ('Phào tường Tân cổ điển',21),
  ('Phào trần Tân cổ điển',21),
  ('Sơn lại tường nhà',22),
  ('Sơn lại trần nhà',22),
  ('Sàn gỗ An Cường 8mm',23),
  ('Sàn gỗ An Cường xương cá 12mm kiểu công nghiệp',23),
  ('Sàn gỗ An Cường xương cá 12mm kiểu tự nhiên',23),
  ('Sàn gỗ JOHOR; BORNEO; SAMALAY; SEMPORNA; SMATWOOD',23),
  ('Sàn gỗ CLASSIC FLOORING; AGT',23),
  ('Sàn gỗ A Qua; KANSAS',23),
  ('Phào chân tường',23),
  ('Lót sàn',23);
INSERT INTO thongso (dai,rong,cao,don_vi,don_gia) VALUES
(0.0,600.0,810.0,'mét dài',2550000),
(0.0,600.0,810.0,'mét dài',3050000),
(0.0,600.0,810.0,'mét dài',3450000),
(0.0,600.0,810.0,'mét dài',3950000),
(0.0,350.0,750.0,'mét dài',1800000),
(0.0,350.0,750.0,'mét dài',2350000),
(0.0,350.0,750.0,'mét dài',2700000),
(0.0,350.0,400.0,'mét dài',1435000),
(0.0,350.0,400.0,'mét dài',1790000),
(0.0,350.0,810.0,'mét dài',1950000),
(0.0,600.0,810.0,'mét dài',3550000),
(0.0,600.0,810.0,'mét dài',4150000),
(0.0,600.0,810.0,'mét dài',5490000),
(0.0,600.0,810.0,'mét dài',6250000),
(0.0,300.0,36.0,'mét dài',350000),
(1600.0,650.0,750.0,'bộ',5350000),
(500.0,550.0,900.0,'chiếc',1550000),
(0.0,800.0,750.0,'mét dài',3980000),
(300.0,300.0,400.0,'chiếc',1550000),
(800.0,700.0,400.0,'bộ',2950000);

INSERT INTO vatlieu (name,hang_muc_id,thong_so_id) VALUES
('- Thùng: nhựa Picomat 17mm chống nước tuyệt đối.
- Cánh: MDF chống ẩm 17mm phủ Melamine An Cường.
- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',1,1),
('- Thùng: nhựa Picomat 17mm chống nước tuyệt đối.
- Cánh: MDF chống ẩm 17mm phủ Laminate An Cường.
- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',1,2),
('- Thùng: nhựa Picomat 17mm chống nước tuyệt đối.
- Cánh: MDF chống ẩm 17mm phủ Acrylic An Cường.
- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',1,3),
('- Thùng: Inox 304
- Cánh: MDF  chống ẩm 17mm phủ Acrylic An Cường.
- Hậu: tấm inox 304',1,4),
('Thùng và cánh: MDF chống ẩm 17mm phủ Melamine An Cường
- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',2,5),
('Thùng: MDF chống ẩm 17mm phủ melamin An Cường
Cánh: MDF chống ẩm 17mm phủ Laminate An Cường
- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',2,6),
('Thùng: MDF chống ẩm 17mm phủ melamin An Cường
Cánh: MDF chống ẩm 17mm phủ Acrylic An Cường
Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',2,7),
('Thùng và cánh: MDF chống ẩm 17mm phủ Melamine An Cường
- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',3,8),
('Thùng: MDF chống ẩm 17mm phủ melamin An Cường
Cánh: MDF chống ẩm 17mm phủ Laminate An Cường
- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',3,9),
('Thùng: MDF chống ẩm 17mm phủ melamin An Cường
Cánh: MDF chống ẩm 17mm phủ Acrylic An Cường
Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối',3,10),
('-Thùng: nhựa Picomat 17mm  chống nước tuyệt đối
-Cánh: MDF chống ẩm 17mm phủ Melamine An Cường
- Mặt lộ: MDF chống ẩm 17mm phủ Melamine An Cường ',4,11),
('- Thùng: nhựa Picomat 17mm chống nước tuyệt đối.
- Cánh: MDF chống ẩm 17mm phủ Laminate An Cường.
- Mặt lộ: MDF chống ẩm 17mm phủ Laminate An Cường.',4,12),
('- Thùng: nhựa Picomat 17mm chống nước tuyệt đối.
- Cánh: MDF chống ẩm 17mm phủ Acrylic An Cường.
- Mặt lộ:  MDF chống ẩm 17mm phủ Acrylic An Cường.',4,13),
('- Thùng: Inox 304 Dày ....
- Cánh: MDF  chống ẩm 17mm phủ Acrylic An Cường.
- Mặt lộ:  MDF chống ẩm 17mm phủ Acrylic An Cường.',4,14),
('MDF chống ẩm 17mm phủ melamin An Cường',5,15),
('Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',6,16),
('Giá trị ước tính ý nghĩa tham khảo, giá trị thực tính theo mẫu 3D hoặc lựa chọn mẫu thực tế',7,17),
('Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',8,18),
('Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',9,19),
('Khung gỗ thông, bọc da hoặc nỉ công nghiệp Hàn Quốc, Giá trị tạm tính tính theo mẫu thực tế',10,20)


