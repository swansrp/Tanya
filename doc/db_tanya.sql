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
  KEY `FK_admin_info_user_info` (`user_id`),
  CONSTRAINT `FK_admin_info_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员信息';

-- 正在导出表  tanya.admin_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `admin_info` DISABLE KEYS */;
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
  `confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '确认时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_campaign_info_trader_info` (`trader_id`),
  KEY `FK_campaign_info_goods_info` (`goods_id`),
  CONSTRAINT `FK_campaign_info_goods_info` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_campaign_info_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
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
  CONSTRAINT `FK_campaign_salesman_map_campaign_info` FOREIGN KEY (`campaign_id`) REFERENCES `campaign_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_campaign_salesman_map_salesman_info` FOREIGN KEY (`salesman_id`) REFERENCES `salesman_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
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
  `factory_id` int(10) unsigned DEFAULT NULL COMMENT '药厂id',
  `merchant_id` int(10) unsigned DEFAULT NULL COMMENT '渠道商id',
  `goods_id` int(10) unsigned DEFAULT NULL COMMENT '商品id',
  `goods_number` int(10) unsigned NOT NULL COMMENT '活动数量',
  `amount` double NOT NULL COMMENT '活动金额',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '确认时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_discount_info_factory_info` (`factory_id`),
  KEY `FK_discount_info_merchant_info` (`merchant_id`),
  KEY `FK_discount_info_goods_info` (`goods_id`),
  CONSTRAINT `FK_discount_info_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_discount_info_goods_info` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_discount_info_merchant_info` FOREIGN KEY (`merchant_id`) REFERENCES `merchant_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医药公司人员信息';

-- 正在导出表  tanya.factory_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `factory_info` DISABLE KEYS */;
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
  CONSTRAINT `FK_factory_merchant_map_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_factory_merchant_map_merchant_info` FOREIGN KEY (`merchant_id`) REFERENCES `merchant_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='厂商-渠道关系';

-- 正在导出表  tanya.factory_merchant_map 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `factory_merchant_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `factory_merchant_map` ENABLE KEYS */;

-- 导出  表 tanya.goods_factory_merchant_map 结构
DROP TABLE IF EXISTS `goods_factory_merchant_map`;
CREATE TABLE IF NOT EXISTS `goods_factory_merchant_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
  `factory_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '药厂id',
  `factory_metchat_map_id` int(10) unsigned DEFAULT '0' COMMENT '药厂渠道关系id',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_goods_factory_merchant_map_goods_info` (`goods_id`),
  KEY `FK_goods_factory_merchant_map_factory_merchant_map` (`factory_metchat_map_id`),
  KEY `FK_goods_factory_merchant_map_factory_info` (`factory_id`),
  CONSTRAINT `FK_goods_factory_merchant_map_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_goods_factory_merchant_map_factory_merchant_map` FOREIGN KEY (`factory_metchat_map_id`) REFERENCES `factory_merchant_map` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_goods_factory_merchant_map_goods_info` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品渠道关系';

-- 正在导出表  tanya.goods_factory_merchant_map 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `goods_factory_merchant_map` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='药品信息';

-- 正在导出表  tanya.goods_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `goods_info` DISABLE KEYS */;
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
  KEY `FK_merchant_info_user_info` (`user_id`),
  CONSTRAINT `FK_merchant_info_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商务渠道公司';

-- 正在导出表  tanya.merchant_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `merchant_info` DISABLE KEYS */;
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
  CONSTRAINT `FK_notification_info_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_notification_info_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
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
  `trader_id` int(10) unsigned NOT NULL COMMENT '发起销售员id',
  `factory_id` int(10) unsigned NOT NULL COMMENT '发起药厂id',
  `merchant_id` int(10) unsigned NOT NULL COMMENT '渠道id',
  `shop_id` int(10) unsigned NOT NULL COMMENT '目标药店',
  `goods_id` int(10) unsigned NOT NULL COMMENT '商品id',
  `goods_number` int(10) unsigned NOT NULL COMMENT '商品数量',
  `amount` double NOT NULL COMMENT '订单金额',
  `campaign_id` int(10) unsigned DEFAULT NULL COMMENT '参加活动id',
  `start_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期起始',
  `end_at` timestamp(3) NULL DEFAULT NULL COMMENT '有效期结束',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `factory_confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '药厂确认时间',
  `merchant_confirm_at` timestamp(3) NULL DEFAULT NULL COMMENT '渠道确认时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_order_info_factory_info` (`factory_id`),
  KEY `FK_order_info_merchant_info` (`merchant_id`),
  KEY `FK_order_info_shop_info` (`shop_id`),
  KEY `FK_order_info_trader_info` (`trader_id`),
  KEY `FK_order_info_goods_info` (`goods_id`),
  KEY `FK_order_info_campaign_info` (`campaign_id`),
  CONSTRAINT `FK_order_info_campaign_info` FOREIGN KEY (`campaign_id`) REFERENCES `campaign_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_order_info_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_order_info_goods_info` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_order_info_merchant_info` FOREIGN KEY (`merchant_id`) REFERENCES `merchant_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_order_info_shop_info` FOREIGN KEY (`shop_id`) REFERENCES `shop_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_order_info_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单信息';

-- 正在导出表  tanya.order_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;

-- 导出  表 tanya.role_info 结构
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE IF NOT EXISTS `role_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `table_name` varchar(200) DEFAULT NULL COMMENT '角色信息表名',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色信息';

-- 正在导出表  tanya.role_info 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `role_info` DISABLE KEYS */;
REPLACE INTO `role_info` (`id`, `title`, `table_name`, `valid`) VALUES
	(1, '管理员', 'AdminInfo', 1),
	(2, '商务公司', 'MerchantInfo', 1),
	(3, '药厂销售主管', 'FactoryInfo', 1),
	(4, '药厂销售代表', 'TraderInfo', 1),
	(5, '促销员', 'SalesmanInfo', 1);
/*!40000 ALTER TABLE `role_info` ENABLE KEYS */;

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
  CONSTRAINT `FK_salesman_trader_map_salesman_info` FOREIGN KEY (`salesman_id`) REFERENCES `salesman_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_salesman_trader_map_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='药店信息';

-- 正在导出表  tanya.shop_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `shop_info` DISABLE KEYS */;
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
  CONSTRAINT `FK_trader_factory_merchant_map_factory_info` FOREIGN KEY (`factory_id`) REFERENCES `factory_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_trader_factory_merchant_map_factory_merchant_map` FOREIGN KEY (`factory_merchant_map_id`) REFERENCES `factory_merchant_map` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_trader_factory_merchant_map_trader_info` FOREIGN KEY (`trader_id`) REFERENCES `trader_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售代表-药厂-渠道关系';

-- 正在导出表  tanya.trader_factory_merchant_map 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trader_factory_merchant_map` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售代表信息';

-- 正在导出表  tanya.trader_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `trader_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `trader_info` ENABLE KEYS */;

-- 导出  表 tanya.user_info 结构
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE IF NOT EXISTS `user_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `guid` varchar(50) DEFAULT NULL COMMENT '用户唯一标识',
  `wechat_id` varchar(50) DEFAULT NULL COMMENT '微信id',
  `comment` varchar(100) DEFAULT NULL COMMENT '备注',
  `password` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `last_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '最后登入时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `guid` (`guid`),
  UNIQUE KEY `wechat_id` (`wechat_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员信息';

-- 正在导出表  tanya.user_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;

-- 导出  表 tanya.user_role_map 结构
DROP TABLE IF EXISTS `user_role_map`;
CREATE TABLE IF NOT EXISTS `user_role_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `role_id` int(10) unsigned NOT NULL COMMENT '角色id',
  `create_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `FK_user_role_map_user_info` (`user_id`),
  KEY `FK_user_role_map_role_info` (`role_id`),
  CONSTRAINT `FK_user_role_map_role_info` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_user_role_map_user_info` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

-- 正在导出表  tanya.user_role_map 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `user_role_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role_map` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
