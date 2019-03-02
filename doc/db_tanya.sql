-- --------------------------------------------------------
-- 主机:                           47.104.89.137
-- 服务器版本:                        5.7.25 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 tanya 的数据库结构
DROP DATABASE IF EXISTS `tanya`;
CREATE DATABASE IF NOT EXISTS `tanya` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tanya`;

-- 导出  表 tanya.admin_info 结构
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE IF NOT EXISTS `admin_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `FK_admin_info_user_info` (`user_id`),
  CONSTRAINT `FK_admin_info_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='管理员信息';

-- 正在导出表  tanya.admin_info 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `admin_info` DISABLE KEYS */;
REPLACE INTO `admin_info` (`id`, `title`, `comment`, `user_id`, `start_at`, `end_at`, `create_at`, `update_at`, `valid`) VALUES
	(1, '管理员A', NULL, NULL, NULL, NULL, '2019-02-10 13:03:00.077', '2019-02-12 14:06:38.606', 1),
	(2, '管理员B', NULL, NULL, NULL, NULL, '2019-02-10 13:03:10.578', '2019-02-12 13:53:26.716', 1),
	(3, '管理员C', NULL, NULL, NULL, NULL, '2019-02-10 13:03:37.310', '2019-02-10 13:03:37.310', 1),
	(4, '管理员D', '超级管理员创建管理员', NULL, '2019-02-12 00:21:32.135', '2024-02-12 00:21:32.135', '2019-02-12 00:21:32.156', '2019-02-12 15:44:42.477', 1),
	(5, 'admin1', 'test admin1', NULL, '2019-02-12 20:27:07.597', '2024-02-12 20:27:07.597', '2019-02-12 20:27:07.616', '2019-02-12 20:27:07.628', 1);
/*!40000 ALTER TABLE `admin_info` ENABLE KEYS */;

-- 导出  表 tanya.campaign_history 结构
DROP TABLE IF EXISTS `campaign_history`;
CREATE TABLE IF NOT EXISTS `campaign_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `comment` varchar(50) DEFAULT NULL COMMENT '备注',
  `salesman_id` int(10) unsigned DEFAULT NULL COMMENT '上报促销员id',
  `campaign_id` int(10) unsigned DEFAULT NULL COMMENT '上报促销活动id',
  `number` int(10) unsigned DEFAULT NULL COMMENT '促销活动数量',
  `url` varchar(200) DEFAULT NULL COMMENT '上传照片地址',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '确认时间',
  `rewards` int(10) unsigned DEFAULT NULL COMMENT '该促销积分回馈',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_campaign_history_campaign_info` (`campaign_id`),
  KEY `FK_campaign_history_salesman_info` (`salesman_id`),
  CONSTRAINT `FK_campaign_history_campaign_info` FOREIGN KEY (`campaign_id`) REFERENCES `campaign_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_campaign_history_salesman_info` FOREIGN KEY (`salesman_id`) REFERENCES `salesman_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动历史信息';

-- 正在导出表  tanya.campaign_history 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `campaign_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_history` ENABLE KEYS */;

-- 导出  表 tanya.campaign_info 结构
DROP TABLE IF EXISTS `campaign_info`;
CREATE TABLE IF NOT EXISTS `campaign_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '促销活动名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '促销活动备注',
  `trader_id` int(10) unsigned NOT NULL COMMENT '发起销售人员id',
  `goods_id` int(10) unsigned NOT NULL COMMENT '促销商品id',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `confirm_status` tinyint(3) DEFAULT NULL COMMENT '确认状态',
  `confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '确认时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_campaign_info_trader_info` (`trader_id`),
  KEY `FK_campaign_info_goods_info` (`goods_id`),
  CONSTRAINT `FK_campaign_info_goods_info` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_campaign_info_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销积分活动';

-- 正在导出表  tanya.campaign_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `campaign_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_info` ENABLE KEYS */;

-- 导出  表 tanya.campaign_salesman_map 结构
DROP TABLE IF EXISTS `campaign_salesman_map`;
CREATE TABLE IF NOT EXISTS `campaign_salesman_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `campaign_id` int(10) unsigned NOT NULL COMMENT '活动id',
  `salesman_id` int(10) unsigned NOT NULL COMMENT '促销人员id',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_campaign_salesman_map_campaign_info` (`campaign_id`),
  KEY `FK_campaign_salesman_map_salesman_info` (`salesman_id`),
  CONSTRAINT `FK_campaign_salesman_map_campaign_info` FOREIGN KEY (`campaign_id`) REFERENCES `campaign_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_campaign_salesman_map_salesman_info` FOREIGN KEY (`salesman_id`) REFERENCES `salesman_info` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动-促销员关系';

-- 正在导出表  tanya.campaign_salesman_map 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `campaign_salesman_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_salesman_map` ENABLE KEYS */;

-- 导出  表 tanya.discount_info 结构
DROP TABLE IF EXISTS `discount_info`;
CREATE TABLE IF NOT EXISTS `discount_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '活动名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `factory_metchat_map_id` int(10) unsigned DEFAULT NULL COMMENT '药厂渠道关系id',
  `goods_id` int(10) unsigned DEFAULT NULL COMMENT '商品id',
  `goods_number` int(10) unsigned NOT NULL COMMENT '活动数量',
  `amount` double NOT NULL COMMENT '活动金额',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `confirm_status` tinyint(3) DEFAULT NULL COMMENT '确认状态',
  `confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '确认时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_discount_info_merchant_info` (`factory_metchat_map_id`),
  KEY `FK_discount_info_goods_info` (`goods_id`),
  CONSTRAINT `FK_discount_info_factory_merchant_map` FOREIGN KEY (`factory_metchat_map_id`) REFERENCES `factory_merchant_map` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_discount_info_goods_info` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品销售活动';

-- 正在导出表  tanya.discount_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `discount_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `discount_info` ENABLE KEYS */;

-- 导出  表 tanya.factory_info 结构
DROP TABLE IF EXISTS `factory_info`;
CREATE TABLE IF NOT EXISTS `factory_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_factory_info_user_info` (`user_id`),
  CONSTRAINT `FK_factory_info_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='医药公司人员信息';

-- 正在导出表  tanya.factory_info 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `factory_info` DISABLE KEYS */;
REPLACE INTO `factory_info` (`id`, `title`, `comment`, `user_id`, `start_at`, `end_at`, `create_at`, `update_at`, `valid`) VALUES
	(1, '药厂A', '渠道商创建药厂测试', NULL, '2019-02-12 17:57:33.407', '2020-02-12 17:57:33.407', '2019-02-12 17:57:33.421', '2019-02-12 18:22:09.971', 1),
	(2, 'test C123', '2123123123', 6, '2019-02-16 14:32:10.407', '2020-02-16 14:32:10.407', '2019-02-16 14:32:10.427', '2019-02-20 23:25:20.626', 1),
	(3, 'test 111222', '2123123123222', NULL, '2019-02-16 14:32:30.764', '2020-02-16 14:32:30.764', '2019-02-16 14:32:30.765', '2019-02-16 14:32:30.778', 1),
	(4, 'test22233', 'test123', 2, '2019-02-16 15:41:49.976', '2020-02-16 15:41:49.976', '2019-02-16 15:41:49.976', '2019-02-21 20:36:47.217', 1);
/*!40000 ALTER TABLE `factory_info` ENABLE KEYS */;

-- 导出  表 tanya.factory_merchant_map 结构
DROP TABLE IF EXISTS `factory_merchant_map`;
CREATE TABLE IF NOT EXISTS `factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `merchant_id` int(10) unsigned DEFAULT NULL COMMENT '渠道id',
  `factory_id` int(10) unsigned DEFAULT NULL COMMENT '药厂id',
  `goods_number` int(10) unsigned DEFAULT NULL COMMENT '注册商品数量',
  `trader_number` int(10) unsigned DEFAULT NULL COMMENT '销售代表数量',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后登入时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_factory_merchant_map_merchant_info` (`merchant_id`),
  KEY `FK_factory_merchant_map_factory_info` (`factory_id`),
  CONSTRAINT `FK_factory_merchant_map_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_factory_merchant_map_merchant_info` FOREIGN KEY (`merchant_id`) REFERENCES `merchant_info` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='厂商-渠道关系';

-- 正在导出表  tanya.factory_merchant_map 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `factory_merchant_map` DISABLE KEYS */;
REPLACE INTO `factory_merchant_map` (`id`, `merchant_id`, `factory_id`, `goods_number`, `trader_number`, `start_at`, `end_at`, `create_at`, `update_at`, `valid`) VALUES
	(1, 1, 1, 1, 1, '2019-02-12 17:57:33.450', '2020-02-12 17:57:18.000', '2019-02-12 17:57:33.457', '2019-02-17 19:41:09.241', 1),
	(2, 2, 2, 12, 13, '2019-02-16 14:32:10.444', '2020-02-12 17:57:18.000', '2019-02-16 14:32:10.459', '2019-02-20 23:25:20.636', 1),
	(3, 2, 3, 1, 1, '2019-02-16 14:32:30.796', '2020-02-12 17:57:18.000', '2019-02-16 14:32:30.796', '2019-02-17 19:41:11.090', 1),
	(4, 2, 4, 1, 1, '2019-02-16 15:41:49.986', '2020-02-12 17:57:18.000', '2019-02-16 15:41:49.986', '2019-02-17 19:41:12.241', 1);
/*!40000 ALTER TABLE `factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.goods_factory_merchant_map 结构
DROP TABLE IF EXISTS `goods_factory_merchant_map`;
CREATE TABLE IF NOT EXISTS `goods_factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
  `factory_metchat_map_id` int(10) unsigned DEFAULT '0' COMMENT '药厂渠道关系id',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_goods_factory_merchant_map_goods_info` (`goods_id`),
  KEY `FK_goods_factory_merchant_map_factory_merchant_map` (`factory_metchat_map_id`),
  CONSTRAINT `FK_goods_factory_merchant_map_factory_merchant_map` FOREIGN KEY (`factory_metchat_map_id`) REFERENCES `factory_merchant_map` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_goods_factory_merchant_map_goods_info` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商品渠道关系';

-- 正在导出表  tanya.goods_factory_merchant_map 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `goods_factory_merchant_map` DISABLE KEYS */;
REPLACE INTO `goods_factory_merchant_map` (`id`, `goods_id`, `factory_metchat_map_id`, `start_at`, `end_at`, `create_at`, `update_at`, `valid`) VALUES
	(1, 1, 4, NULL, NULL, '2019-02-21 20:37:58.744', '2019-02-21 20:37:58.748', 1);
/*!40000 ALTER TABLE `goods_factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.goods_info 结构
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE IF NOT EXISTS `goods_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '产品名称',
  `comment` varchar(500) DEFAULT NULL COMMENT '产品备注',
  `photo_url` varchar(200) DEFAULT NULL COMMENT '产品照片地址',
  `production` varchar(50) NOT NULL COMMENT '生产单位',
  `spec` varchar(50) NOT NULL COMMENT '规格',
  `amount` double NOT NULL COMMENT '价格',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='药品信息';

-- 正在导出表  tanya.goods_info 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `goods_info` DISABLE KEYS */;
REPLACE INTO `goods_info` (`id`, `title`, `comment`, `photo_url`, `production`, `spec`, `amount`, `start_at`, `end_at`, `create_at`, `update_at`, `valid`) VALUES
	(1, '测试创建的退烧药', '新增药品', 'string', 'string', 'string', 0, NULL, NULL, '2019-02-21 20:37:58.725', '2019-02-22 15:14:15.373', 1);
/*!40000 ALTER TABLE `goods_info` ENABLE KEYS */;

-- 导出  表 tanya.merchant_info 结构
DROP TABLE IF EXISTS `merchant_info`;
CREATE TABLE IF NOT EXISTS `merchant_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `FK_merchant_info_user_info` (`user_id`),
  CONSTRAINT `FK_merchant_info_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='商务渠道公司';

-- 正在导出表  tanya.merchant_info 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `merchant_info` DISABLE KEYS */;
REPLACE INTO `merchant_info` (`id`, `title`, `comment`, `user_id`, `start_at`, `end_at`, `create_at`, `update_at`, `valid`) VALUES
	(1, '测试商务渠道A', '测试修改时间', NULL, '2019-12-17 04:35:09.000', '2019-02-17 04:35:09.000', '2019-02-10 13:03:52.856', '2019-02-17 12:59:36.847', 1),
	(2, '商务渠道B', NULL, 1, '2019-02-11 17:57:10.000', '2020-02-12 17:57:18.000', '2019-02-10 13:04:08.884', '2019-02-14 23:38:43.819', 1);
/*!40000 ALTER TABLE `merchant_info` ENABLE KEYS */;

-- 导出  表 tanya.notification_info 结构
DROP TABLE IF EXISTS `notification_info`;
CREATE TABLE IF NOT EXISTS `notification_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '通知',
  `comment` varchar(200) DEFAULT NULL COMMENT '通知内容',
  `factory_id` int(10) unsigned NOT NULL COMMENT '药厂id',
  `trader_id` int(10) unsigned NOT NULL COMMENT '销售代表id',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_notification_info_factory_info` (`factory_id`),
  KEY `FK_notification_info_trader_info` (`trader_id`),
  CONSTRAINT `FK_notification_info_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_notification_info_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告信息';

-- 正在导出表  tanya.notification_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `notification_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification_info` ENABLE KEYS */;

-- 导出  表 tanya.order_info 结构
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE IF NOT EXISTS `order_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '订单名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `trader_factory_merchant_id` int(10) unsigned NOT NULL COMMENT '流通渠道id',
  `shop_id` int(10) unsigned NOT NULL COMMENT '目标药店',
  `goods_id` int(10) unsigned NOT NULL COMMENT '商品id',
  `goods_number` int(10) unsigned NOT NULL COMMENT '商品数量',
  `amount` double NOT NULL COMMENT '订单金额',
  `discount_id` int(10) unsigned DEFAULT NULL COMMENT '参加活动id',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `factory_confirm_status` tinyint(3) DEFAULT NULL COMMENT '药厂确认状态',
  `factory_confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '药厂确认时间',
  `merchant_confirm_status` tinyint(3) DEFAULT NULL COMMENT '渠道确认状态',
  `merchant_confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '渠道确认时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_order_info_merchant_info` (`trader_factory_merchant_id`),
  KEY `FK_order_info_shop_info` (`shop_id`),
  KEY `FK_order_info_goods_info` (`goods_id`),
  KEY `FK_order_info_discount_info` (`discount_id`),
  CONSTRAINT `FK_order_info_discount_info` FOREIGN KEY (`discount_id`) REFERENCES `discount_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_order_info_goods_info` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_order_info_shop_info` FOREIGN KEY (`shop_id`) REFERENCES `shop_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_order_info_trader_factory_merchant_map` FOREIGN KEY (`trader_factory_merchant_id`) REFERENCES `trader_factory_merchant_map` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单信息';

-- 正在导出表  tanya.order_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;

-- 导出  表 tanya.permission_info 结构
DROP TABLE IF EXISTS `permission_info`;
CREATE TABLE IF NOT EXISTS `permission_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) unsigned DEFAULT NULL COMMENT '父编号,本权限可能是该父编号权限的子权限',
  `parent_ids` varchar(20) DEFAULT NULL COMMENT '父编号列表',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view',
  `resource_type` varchar(20) DEFAULT NULL COMMENT '资源类型，[menu|button]',
  `url` varchar(200) DEFAULT NULL COMMENT '资源路径 如：/userinfo/list',
  `name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='权限信息';

-- 正在导出表  tanya.permission_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `permission_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission_info` ENABLE KEYS */;

-- 导出  表 tanya.role_info 结构
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE IF NOT EXISTS `role_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `module_name` varchar(20) DEFAULT NULL COMMENT '角色信息模块名',
  `comment` varchar(100) DEFAULT NULL COMMENT '角色描述,UI界面显示使用',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色信息';

-- 正在导出表  tanya.role_info 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `role_info` DISABLE KEYS */;
REPLACE INTO `role_info` (`id`, `role`, `module_name`, `comment`, `create_at`, `update_at`, `valid`) VALUES
	(1, 'superAdmin', NULL, '超级管理员', '2019-02-10 12:25:15.801', '2019-02-10 12:27:40.746', 1),
	(2, 'admin', NULL, '管理员', '2019-02-10 12:25:28.783', '2019-02-10 12:27:36.703', 1),
	(3, 'merchant', NULL, '渠道商', '2019-02-10 12:26:15.559', '2019-02-10 12:27:35.441', 1),
	(4, 'factory', NULL, '药厂主管', '2019-02-10 12:27:00.078', '2019-02-10 12:27:34.147', 1),
	(5, 'trader', NULL, '销售代表', '2019-02-10 12:27:24.703', '2019-02-10 12:27:32.614', 1),
	(6, 'salesman', NULL, '促销员', '2019-02-10 12:27:58.121', '2019-02-10 12:27:58.121', 1);
/*!40000 ALTER TABLE `role_info` ENABLE KEYS */;

-- 导出  表 tanya.role_permission_map 结构
DROP TABLE IF EXISTS `role_permission_map`;
CREATE TABLE IF NOT EXISTS `role_permission_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `perimission_id` int(10) unsigned NOT NULL COMMENT '权限id',
  `role_id` int(10) unsigned NOT NULL COMMENT '角色id',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_role_permission_map_permission_info` (`perimission_id`),
  KEY `FK_role_permission_map_role_info` (`role_id`),
  CONSTRAINT `FK_role_permission_map_permission_info` FOREIGN KEY (`perimission_id`) REFERENCES `permission_info` (`id`),
  CONSTRAINT `FK_role_permission_map_role_info` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='权限信息';

-- 正在导出表  tanya.role_permission_map 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `role_permission_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permission_map` ENABLE KEYS */;

-- 导出  表 tanya.salesman_info 结构
DROP TABLE IF EXISTS `salesman_info`;
CREATE TABLE IF NOT EXISTS `salesman_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `rewards` int(10) unsigned DEFAULT NULL COMMENT '促销员积分',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_salesman_info_user_info` (`user_id`),
  CONSTRAINT `FK_salesman_info_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销员信息';

-- 正在导出表  tanya.salesman_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `salesman_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesman_info` ENABLE KEYS */;

-- 导出  表 tanya.salesman_trader_map 结构
DROP TABLE IF EXISTS `salesman_trader_map`;
CREATE TABLE IF NOT EXISTS `salesman_trader_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trader_id` int(10) unsigned NOT NULL COMMENT '销售人员id',
  `salesman_id` int(10) unsigned NOT NULL COMMENT '促销员id',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_salesman_trader_map_trader_info` (`trader_id`),
  KEY `FK_salesman_trader_map_salesman_info` (`salesman_id`),
  CONSTRAINT `FK_salesman_trader_map_salesman_info` FOREIGN KEY (`salesman_id`) REFERENCES `salesman_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_salesman_trader_map_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销员-销售代表关系';

-- 正在导出表  tanya.salesman_trader_map 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `salesman_trader_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesman_trader_map` ENABLE KEYS */;

-- 导出  表 tanya.shop_info 结构
DROP TABLE IF EXISTS `shop_info`;
CREATE TABLE IF NOT EXISTS `shop_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '药店名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '药店备注',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后登入时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='药店信息';

-- 正在导出表  tanya.shop_info 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `shop_info` DISABLE KEYS */;
REPLACE INTO `shop_info` (`id`, `title`, `comment`, `create_at`, `update_at`, `valid`) VALUES
	(1, '222', '333', '2019-02-22 15:09:15.751', '2019-02-28 00:21:45.049', 1),
	(2, '药店测试', '药店测试1', '2019-02-27 21:27:26.262', '2019-02-27 21:27:26.268', 1),
	(3, '测试药店22', '测试1', '2019-02-27 23:01:20.113', '2019-02-28 22:09:04.846', 1),
	(4, 'test3', 'test4', '2019-02-27 23:06:20.822', '2019-02-27 23:06:20.827', 1),
	(5, 'test5', 'test6', '2019-02-27 23:06:37.714', '2019-02-27 23:06:37.718', 1);
/*!40000 ALTER TABLE `shop_info` ENABLE KEYS */;

-- 导出  表 tanya.trader_factory_merchant_map 结构
DROP TABLE IF EXISTS `trader_factory_merchant_map`;
CREATE TABLE IF NOT EXISTS `trader_factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trader_id` int(10) unsigned NOT NULL COMMENT '销售人员id',
  `factory_id` int(10) unsigned NOT NULL COMMENT '药厂id',
  `factory_merchant_map_id` int(10) unsigned NOT NULL COMMENT '药厂渠道关系id',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_trader_factory_merchant_map_trader_info` (`trader_id`),
  KEY `FK_trader_factory_merchant_map_factory_info` (`factory_id`),
  KEY `FK_trader_factory_merchant_map_factory_merchant_map` (`factory_merchant_map_id`),
  CONSTRAINT `FK_trader_factory_merchant_map_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_trader_factory_merchant_map_factory_merchant_map` FOREIGN KEY (`factory_merchant_map_id`) REFERENCES `factory_merchant_map` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_trader_factory_merchant_map_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='销售代表-药厂-渠道关系';

-- 正在导出表  tanya.trader_factory_merchant_map 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `trader_factory_merchant_map` DISABLE KEYS */;
REPLACE INTO `trader_factory_merchant_map` (`id`, `trader_id`, `factory_id`, `factory_merchant_map_id`, `start_at`, `end_at`, `create_at`, `update_at`, `valid`) VALUES
	(1, 1, 1, 1, '2019-02-16 17:19:35.136', '2020-02-17 17:19:48.000', '2019-02-17 17:19:35.136', '2019-02-17 17:19:56.009', 1),
	(2, 2, 2, 2, '2019-02-17 19:43:26.650', '2020-02-12 17:57:18.000', '2019-02-17 19:43:26.651', '2019-02-17 19:43:26.657', 1);
/*!40000 ALTER TABLE `trader_factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.trader_info 结构
DROP TABLE IF EXISTS `trader_info`;
CREATE TABLE IF NOT EXISTS `trader_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_trader_info_user_info` (`user_id`),
  CONSTRAINT `FK_trader_info_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='销售代表信息';

-- 正在导出表  tanya.trader_info 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `trader_info` DISABLE KEYS */;
REPLACE INTO `trader_info` (`id`, `title`, `comment`, `user_id`, `create_at`, `update_at`, `valid`) VALUES
	(1, '销售人员测试A', NULL, NULL, '2019-02-17 17:18:56.432', '2019-02-17 17:20:07.768', 1),
	(2, '销售D', '测试', 7, '2019-02-17 19:43:26.634', '2019-02-20 22:23:24.341', 1);
/*!40000 ALTER TABLE `trader_info` ENABLE KEYS */;

-- 导出  表 tanya.user_info 结构
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE IF NOT EXISTS `user_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `guid` varchar(32) DEFAULT NULL COMMENT '系统唯一标识',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(256) DEFAULT NULL COMMENT '登录密码',
  `name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `id_card_num` varchar(50) DEFAULT NULL COMMENT '身份证号码',
  `wechat_id` varchar(50) DEFAULT NULL COMMENT '微信openId',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱号码',
  `comment` varchar(256) DEFAULT NULL COMMENT '角色备注',
  `state` char(1) DEFAULT '0' COMMENT '用户状态：0:正常状态,1：用户被锁定',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `last_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '最后登入时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `guid` (`guid`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='备注信息';

-- 正在导出表  tanya.user_info 的数据：~12 rows (大约)
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
REPLACE INTO `user_info` (`id`, `guid`, `username`, `password`, `name`, `id_card_num`, `wechat_id`, `phone`, `email`, `comment`, `state`, `create_at`, `update_at`, `last_at`, `valid`) VALUES
	(1, 'KKKXK4K0SYatbrymVkzWDA', 'cuihsh@126.com', NULL, '崔浩晟B级', NULL, 'oQoIN5JaXtzykCbI2-9ykXgOorL0', '1661112233111', 'cuihsh@126.com', NULL, '0', '2019-02-10 12:56:42.385', '2019-02-28 21:34:28.018', '2019-02-28 21:34:28.017', 1),
	(2, 'dWuNJa8uSoSH4BEdWixdZQ', 'sharp', '385820687a9fc2030f10230506ac8611343a27a448859265', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-02-12 00:01:21.988', '2019-02-25 21:06:43.326', '2019-02-25 21:06:43.325', 1),
	(3, 'QPkoEO-hT4CLrSdWNRrtEw', 'wys', '36581f08fd90c07c85b5b01a23a217c07043310d08691c7b', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-02-12 13:45:47.566', '2019-02-12 17:53:24.923', '2019-02-12 17:53:24.915', 1),
	(4, 'OAT2TWPYQji5bn_XsH5jAw', 'superAdmin', 'c4798372be42a10c7534ba85b8359913a14448c41c061100', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-02-12 17:37:22.267', '2019-02-28 18:10:36.203', '2019-02-28 18:10:36.202', 1),
	(5, '2dMe3zzPRvuUn1AcGQH8NQ', '1726471017@qq.com', NULL, '曾蕾11', NULL, 'oQoIN5JC88St0HzNTqii0oDNm-zk', '18522120314', '1726471017@qq.com', NULL, '0', '2019-02-14 22:40:38.509', '2019-02-28 23:19:45.105', '2019-02-28 23:17:54.343', 1),
	(6, 'q64jviX7T0WFDKxKPYdKdQ', 'cuihsh', NULL, '崔浩晟', NULL, 'oQoIN5EXHS-HAhhr-RKulOEOvcTs', '15520552055', 'cuihsh', NULL, '0', '2019-02-17 12:02:58.046', '2019-02-20 22:22:42.140', '2019-02-20 22:22:42.139', 1),
	(7, 'eEzbN1ldSZmXn8IOuaCOhg', '精明哦', NULL, '崔浩晟3销售人员', NULL, 'oQoIN5FwaTvPvjqCxZ5WGrPg44EQ', '51543464878', '精明哦', NULL, '0', '2019-02-17 23:30:20.621', '2019-02-20 22:21:49.338', '2019-02-20 22:21:49.337', 1),
	(8, 'x04nj4gXRjy3DmoAAc5ntQ', 'merchant', '324c82130451720264f02063f76187d9dd9561856bb73799', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-02-25 22:48:41.862', '2019-02-25 22:57:29.369', '2019-02-25 22:57:29.368', 1),
	(9, 'm_0-3-DqSwWZBvOHSjG0kQ', 'admin', 'c37521117257c6938ec38621c4438d35c043e06b42119f0a', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-02-25 22:48:56.066', '2019-02-25 22:55:22.843', '2019-02-25 22:55:22.843', 1),
	(10, 'gclk7bGOQWiTunrjM_3mzw', 'factory', '31178063d67455368a651a1b77ea4ed6fd7919164a22da64', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-02-25 22:49:06.931', '2019-02-28 17:05:31.472', '2019-02-28 17:05:31.471', 1),
	(11, '7QQsu1mrRKyrBoPG4syGzw', 'trader', '61bb7b306b7e776c4076032344461db6418f025b67a0a60b', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-02-25 22:49:12.851', '2019-02-25 22:56:31.713', '2019-02-25 22:56:31.712', 1),
	(12, 'Dsi-15XqQzig0DHt0SvwlA', 'salesman', '471f6be7c88e56f54f58913ef9ad32a9068601b88ea1f82f', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-02-25 22:49:20.270', '2019-02-25 22:56:47.985', '2019-02-25 22:56:47.984', 1);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;

-- 导出  表 tanya.user_role_map 结构
DROP TABLE IF EXISTS `user_role_map`;
CREATE TABLE IF NOT EXISTS `user_role_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户id',
  `role_id` int(10) unsigned DEFAULT NULL COMMENT '角色id',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_user_role_map_user_info` (`user_id`),
  KEY `FK_user_role_map_role_info` (`role_id`),
  CONSTRAINT `FK_user_role_map_role_info` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`id`),
  CONSTRAINT `FK_user_role_map_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

-- 正在导出表  tanya.user_role_map 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `user_role_map` DISABLE KEYS */;
REPLACE INTO `user_role_map` (`id`, `user_id`, `role_id`, `create_at`, `update_at`, `valid`) VALUES
	(1, 1, 3, '2019-02-10 12:58:45.258', '2019-02-14 23:17:11.522', 1),
	(2, 2, 4, '2019-02-12 00:06:39.962', '2019-02-21 20:36:47.250', 1),
	(3, 4, 1, '2019-02-12 17:37:50.698', '2019-02-12 17:37:50.698', 1),
	(6, 3, NULL, '2019-02-12 13:52:32.630', '2019-02-12 18:22:09.994', 1),
	(7, 6, 4, '2019-02-17 12:57:01.830', '2019-02-19 23:24:53.993', 1),
	(8, 7, 5, '2019-02-20 22:23:24.361', '2019-02-20 22:23:24.371', 1);
/*!40000 ALTER TABLE `user_role_map` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
