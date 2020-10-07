/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 07/10/2020 14:07:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_login_at` datetime(6) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '2020-10-07 13:00:28.000000', 'jerry@gmail.com', '2020-10-07 13:00:22.000000', '$2a$10$vaTSB5nCmmyamkQvbAgLtu030mt1mn.YN73c5xyX8BBHdTEyPGb3u', '2020-10-07 13:00:42.000000', 'ADMIN', 'jerry');
INSERT INTO `user` VALUES (2, '2020-10-07 13:09:57.000000', 'jack@gmail.com', '2020-10-07 13:10:08.000000', '$10$vaTSB5nCmmyamkQvbAgLtu030mt1mn.YN73c5xyX8BBHdTEyPGb3u', '2020-10-07 13:10:18.000000', 'DEVELOPER', 'jack');
INSERT INTO `user` VALUES (3, '2020-10-07 13:10:45.000000', 'tom@gmail.com', '2020-10-07 13:10:54.000000', '$10$vaTSB5nCmmyamkQvbAgLtu030mt1mn.YN73c5xyX8BBHdTEyPGb3u', '2020-10-07 13:11:00.000000', 'MEMBER', 'tom');
INSERT INTO `user` VALUES (4, '2020-10-07 13:11:41.000000', 'dannie', '2020-10-07 13:11:36.000000', '$10$vaTSB5nCmmyamkQvbAgLtu030mt1mn.YN73c5xyX8BBHdTEyPGb3u', '2020-10-07 13:11:30.000000', 'GUEST', 'dannie');
INSERT INTO `user` VALUES (5, '2020-10-07 13:12:26.000000', 'tony', '2020-10-07 13:12:20.000000', '$10$vaTSB5nCmmyamkQvbAgLtu030mt1mn.YN73c5xyX8BBHdTEyPGb3u', '2020-10-07 13:12:14.000000', 'ROOT', 'tony');

SET FOREIGN_KEY_CHECKS = 1;
