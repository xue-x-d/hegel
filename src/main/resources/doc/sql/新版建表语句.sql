
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
`dealNum`  tinyint(4) NOT NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
UNIQUE INDEX `trade_order_topic_puttime` (`topic`, `pubTime`, `oid`) USING BTREE ,
INDEX `trade_deal` (`dealStatus`) USING BTREE 
)
;
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
;














-- 需要额外添加的索引
-- user
-- create   unique index user_username on user(username);
-- create    index user_userId on user(userId);
create    index index_id_expiresTime_mktBalance on user(id,expiresTime,mktBalance)
-- trade
create   unique index trade_tid on trade(tid);
create   index trade_user_created on trade(user,created);
create   index trade_user_buyernick on trade(user,buyernick);
create   index trade__status_created on trade(status,created);
create   index trade_receiverMobile on trade (receiverMobile);
create   index trade_user_status on trade (user,status); 
-- orders
create index index_numIid on orders(numIid);
create   unique  index index_oid on orders(oid);
-- trade_rate
create   unique index oid_role on trade_rate(oid,role);
create   index trade_rate_nick_user_role on trade_rate(nick,user,role);
create   index trade_rate__user_created_role on trade_rate(user,created,role);
create   index tid_index on trade_rate(tid);
-- buyer
create   unique index buyer_user_buyernick on buyer(user,buyerNick);
create   index index_user_birthday on buyer(user,birthday);
create   index index_user_lastBuyTime on buyer(user,lastBuyTime);
create   index buyer_user_buyerMobile on buyer (user,buyerMobile);
--item
create   unique index item_numIid_index on item(numIid,user);
create   index item__user on item(user);
create   index item__user_modified on item(user,modified);
create   index item_popid on item(popId);

-- task
create   unique index taskId_index on task(taskId);
create    index task_user on task(user);
create    index task_taskStatus_type_index on task(taskStatus,type);
-- Today_Info
create unique index todayinfo_user_dateNum_type on Today_Info(user,dateNum,type);
-- rfm
create  unique index rfm_user_dateNum_r on rfm(user,dateNum,r) ;
-- Buy_Cyclist_Count
create unique index BuyCyclistCount_user_dateNum_type on Buy_Cyclist_Count(user,dateNum,type);
-- Shop_Info
create unique index shopinfo_user on Shop_Info(user);
-- spop
create unique index spop_user_popid on spop(user,pop); 
-- scid
create unique index scid_user_cidId on scid(user,cid);
-- item_analysis
create unique index index_numIid on item_analysis (numIid);
create index index_user on item_analysis (user);
-- grade_overview
create unique index index_user_updateTime_grade on grade_overview (user,updateTime,grade);
-- grade
create index index_user_curGrade on grade (user,curGrade);
-- residence
create unique index index_user_dayTime_province on residence (user,dayTime,province(3));
-- time_list
create unique index index_user_role on time_list (user,role);
-- refund
create unique index index_refundId on refund (refundId);
create index index_user_created_status on refund (user,created,status);
-- dunning 
create unique index index_tid on dunning (tid);
create  index index_user_sendTime on dunning (user,sendTime);

-- notify_sms 
create unique index type_tid_index on notify_sms (tid,type);
create unique index index_user_type_outsid1 on notify_sms (user,type,outSid);
create index notify_sms__deleted_status_sendTime on notify_sms (deleted,status,sendTime);
create index notify_sms__user_type_sendTime on notify_sms (user,type,sendTime);
create index notify_sms__taskId on notify_sms (taskId);
--  marketing_statistic
create unique index index_user_type_updateTime on marketing_statistic (user,type,updateTime);
-- blacklist
create unique index blacklist__user_mobileNo on blacklist (user,mobileNo);
-- code
create unique index code__codno on code (codno);
-- remind_template
create unique index user_type_index on remind_template (user,type);
create  index user_type_index_status on remind_template (user,type,status);
-- reward_detail
create unique index index_sendRecord_tid on reward_detail (sendRecord,tid);
-- create  index index_user_deleted on reward_detail (user,deleted);
create index index_user_recordTime on reward_detail (user,recordTime);
-- subuser
create unique index subId_index on subuser (subId);

-- send_record
create index index_user_sendTime on send_record (user,sendTime)
create index index_taskId on send_record (taskId)
create index index_deleted_status_sendTime on send_record (deleted,status,sendTime)

-- send_detail
create unique index send_detail__sendRecord_mobileNo on send_detail (sendRecord,mobileNo)
create index send_detail__sendRecord on send_detail (sendRecord)
create index send_detail__mobileNo on send_detail (mobileNo)
create index send_detail__taskId_mobileNo on send_detail (taskId,mobileNo)

-- message_reaward
create unique index index_sendRecord_recordTime on message_reward (sendRecord,recordTime)
create index index_user_sendRecord on message_reward (user,sendReocrd)

--customer
create index customer__user_mobileNo on customer (user,mobileNo)

--logistics
create unique index tid_index on logistics (tid)
-- 视图
 -- 卖家催款
create or replace view  nopay_trade_seller as select t.tid ,t.toBuyerDunningPay ,t.toSellerCreateTrade ,t.buyerNick,t.title,t.smsItemName,t.user,t.id ,t.receiverMobile,t.created,r.settingRemindTime,r.id as rid from Trade t,User u ,Remind_Template r  
where t.user=u.id and r.user = u.id and r.type=201 and r.status=1 and u.expiresTime >UNIX_TIMESTAMP(now())*1000 and 
t.status = 'WAIT_BUYER_PAY'   and u.mktBalance >0   and t.toSellerCreateTrade =0 and
 t.created> UNIX_TIMESTAMP(now())*1000-3600000*3 and t.created < UNIX_TIMESTAMP(now())*1000-3600000*2;
 -- 买家催款
create or replace view nopay_trade as select t.tid ,t.toBuyerDunningPay ,t.toSellerCreateTrade ,t.buyerNick,t.title,t.smsItemName,t.user,t.id ,t.receiverMobile,t.created,r.settingRemindTime,r.id as rid from Trade t FORCE INDEX (trade__status_created),User u ,Remind_Template r  
where t.user=u.id and r.user = u.id and r.type=101 and r.status=1 and t.toBuyerDunningPay=0  and u.expiresTime >UNIX_TIMESTAMP(now())*1000 and 
t.status = 'WAIT_BUYER_PAY'   and u.mktBalance >0  and
 t.created> UNIX_TIMESTAMP(now())*1000-3600000*12.5;
 
create view nopay_trade_send as select a.*,Remind_Template.id as send  from nopay_trade
 as a left join Remind_Template on a.rid=Remind_Template.id and  UNIX_TIMESTAMP(now())*1000 -a.created> 60000*a.settingRemindTime and UNIX_TIMESTAMP(now())*1000 -a.created < 60000*(a.settingRemindTime+60);

 -- tradr_order 订单,子订单视图
CREATE or REPLACE view `trade_order` AS select `t`.`buyerNick` AS `buyernick`,`t`.`created` AS `created`,`o`.`numIid` AS `numiid`,`t`.`status` AS `status`,`t`.`user` AS `user`,`i`.`cid` AS `cid`,`i`.`popId` AS `popId`,`i`.`user` AS `iUser` from ((`trade` `t` left join `orders` `o` on((`t`.`tid` = `o`.`tid`))) left join `item` `i` on((`i`.`numIid` = `o`.`numIid`)))
-- ut 视图 
CREATE  or REPLACE  VIEW `ut` AS select from_unixtime((`task`.`createDate` / 1000)) AS `createDate`,cast(((UNIX_TIMESTAMP()*1000- `task`.`createDate`) / 60000) as unsigned) AS `t`,from_unixtime((`user`.`expiresTime` / 1000)) AS `expiresTime`,`user`.`username` AS `username`,`task`.`taskStatus` AS `taskStatus`,`user`.`accessToken` AS `accessToken`,`task`.`taskId` AS `taskId` from (`task` join `user`) where ((`user`.`id` = `task`.`user`) and (`task`.`taskStatus` <> 'TASK_COMPLETE') and (`task`.`type` = 0)) order by from_unixtime((`task`.`createDate` / 1000)) desc limit 20;
 -- taskinfo视图
CREATE  or REPLACE  VIEW `taskinfo` AS select from_unixtime((`task`.`createDate` / 1000)) AS `createDate`,from_unixtime((`task`.`finishedTime` / 1000)) AS `finishedTime`,`task`.`taskStatus` AS `taskStatus`,`task`.`user` AS `user`,`user`.`username` AS `username`,`task`.`taskId` AS `taskId` from (`task` join `user`) where ((`task`.`user` = `user`.`id`) and (`task`.`type` = 0)) order by `user`.`creationDate` desc limit 20;



-- buyer插入插入时间和跟新时间
alter table buyer add insertTime bigint(20) NULL ;
alter table buyer add updatetime bigint(20) NULL ;
-- 触发器
-- buyer插入时间
CREATE TRIGGER buyer_inserttime BEFORE INSERT ON buyer FOR EACH ROW  SET new.insertTime=UNIX_TIMESTAMP()*1000,new.updatetime=UNIX_TIMESTAMP()*1000;
-- buyer跟新时间
CREATE TRIGGER buyer_updatetime BEFORE UPDATE ON buyer FOR EACH ROW  SET new.updatetime=UNIX_TIMESTAMP()*1000;











