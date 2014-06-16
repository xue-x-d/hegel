/*
Navicat MySQL Data Transfer

Source Server         : serverDB
Source Server Version : 50529
Source Host           : 192.168.1.106:3306
Source Database       : shomop_group1

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2014-04-17 12:22:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `apierrorrecord_detailslist`
-- ----------------------------
DROP TABLE IF EXISTS `apierrorrecord_detailslist`;
CREATE TABLE `apierrorrecord_detailslist` (
  `ApiErrorRecord_id` varchar(32) NOT NULL,
  `detailsList` varchar(255) DEFAULT NULL,
  KEY `FK4F0757E09E891C` (`ApiErrorRecord_id`),
  CONSTRAINT `FK4F0757E09E891C` FOREIGN KEY (`ApiErrorRecord_id`) REFERENCES `api_error_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apierrorrecord_detailslist
-- ----------------------------

-- ----------------------------
-- Table structure for `api_error_record`
-- ----------------------------
DROP TABLE IF EXISTS `api_error_record`;
CREATE TABLE `api_error_record` (
  `id` varchar(32) NOT NULL,
  `day` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `errorApi` varchar(255) DEFAULT NULL,
  `month` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of api_error_record
-- ----------------------------

-- ----------------------------
-- Table structure for `balance_update_record`
-- ----------------------------
DROP TABLE IF EXISTS `balance_update_record`;
CREATE TABLE `balance_update_record` (
  `updateTime` bigint(20) DEFAULT NULL,
  `balanceType` int(11) DEFAULT NULL,
  `oldValue` int(11) DEFAULT NULL,
  `newValue` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `remark` text,
  `relatedRecordId` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `balance_update_record__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of balance_update_record
-- ----------------------------

-- ----------------------------
-- Table structure for `blacklist`
-- ----------------------------
DROP TABLE IF EXISTS `blacklist`;
CREATE TABLE `blacklist` (
  `creationDate` bigint(20) DEFAULT NULL,
  `mobileNo` varchar(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `blacklist__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for `buyer`
-- ----------------------------
DROP TABLE IF EXISTS `buyer`;
CREATE TABLE `buyer` (
  `id` varchar(32) NOT NULL,
  `aveMonthBuyPrice` int(11) NOT NULL,
  `badRate` int(11) NOT NULL,
  `blacklist` tinyint(4) DEFAULT '0',
  `buyAmountPrice` int(11) NOT NULL,
  `buyCyclist` int(11) NOT NULL,
  `buyNum` int(11) NOT NULL,
  `buyerCity` varchar(20) DEFAULT NULL,
  `buyerId` bigint(20) NOT NULL,
  `buyerLevel` int(11) NOT NULL,
  `buyerMobile` varchar(30) DEFAULT NULL,
  `buyerName` varchar(30) DEFAULT NULL,
  `buyerNick` varchar(50) DEFAULT NULL,
  `buyerState` varchar(20) DEFAULT NULL,
  `changeStatus` tinyint(1) NOT NULL,
  `deal` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `exGrade` bigint(20) NOT NULL,
  `firstBuyTime` bigint(20) DEFAULT NULL,
  `grade` bigint(20) NOT NULL,
  `groupIds` text,
  `lastActivateTime` bigint(20) NOT NULL,
  `lastBuyTime` bigint(20) DEFAULT NULL,
  `lastUpgradeTime` bigint(20) NOT NULL,
  `neutralRate` int(11) NOT NULL,
  `nopayPrice` bigint(20) NOT NULL,
  `relationSource` int(11) NOT NULL,
  `sendTime` bigint(20) DEFAULT '0',
  `shomopBuyNum` int(11) NOT NULL,
  `taobaoBuyAmountPrice` int(11) NOT NULL,
  `taobaoBuyNum` int(11) NOT NULL,
  `taobaoLastBuyTime` bigint(20) DEFAULT NULL,
  `thisMonthBuyPrice` bigint(20) NOT NULL,
  `tradeAvePrice` int(11) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  `insertTime` bigint(20) DEFAULT NULL,
  `updatetime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `buyer_user_buyernick` (`user`,`buyerNick`),
  KEY `buyer__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of buyer
-- ----------------------------

-- ----------------------------
-- Table structure for `buyer_group`
-- ----------------------------
DROP TABLE IF EXISTS `buyer_group`;
CREATE TABLE `buyer_group` (
  `id` varchar(32) NOT NULL,
  `buyAmountPrice` int(11) NOT NULL,
  `buyNum` int(11) NOT NULL,
  `buyerNick` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `endTime` bigint(20) DEFAULT NULL,
  `mobile` varchar(30) DEFAULT NULL,
  `startTime` bigint(20) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `buyer_group__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of buyer_group
-- ----------------------------

-- ----------------------------
-- Table structure for `buy_cyclist_count`
-- ----------------------------
DROP TABLE IF EXISTS `buy_cyclist_count`;
CREATE TABLE `buy_cyclist_count` (
  `id` varchar(32) NOT NULL,
  `b_0_7` int(11) NOT NULL,
  `b_30_60` int(11) NOT NULL,
  `b_60_90` int(11) NOT NULL,
  `b_7_30` int(11) NOT NULL,
  `b_90_MAX` int(11) NOT NULL,
  `dateNum` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `payTradeCount` bigint(20) NOT NULL,
  `returnedTradeCount` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `BuyCyclistCount_user_dateNum_type` (`user`,`dateNum`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of buy_cyclist_count
-- ----------------------------

-- ----------------------------
-- Table structure for `code`
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
  `id` varchar(32) NOT NULL,
  `codmemo` varchar(255) DEFAULT NULL,
  `codname` varchar(255) DEFAULT NULL,
  `codno` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of code
-- ----------------------------

-- ----------------------------
-- Table structure for `coupon`
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `creationDate` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picUrl` varchar(255) DEFAULT NULL,
  `sUrlId` varchar(6) DEFAULT NULL,
  `longUrl` varchar(200) DEFAULT NULL,
  `startTime` bigint(20) DEFAULT NULL,
  `endTime` bigint(20) DEFAULT NULL,
  `payment` bigint(20) DEFAULT NULL,
  `sendNum` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `coupon__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coupon
-- ----------------------------

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `name` varchar(50) DEFAULT NULL,
  `mobileNo` varchar(20) DEFAULT NULL,
  `remark` text,
  `deleted` tinyint(1) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `creationDate` bigint(20) DEFAULT NULL,
  `updateDate` bigint(20) DEFAULT NULL,
  `mobileType` int(11) DEFAULT NULL,
  `sendTime` bigint(20) DEFAULT NULL,
  `blacklist` tinyint(1) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for `customer_customergroups_customer_group`
-- ----------------------------
DROP TABLE IF EXISTS `customer_customergroups_customer_group`;
CREATE TABLE `customer_customergroups_customer_group` (
  `customer_customerGroups` varchar(32) DEFAULT NULL,
  `customer_group_customers` varchar(32) DEFAULT NULL,
  KEY `customer_customerGroups_customer_group__customer_group_customers` (`customer_group_customers`),
  KEY `customer_customerGroups_customer_group__customer_customerGroups` (`customer_customerGroups`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_customergroups_customer_group
-- ----------------------------

-- ----------------------------
-- Table structure for `customer_group`
-- ----------------------------
DROP TABLE IF EXISTS `customer_group`;
CREATE TABLE `customer_group` (
  `id` varchar(32) NOT NULL,
  `buyAmountPrice` bigint(20) NOT NULL,
  `buyCyclist` bigint(20) NOT NULL,
  `buyNumTotal` bigint(20) NOT NULL,
  `creationDate` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `isDynamic` tinyint(1) DEFAULT NULL,
  `lastBuyTime` bigint(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `priceProportion` int(11) NOT NULL,
  `total` bigint(20) NOT NULL,
  `totalMobile` int(11) NOT NULL,
  `totalOther` int(11) NOT NULL,
  `totalTelecom` int(11) NOT NULL,
  `totalUnicom` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `updateDate` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_group__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_group
-- ----------------------------

-- ----------------------------
-- Table structure for `dunning`
-- ----------------------------
DROP TABLE IF EXISTS `dunning`;
CREATE TABLE `dunning` (
  `id` varchar(32) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `payment` bigint(20) NOT NULL,
  `sendTime` bigint(20) DEFAULT NULL,
  `tid` bigint(20) NOT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dunning
-- ----------------------------

-- ----------------------------
-- Table structure for `filter_condition`
-- ----------------------------
DROP TABLE IF EXISTS `filter_condition`;
CREATE TABLE `filter_condition` (
  `buyFrequency` text,
  `tradeAvePrice` text,
  `lastBuyTime` text,
  `buyerState` text,
  `buyerCity` text,
  `buyMaxNumCid` text,
  `aveMonthBuyPrice` text,
  `buyerLevel` text,
  `goodProportionRate` text,
  `neutralRate` text,
  `badRate` text,
  `buyNum` text,
  `numIid` varchar(630) DEFAULT NULL,
  `filterOutNumIid` varchar(630) DEFAULT NULL,
  `isSignIn` tinyint(1) DEFAULT NULL,
  `buyerNick` varchar(50) DEFAULT NULL,
  `buyerName` varchar(50) DEFAULT NULL,
  `minPurchaseDay` bigint(20) DEFAULT NULL,
  `maxPurchaseDay` bigint(20) DEFAULT NULL,
  `minPlaceAnOrderDay` smallint(6) DEFAULT NULL,
  `maxPlaceAnOrderDay` smallint(6) DEFAULT NULL,
  `minBuyAmountPrice` int(11) DEFAULT NULL,
  `maxBuyAmountPrice` int(11) DEFAULT NULL,
  `minThisMonthBuyPrice` int(11) DEFAULT NULL,
  `maxThisMonthBuyPrice` int(11) DEFAULT NULL,
  `minSendTime` smallint(6) DEFAULT NULL,
  `maxSendTime` smallint(6) DEFAULT NULL,
  `logisticsStatus` tinyint(4) DEFAULT NULL,
  `orderBy` varchar(255) DEFAULT NULL,
  `limit` int(11) DEFAULT NULL,
  `propsName` text,
  `cid` text,
  `grade` bigint(20) DEFAULT NULL,
  `groupId` varchar(30) DEFAULT NULL,
  `filterSql` varchar(400) DEFAULT NULL,
  `minBuyCyclist` smallint(6) DEFAULT NULL,
  `maxBuyCyclist` smallint(6) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `customerGroup` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `filter_condition__customerGroup` (`customerGroup`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of filter_condition
-- ----------------------------

-- ----------------------------
-- Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` varchar(32) NOT NULL,
  `curGrade` bigint(20) NOT NULL,
  `curGradeName` varchar(255) DEFAULT NULL,
  `discount` bigint(20) NOT NULL,
  `nextGrade` bigint(20) NOT NULL,
  `nextGradeName` varchar(255) DEFAULT NULL,
  `nextUpgradeAmount` bigint(20) NOT NULL,
  `nextUpgradeCount` bigint(20) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_curGrade` (`user`,`curGrade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------

-- ----------------------------
-- Table structure for `grade_overview`
-- ----------------------------
DROP TABLE IF EXISTS `grade_overview`;
CREATE TABLE `grade_overview` (
  `id` varchar(32) NOT NULL,
  `allMemberCount` bigint(20) NOT NULL,
  `grade` bigint(20) NOT NULL,
  `memberCount` bigint(20) NOT NULL,
  `totalFrequency` bigint(20) NOT NULL,
  `totalMonetary` bigint(20) NOT NULL,
  `totalRecency` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_updateTime_grade` (`user`,`updateTime`,`grade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade_overview
-- ----------------------------

-- ----------------------------
-- Table structure for `intervals`
-- ----------------------------
DROP TABLE IF EXISTS `intervals`;
CREATE TABLE `intervals` (
  `id` varchar(32) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `intervals__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of intervals
-- ----------------------------

-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` varchar(32) NOT NULL,
  `approveStatus` varchar(20) DEFAULT NULL,
  `cid` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `delistTime` bigint(20) DEFAULT NULL,
  `hasDiscount` tinyint(1) DEFAULT NULL,
  `hasInvoice` tinyint(1) DEFAULT NULL,
  `hasShowcase` tinyint(1) DEFAULT NULL,
  `isUnsalable` tinyint(1) NOT NULL,
  `listTime` bigint(20) DEFAULT NULL,
  `modified` bigint(20) DEFAULT NULL,
  `nick` varchar(50) DEFAULT NULL,
  `num` bigint(20) DEFAULT NULL,
  `numIid` bigint(20) DEFAULT NULL,
  `outerId` varchar(64) DEFAULT NULL,
  `picUrl` text,
  `popId` bigint(20) NOT NULL,
  `postageId` bigint(20) DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `sellerCids` text,
  `simpleName` varchar(60) DEFAULT NULL,
  `title` varchar(60) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `validThru` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------

-- ----------------------------
-- Table structure for `item_analysis`
-- ----------------------------
DROP TABLE IF EXISTS `item_analysis`;
CREATE TABLE `item_analysis` (
  `id` varchar(32) NOT NULL,
  `firstSaleTime` bigint(20) NOT NULL,
  `lastSaleTime` bigint(20) NOT NULL,
  `numIid` bigint(20) NOT NULL,
  `returnedAmountNum` bigint(20) NOT NULL,
  `returnedSaleNum` bigint(20) NOT NULL,
  `totalAmountNum` bigint(20) NOT NULL,
  `totalSaleNum` bigint(20) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_numIid` (`numIid`),
  KEY `index_user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_analysis
-- ----------------------------

-- ----------------------------
-- Table structure for `login_history`
-- ----------------------------
DROP TABLE IF EXISTS `login_history`;
CREATE TABLE `login_history` (
  `trialVersion` tinyint(1) DEFAULT NULL,
  `loginTime` bigint(20) DEFAULT NULL,
  `purchaseTime` bigint(20) DEFAULT NULL,
  `mktBalance` int(11) DEFAULT NULL,
  `buyerNum` bigint(20) DEFAULT NULL,
  `lastUploadTime` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `login_history__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_history
-- ----------------------------

-- ----------------------------
-- Table structure for `logistics`
-- ----------------------------
DROP TABLE IF EXISTS `logistics`;
CREATE TABLE `logistics` (
  `id` varchar(32) NOT NULL,
  `companyName` varchar(15) DEFAULT NULL,
  `detail` text,
  `tid` bigint(20) NOT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logistics
-- ----------------------------

-- ----------------------------
-- Table structure for `marketing_statistic`
-- ----------------------------
DROP TABLE IF EXISTS `marketing_statistic`;
CREATE TABLE `marketing_statistic` (
  `id` varchar(32) NOT NULL,
  `SMSQuantity` int(11) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `day` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `tradeQuantity` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of marketing_statistic
-- ----------------------------

-- ----------------------------
-- Table structure for `message_reward`
-- ----------------------------
DROP TABLE IF EXISTS `message_reward`;
CREATE TABLE `message_reward` (
  `id` varchar(32) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `hits` int(11) NOT NULL,
  `recordTime` bigint(20) DEFAULT NULL,
  `roi` int(11) NOT NULL,
  `sendRecord` varchar(255) DEFAULT NULL,
  `success` int(11) NOT NULL,
  `tids` text,
  `tradeQuantity` int(11) NOT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `message_reward__sendRecord` (`sendRecord`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message_reward
-- ----------------------------

-- ----------------------------
-- Table structure for `message_template`
-- ----------------------------
DROP TABLE IF EXISTS `message_template`;
CREATE TABLE `message_template` (
  `message` text,
  `deleted` tinyint(1) DEFAULT NULL,
  `creationDate` bigint(20) DEFAULT NULL,
  `updateDate` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `message_template__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message_template
-- ----------------------------

-- ----------------------------
-- Table structure for `notify_sms`
-- ----------------------------
DROP TABLE IF EXISTS `notify_sms`;
CREATE TABLE `notify_sms` (
  `id` varchar(32) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `finishedTime` bigint(20) DEFAULT NULL,
  `message` text,
  `mobileNo` varchar(30) DEFAULT NULL,
  `outSid` varchar(50) DEFAULT NULL,
  `sendTime` bigint(20) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `submitTime` bigint(20) DEFAULT NULL,
  `submitToAPIServerTime` bigint(20) DEFAULT NULL,
  `taskId` varchar(32) DEFAULT NULL,
  `tid` bigint(20) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_tid_index` (`tid`,`type`),
  UNIQUE KEY `index_user_type_outsid1` (`user`,`type`,`outSid`),
  KEY `notify_sms__user` (`user`),
  KEY `notify_sms__deleted_status_sendTime` (`deleted`,`status`,`sendTime`),
  KEY `notify_sms__user_type_sendTime` (`user`,`type`,`sendTime`),
  KEY `notify_sms__taskId` (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notify_sms
-- ----------------------------

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` varchar(32) NOT NULL,
  `adjustFee` varchar(20) DEFAULT NULL,
  `buyerNick` varchar(50) DEFAULT NULL,
  `buyerRate` tinyint(1) DEFAULT NULL,
  `cid` bigint(20) DEFAULT NULL,
  `consignTime` varchar(50) DEFAULT NULL,
  `created` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `discountFee` varchar(20) DEFAULT NULL,
  `endTime` bigint(20) DEFAULT NULL,
  `isOversold` tinyint(1) DEFAULT NULL,
  `isServiceOrder` tinyint(1) DEFAULT NULL,
  `itemMealId` bigint(20) DEFAULT NULL,
  `itemMealName` text,
  `modified` bigint(20) DEFAULT NULL,
  `num` bigint(20) DEFAULT NULL,
  `numIid` bigint(20) DEFAULT NULL,
  `oid` bigint(20) DEFAULT NULL,
  `orderFrom` varchar(20) DEFAULT NULL,
  `outerIid` varchar(64) DEFAULT NULL,
  `outerSkuId` varchar(64) DEFAULT NULL,
  `payment` varchar(20) DEFAULT NULL,
  `picPath` text,
  `price` varchar(20) DEFAULT NULL,
  `refundId` bigint(20) DEFAULT NULL,
  `refundStatus` varchar(50) DEFAULT NULL,
  `sellerNick` varchar(50) DEFAULT NULL,
  `sellerRate` tinyint(1) DEFAULT NULL,
  `sellerType` varchar(20) DEFAULT NULL,
  `shippingType` varchar(20) DEFAULT NULL,
  `skuId` varchar(64) DEFAULT NULL,
  `skuPropertiesName` text,
  `snapshot` text,
  `snapshotUrl` text,
  `status` varchar(50) DEFAULT NULL,
  `tid` bigint(20) DEFAULT NULL,
  `timeoutActionTime` bigint(20) DEFAULT NULL,
  `title` varchar(60) DEFAULT NULL,
  `totalFee` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `orders_tid_oid` (`tid`,`oid`),
  KEY `orders_tid_index` (`tid`),
  KEY `index_numIid` (`numIid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for `payment`
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `tradeNo` varchar(64) DEFAULT NULL,
  `orderSubject` varchar(60) DEFAULT NULL,
  `orderDesc` varchar(255) DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `discount` varchar(20) DEFAULT NULL,
  `goodsType` int(11) DEFAULT NULL,
  `goodsNo` varchar(20) DEFAULT NULL,
  `messageCount` int(11) DEFAULT NULL,
  `buyerEmail` varchar(100) DEFAULT NULL,
  `tradeStatus` varchar(50) DEFAULT NULL,
  `submitTime` bigint(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `successTime` bigint(20) DEFAULT NULL,
  `finishedTime` bigint(20) DEFAULT NULL,
  `closedTime` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `payment__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payment
-- ----------------------------

-- ----------------------------
-- Table structure for `promotion_task`
-- ----------------------------
DROP TABLE IF EXISTS `promotion_task`;
CREATE TABLE `promotion_task` (
  `id` varchar(32) NOT NULL,
  `buyerNum` int(11) NOT NULL,
  `buyerUploadNum` int(11) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `endTime` bigint(20) NOT NULL,
  `groupId` varchar(255) DEFAULT NULL,
  `groupType` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sendRecordId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `taobaoGroupId` bigint(20) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `promotion_task__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of promotion_task
-- ----------------------------

-- ----------------------------
-- Table structure for `refund`
-- ----------------------------
DROP TABLE IF EXISTS `refund`;
CREATE TABLE `refund` (
  `id` varchar(32) NOT NULL,
  `buyerNick` varchar(255) DEFAULT NULL,
  `created` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `modified` bigint(20) NOT NULL,
  `num` int(11) NOT NULL,
  `oid` bigint(20) NOT NULL,
  `payment` bigint(20) NOT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `refundFee` bigint(20) NOT NULL,
  `refundId` bigint(20) NOT NULL,
  `sellerNick` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `tid` bigint(20) NOT NULL,
  `title` varchar(30) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_refundId` (`refundId`),
  KEY `index_user_created_status` (`user`,`created`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of refund
-- ----------------------------

-- ----------------------------
-- Table structure for `remind_template`
-- ----------------------------
DROP TABLE IF EXISTS `remind_template`;
CREATE TABLE `remind_template` (
  `id` varchar(32) NOT NULL,
  `creationDate` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `filterBadRate` tinyint(1) NOT NULL,
  `filterCurrentDaySend` tinyint(1) NOT NULL,
  `message` text,
  `payment` int(11) NOT NULL DEFAULT '0',
  `settingRemindTime` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `type` int(11) NOT NULL,
  `updateDate` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `remind_template__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of remind_template
-- ----------------------------

-- ----------------------------
-- Table structure for `reply_record`
-- ----------------------------
DROP TABLE IF EXISTS `reply_record`;
CREATE TABLE `reply_record` (
  `mobileNo` varchar(20) DEFAULT NULL,
  `message` text,
  `replyTime` bigint(20) DEFAULT NULL,
  `replyOption` int(11) DEFAULT NULL,
  `taskId` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `sendDetail` varchar(32) DEFAULT NULL,
  `sendRecord` varchar(32) DEFAULT NULL,
  `notifySMS` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `reply_record__notifySMS` (`notifySMS`),
  KEY `reply_record__sendRecord` (`sendRecord`),
  KEY `reply_record__sendDetail` (`sendDetail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply_record
-- ----------------------------

-- ----------------------------
-- Table structure for `residence`
-- ----------------------------
DROP TABLE IF EXISTS `residence`;
CREATE TABLE `residence` (
  `id` varchar(32) NOT NULL,
  `amount` int(11) NOT NULL,
  `buyerNum` int(11) NOT NULL,
  `dayTime` bigint(20) NOT NULL,
  `familiarAmount` int(11) NOT NULL,
  `familiarBuyerNum` int(11) NOT NULL,
  `familiarTradeNum` int(11) NOT NULL,
  `province` varchar(255) DEFAULT NULL,
  `tradeNum` int(11) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_dayTime_province` (`user`,`dayTime`,`province`(3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of residence
-- ----------------------------

-- ----------------------------
-- Table structure for `reward_detail`
-- ----------------------------
DROP TABLE IF EXISTS `reward_detail`;
CREATE TABLE `reward_detail` (
  `id` varchar(32) NOT NULL,
  `buyerNick` varchar(50) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `payment` bigint(20) NOT NULL,
  `paytime` bigint(20) DEFAULT NULL,
  `recordTime` bigint(20) DEFAULT NULL,
  `sendRecord` varchar(32) DEFAULT NULL,
  `tid` bigint(20) NOT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reward_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `rfm`
-- ----------------------------
DROP TABLE IF EXISTS `rfm`;
CREATE TABLE `rfm` (
  `id` varchar(32) NOT NULL,
  `dateNum` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `r` int(11) NOT NULL,
  `rf1AmountPriceNum` int(11) NOT NULL,
  `rf1AvgPriceNum` int(11) NOT NULL,
  `rf1BuyerNum` int(11) NOT NULL,
  `rf2AmountPriceNum` int(11) NOT NULL,
  `rf2AvgPriceNum` int(11) NOT NULL,
  `rf2BuyerNum` int(11) NOT NULL,
  `rf3AmountPriceNum` int(11) NOT NULL,
  `rf3AvgPriceNum` int(11) NOT NULL,
  `rf3BuyerNum` int(11) NOT NULL,
  `rf4AmountPriceNum` int(11) NOT NULL,
  `rf4AvgPriceNum` int(11) NOT NULL,
  `rf4BuyerNum` int(11) NOT NULL,
  `rf5AmountPriceNum` int(11) NOT NULL,
  `rf5AvgPriceNum` int(11) NOT NULL,
  `rf5BuyerNum` int(11) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rfm_user_dateNum_r` (`user`,`dateNum`,`r`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rfm
-- ----------------------------

-- ----------------------------
-- Table structure for `scid`
-- ----------------------------
DROP TABLE IF EXISTS `scid`;
CREATE TABLE `scid` (
  `id` varchar(32) NOT NULL,
  `cid` bigint(20) NOT NULL,
  `cidName` varchar(255) DEFAULT NULL,
  `updateTime` bigint(20) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `scid_user_cidId` (`user`,`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scid
-- ----------------------------

-- ----------------------------
-- Table structure for `send_customer`
-- ----------------------------
DROP TABLE IF EXISTS `send_customer`;
CREATE TABLE `send_customer` (
  `status` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `customer` varchar(32) DEFAULT NULL,
  `sendRecord` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `send_customer__sendRecord` (`sendRecord`),
  KEY `send_customer__customer` (`customer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_customer
-- ----------------------------

-- ----------------------------
-- Table structure for `send_customer_group`
-- ----------------------------
DROP TABLE IF EXISTS `send_customer_group`;
CREATE TABLE `send_customer_group` (
  `status` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `customerGroup` varchar(32) DEFAULT NULL,
  `sendRecord` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `send_customer_group__sendRecord` (`sendRecord`),
  KEY `send_customer_group__customerGroup` (`customerGroup`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_customer_group
-- ----------------------------

-- ----------------------------
-- Table structure for `send_detail`
-- ----------------------------
DROP TABLE IF EXISTS `send_detail`;
CREATE TABLE `send_detail` (
  `id` varchar(32) NOT NULL,
  `customerName` varchar(50) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `mobileNo` varchar(20) DEFAULT NULL,
  `sendRecord` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `send_detail__sendRecord_mobileNo` (`sendRecord`,`mobileNo`),
  KEY `send_detail__sendRecord` (`sendRecord`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `send_filter`
-- ----------------------------
DROP TABLE IF EXISTS `send_filter`;
CREATE TABLE `send_filter` (
  `maxSendTime` smallint(6) DEFAULT NULL,
  `excludeBadRate` tinyint(1) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `sendRecord` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `send_filter__sendRecord` (`sendRecord`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_filter
-- ----------------------------

-- ----------------------------
-- Table structure for `send_record`
-- ----------------------------
DROP TABLE IF EXISTS `send_record`;
CREATE TABLE `send_record` (
  `id` varchar(32) NOT NULL,
  `ROIRank` int(11) NOT NULL,
  `ROIValue` int(11) NOT NULL,
  `chargeFactor` int(11) NOT NULL,
  `convertRank` int(11) NOT NULL,
  `convertValue` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `finishedTime` bigint(20) DEFAULT NULL,
  `message` text,
  `sendTime` bigint(20) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `submitTime` bigint(20) DEFAULT NULL,
  `submitToAPIServerTime` bigint(20) DEFAULT NULL,
  `taskId` varchar(32) DEFAULT NULL,
  `total` int(11) NOT NULL,
  `totalMobile` int(11) NOT NULL,
  `totalOther` int(11) NOT NULL,
  `totalTelecom` int(11) NOT NULL,
  `totalUnicom` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `send_record__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_record
-- ----------------------------

-- ----------------------------
-- Table structure for `send_status_report`
-- ----------------------------
DROP TABLE IF EXISTS `send_status_report`;
CREATE TABLE `send_status_report` (
  `mobileNo` varchar(20) DEFAULT NULL,
  `taskId` varchar(32) DEFAULT NULL,
  `reportTime` bigint(20) DEFAULT NULL,
  `report` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `sendDetail` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `send_status_report__sendDetail` (`sendDetail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_status_report
-- ----------------------------

-- ----------------------------
-- Table structure for `send_survey_template`
-- ----------------------------
DROP TABLE IF EXISTS `send_survey_template`;
CREATE TABLE `send_survey_template` (
  `question` text,
  `optionA` varchar(50) DEFAULT NULL,
  `optionB` varchar(50) DEFAULT NULL,
  `optionC` varchar(50) DEFAULT NULL,
  `optionD` varchar(50) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `sendRecord` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `send_survey_template__sendRecord` (`sendRecord`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_survey_template
-- ----------------------------

-- ----------------------------
-- Table structure for `shop`
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` varchar(32) NOT NULL,
  `allAmountPrice` bigint(20) NOT NULL,
  `allCloseTradeNum` int(11) NOT NULL,
  `allItemNum` int(11) NOT NULL,
  `allRefundTradeNum` int(11) NOT NULL,
  `allSuccessTradeNum` int(11) NOT NULL,
  `allTradeNum` int(11) NOT NULL,
  `amountOfLastMonth` bigint(20) NOT NULL,
  `badRateNum` int(11) NOT NULL,
  `buyerNum` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `goodRateNum` int(11) NOT NULL,
  `informNum` int(11) NOT NULL,
  `investment` bigint(20) NOT NULL,
  `lastCountTime` bigint(20) NOT NULL,
  `marketingHrefclickCount` bigint(20) NOT NULL,
  `marketingMoneyCount` bigint(20) NOT NULL,
  `marketingRoi` bigint(20) NOT NULL,
  `marketingSmsCount` bigint(20) NOT NULL,
  `marketingSucceedCount` bigint(20) NOT NULL,
  `marketingTradeAmount` bigint(20) NOT NULL,
  `marketingTradeNum` int(11) NOT NULL,
  `minDate` bigint(20) NOT NULL,
  `month` int(11) NOT NULL,
  `neutralRateNum` int(11) NOT NULL,
  `noPayTradeNum` int(11) NOT NULL,
  `notifySmsCount` bigint(20) NOT NULL,
  `notifySmsRateCount` bigint(20) NOT NULL,
  `notifySmsReturnCount` bigint(20) NOT NULL,
  `notifySmsconfirmCount` bigint(20) NOT NULL,
  `orderNum` int(11) NOT NULL,
  `recentCloseTradeNum` int(11) NOT NULL,
  `recentRefundTradeNum` int(11) NOT NULL,
  `recentSuccessTradeNum` int(11) NOT NULL,
  `recentTradeNum` int(11) NOT NULL,
  `refundSmsSucceedCount` int(11) NOT NULL,
  `returnedCustomerBuyTradeNum` int(11) NOT NULL,
  `reward` bigint(20) NOT NULL,
  `roi` bigint(20) NOT NULL,
  `score` int(11) NOT NULL,
  `scoreProportion` int(11) NOT NULL,
  `totalOfPastMonth` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `unSalableItemNum` int(11) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  `week` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `shop__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------

-- ----------------------------
-- Table structure for `shop_info`
-- ----------------------------
DROP TABLE IF EXISTS `shop_info`;
CREATE TABLE `shop_info` (
  `id` varchar(32) NOT NULL,
  `allAmountPrice` bigint(20) NOT NULL,
  `allbuyerCount` bigint(20) NOT NULL,
  `avgBuyCyclist` int(11) NOT NULL,
  `badRateBuyerCount` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `noPayBuyerCount` bigint(20) NOT NULL,
  `noPayPrice` bigint(20) NOT NULL,
  `noPayTradeCount` bigint(20) NOT NULL,
  `onsellItemCount` int(11) NOT NULL,
  `payment` bigint(20) NOT NULL,
  `refundSmsCount` bigint(20) NOT NULL,
  `refundSmsSucceedCount` int(11) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shopinfo_user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_info
-- ----------------------------

-- ----------------------------
-- Table structure for `spop`
-- ----------------------------
DROP TABLE IF EXISTS `spop`;
CREATE TABLE `spop` (
  `id` varchar(32) NOT NULL,
  `pop` bigint(20) NOT NULL,
  `popName` varchar(255) DEFAULT NULL,
  `updatetime` bigint(20) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `spop_user_popid` (`user`,`pop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of spop
-- ----------------------------

-- ----------------------------
-- Table structure for `subuser`
-- ----------------------------
DROP TABLE IF EXISTS `subuser`;
CREATE TABLE `subuser` (
  `id` varchar(32) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `filterData` tinyint(1) NOT NULL,
  `historyLoginTime` text,
  `lastLoginTime` bigint(20) DEFAULT NULL,
  `modifySetting` tinyint(1) NOT NULL,
  `sendMessage` tinyint(1) NOT NULL,
  `subId` bigint(20) DEFAULT NULL,
  `subNick` varchar(255) DEFAULT NULL,
  `subStatus` bigint(20) DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `userNick` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `subuser__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subuser
-- ----------------------------

-- ----------------------------
-- Table structure for `survey_template`
-- ----------------------------
DROP TABLE IF EXISTS `survey_template`;
CREATE TABLE `survey_template` (
  `question` text,
  `optionA` varchar(50) DEFAULT NULL,
  `optionB` varchar(50) DEFAULT NULL,
  `optionC` varchar(50) DEFAULT NULL,
  `optionD` varchar(50) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `creationDate` bigint(20) DEFAULT NULL,
  `updateDate` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `survey_template__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of survey_template
-- ----------------------------

-- ----------------------------
-- Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` varchar(32) NOT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `downloadUrl` varchar(100) DEFAULT NULL,
  `endTime` bigint(20) DEFAULT NULL,
  `finishedTime` bigint(20) DEFAULT NULL,
  `memo` varchar(50) DEFAULT NULL,
  `method` varchar(100) DEFAULT NULL,
  `startTime` bigint(20) DEFAULT NULL,
  `taskId` bigint(20) DEFAULT NULL,
  `taskStatus` varchar(50) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `taskId_index` (`taskId`),
  KEY `task__user` (`user`),
  KEY `task_user` (`user`),
  KEY `task_taskStatus_type_index` (`taskStatus`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------

-- ----------------------------
-- Table structure for `time_list`
-- ----------------------------
DROP TABLE IF EXISTS `time_list`;
CREATE TABLE `time_list` (
  `id` varchar(32) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `role` int(11) NOT NULL,
  `timeList` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_role` (`user`,`role`),
  KEY `time_list__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of time_list
-- ----------------------------

-- ----------------------------
-- Table structure for `today_info`
-- ----------------------------
DROP TABLE IF EXISTS `today_info`;
CREATE TABLE `today_info` (
  `id` varchar(32) NOT NULL,
  `activateBuyerCount` bigint(20) NOT NULL,
  `activateBuyerPrice` bigint(20) NOT NULL,
  `allTradeCount` bigint(20) NOT NULL,
  `avgBuyCyclist` bigint(20) NOT NULL,
  `dateNum` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `lastActivateBuyerCount` bigint(20) NOT NULL,
  `lastBuyCount` bigint(20) NOT NULL,
  `lastBuyFiveAvgPrice` bigint(20) NOT NULL,
  `lastBuyFiveCount` bigint(20) NOT NULL,
  `lastBuyFivePrice` bigint(20) NOT NULL,
  `lastBuyFourAvgPrice` bigint(20) NOT NULL,
  `lastBuyFourCount` bigint(20) NOT NULL,
  `lastBuyFourPrice` bigint(20) NOT NULL,
  `lastBuyOneAvgPrice` bigint(20) NOT NULL,
  `lastBuyOneCount` bigint(20) NOT NULL,
  `lastBuyOnePrice` bigint(20) NOT NULL,
  `lastBuyThreeAvgPrice` bigint(20) NOT NULL,
  `lastBuyThreeCount` bigint(20) NOT NULL,
  `lastBuyThreePrice` bigint(20) NOT NULL,
  `lastBuyTwoAvgPrice` bigint(20) NOT NULL,
  `lastBuyTwoCount` bigint(20) NOT NULL,
  `lastBuyTwoPrice` bigint(20) NOT NULL,
  `lastBuyZeroCount` bigint(20) NOT NULL,
  `newBuyerCount` bigint(20) NOT NULL,
  `noPayment` bigint(20) NOT NULL,
  `payTradeCount` bigint(20) NOT NULL,
  `payment` bigint(20) NOT NULL,
  `returnedBuyerCount` bigint(20) NOT NULL,
  `returnedBuyerPrice` bigint(20) NOT NULL,
  `returnedTradeCount` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  `waitPayBuyerCount` bigint(20) NOT NULL,
  `waitSendTrade` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `todayinfo_user_dateNum_type` (`user`,`dateNum`,`type`),
  KEY `today_info__user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of today_info
-- ----------------------------

-- ----------------------------
-- Table structure for `trade`
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade` (
  `id` varchar(32) NOT NULL,
  `adjustFee` varchar(20) DEFAULT NULL,
  `alipayId` bigint(20) DEFAULT NULL,
  `alipayNo` varchar(64) DEFAULT NULL,
  `alipayUrl` text,
  `alipayWarnMsg` text,
  `areaId` varchar(64) DEFAULT NULL,
  `arriveCity` varchar(20) DEFAULT NULL,
  `availableConfirmFee` varchar(20) DEFAULT NULL,
  `buyNum` int(11) NOT NULL,
  `buyerAlipayNo` varchar(100) DEFAULT NULL,
  `buyerArea` varchar(50) DEFAULT NULL,
  `buyerCodFee` varchar(20) DEFAULT NULL,
  `buyerEmail` varchar(100) DEFAULT NULL,
  `buyerFlag` bigint(20) DEFAULT NULL,
  `buyerMemo` text,
  `buyerMessage` text,
  `buyerNick` varchar(50) DEFAULT NULL,
  `buyerObtainPointFee` bigint(20) DEFAULT NULL,
  `buyerRate` tinyint(1) DEFAULT NULL,
  `canRate` tinyint(1) DEFAULT NULL,
  `codFee` varchar(20) DEFAULT NULL,
  `codStatus` varchar(50) DEFAULT NULL,
  `commissionFee` varchar(20) DEFAULT NULL,
  `companyName` varchar(50) DEFAULT NULL,
  `consignTime` bigint(20) DEFAULT NULL,
  `created` bigint(20) DEFAULT NULL,
  `creditCardFee` varchar(20) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `discountFee` varchar(20) DEFAULT NULL,
  `endTime` bigint(20) DEFAULT NULL,
  `eticketExt` text,
  `expressAgencyFee` varchar(20) DEFAULT NULL,
  `hasBuyerMessage` tinyint(1) DEFAULT NULL,
  `hasPostFee` tinyint(1) DEFAULT NULL,
  `hasYfx` tinyint(1) DEFAULT NULL,
  `insertTime` bigint(20) DEFAULT NULL,
  `invoiceName` varchar(60) DEFAULT NULL,
  `is3D` tinyint(1) DEFAULT NULL,
  `isBrandSale` tinyint(1) DEFAULT NULL,
  `isForceWlb` tinyint(1) DEFAULT NULL,
  `isLgtype` tinyint(1) DEFAULT NULL,
  `isPartConsign` tinyint(1) DEFAULT NULL,
  `lgAging` varchar(50) DEFAULT NULL,
  `lgAgingType` varchar(50) DEFAULT NULL,
  `markDesc` text,
  `modified` bigint(20) DEFAULT NULL,
  `num` bigint(20) DEFAULT NULL,
  `numIid` bigint(20) DEFAULT NULL,
  `nutFeature` text,
  `outSid` varchar(64) DEFAULT NULL,
  `payTime` bigint(20) DEFAULT NULL,
  `payment` varchar(20) DEFAULT NULL,
  `picPath` text,
  `pointFee` bigint(20) DEFAULT NULL,
  `postFee` varchar(20) DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `promotion` text,
  `realPointFee` bigint(20) DEFAULT NULL,
  `receiveTime` varchar(50) DEFAULT NULL,
  `receivedPayment` varchar(20) DEFAULT NULL,
  `receiverAddress` text,
  `receiverCity` varchar(20) DEFAULT NULL,
  `receiverDistrict` varchar(20) DEFAULT NULL,
  `receiverMobile` varchar(20) DEFAULT NULL,
  `receiverName` varchar(50) DEFAULT NULL,
  `receiverPhone` varchar(30) DEFAULT NULL,
  `receiverState` varchar(20) DEFAULT NULL,
  `receiverZip` varchar(20) DEFAULT NULL,
  `sellerAlipayNo` varchar(100) DEFAULT NULL,
  `sellerCanRate` tinyint(1) DEFAULT NULL,
  `sellerCodFee` varchar(20) DEFAULT NULL,
  `sellerEmail` varchar(100) DEFAULT NULL,
  `sellerFlag` bigint(20) DEFAULT NULL,
  `sellerMemo` text,
  `sellerMobile` varchar(20) DEFAULT NULL,
  `sellerName` varchar(50) DEFAULT NULL,
  `sellerNick` varchar(50) DEFAULT NULL,
  `sellerPhone` varchar(30) DEFAULT NULL,
  `sellerRate` tinyint(1) DEFAULT NULL,
  `sendTime` varchar(50) DEFAULT NULL,
  `shippingStatus` varchar(50) DEFAULT NULL,
  `shippingType` varchar(20) DEFAULT NULL,
  `smsItemName` varchar(30) DEFAULT NULL,
  `snapshot` text,
  `snapshotUrl` text,
  `status` varchar(50) DEFAULT NULL,
  `stepPaidFee` varchar(20) DEFAULT NULL,
  `stepTradeStatus` varchar(50) DEFAULT NULL,
  `tid` bigint(20) DEFAULT NULL,
  `timeoutActionTime` bigint(20) DEFAULT NULL,
  `title` varchar(60) DEFAULT NULL,
  `toBuyerBadRate` int(11) NOT NULL,
  `toBuyerDeliverGoods` int(11) NOT NULL,
  `toBuyerDunningPay` int(11) NOT NULL,
  `toBuyerGoodRate` int(11) NOT NULL,
  `toBuyerReachGoods` int(11) NOT NULL,
  `toBuyerReceiveGoods` int(11) NOT NULL,
  `toBuyerRefundPay` int(11) NOT NULL,
  `toBuyerThradeClose` int(11) NOT NULL,
  `toSellerBadRate` int(11) NOT NULL,
  `toSellerCreateTrade` int(11) NOT NULL,
  `toSellerReachGoods` int(11) NOT NULL,
  `toSellerRefundPay` int(11) NOT NULL,
  `toSellerThradeClose` int(11) NOT NULL,
  `totalFee` varchar(20) DEFAULT NULL,
  `tradeFrom` varchar(20) DEFAULT NULL,
  `tradeMemo` text,
  `tradeSource` varchar(50) DEFAULT NULL,
  `type` text,
  `user` varchar(32) DEFAULT NULL,
  `yfxFee` varchar(20) DEFAULT NULL,
  `yfxId` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `trade_tid` (`tid`),
  KEY `trade__user` (`user`),
  KEY `trade_user_created` (`user`,`created`),
  KEY `trade_user_buyernick` (`user`,`buyerNick`),
  KEY `trade__status_created` (`status`,`created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade
-- ----------------------------

-- ----------------------------
-- Table structure for `trade_rate`
-- ----------------------------
DROP TABLE IF EXISTS `trade_rate`;
CREATE TABLE `trade_rate` (
  `id` varchar(32) NOT NULL,
  `content` text,
  `created` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  `itemPrice` varchar(20) DEFAULT NULL,
  `itemTitle` varchar(60) DEFAULT NULL,
  `nick` varchar(50) DEFAULT NULL,
  `numIid` bigint(20) DEFAULT NULL,
  `oid` bigint(20) DEFAULT NULL,
  `ratedNick` varchar(20) DEFAULT NULL,
  `reply` text,
  `result` varchar(20) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `tid` bigint(20) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `validScore` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `oid_role` (`oid`,`role`),
  KEY `trade_rate__user` (`user`),
  KEY `trade_rate_nick_user_role` (`nick`,`user`,`role`),
  KEY `trade_rate__user_created_role` (`user`,`created`,`role`),
  KEY `tid_index` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade_rate
-- ----------------------------

-- ----------------------------
-- View structure for `nopay_trade`
-- ----------------------------
DROP VIEW IF EXISTS `nopay_trade`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.1.%` SQL SECURITY DEFINER VIEW `nopay_trade` AS select `t`.`tid` AS `tid`,`t`.`toBuyerDunningPay` AS `toBuyerDunningPay`,`t`.`toSellerCreateTrade` AS `toSellerCreateTrade`,`t`.`buyerNick` AS `buyerNick`,`t`.`title` AS `title`,`t`.`smsItemName` AS `smsItemName`,`t`.`user` AS `user`,`t`.`id` AS `id`,`t`.`receiverMobile` AS `receiverMobile`,`t`.`created` AS `created`,`r`.`settingRemindTime` AS `settingRemindTime`,`r`.`id` AS `rid` from ((`trade` `t` FORCE INDEX (`trade__status_created`) join `user` `u`) join `remind_template` `r`) where ((`t`.`user` = `u`.`id`) and (`r`.`user` = `u`.`id`) and (`r`.`type` = 101) and (`r`.`status` = 1) and (`t`.`toBuyerDunningPay` = 0) and (`u`.`expiresTime` > (unix_timestamp(now()) * 1000)) and (`t`.`status` = 'WAIT_BUYER_PAY') and (`u`.`mktBalance` > 0) and (`t`.`created` > ((unix_timestamp(now()) * 1000) - (3600000 * 12.5)))) ;

-- ----------------------------
-- View structure for `nopay_trade_seller`
-- ----------------------------
DROP VIEW IF EXISTS `nopay_trade_seller`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.1.%` SQL SECURITY DEFINER VIEW `nopay_trade_seller` AS select `t`.`tid` AS `tid`,`t`.`toBuyerDunningPay` AS `toBuyerDunningPay`,`t`.`toSellerCreateTrade` AS `toSellerCreateTrade`,`t`.`buyerNick` AS `buyerNick`,`t`.`title` AS `title`,`t`.`smsItemName` AS `smsItemName`,`t`.`user` AS `user`,`t`.`id` AS `id`,`t`.`receiverMobile` AS `receiverMobile`,`t`.`created` AS `created`,`r`.`settingRemindTime` AS `settingRemindTime`,`r`.`id` AS `rid` from ((`trade` `t` join `user` `u`) join `remind_template` `r`) where ((`t`.`user` = `u`.`id`) and (`r`.`user` = `u`.`id`) and (`r`.`type` = 201) and (`r`.`status` = 1) and (`u`.`expiresTime` > (unix_timestamp(now()) * 1000)) and (`t`.`status` = 'WAIT_BUYER_PAY') and (`u`.`mktBalance` > 0) and (`t`.`toSellerCreateTrade` = 0) and (`t`.`created` > ((unix_timestamp(now()) * 1000) - (3600000 * 3))) and (`t`.`created` < ((unix_timestamp(now()) * 1000) - (3600000 * 2)))) ;

-- ----------------------------
-- View structure for `nopay_trade_send`
-- ----------------------------
DROP VIEW IF EXISTS `nopay_trade_send`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.1.%` SQL SECURITY DEFINER VIEW `nopay_trade_send` AS select `a`.`tid` AS `tid`,`a`.`toBuyerDunningPay` AS `toBuyerDunningPay`,`a`.`toSellerCreateTrade` AS `toSellerCreateTrade`,`a`.`buyerNick` AS `buyerNick`,`a`.`title` AS `title`,`a`.`smsItemName` AS `smsItemName`,`a`.`user` AS `user`,`a`.`id` AS `id`,`a`.`receiverMobile` AS `receiverMobile`,`a`.`created` AS `created`,`a`.`settingRemindTime` AS `settingRemindTime`,`a`.`rid` AS `rid`,`shomop_group1`.`remind_template`.`id` AS `send` from (`nopay_trade` `a` left join `remind_template` on(((`a`.`rid` = `shomop_group1`.`remind_template`.`id`) and (((unix_timestamp(now()) * 1000) - `a`.`created`) > (60000 * `a`.`settingRemindTime`)) and (((unix_timestamp(now()) * 1000) - `a`.`created`) < (60000 * (`a`.`settingRemindTime` + 60)))))) ;

-- ----------------------------
-- View structure for `taskinfo`
-- ----------------------------
DROP VIEW IF EXISTS `taskinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.1.%` SQL SECURITY DEFINER VIEW `taskinfo` AS select from_unixtime((`shomop_group1`.`task`.`createDate` / 1000)) AS `createDate`,from_unixtime((`shomop_group1`.`task`.`finishedTime` / 1000)) AS `finishedTime`,`shomop_group1`.`task`.`taskStatus` AS `taskStatus`,`shomop_group1`.`task`.`user` AS `user`,`shomop_group1`.`user`.`username` AS `username`,`shomop_group1`.`task`.`taskId` AS `taskId` from (`task` join `user`) where ((`shomop_group1`.`task`.`user` = `shomop_group1`.`user`.`id`) and (`shomop_group1`.`task`.`type` = 0)) order by `shomop_group1`.`user`.`creationDate` desc limit 20 ;

-- ----------------------------
-- View structure for `trade_order`
-- ----------------------------
DROP VIEW IF EXISTS `trade_order`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.1.%` SQL SECURITY DEFINER VIEW `trade_order` AS select `t`.`buyerNick` AS `buyernick`,`t`.`created` AS `created`,`o`.`numIid` AS `numiid`,`t`.`status` AS `status`,`t`.`user` AS `user`,`i`.`cid` AS `cid`,`i`.`popId` AS `popId` from ((`trade` `t` left join `orders` `o` on((`t`.`tid` = `o`.`tid`))) left join `item` `i` on((`i`.`numIid` = `o`.`numIid`))) ;

-- ----------------------------
-- View structure for `ut`
-- ----------------------------
DROP VIEW IF EXISTS `ut`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.1.%` SQL SECURITY DEFINER VIEW `ut` AS select from_unixtime((`shomop_group1`.`task`.`createDate` / 1000)) AS `createDate`,cast((((unix_timestamp() * 1000) - `shomop_group1`.`task`.`createDate`) / 60000) as unsigned) AS `t`,from_unixtime((`shomop_group1`.`user`.`expiresTime` / 1000)) AS `expiresTime`,`shomop_group1`.`user`.`username` AS `username`,`shomop_group1`.`task`.`taskStatus` AS `taskStatus`,`shomop_group1`.`user`.`accessToken` AS `accessToken`,`shomop_group1`.`task`.`taskId` AS `taskId` from (`task` join `user`) where ((`shomop_group1`.`user`.`id` = `shomop_group1`.`task`.`user`) and (`shomop_group1`.`task`.`taskStatus` <> 'TASK_COMPLETE') and (`shomop_group1`.`task`.`type` = 0)) order by from_unixtime((`shomop_group1`.`task`.`createDate` / 1000)) desc limit 20 ;

-- ----------------------------
-- Function structure for `addsms`
-- ----------------------------
DROP FUNCTION IF EXISTS `addsms`;
DELIMITER ;;
CREATE DEFINER=`root`@`192.168.1.%` FUNCTION `addsms`(`usernameString` varchar(100),`smsnum` int) RETURNS varchar(100) CHARSET utf8
BEGIN
DECLARE userId varchar(32);
DECLARE oldValue int;
DECLARE retuenValue varchar(100);
select id INTO userId from user where username=usernameString limit 1;
if (userId != '') then 
select mktBalance into oldValue from user where username=usernameString limit 1;
update user set mktBalance=mktBalance+ smsnum where id=userId;
insert into balance_update_record(updateTime, balanceType, oldValue, newValue, type, remark, relatedRecordId, deleted, user, id) values (unix_timestamp(now())*1000, 0, oldValue, oldValue+smsnum, 1000, 'addSMS函数赠送', NULL, false, userId, replace(uuid(),'-',''));
set retuenValue=CONCAT(usernameString,'已经由',oldValue,'条增加了',smsnum,'条');
else
set retuenValue=CONCAT('查无此用户名:',usernameString);
end if;
if(select INSTR(usernameString, 'taobao')<1) then
set retuenValue='名字必须以_taobao结尾';
end if;
RETURN (retuenValue);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `extractNum`
-- ----------------------------
DROP FUNCTION IF EXISTS `extractNum`;
DELIMITER ;;
CREATE DEFINER=`root`@`192.168.1.%` FUNCTION `extractNum`(address VARCHAR(100)) RETURNS varchar(11) CHARSET utf8
BEGIN
DECLARE v_Tmp VARCHAR(11) DEFAULT '';
DECLARE num_temp VARCHAR(1) DEFAULT '';
DECLARE v_length INT DEFAULT 0;
DECLARE find_pos INT DEFAULT 0 ;
set v_length=CHAR_LENGTH(address);
set find_pos=LOCATE('1',address); 
IF(v_length>= 11 and find_pos>=0) THEN
WHILE find_pos <= v_length DO
SET num_temp = mid(address,find_pos,1);
IF (CHAR_LENGTH(num_temp)>0 and ASCII(num_temp)>=48 and ASCII(num_temp)<=57) THEN
SET v_Tmp=concat(v_Tmp,num_temp);
ELSE 
SET v_Tmp='';
END IF;
IF (CHAR_LENGTH(v_Tmp) = 11) THEN
RETURN v_Tmp;
END IF;
SET find_pos = find_pos + 1;
END WHILE;
END IF;
RETURN v_Tmp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `GetNum`
-- ----------------------------
DROP FUNCTION IF EXISTS `GetNum`;
DELIMITER ;;
CREATE DEFINER=`root`@`192.168.1.%` FUNCTION `GetNum`(Varstring varchar(50)) RETURNS varchar(30) CHARSET utf8
BEGIN
  DECLARE v_length INT DEFAULT 0;
  DECLARE v_Tmp varchar(50) default '';
  set v_length=CHAR_LENGTH(Varstring);
  WHILE v_length > 0 DO
 
    if (ASCII(mid(Varstring,v_length,1))>47 and ASCII(mid(Varstring,v_length,1))<58) then
 
        set v_Tmp=concat(v_Tmp,mid(Varstring,v_length,1));
    end if; 
    SET v_length = v_length - 1; 
  END WHILE;
    RETURN REVERSE(v_Tmp);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `insertZMKM`
-- ----------------------------
DROP FUNCTION IF EXISTS `insertZMKM`;
DELIMITER ;;
CREATE DEFINER=`root`@`192.168.1.%` FUNCTION `insertZMKM`(usernameString varchar(300)) RETURNS varchar(32) CHARSET utf8
BEGIN
DECLARE Id1 varchar(32);
DECLARE retuenValue varchar(32);
select id INTO Id1 from code where codno = '芝麻开门' limit 1;
if (Id1 != '') then
set retuenValue='已经存在命令';
else
insert into code (id,deleted,codno,codname) value (REPLACE(uuid(),'-',''),0,'芝麻开门',usernameString);
set retuenValue='插入成功';
end if;
RETURN (retuenValue);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `th`
-- ----------------------------
DROP FUNCTION IF EXISTS `th`;
DELIMITER ;;
CREATE DEFINER=`root`@`192.168.1.%` FUNCTION `th`(Varstring bigint) RETURNS bigint(20)
BEGIN
    RETURN (UNIX_TIMESTAMP(now())+Varstring*3600)*1000;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `timer`
-- ----------------------------
DROP FUNCTION IF EXISTS `timer`;
DELIMITER ;;
CREATE DEFINER=`root`@`192.168.1.%` FUNCTION `timer`(stampe bigint(20),format VARCHAR(20)) RETURNS varchar(50) CHARSET utf8
BEGIN
  DECLARE f varchar(20) DEFAULT '%Y-%m-%d %H:%i:%s';
  DECLARE v_len INT DEFAULT 0;
  set v_len=CHAR_LENGTH(format);
  IF(v_len >= 6 ) THEN
  set f=format;
  END IF;
  RETURN FROM_UNIXTIME(stampe/1000,f);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `u`
-- ----------------------------
DROP FUNCTION IF EXISTS `u`;
DELIMITER ;;
CREATE DEFINER=`root`@`192.168.1.%` FUNCTION `u`(usernameString varchar(50)) RETURNS varchar(1000) CHARSET utf8
BEGIN
DECLARE userId varchar(32);
DECLARE retuenValue varchar(1000);
DECLARE createtime varchar(32);
DECLARE taskStatus1 varchar(32);
DECLARE taskCreateTime varchar(32);
DECLARE mktBalance1 int(11);
select u.id,u.mktBalance,from_unixtime(u.creationDate/1000) ,taskStatus ,from_unixtime(t.finishedTime/1000)  INTO userId,mktBalance1,createtime,  taskStatus1,taskCreateTime from user u left join task t on u.id=t.user  where username = CONCAT(usernameString,'_taobao')limit 1;
set retuenValue=CONCAT('', '             id               | mktBalance|     creationDate    |    taskStatus   |   taskFinishedTime \n');
set retuenValue=CONCAT(retuenValue,userId);
set retuenValue=CONCAT(retuenValue,'    ');
set retuenValue=CONCAT(retuenValue,mktBalance1);
set retuenValue=CONCAT(retuenValue,'    ');
set retuenValue=CONCAT(retuenValue,createtime);
set retuenValue=CONCAT(retuenValue,'    ');
set retuenValue=CONCAT(retuenValue,taskStatus1);
set retuenValue=CONCAT(retuenValue,'    ');
set retuenValue=CONCAT(retuenValue,taskCreateTime);
RETURN (retuenValue);
 
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `buyer_inserttime`;
DELIMITER ;;
CREATE TRIGGER `buyer_inserttime` BEFORE INSERT ON `buyer` FOR EACH ROW SET new.insertTime=UNIX_TIMESTAMP()*1000,new.updatetime=UNIX_TIMESTAMP()*1000
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `buyer_updatetime`;
DELIMITER ;;
CREATE TRIGGER `buyer_updatetime` BEFORE UPDATE ON `buyer` FOR EACH ROW SET new.updatetime=UNIX_TIMESTAMP()*1000
;;
DELIMITER ;
