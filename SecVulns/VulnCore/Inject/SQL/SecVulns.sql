/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : 127.0.0.1:3306
 Source Schema         : SecVulns

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 26/06/2024 15:25:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` (`id`, `username`, `password`) VALUES (165827712, 'Whoopsunix', 'Whoopsunixpass');
INSERT INTO `users` (`id`, `username`, `password`) VALUES (1362349079, 'admin', '123456');
INSERT INTO `users` (`id`, `username`, `password`) VALUES (1467415847, 'superadmin', '&&*&*ASxxxads');
COMMIT;

-- ----------------------------
-- Table structure for xss
-- ----------------------------
DROP TABLE IF EXISTS `xss`;
CREATE TABLE `xss` (
  `id` int DEFAULT NULL,
  `messgae` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of xss
-- ----------------------------
BEGIN;
INSERT INTO `xss` (`id`, `messgae`) VALUES (1234497866, '<img src=x onerror=alert(1)>');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
