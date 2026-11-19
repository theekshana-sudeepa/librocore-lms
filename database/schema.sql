-- Library Management System (LMSystem) Database Schema & Seed Data
-- Database: library_dbdb

CREATE DATABASE IF NOT EXISTS `library_dbdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `library_dbdb`;

-- 1. Status
DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `status` (`id`, `name`) VALUES
(1, 'ACTIVE'),
(2, 'INACTIVE'),
(3, 'PENDING'),
(4, 'ORDERED'),
(5, 'DELIVERED');

-- 2. User Types
DROP TABLE IF EXISTS `user_type`;
CREATE TABLE `user_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user_type` (`id`, `type`) VALUES
(1, 'SUPERADMIN'),
(2, 'ADMIN'),
(3, 'USER');

-- 3. Gender
DROP TABLE IF EXISTS `gender`;
CREATE TABLE `gender` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `gender` (`id`, `name`) VALUES
(1, 'Male'),
(2, 'Female'),
(3, 'Other');

-- 4. Location Hierarchy (Province, District, City)
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `province` (`id`, `name`) VALUES
(1, 'Western Province'),
(2, 'Central Province'),
(3, 'Southern Province'),
(4, 'Northern Province'),
(5, 'Eastern Province'),
(6, 'North Western Province'),
(7, 'North Central Province'),
(8, 'Uva Province'),
(9, 'Sabaragamuwa Province');

DROP TABLE IF EXISTS `district`;
CREATE TABLE `district` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `province_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_district_province` (`province_id`),
  CONSTRAINT `fk_district_province` FOREIGN KEY (`province_id`) REFERENCES `province` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `district` (`id`, `name`, `province_id`) VALUES
(1, 'Colombo', 1),
(2, 'Gampaha', 1),
(3, 'Kalutara', 1),
(4, 'Kandy', 2),
(5, 'Matale', 2),
(6, 'Nuwara Eliya', 2),
(7, 'Galle', 3),
(8, 'Matara', 3),
(9, 'Hambantota', 3),
(10, 'Jaffna', 4),
(11, 'Kurunegala', 6);

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `district_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_city_district` (`district_id`),
  CONSTRAINT `fk_city_district` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `city` (`id`, `name`, `district_id`) VALUES
(1, 'Colombo 07', 1),
(2, 'Dehiwala', 1),
(3, 'Moratuwa', 1),
(4, 'Negombo', 2),
(5, 'Gampaha', 2),
(6, 'Kelaniya', 2),
(7, 'Panadura', 3),
(8, 'Kandy City', 4),
(9, 'Peradeniya', 4),
(10, 'Galle Fort', 7);

-- 5. User Management
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nic_no` VARCHAR(20) NOT NULL UNIQUE,
  `fName` VARCHAR(50) NOT NULL,
  `lName` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `dob` VARCHAR(20) DEFAULT NULL,
  `gender_id` INT NOT NULL,
  `barcode_path` VARCHAR(255) DEFAULT '',
  `joined_date` VARCHAR(30) DEFAULT NULL,
  `user_type_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_gender` (`gender_id`),
  KEY `fk_user_usertype` (`user_type_id`),
  KEY `fk_user_status` (`status_id`),
  CONSTRAINT `fk_user_gender` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`),
  CONSTRAINT `fk_user_usertype` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`),
  CONSTRAINT `fk_user_status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` (`id`, `nic_no`, `fName`, `lName`, `email`, `dob`, `gender_id`, `barcode_path`, `joined_date`, `user_type_id`, `status_id`) VALUES
(1, '200010001001', 'Super', 'Administrator', 'superadmin@example.com', '2000-01-10', 1, '', '2024-01-01', 1, 1),
(2, '200020002002', 'Chief', 'Librarian', 'admin@example.com', '2000-02-15', 1, '', '2024-01-15', 2, 1),
(3, '200030003003', 'Alex', 'Perera', 'alex.perera@example.com', '2000-03-20', 1, '', '2024-02-10', 3, 1),
(4, '200040004004', 'Kasun', 'Silva', 'kasun.silva@example.com', '2000-04-22', 1, '', '2024-02-20', 3, 1),
(5, '200050005005', 'Nisansala', 'Fernando', 'nisansala.f@example.com', '2000-05-10', 2, '', '2024-03-01', 3, 2);

-- 6. Login Details
DROP TABLE IF EXISTS `login_details`;
CREATE TABLE `login_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_login_user` (`user_id`),
  CONSTRAINT `fk_login_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Note: Passwords match PASSWORD_VALIDATION: at least 1 uppercase, 1 lowercase, 1 number, 1 special char, 8+ chars
INSERT INTO `login_details` (`id`, `username`, `password`, `user_id`) VALUES
(1, 'superadmin', 'SuperAdmin@123', 1),
(2, 'admin', 'Admin@123', 2);

-- 7. Contact Numbers
DROP TABLE IF EXISTS `contact_number`;
CREATE TABLE `contact_number` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pNumber01` VARCHAR(20) NOT NULL,
  `pNumber02` VARCHAR(20) DEFAULT NULL,
  `user_nic_no` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_contact_user` (`user_nic_no`),
  CONSTRAINT `fk_contact_user` FOREIGN KEY (`user_nic_no`) REFERENCES `user` (`nic_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `contact_number` (`id`, `pNumber01`, `pNumber02`, `user_nic_no`) VALUES
(1, '0700000001', '0110000001', '200010001001'),
(2, '0700000002', '0110000002', '200020002002'),
(3, '0700000003', '0700000013', '200030003003'),
(4, '0700000004', '', '200040004004'),
(5, '0700000005', '', '200050005005');

-- 8. Addresses
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `lane_1` VARCHAR(100) NOT NULL,
  `lane_2` VARCHAR(100) DEFAULT '',
  `postal_code` VARCHAR(20) DEFAULT '',
  `city_id` INT NOT NULL,
  `district_id` INT NOT NULL,
  `province_id` INT NOT NULL,
  `user_nic_no` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_address_city` (`city_id`),
  KEY `fk_address_district` (`district_id`),
  KEY `fk_address_province` (`province_id`),
  KEY `fk_address_user` (`user_nic_no`),
  CONSTRAINT `fk_address_city` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_address_district` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`),
  CONSTRAINT `fk_address_province` FOREIGN KEY (`province_id`) REFERENCES `province` (`id`),
  CONSTRAINT `fk_address_user` FOREIGN KEY (`user_nic_no`) REFERENCES `user` (`nic_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `address` (`id`, `lane_1`, `lane_2`, `postal_code`, `city_id`, `district_id`, `province_id`, `user_nic_no`) VALUES
(1, 'No 10, Independence Ave', 'Cinnamon Gardens', '00700', 1, 1, 1, '200010001001'),
(2, 'No 45, Main Street', 'Station Road', '10350', 2, 1, 1, '200020002002'),
(3, 'No 12, Library Lane', 'Central Avenue', '10400', 3, 1, 1, '200030003003'),
(4, 'No 88, Kandy Road', 'Park View', '11850', 5, 2, 1, '200040004004'),
(5, 'No 5, Hill Street', 'University Road', '20000', 8, 4, 2, '200050005005');

-- 9. Book Categories
DROP TABLE IF EXISTS `main_category`;
CREATE TABLE `main_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `Color_code` VARCHAR(20) DEFAULT '#0575E6',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `main_category` (`id`, `name`, `Color_code`) VALUES
(1, 'Computer Science & IT', '#0575E6'),
(2, 'Software Engineering', '#00C0FF'),
(3, 'Data Science & AI', '#9B51E0'),
(4, 'Business & Management', '#27AE60'),
(5, 'Fiction & Literature', '#F2994A');

DROP TABLE IF EXISTS `sub_category`;
CREATE TABLE `sub_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `Color_code` VARCHAR(20) DEFAULT '#00C0FF',
  `main_category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_subcat_maincat` (`main_category_id`),
  CONSTRAINT `fk_subcat_maincat` FOREIGN KEY (`main_category_id`) REFERENCES `main_category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `sub_category` (`id`, `name`, `Color_code`, `main_category_id`) VALUES
(1, 'Java Programming', '#0575E6', 1),
(2, 'Web Architecture', '#00C0FF', 1),
(3, 'Design Patterns', '#9B51E0', 2),
(4, 'Clean Code & Refactoring', '#27AE60', 2),
(5, 'Machine Learning Algorithms', '#EB5757', 3),
(6, 'Agile Project Management', '#F2C94C', 4);

-- 10. Books
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` VARCHAR(50) NOT NULL,
  `main_category_id` INT NOT NULL,
  `sub_category_id` INT NOT NULL,
  `name` VARCHAR(150) NOT NULL,
  `book_author` VARCHAR(100) NOT NULL,
  `published_date` VARCHAR(30) DEFAULT NULL,
  `added_date` VARCHAR(30) DEFAULT NULL,
  `price` DECIMAL(10,2) NOT NULL DEFAULT '0.00',
  `book_qr` VARCHAR(255) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `fk_book_maincat` (`main_category_id`),
  KEY `fk_book_subcat` (`sub_category_id`),
  CONSTRAINT `fk_book_maincat` FOREIGN KEY (`main_category_id`) REFERENCES `main_category` (`id`),
  CONSTRAINT `fk_book_subcat` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `book` (`id`, `main_category_id`, `sub_category_id`, `name`, `book_author`, `published_date`, `added_date`, `price`, `book_qr`) VALUES
('BK-1001', 1, 1, 'Core Java: Fundamentals (12th Edition)', 'Cay S. Horstmann', '2022-03-15', '2024-01-10', 4500.00, ''),
('BK-1002', 2, 3, 'Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma, Richard Helm', '1994-10-21', '2024-01-12', 6200.00, ''),
('BK-1003', 2, 4, 'Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', '2008-08-01', '2024-01-15', 5500.00, ''),
('BK-1004', 3, 5, 'Hands-On Machine Learning with Scikit-Learn, Keras, and TensorFlow', 'Aurélien Géron', '2022-10-04', '2024-02-01', 7800.00, ''),
('BK-1005', 1, 2, 'Designing Data-Intensive Applications', 'Martin Kleppmann', '2017-03-16', '2024-02-14', 6800.00, '');

-- 11. Book Issue & Circulation
DROP TABLE IF EXISTS `b_issued`;
CREATE TABLE `b_issued` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_nic_no` VARCHAR(20) NOT NULL,
  `book_id` VARCHAR(50) NOT NULL,
  `issued_date` VARCHAR(30) NOT NULL,
  `return_date` VARCHAR(30) NOT NULL,
  `reciept_path` VARCHAR(255) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_issue_user` (`user_nic_no`),
  KEY `fk_issue_book` (`book_id`),
  CONSTRAINT `fk_issue_user` FOREIGN KEY (`user_nic_no`) REFERENCES `user` (`nic_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_issue_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `b_issued` (`id`, `user_nic_no`, `book_id`, `issued_date`, `return_date`, `reciept_path`) VALUES
(1, '200030003003', 'BK-1001', '2024-08-01', '2024-08-15', '1'),
(2, '200040004004', 'BK-1003', '2024-08-10', '2024-08-24', '1');

-- 12. Feedback
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(50) NOT NULL,
  `lname` VARCHAR(50) NOT NULL,
  `nic_no` VARCHAR(20) NOT NULL,
  `message` TEXT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `feedback` (`id`, `fname`, `lname`, `nic_no`, `message`) VALUES
(1, 'Alex', 'Perera', '200030003003', 'Outstanding library system interface! Very intuitive search and barcode integration.'),
(2, 'Kasun', 'Silva', '200040004004', 'Book issuance was quick and seamless. Love the automated receipt generator.');
