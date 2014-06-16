 -- 建唯一索引
create  unique index trade_tid_index on trade(tid);
create unique index orders_oid_index on orders(oid);

-- 增加字段
-- buyer表
alter table buyer add grade  bigint(20) NULL DEFAULT 0;
alter table buyer add deal  tinyint(1) DEFAULT 0 ;
alter table buyer add changeStatus  tinyint(1) DEFAULT 0 ;
alter table buyer add groupIds  text;
alter table buyer add relationSource  tinyint(1) DEFAULT 0 ;
alter table buyer add buyerId  bigint(20) DEFAULT 0 ; 
--Filter_Condition表
    alter table Filter_Condition add groupId varchar(30);
    alter table Filter_Condition add grade bigint(20) NULL ;
--User表
    alter table User add lastDownBuyerTime  bigint(20)  DEFAULT 0;
-- orders表新增 created 字段 
alter table orders add created BIGINT(20);
update orders a set created=(select DISTINCT created from trade as t  where t.tid = a.tid); 
-- trade 表新增字段 宝贝名称
alter table trade add smsItemName varchar(30);
-- user 表新增字段 userId
alter table user add userId BIGINT(20);
-- user 表新增字段 refreshToken ,purchaseTime
alter table user add purchaseTime BIGINT(20);
alter table user add refreshToken TEXT;
--item表增加popId字段
alter table item add popId BIGINT(20)  DEFAULT 0;
-- FilterCondition
alter table Filter_Condition add filterSql  varchar(400);
alter table Filter_Condition add cid  varchar(400);
--视图
 --卖家催款
create or replace view  nopay_trade_seller as select t.tid ,t.toBuyerDunningPay ,t.toSellerCreateTrade ,t.buyerNick,t.title,t.smsItemName,t.user,t.id ,t.receiverMobile,t.created,r.settingRemindTime,r.id as rid from Trade t,User u ,Remind_Template r  
where t.user=u.id and r.user = u.id and r.type=201 and r.status=1 and u.expiresTime >UNIX_TIMESTAMP(now())*1000 and 
t.status = 'WAIT_BUYER_PAY'   and u.mktBalance >0   and t.toSellerCreateTrade =0 and
 t.created> UNIX_TIMESTAMP(now())*1000-3600000*3 and t.created < UNIX_TIMESTAMP(now())*1000-3600000*2;
 --买家催款
create or replace view nopay_trade as select t.tid ,t.toBuyerDunningPay ,t.toSellerCreateTrade ,t.buyerNick,t.title,t.smsItemName,t.user,t.id ,t.receiverMobile,t.created,r.settingRemindTime,r.id as rid from Trade t FORCE INDEX (trade__status_created),User u ,Remind_Template r  
where t.user=u.id and r.user = u.id and r.type=101 and r.status=1 and t.toBuyerDunningPay=0  and u.expiresTime >UNIX_TIMESTAMP(now())*1000 and 
t.status = 'WAIT_BUYER_PAY'   and u.mktBalance >0  and
 t.created> UNIX_TIMESTAMP(now())*1000-3600000*12.5;
 
create view nopay_trade_send as select a.*,Remind_Template.id as send  from nopay_trade
 as a left join Remind_Template on a.rid=Remind_Template.id and  UNIX_TIMESTAMP(now())*1000 -a.created> 60000*a.settingRemindTime and UNIX_TIMESTAMP(now())*1000 -a.created < 60000*(a.settingRemindTime+60);

 -- tradr_order 订单,子订单视图
create or REPLACE view trade_order as select t.buyerNick AS buyernick,t.created AS created,o.numIid AS numiid,t.status AS status,t.user AS user,i.cid as cid,i.popId from trade t left join orders o on t.tid = o.tid left join item i on i.numiid=o.numiid
 
-- unique index spop表
create unique index index_spop_user_popid on spop on(user,pop); 
CREATE TABLE `scid` (
`id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`cid`  bigint(20) NOT NULL ,
`cidName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`updateTime`  bigint(20) NOT NULL ,
`user`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `scid_user_cidId` (`user`, `cid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

CREATE TABLE `spop` (
`id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`pop`  bigint(20) NOT NULL ,
`popName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`updatetime`  bigint(20) NOT NULL ,
`user`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `spop_user_popid` (`user`, `pop`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;



create unique index index_scid_user_cidId on spop on(user,cid); 

-- 通知服务的item通知
 CREATE TABLE `tn_item` (
`userId`  bigint(20) NOT NULL DEFAULT 0 ,
`topic`  tinyint(4) NOT NULL ,
`pubTime`  bigint(20) NOT NULL DEFAULT 0 ,
`dealStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`smsSellerStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`smsBuyerStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`dealTime`  bigint(20) NOT NULL DEFAULT 0 ,
`dealspend`  bigint(20) NOT NULL DEFAULT 0 ,
`insertTime`  bigint(20) NOT NULL DEFAULT 0 ,
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`numiid`  bigint(20) NOT NULL DEFAULT 0 ,
`nId`  bigint(20) NOT NULL ,
`test`  tinyint(4) NOT NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
UNIQUE INDEX `item_numiid_pubtime` (`numiid`, `pubTime`) USING BTREE ,
INDEX `item_deal` (`dealStatus`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


--通知服务的任务通知 tn_topats
CREATE TABLE `tn_topats` (
`userId`  bigint(20) NOT NULL DEFAULT 0 ,
`topic`  tinyint(4) NOT NULL ,
`task_status`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`api_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`pubTime`  bigint(20) NOT NULL DEFAULT 0 ,
`dealStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`smsSellerStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`smsBuyerStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`dealTime`  bigint(20) NOT NULL DEFAULT 0 ,
`dealspend`  bigint(20) NOT NULL DEFAULT 0 ,
`insertTime`  bigint(20) NOT NULL DEFAULT 0 ,
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`taskid`  bigint(20) NOT NULL DEFAULT 0 ,
`nId`  bigint(20) NOT NULL ,
`test`  tinyint(4) NOT NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
UNIQUE INDEX `tn_taskid` (`taskid`) USING BTREE ,
INDEX `tn_deal` (`dealStatus`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

-- 通知服务的订单通知
CREATE TABLE `tn_trade` (
`userId`  bigint(20) NOT NULL DEFAULT 0 ,
`topic`  tinyint(4) NOT NULL ,
`pubTime`  bigint(20) NOT NULL DEFAULT 0 ,
`dealStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`smsSellerStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`smsBuyerStatus`  tinyint(4) NOT NULL DEFAULT 0 ,
`dealTime`  bigint(20) NOT NULL DEFAULT 0 ,
`dealspend`  bigint(20) NOT NULL DEFAULT 0 ,
`insertTime`  bigint(20) NOT NULL DEFAULT 0 ,
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`tid`  bigint(20) NOT NULL DEFAULT 0 ,
`oid`  bigint(20) NOT NULL DEFAULT 0 ,
`nId`  bigint(20) NOT NULL ,
`test`  tinyint(4) NOT NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
UNIQUE INDEX `trade_order_topic_puttime` (`topic`, `pubTime`, `oid`) USING BTREE ,
INDEX `trade_deal` (`dealStatus`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

-- 催款详情表
CREATE TABLE `dunning` (
  `id` varchar(32) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `payment` bigint(20) NOT NULL,
  `sendTime` bigint(20) DEFAULT NULL,
  `tid` bigint(20) NOT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_tid` (`tid`),
  KEY `index_user_sendTime` (`user`,`sendTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create unique index index_tid on dunning (tid);
create index index_user_sendTime on dunning (user,sendTime);

-- 营销详情表
CREATE TABLE `reward_detail` (
  `id` varchar(32) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `payment` bigint(20) NOT NULL,
  `recordTime` bigint(20) DEFAULT NULL,
  `sendRecord` varchar(32) DEFAULT NULL,
  `tid` bigint(20) NOT NULL,
  `user` varchar(32) DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `buyerNick` varchar(50) DEFAULT NULL,
  `paytime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_sendRecord_tid` (`sendRecord`,`tid`),
  KEY `index_user_deleted` (`user`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create unique INDEX index_sendRecord_tid on reward_detail(sendRecord,tid);
alter table reward_detail add index index_user_deleted (user,deleted);

--buyer插入插入时间和跟新时间
    alter table buyer add insertTime bigint(20) NULL ;
    alter table buyer add updatetime bigint(20) NULL ;
--触发器
--buyer插入时间
CREATE TRIGGER buyer_inserttime BEFORE INSERT ON buyer FOR EACH ROW  SET new.insertTime=UNIX_TIMESTAMP()*1000,new.updatetime=UNIX_TIMESTAMP()*1000;
--buyer跟新时间
CREATE TRIGGER buyer_updatetime BEFORE UPDATE ON buyer FOR EACH ROW  SET new.updatetime=UNIX_TIMESTAMP()*1000;

create trigger tn_bak after insert on tn_trade for each row 
if (NEW.userid=58253155 or NEW.userid=765026597) then
insert into shomop_group1.tn_trade(id,oid,TID,USERID,topic,pubTime,dealStatus,smsSellerStatus,smsBuyerStatus,dealTime,dealspend,insertTime,nId,test) 
values
(NEW.id,NEW.oid,NEW.TID,NEW.USERID,NEW.topic,NEW.pubTime,NEW.dealStatus,NEW.smsSellerStatus,NEW.smsBuyerStatus,NEW.dealTime,NEW.dealspend,NEW.insertTime,NEW.nId,NEW.test);
end if;
