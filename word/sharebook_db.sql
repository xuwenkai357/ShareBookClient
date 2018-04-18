/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50041
Source Host           : localhost:3306
Source Database       : sharebook_db

Target Server Type    : MYSQL
Target Server Version : 50041
File Encoding         : 65001

Date: 2018-04-18 13:36:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info` (
  `Book_Info_Id` int(11) NOT NULL,
  `isbn` varchar(13) default NULL,
  `title` varchar(255) default NULL,
  `image` varchar(255) default NULL,
  `publisher` varchar(255) default NULL,
  `pubdate` varchar(255) default NULL,
  `author` varchar(255) default NULL,
  `summary` varchar(255) default NULL,
  `page` varchar(255) default NULL,
  `price` varchar(255) default NULL,
  `hot` int(11) default NULL,
  `enter_time` datetime default NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for book_list
-- ----------------------------
DROP TABLE IF EXISTS `book_list`;
CREATE TABLE `book_list` (
  `Book_Id` int(11) NOT NULL auto_increment,
  `Owner_Id` int(11) NOT NULL,
  `Book_Info_Id` int(11) NOT NULL,
  `Status` int(1) NOT NULL,
  PRIMARY KEY  (`Book_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for borrow_list
-- ----------------------------
DROP TABLE IF EXISTS `borrow_list`;
CREATE TABLE `borrow_list` (
  `Borrow_Id` int(11) NOT NULL,
  `Book_Id` int(11) default NULL,
  `Book_Info_Id` int(11) default NULL,
  `Owner_Id` int(11) default NULL,
  `Borrower_Id` int(11) default NULL,
  `Borrow_Time` datetime default NULL,
  `Return_Time` datetime default NULL,
  `Confine_Time` datetime default NULL,
  PRIMARY KEY  (`Borrow_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dynamic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic` (
  `Dynamic_Id` int(11) NOT NULL,
  `Book_Info_Id` int(11) default NULL,
  `User_Id` int(11) default NULL,
  `Body` varchar(255) default NULL,
  `Create_Time` datetime default NULL,
  PRIMARY KEY  (`Dynamic_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `Message_Id` int(11) NOT NULL,
  `User_Id` int(11) default NULL,
  `Body` varchar(255) default NULL,
  `Create_Time` datetime default NULL,
  `Status` varchar(255) default NULL,
  PRIMARY KEY  (`Message_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for taobao
-- ----------------------------
DROP TABLE IF EXISTS `taobao`;
CREATE TABLE `taobao` (
  `user` varchar(255) default NULL,
  `image` varchar(255) default NULL,
  `price` double(16,0) default NULL,
  `time` varchar(255) default NULL,
  `region` varchar(255) default NULL,
  `sign` varchar(255) NOT NULL,
  `title` varchar(255) default NULL,
  PRIMARY KEY  (`sign`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `User_Id` int(255) NOT NULL auto_increment,
  `User` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Token` varchar(255) NOT NULL,
  PRIMARY KEY  (`User_Id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `User_Id` int(11) NOT NULL,
  `User_Name` varchar(255) default NULL,
  `Home_Name` varchar(255) default NULL,
  `City_Id` int(11) default NULL,
  `County_Id` int(11) default NULL,
  `Photo_Url` varchar(255) default NULL,
  `Phone` varchar(255) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
