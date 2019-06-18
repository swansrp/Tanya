-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.21 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 tanya 的数据库结构
CREATE DATABASE IF NOT EXISTS `tanya_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tanya_test`;

-- 导出  表 tanya.admin_info 结构
CREATE TABLE IF NOT EXISTS `admin_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员信息';

-- 正在导出表  tanya.admin_info 的数据：~0 rows (大约)
DELETE FROM `admin_info`;
/*!40000 ALTER TABLE `admin_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_info` ENABLE KEYS */;

-- 导出  表 tanya.campaign_history 结构
CREATE TABLE IF NOT EXISTS `campaign_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `comment` varchar(50) DEFAULT NULL COMMENT '备注',
  `salesman_id` int(10) unsigned DEFAULT NULL COMMENT '上报促销员id',
  `campaign_id` int(10) unsigned DEFAULT NULL COMMENT '上报促销活动id',
  `number` int(10) unsigned DEFAULT NULL COMMENT '促销活动数量',
  `url` varchar(200) DEFAULT NULL COMMENT '上传照片地址',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `confirm_status` tinyint(3) DEFAULT NULL COMMENT '确认状态',
  `confirm_at` datetime(3) DEFAULT NULL COMMENT '确认时间',
  `confirm_by` int(11) DEFAULT NULL COMMENT '确认者id',
  `rewards` int(10) unsigned DEFAULT NULL COMMENT '该促销积分回馈',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动历史信息';

-- 正在导出表  tanya.campaign_history 的数据：~0 rows (大约)
DELETE FROM `campaign_history`;
/*!40000 ALTER TABLE `campaign_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_history` ENABLE KEYS */;

-- 导出  表 tanya.campaign_info 结构
CREATE TABLE IF NOT EXISTS `campaign_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '促销活动名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '促销活动备注',
  `trader_id` int(10) unsigned NOT NULL COMMENT '发起销售人员id',
  `goods_id` int(10) unsigned NOT NULL COMMENT '促销商品id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `confirm_status` tinyint(3) DEFAULT NULL COMMENT '确认状态',
  `confirm_at` datetime(3) DEFAULT NULL COMMENT '确认时间',
  `confirm_by` int(11) DEFAULT NULL COMMENT '确认者id',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销积分活动';

-- 正在导出表  tanya.campaign_info 的数据：~0 rows (大约)
DELETE FROM `campaign_info`;
/*!40000 ALTER TABLE `campaign_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_info` ENABLE KEYS */;

-- 导出  表 tanya.campaign_salesman_map 结构
CREATE TABLE IF NOT EXISTS `campaign_salesman_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `campaign_id` int(10) unsigned NOT NULL COMMENT '活动id',
  `salesman_id` int(10) unsigned NOT NULL COMMENT '促销人员id',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动-促销员关系';

-- 正在导出表  tanya.campaign_salesman_map 的数据：~0 rows (大约)
DELETE FROM `campaign_salesman_map`;
/*!40000 ALTER TABLE `campaign_salesman_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_salesman_map` ENABLE KEYS */;

-- 导出  表 tanya.discount_info 结构
CREATE TABLE IF NOT EXISTS `discount_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '活动名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `factory_metchat_map_id` int(10) unsigned DEFAULT NULL COMMENT '药厂渠道关系id',
  `goods_id` int(10) unsigned DEFAULT NULL COMMENT '商品id',
  `goods_number` int(10) unsigned NOT NULL COMMENT '活动数量',
  `amount` double NOT NULL COMMENT '活动金额',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `confirm_status` tinyint(3) DEFAULT NULL COMMENT '确认状态',
  `confirm_at` datetime(3) DEFAULT NULL COMMENT '确认时间',
  `confirm_by` int(11) DEFAULT NULL COMMENT '确认者id',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品销售活动';

-- 正在导出表  tanya.discount_info 的数据：~0 rows (大约)
DELETE FROM `discount_info`;
/*!40000 ALTER TABLE `discount_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `discount_info` ENABLE KEYS */;

-- 导出  表 tanya.factory_info 结构
CREATE TABLE IF NOT EXISTS `factory_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医药公司人员信息';

-- 正在导出表  tanya.factory_info 的数据：~0 rows (大约)
DELETE FROM `factory_info`;
/*!40000 ALTER TABLE `factory_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `factory_info` ENABLE KEYS */;

-- 导出  表 tanya.factory_merchant_map 结构
CREATE TABLE IF NOT EXISTS `factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `merchant_id` int(10) unsigned DEFAULT NULL COMMENT '渠道id',
  `factory_id` int(10) unsigned DEFAULT NULL COMMENT '药厂id',
  `goods_number` int(10) unsigned DEFAULT NULL COMMENT '注册商品数量',
  `trader_number` int(10) unsigned DEFAULT NULL COMMENT '销售代表数量',
  `discount_number` int(10) unsigned DEFAULT NULL COMMENT '商品活动数量',
  `campaign_number` int(10) unsigned DEFAULT NULL COMMENT '促销活动数量',
  `goods_trader_bind` tinyint(3) unsigned DEFAULT NULL COMMENT '商品-营销员绑定功能',
  `shop_trader_bind` tinyint(3) unsigned DEFAULT NULL COMMENT '商店-营销员绑定功能',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后登入时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='厂商-渠道关系';

-- 正在导出表  tanya.factory_merchant_map 的数据：~0 rows (大约)
DELETE FROM `factory_merchant_map`;
/*!40000 ALTER TABLE `factory_merchant_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.feature 结构
CREATE TABLE IF NOT EXISTS `feature` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(50) NOT NULL COMMENT '功能标签',
  `value` varchar(50) NOT NULL DEFAULT '0' COMMENT '功能值',
  `comment` varchar(50) DEFAULT '0' COMMENT '功能描述',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统功能表';

-- 正在导出表  tanya.feature 的数据：~0 rows (大约)
DELETE FROM `feature`;
/*!40000 ALTER TABLE `feature` DISABLE KEYS */;
INSERT INTO `feature` (`id`, `key`, `value`, `comment`, `create_at`, `update_at`, `valid`) VALUES
	(1, 'SHOP_BIND_FACTORY_MERCHANT', '0', '0', '2019-04-06 11:12:08.162', '2019-04-06 11:12:08.162', 1),
	(2, 'MERCHANT_BIND_ADMIN', '0', '0', '2019-04-07 14:33:57.821', '2019-04-07 14:34:46.333', 1),
	(3, 'NOTIFICATION_DEFAULT_PERIOD', '15', '0', '2019-04-07 14:34:05.362', '2019-04-07 14:34:59.763', 1),
	(4, 'DISCOUNT_DEFAULT_PERIOD', '365', '0', '2019-04-07 14:34:11.002', '2019-04-07 14:35:59.442', 1),
	(5, 'ORDER_DEFAULT_PERIOD', '365', '0', '2019-04-07 14:34:21.337', '2019-04-07 14:36:01.279', 1),
	(6, 'CAMPAIGN_DEFAULT_PERIOD', '365', '0', '2019-04-07 14:34:40.426', '2019-04-07 14:36:02.442', 1),
	(7, 'MERCHANT_DEFAULT_PERIOD', '30', '0', '2019-04-07 14:36:31.185', '2019-04-07 14:36:31.185', 1);
/*!40000 ALTER TABLE `feature` ENABLE KEYS */;

-- 导出  表 tanya.goods_factory_merchant_map 结构
CREATE TABLE IF NOT EXISTS `goods_factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
  `factory_merchant_map_id` int(10) unsigned NOT NULL COMMENT '药厂渠道关系id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品渠道关系';

-- 正在导出表  tanya.goods_factory_merchant_map 的数据：~0 rows (大约)
DELETE FROM `goods_factory_merchant_map`;
/*!40000 ALTER TABLE `goods_factory_merchant_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods_factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.goods_info 结构
CREATE TABLE IF NOT EXISTS `goods_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '产品名称',
  `comment` varchar(500) DEFAULT NULL COMMENT '产品备注',
  `photo_url` varchar(200) DEFAULT NULL COMMENT '产品照片地址',
  `production` varchar(50) NOT NULL COMMENT '生产单位',
  `spec` varchar(50) NOT NULL COMMENT '规格',
  `amount` double NOT NULL COMMENT '价格',
  `merchant_id` int(10) unsigned DEFAULT NULL COMMENT '渠道id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='药品信息';

-- 正在导出表  tanya.goods_info 的数据：~0 rows (大约)
DELETE FROM `goods_info`;
/*!40000 ALTER TABLE `goods_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods_info` ENABLE KEYS */;

-- 导出  表 tanya.goods_trader_factory_merchant_map 结构
CREATE TABLE IF NOT EXISTS `goods_trader_factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trader_id` int(10) unsigned NOT NULL COMMENT '销售人员id',
  `goods_id` int(10) unsigned NOT NULL COMMENT '商品id',
  `trader_factory_merchant_map_id` int(10) unsigned NOT NULL COMMENT '销售药厂渠道关系id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品-销售代表-药厂-渠道关系';

-- 正在导出表  tanya.goods_trader_factory_merchant_map 的数据：~0 rows (大约)
DELETE FROM `goods_trader_factory_merchant_map`;
/*!40000 ALTER TABLE `goods_trader_factory_merchant_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods_trader_factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.merchant_admin_map 结构
CREATE TABLE IF NOT EXISTS `merchant_admin_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `merchant_id` int(10) unsigned DEFAULT NULL COMMENT '渠道id',
  `admin_id` int(10) unsigned DEFAULT NULL COMMENT '管理员id',
  `factory_number` int(10) unsigned DEFAULT NULL COMMENT '药厂数量',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `sign` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否签约',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后登入时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道-管理员关系';

-- 正在导出表  tanya.merchant_admin_map 的数据：~0 rows (大约)
DELETE FROM `merchant_admin_map`;
/*!40000 ALTER TABLE `merchant_admin_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `merchant_admin_map` ENABLE KEYS */;

-- 导出  表 tanya.merchant_info 结构
CREATE TABLE IF NOT EXISTS `merchant_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商务渠道公司';

-- 正在导出表  tanya.merchant_info 的数据：~0 rows (大约)
DELETE FROM `merchant_info`;
/*!40000 ALTER TABLE `merchant_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `merchant_info` ENABLE KEYS */;

-- 导出  表 tanya.notification_info 结构
CREATE TABLE IF NOT EXISTS `notification_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '通知',
  `comment` varchar(200) DEFAULT NULL COMMENT '通知内容',
  `factory_id` int(10) unsigned NOT NULL COMMENT '药厂id',
  `trader_id` int(10) unsigned DEFAULT NULL COMMENT '销售代表id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告信息';

-- 正在导出表  tanya.notification_info 的数据：~0 rows (大约)
DELETE FROM `notification_info`;
/*!40000 ALTER TABLE `notification_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification_info` ENABLE KEYS */;

-- 导出  表 tanya.order_info 结构
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
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `factory_confirm_status` tinyint(3) DEFAULT NULL COMMENT '药厂确认状态',
  `factory_confirm_at` datetime(3) DEFAULT NULL COMMENT '药厂确认时间',
  `factory_confirm_by` int(11) DEFAULT NULL COMMENT '工厂确认者id',
  `merchant_confirm_status` tinyint(3) DEFAULT NULL COMMENT '渠道确认状态',
  `merchant_confirm_at` datetime(3) DEFAULT NULL COMMENT '渠道确认时间',
  `merchant_confirm_number` int(11) DEFAULT NULL COMMENT '渠道确认数量',
  `merchant_confirm_by` int(11) DEFAULT NULL COMMENT '渠道确认者id',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单信息';

-- 正在导出表  tanya.order_info 的数据：~0 rows (大约)
DELETE FROM `order_info`;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;

-- 导出  表 tanya.permission_info 结构
CREATE TABLE IF NOT EXISTS `permission_info` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT '父编号,本权限可能是该父编号权限的子权限',
  `permission` VARCHAR(100) NULL DEFAULT NULL,
  `resource_type` VARCHAR(20) NULL DEFAULT NULL COMMENT '资源类型，[menu|button]',
  `path` VARCHAR(50) NULL DEFAULT NULL COMMENT '资源路径 如：/userinfo/list',
  `icon_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '图标地址',
  `name` VARCHAR(50) NULL DEFAULT NULL COMMENT '权限名称',
  `create_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限信息';

-- 正在导出表  tanya.permission_info 的数据：~0 rows (大约)
DELETE FROM `permission_info`;
/*!40000 ALTER TABLE `permission_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission_info` ENABLE KEYS */;

-- 导出  表 tanya.role_info 结构
CREATE TABLE IF NOT EXISTS `role_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `module_name` varchar(20) DEFAULT NULL COMMENT '角色信息模块名',
  `comment` varchar(100) DEFAULT NULL COMMENT '角色描述,UI界面显示使用',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色信息';

-- 正在导出表  tanya.role_info 的数据：~6 rows (大约)
DELETE FROM `role_info`;
/*!40000 ALTER TABLE `role_info` DISABLE KEYS */;
INSERT INTO `role_info` (`id`, `role`, `module_name`, `comment`, `create_at`, `update_at`, `valid`) VALUES
	(1, 'superAdmin', NULL, '超级管理员', '2019-02-10 12:25:15.801', '2019-02-10 12:27:40.746', 1),
	(2, 'admin', NULL, '管理员', '2019-02-10 12:25:28.783', '2019-02-10 12:27:36.703', 1),
	(3, 'merchant', NULL, '渠道商', '2019-02-10 12:26:15.559', '2019-02-10 12:27:35.441', 1),
	(4, 'factory', NULL, '药厂主管', '2019-02-10 12:27:00.078', '2019-02-10 12:27:34.147', 1),
	(5, 'trader', NULL, '销售代表', '2019-02-10 12:27:24.703', '2019-02-10 12:27:32.614', 1),
	(6, 'salesman', NULL, '促销员', '2019-02-10 12:27:58.121', '2019-02-10 12:27:58.121', 1);
/*!40000 ALTER TABLE `role_info` ENABLE KEYS */;

-- 导出  表 tanya.role_permission_map 结构
CREATE TABLE IF NOT EXISTS `role_permission_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `perimission_id` int(10) unsigned NOT NULL COMMENT '权限id',
  `role_id` int(10) unsigned NOT NULL COMMENT '角色id',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限信息';

-- 正在导出表  tanya.role_permission_map 的数据：~0 rows (大约)
DELETE FROM `role_permission_map`;
/*!40000 ALTER TABLE `role_permission_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permission_map` ENABLE KEYS */;

-- 导出  表 tanya.salesman_info 结构
CREATE TABLE IF NOT EXISTS `salesman_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `rewards` int(10) unsigned DEFAULT NULL COMMENT '促销员积分',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销员信息';

-- 正在导出表  tanya.salesman_info 的数据：~0 rows (大约)
DELETE FROM `salesman_info`;
/*!40000 ALTER TABLE `salesman_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesman_info` ENABLE KEYS */;

-- 导出  表 tanya.salesman_trader_map 结构
CREATE TABLE IF NOT EXISTS `salesman_trader_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trader_id` int(10) unsigned NOT NULL COMMENT '销售人员id',
  `salesman_id` int(10) unsigned NOT NULL COMMENT '促销员id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销员-销售代表关系';

-- 正在导出表  tanya.salesman_trader_map 的数据：~0 rows (大约)
DELETE FROM `salesman_trader_map`;
/*!40000 ALTER TABLE `salesman_trader_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesman_trader_map` ENABLE KEYS */;

-- 导出  表 tanya.shop_factory_merchant_map 结构
CREATE TABLE IF NOT EXISTS `shop_factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` int(10) unsigned NOT NULL COMMENT '药店id',
  `factory_merchant_map_id` int(10) unsigned DEFAULT '0' COMMENT '药厂渠道关系id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='药店渠道关系';

-- 正在导出表  tanya.shop_factory_merchant_map 的数据：~0 rows (大约)
DELETE FROM `shop_factory_merchant_map`;
/*!40000 ALTER TABLE `shop_factory_merchant_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop_factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.shop_info 结构
CREATE TABLE IF NOT EXISTS `shop_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '药店名称',
  `comment` varchar(200) DEFAULT NULL COMMENT '药店备注',
  `merchant_id` int(10) unsigned NOT NULL COMMENT '渠道id',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后登入时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='药店信息';

-- 正在导出表  tanya.shop_info 的数据：~0 rows (大约)
DELETE FROM `shop_info`;
/*!40000 ALTER TABLE `shop_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop_info` ENABLE KEYS */;

-- 导出  表 tanya.shop_trader_factory_merchant_map 结构
CREATE TABLE IF NOT EXISTS `shop_trader_factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trader_id` int(10) unsigned NOT NULL COMMENT '销售人员id',
  `shop_id` int(10) unsigned NOT NULL COMMENT '药店id',
  `trader_factory_merchant_map_id` int(10) unsigned NOT NULL COMMENT '销售药厂渠道关系id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='药店-销售代表-药厂-渠道关系';

-- 正在导出表  tanya.shop_trader_factory_merchant_map 的数据：~0 rows (大约)
DELETE FROM `shop_trader_factory_merchant_map`;
/*!40000 ALTER TABLE `shop_trader_factory_merchant_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop_trader_factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.trader_factory_merchant_map 结构
CREATE TABLE IF NOT EXISTS `trader_factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trader_id` int(10) unsigned NOT NULL COMMENT '销售人员id',
  `factory_id` int(10) unsigned NOT NULL COMMENT '药厂id',
  `factory_merchant_map_id` int(10) unsigned NOT NULL COMMENT '药厂渠道关系id',
  `start_at` datetime(3) DEFAULT NULL COMMENT '有效期起始',
  `end_at` datetime(3) DEFAULT NULL COMMENT '有效期结束',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售代表-药厂-渠道关系';

-- 正在导出表  tanya.trader_factory_merchant_map 的数据：~0 rows (大约)
DELETE FROM `trader_factory_merchant_map`;
/*!40000 ALTER TABLE `trader_factory_merchant_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `trader_factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.trader_info 结构
CREATE TABLE IF NOT EXISTS `trader_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '角色名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `comment` varchar(200) DEFAULT NULL COMMENT '角色备注',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '人员信息',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售代表信息';

-- 正在导出表  tanya.trader_info 的数据：~0 rows (大约)
DELETE FROM `trader_info`;
/*!40000 ALTER TABLE `trader_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `trader_info` ENABLE KEYS */;

-- 导出  表 tanya.user_info 结构
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
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `last_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '最后登入时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `guid` (`guid`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='备注信息';

-- 正在导出表  tanya.user_info 的数据：~1 rows (大约)
DELETE FROM `user_info`;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` (`id`, `guid`, `username`, `password`, `name`, `id_card_num`, `wechat_id`, `phone`, `email`, `comment`, `state`, `create_at`, `update_at`, `last_at`, `valid`) VALUES
	(1, 'OAT2TWPYQji5bn_XsH5jAw', 'superAdmin', 'c4798372be42a10c7534ba85b8359913a14448c41c061100', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2019-04-04 14:08:24.665', '2019-04-04 20:58:42.673', '2019-04-04 20:58:42.672', 1);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;

-- 导出  表 tanya.user_role_map 结构
CREATE TABLE IF NOT EXISTS `user_role_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户id',
  `role_id` int(10) unsigned DEFAULT NULL COMMENT '角色id',
  `create_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

-- 正在导出表  tanya.user_role_map 的数据：~0 rows (大约)
DELETE FROM `user_role_map`;
/*!40000 ALTER TABLE `user_role_map` DISABLE KEYS */;
INSERT INTO `user_role_map` (`id`, `user_id`, `role_id`, `create_at`, `update_at`, `valid`) VALUES
	(1, 1, 1, '2019-04-04 14:13:55.454', '2019-04-04 14:13:57.485', 1);
/*!40000 ALTER TABLE `user_role_map` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

-- 2019-4-20 增加药品单位 追加促销单位/积分
ALTER TABLE `goods_info`
	ADD COLUMN `unit` VARCHAR(10) NOT NULL COMMENT '单位' AFTER `spec`;
ALTER TABLE `campaign_info`
	ADD COLUMN `unit_amount` INT(10) UNSIGNED NOT NULL COMMENT '促销单位' AFTER `goods_id`,
	ADD COLUMN `reward` INT(10) UNSIGNED NOT NULL COMMENT '促销积分' AFTER `unit_amount`;
-- 2019-5-12 删除用户信息email唯一值校验
ALTER TABLE `user_info`
	DROP INDEX `email`;
-- 2019-6-4 药品药店添加编码field
ALTER TABLE `shop_info`
	ADD COLUMN `code` VARCHAR(20) NULL DEFAULT NULL COMMENT '药店编码' AFTER `id`,
	ADD UNIQUE INDEX `code_merchant_id` (`code`, `merchant_id`);
ALTER TABLE `goods_info`
	ADD COLUMN `code` VARCHAR(20) NULL DEFAULT NULL COMMENT '药品编码' AFTER `id`,
	ADD UNIQUE INDEX `code_merchant_id` (`code`, `merchant_id`);
-- 2019-6-10 factory可以自行添加订单
ALTER TABLE `order_info`
	ADD COLUMN `trader_id` INT(10) UNSIGNED NULL COMMENT '营销员id' AFTER `trader_factory_merchant_id`,
	ADD COLUMN `factory_merchant_id` INT(10) UNSIGNED NOT NULL COMMENT '厂商渠道id' AFTER `trader_id`;
ALTER TABLE `order_info`
	ALTER `trader_factory_merchant_id` DROP DEFAULT;
ALTER TABLE `order_info`
	CHANGE COLUMN `trader_factory_merchant_id` `trader_factory_merchant_id` INT(10) UNSIGNED NULL COMMENT '流通渠道id' AFTER `comment`;
UPDATE
    order_info oi
    inner join trader_factory_merchant_map tfmm on tfmm.id = oi.trader_factory_merchant_id
SET
    oi.trader_id = tfmm.trader_id,
    oi.factory_merchant_id = tfmm.factory_merchant_map_id
