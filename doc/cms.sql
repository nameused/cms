/*
Navicat MySQL Data Transfer

Source Server         : 192.168.6.17-IDE
Source Server Version : 50728
Source Host           : 192.168.6.17:3306
Source Database       : cms

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-01-21 18:59:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '父级权限id',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `value` varchar(200) DEFAULT NULL COMMENT '权限值',
  `icon` varchar(500) DEFAULT NULL COMMENT '图标',
  `type` int(1) DEFAULT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
  `uri` varchar(255) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '0', '系统管理', null, null, '0', '', '1', '2020-01-16 17:05:48', '0');
INSERT INTO `permission` VALUES ('2', '1', '用户管理', 'cms:sys:user', null, '1', '/cms/user/index', '1', '2020-01-17 09:45:19', '0');
INSERT INTO `permission` VALUES ('3', '1', '角色管理', 'cms:sys:role', null, '1', '/cms/role/index', '1', '2020-01-17 09:47:25', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `user_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', '负责系统管理', '0', '2020-01-16 17:02:59', '1', '0');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '授权ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '1');
INSERT INTO `role_permission` VALUES ('2', '1', '2');
INSERT INTO `role_permission` VALUES ('3', '1', '3');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '帐号启用状态：0->禁用；1->启用',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `true_name` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(64) NOT NULL,
  `gender` varchar(4) DEFAULT NULL COMMENT '性别',
  `icon` varchar(500) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '1' COMMENT '1为激活,0为禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zmy', '张名扬', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', '男', null, 'zmy@dxct.org', '管理员', '1', '2020-01-16 16:23:35', null, '1');
INSERT INTO `user` VALUES ('2', 'wlj', '吴利娇', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', '女', null, 'wlj@dxct.org', '普通用户', '1', '2020-01-16 16:25:59', null, '1');
INSERT INTO `user` VALUES ('3', 'chn', '陈浩南', '$2a$10$cvC7Za63wSUDHcGStOO3Je6cO9mrWlH6MG3erbTB5vs33nlDQGR5m', '男', 'string', 'chn@dxct.org', '备注', null, '2020-01-21 02:36:44', null, '1');
