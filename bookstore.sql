/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : bookstore

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2025-01-10 21:53:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(30) NOT NULL,
  `type` int(30) NOT NULL,
  `author` varchar(30) NOT NULL,
  `press` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `pressdate` date NOT NULL DEFAULT '2014-09-13',
  `price` decimal(10,2) NOT NULL,
  `count` int(11) NOT NULL,
  `img` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '我与地坛', '1', '余华', '人民大学出版社', '2024-11-14', '30.00', '100', 'http://localhost:1118/static/image/t6_674044.png');
INSERT INTO `book` VALUES ('2', '邓小平时代', '2', '傅高义', '人民大学出版社', '2024-12-10', '50.00', '10', 'http://localhost:1118/static/image/t6_674048.png');
INSERT INTO `book` VALUES ('3', '活着', '1', '余华', '人民大学出版社', '2024-12-02', '20.00', '90', 'http://localhost:1118/static/image/\r\nt6_834464.png');
INSERT INTO `book` VALUES ('4', '追风筝的人', '4', '卡勒德胡塞尼', '人民大学出版社', '2024-12-04', '60.00', '88', 'http://localhost:1118/static/image/\r\nt6_546339.png');
INSERT INTO `book` VALUES ('5', '新摄影笔记', '1', '宁思潇潇', '人民邮电出版社', '2014-09-13', '55.00', '19', 'http://localhost:1118/static/image/t6_40223839.png');
INSERT INTO `book` VALUES ('6', '我在泰国卖佛牌', '1', '鬼店主田七', '中文在线数字出版集团', '2012-06-23', '45.00', '33', 'http://localhost:1118/static/image/t6_933466.png');
INSERT INTO `book` VALUES ('7', '实践论', '1', '毛泽东', '人民出版社', '1975-12-01', '70.00', '100', 'http://localhost:1118/static/image/\r\nt6_3300024283.png');

-- ----------------------------
-- Table structure for bookoreder
-- ----------------------------
DROP TABLE IF EXISTS `bookoreder`;
CREATE TABLE `bookoreder` (
  `id` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `bookid` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `count` int(11) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bookoreder
-- ----------------------------

-- ----------------------------
-- Table structure for bookshelf
-- ----------------------------
DROP TABLE IF EXISTS `bookshelf`;
CREATE TABLE `bookshelf` (
  `bookid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `jointime` datetime NOT NULL,
  PRIMARY KEY (`bookid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bookshelf
-- ----------------------------
INSERT INTO `bookshelf` VALUES ('1', '1', '2024-11-14 00:51:50');

-- ----------------------------
-- Table structure for booktype
-- ----------------------------
DROP TABLE IF EXISTS `booktype`;
CREATE TABLE `booktype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `remark` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of booktype
-- ----------------------------
INSERT INTO `booktype` VALUES ('1', '精品小说', '精品小说');
INSERT INTO `booktype` VALUES ('2', '历史', '历史');
INSERT INTO `booktype` VALUES ('3', '文学', '文学');
INSERT INTO `booktype` VALUES ('4', '艺术', '艺术');
INSERT INTO `booktype` VALUES ('5', '人物传记', '人物传记');
INSERT INTO `booktype` VALUES ('6', '哲学宗教', '哲学宗教');
INSERT INTO `booktype` VALUES ('7', '计算机', '计算机');
INSERT INTO `booktype` VALUES ('8', '心理', '心理');
INSERT INTO `booktype` VALUES ('9', '社会文化', '社会文化');
INSERT INTO `booktype` VALUES ('10', '个人成长', '个人成长');
INSERT INTO `booktype` VALUES ('11', '经济理财', '经济理财');
INSERT INTO `booktype` VALUES ('12', '政治军事', '政治军事');
INSERT INTO `booktype` VALUES ('13', '童书', '童书');
INSERT INTO `booktype` VALUES ('14', '教育学习', '教育学习');
INSERT INTO `booktype` VALUES ('15', '科学技术', '科学技术');
INSERT INTO `booktype` VALUES ('16', '生活百科', '生活百科');
INSERT INTO `booktype` VALUES ('17', '期刊杂志', '期刊杂志');
INSERT INTO `booktype` VALUES ('18', '原版书', '原版书');
INSERT INTO `booktype` VALUES ('19', '医学健康', '医学健康');
INSERT INTO `booktype` VALUES ('20', '男生小说', '男生小说');
INSERT INTO `booktype` VALUES ('21', '女生小说', '女生小说');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `bookid` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`,`userid`,`bookid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货名',
  `goodstype` int(11) NOT NULL COMMENT '分类',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '床单', '1', '5000', '货物不可以挤压');
INSERT INTO `goods` VALUES ('2', '被罩', '1', '500', '货物不可以挤压');
INSERT INTO `goods` VALUES ('3', '枕套', '1', '1000', '货物不可以挤压');
INSERT INTO `goods` VALUES ('4', '洗发水', '2', '800', '货物不可以挤压');
INSERT INTO `goods` VALUES ('5', '沐浴露', '2', '800', '货物不可以挤压');
INSERT INTO `goods` VALUES ('6', '牙刷', '2', '1600', '货物不可以挤压');
INSERT INTO `goods` VALUES ('7', '肥皂', '2', '800', '货物不可以挤压');
INSERT INTO `goods` VALUES ('8', '浴帽', '2', '800', '货物不可以挤压');
INSERT INTO `goods` VALUES ('9', '矿泉水', '3', '2000', '货物不可以挤压');
INSERT INTO `goods` VALUES ('10', '泡面', '3', '1000', '货物不可以挤压');
INSERT INTO `goods` VALUES ('11', '茶包', '3', '1000', '货物不可以挤压');
INSERT INTO `goods` VALUES ('12', '咖啡', '3', '1000', '货物不可以挤压');
INSERT INTO `goods` VALUES ('14', 'apple', '3', '5', '快吃');

-- ----------------------------
-- Table structure for goodstype
-- ----------------------------
DROP TABLE IF EXISTS `goodstype`;
CREATE TABLE `goodstype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of goodstype
-- ----------------------------
INSERT INTO `goodstype` VALUES ('1', '精品小说', '精品小说');
INSERT INTO `goodstype` VALUES ('2', '历史', '历史');
INSERT INTO `goodstype` VALUES ('3', '文学', '文学');
INSERT INTO `goodstype` VALUES ('4', '艺术', '艺术');
INSERT INTO `goodstype` VALUES ('5', '人物传记', '人物传记');
INSERT INTO `goodstype` VALUES ('6', '哲学宗教', '哲学宗教');
INSERT INTO `goodstype` VALUES ('7', '计算机', '计算机');
INSERT INTO `goodstype` VALUES ('8', '心理', '心理');
INSERT INTO `goodstype` VALUES ('9', '社会文化', '社会文化');
INSERT INTO `goodstype` VALUES ('10', '个人成长', '个人成长');
INSERT INTO `goodstype` VALUES ('11', '经济理财', '经济理财');
INSERT INTO `goodstype` VALUES ('12', '政治军事', '政治军事');
INSERT INTO `goodstype` VALUES ('13', '童书', '童书');
INSERT INTO `goodstype` VALUES ('14', '教育学习', '教育学习');
INSERT INTO `goodstype` VALUES ('15', '科学技术', '科学技术');
INSERT INTO `goodstype` VALUES ('16', '生活百科', '生活百科');
INSERT INTO `goodstype` VALUES ('17', '期刊杂志', '期刊杂志');
INSERT INTO `goodstype` VALUES ('18', '原版书', '原版书');
INSERT INTO `goodstype` VALUES ('19', '医学健康', '医学健康');
INSERT INTO `goodstype` VALUES ('20', '男生小说', '男生小说');
INSERT INTO `goodstype` VALUES ('21', '女生小说', '女生小说');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `menuCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单级别',
  `menuParentCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用',
  `menuComponent` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `menuIcon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '001', '管理员管理', '1', null, 'Admin', '0', 'admin/AdminManage.vue', 'el-icon-s-custom');
INSERT INTO `menu` VALUES ('2', '002', '用户管理', '1', null, 'User', '0,1', 'user/UserManage.vue', 'el-icon-user-solid');
INSERT INTO `menu` VALUES ('3', '003', '书籍管理', '1', null, 'Storage', '0,1', 'storage/StorageManage', 'el-icon-office-building');
INSERT INTO `menu` VALUES ('4', '004', '书籍分类管理', '1', null, 'Goodstype', '0,1', 'goodstype/GoodstypeManage', 'el-icon-menu');
INSERT INTO `menu` VALUES ('5', '005', '书评管理', '1', null, 'Record', '0,1,2', 'record/RecordManage', 'el-icon-s-order');
INSERT INTO `menu` VALUES ('6', '006', '订单管理', '1', null, 'Goods', '0,1,2', 'good/GoodsManage', 'el-icon-s-management');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(11) NOT NULL COMMENT '主键',
  `roomname` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货品id',
  `username` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `createtime` datetime NOT NULL COMMENT '备注',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES ('1001', '我与地坛', 'Jack', '600', '2024-06-14 00:00:00', '我在好几篇小说中都提到过一座废弃的古园，实际上就是地坛。许多年前旅游业还没有开展，园子荒芜冷落得如同一片野地，很少被人记起。');
INSERT INTO `record` VALUES ('1002', '红楼梦', '喜欢读书的程序员', '300', '2024-06-21 23:40:09', '我很喜欢历史，喜欢那些过去的人和事，在历史的长河中，有太多的事情值得我们去回味。在我第一次接触历史的二十年之后，我开始动笔，写下了下面的这些文字，写给我自己，也写给所有喜爱历史的人。');
INSERT INTO `record` VALUES ('1003', '我与地坛', 'Lee', '300', '2024-06-21 23:40:10', '地坛离我家很近。或者说我家离地坛很近。总之，只好认为这是缘分。');
INSERT INTO `record` VALUES ('1004', '红楼梦', '章鱼小丸子', '300', '2024-06-23 23:18:14', '敦敏《题芹圃画石》诗说：​“傲骨如君世已奇，嶙峋更见此支离；醉馀奋扫如椽笔，写出胸中磈礧时！”可见曹雪芹的胸襟和画风。可惜他的遗作至今尚未发现。');
INSERT INTO `record` VALUES ('1005', '明朝那些事（全集）', '蜡笔小u丸', '300', '2024-06-23 23:18:15', '一切的事情都从1328年的那个夜晚开始。农民朱五四的妻子陈氏生下了一个男婴，大家都知道了，这个男婴就是后来的朱元璋。');
INSERT INTO `record` VALUES ('1006', '红楼梦', '红烧牛肉面', '300', '2024-06-23 23:18:17', '伟大作家曹雪芹，终于在穷愁困顿中于公元一七六三年即乾隆二十七年壬午除夕去世。他的不朽巨著《石头记》的前八十回');
INSERT INTO `record` VALUES ('1007', '明朝那些事（全集）', 'Sebastian', '300', '2024-06-23 23:18:18', '他出生时，红光满地，夜间房屋中出现异光');
INSERT INTO `record` VALUES ('1008', '我的外婆，从不内耗', '一颗将冷的心', '300', '2024-06-23 23:18:19', '有一节课，你或许已经缺席许久，不在学校的教室里，也不在冰冷的说教中。人生的秘密藏在生活的细微处，通关的技巧在温暖的怀抱中流传。天空没有痕迹，智慧的青鸟已滑过。补上久违的一课，命运的齿轮也已转向你。');
INSERT INTO `record` VALUES ('1009', '明朝那些事（全集）', '企鹅号地铁', '300', '2024-06-23 23:18:20', '因为，他要吃饭。');
INSERT INTO `record` VALUES ('1010', '我的外婆，从不内耗', '土墩墩墩墩墩', '300', '2024-06-24 08:44:14', '天地之大，无边无际，你可以成为你想成为的任何样子。花只是你微不足道的点缀，不开花也没关系，你不必做一朵花。');
INSERT INTO `record` VALUES ('1001', '我与地坛', '超级管理员', '30', '2024-11-16 01:25:11', '退房');
INSERT INTO `record` VALUES ('1001', '我与地坛', '超级管理员', '30', '2024-11-16 01:25:13', '入住');
INSERT INTO `record` VALUES ('1001', '我与地坛', '超级管理员', '30', '2024-11-16 01:25:17', '退房');

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(255) NOT NULL,
  `status` int(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `count` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1008 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of storage
-- ----------------------------
INSERT INTO `storage` VALUES ('1001', '我与地坛', '30', '文学', '1', '史铁生', '50');
INSERT INTO `storage` VALUES ('1002', '明朝那些事（全集）', '100', '历史', '0', '当前明月', '30');
INSERT INTO `storage` VALUES ('1003', 'Python编程：从入门到实践', '80', '计算机', '0', '埃里克·玛瑟斯', '40');
INSERT INTO `storage` VALUES ('1004', '红楼梦', '70', '文学', '0', '曹雪芹', '90');
INSERT INTO `storage` VALUES ('1005', '我的外婆，从不内耗', '40', '个人成长', '0', '理微尘', '30');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phonenumber` varchar(20) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `role` int(1) DEFAULT '0' COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=964435970 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'Jack', '$2a$10$Aw6ff4yhYBN.Cm4uywElruZm/L2ZZrr9r46jTK.Un/8dDJrdDgpl6', null, null, null, null, null, '0');
INSERT INTO `user` VALUES ('2', 'Rose', '$2a$10$Aw6ff4yhYBN.Cm4uywElruZm/L2ZZrr9r46jTK.Un/8dDJrdDgpl6', null, null, null, null, null, '0');
INSERT INTO `user` VALUES ('3', 'www', '$2a$10$SeggjqWAfmyC0pc5m75J/eg.p7EPEdezZmK3XRVRiohmsqXLZQ.hy', null, null, null, null, 'https://cdn.acwing.com/media/user/profile/photo/272294_lg_6c5848129b.jpg', '0');
INSERT INTO `user` VALUES ('4', 'wtw', '$2a$10$ZVb9SnOs.aGXXaQBeX2qwuSPYJGUGXf5VtqFaSQQGIXQsgSeI9Nkq', null, null, null, null, 'https://cdn.acwing.com/media/user/profile/photo/272294_lg_6c5848129b.jpg', '0');
INSERT INTO `user` VALUES ('540860417', 'Jack2', '$2a$10$vhuz0dLpl9.Ep3xiyWh44uD3GjdpUdHniAnh4EzNMPMj3/5oOvxzO', null, null, null, null, 'https://cdn.acwing.com/media/user/profile/photo/272294_lg_6c5848129b.jpg', '0');
