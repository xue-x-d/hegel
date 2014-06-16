
-- ----------------------------
-- View structure for `nopay_trade`
-- ----------------------------
DROP VIEW IF EXISTS `nopay_trade`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `nopay_trade` AS select `t`.`tid` AS `tid`,`t`.`toBuyerDunningPay` AS `toBuyerDunningPay`,`t`.`toSellerCreateTrade` AS `toSellerCreateTrade`,`t`.`buyerNick` AS `buyerNick`,`t`.`title` AS `title`,`t`.`smsItemName` AS `smsItemName`,`t`.`user` AS `user`,`t`.`id` AS `id`,`t`.`receiverMobile` AS `receiverMobile`,`t`.`created` AS `created`,`r`.`settingRemindTime` AS `settingRemindTime`,`r`.`id` AS `rid` from ((`trade` `t` FORCE INDEX (`trade__status_created`) join `user` `u`) join `remind_template` `r`) where ((`t`.`user` = `u`.`id`) and (`r`.`user` = `u`.`id`) and (`r`.`type` = 101) and (`r`.`status` = 1) and (`t`.`toBuyerDunningPay` = 0) and (`u`.`expiresTime` > (unix_timestamp(now()) * 1000)) and (`t`.`status` = 'WAIT_BUYER_PAY') and (`u`.`mktBalance` > 0) and (`t`.`created` > ((unix_timestamp(now()) * 1000) - (3600000 * 12.5)))) ;

-- ----------------------------
-- View structure for `nopay_trade_seller`
-- ----------------------------
DROP VIEW IF EXISTS `nopay_trade_seller`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `nopay_trade_seller` AS select `t`.`tid` AS `tid`,`t`.`toBuyerDunningPay` AS `toBuyerDunningPay`,`t`.`toSellerCreateTrade` AS `toSellerCreateTrade`,`t`.`buyerNick` AS `buyerNick`,`t`.`title` AS `title`,`t`.`smsItemName` AS `smsItemName`,`t`.`user` AS `user`,`t`.`id` AS `id`,`t`.`receiverMobile` AS `receiverMobile`,`t`.`created` AS `created`,`r`.`settingRemindTime` AS `settingRemindTime`,`r`.`id` AS `rid` from ((`trade` `t` join `user` `u`) join `remind_template` `r`) where ((`t`.`user` = `u`.`id`) and (`r`.`user` = `u`.`id`) and (`r`.`type` = 201) and (`r`.`status` = 1) and (`u`.`expiresTime` > (unix_timestamp(now()) * 1000)) and (`t`.`status` = 'WAIT_BUYER_PAY') and (`u`.`mktBalance` > 0) and (`t`.`toSellerCreateTrade` = 0) and (`t`.`created` > ((unix_timestamp(now()) * 1000) - (3600000 * 3))) and (`t`.`created` < ((unix_timestamp(now()) * 1000) - (3600000 * 2)))) ;

-- ----------------------------
-- View structure for `nopay_trade_send`
-- ----------------------------
DROP VIEW IF EXISTS `nopay_trade_send`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `nopay_trade_send` AS select `a`.`tid` AS `tid`,`a`.`toBuyerDunningPay` AS `toBuyerDunningPay`,`a`.`toSellerCreateTrade` AS `toSellerCreateTrade`,`a`.`buyerNick` AS `buyerNick`,`a`.`title` AS `title`,`a`.`smsItemName` AS `smsItemName`,`a`.`user` AS `user`,`a`.`id` AS `id`,`a`.`receiverMobile` AS `receiverMobile`,`a`.`created` AS `created`,`a`.`settingRemindTime` AS `settingRemindTime`,`a`.`rid` AS `rid`,`shomop_group1`.`remind_template`.`id` AS `send` from (`nopay_trade` `a` left join `remind_template` on(((`a`.`rid` = `shomop_group1`.`remind_template`.`id`) and (((unix_timestamp(now()) * 1000) - `a`.`created`) > (60000 * `a`.`settingRemindTime`)) and (((unix_timestamp(now()) * 1000) - `a`.`created`) < (60000 * (`a`.`settingRemindTime` + 60)))))) ;

-- ----------------------------
-- View structure for `taskinfo`
-- ----------------------------
DROP VIEW IF EXISTS `taskinfo`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `taskinfo` AS select from_unixtime((`shomop_group1`.`task`.`createDate` / 1000)) AS `createDate`,from_unixtime((`shomop_group1`.`task`.`finishedTime` / 1000)) AS `finishedTime`,`shomop_group1`.`task`.`taskStatus` AS `taskStatus`,`shomop_group1`.`task`.`user` AS `user`,`shomop_group1`.`user`.`username` AS `username`,`shomop_group1`.`task`.`taskId` AS `taskId` from (`task` join `user`) where ((`shomop_group1`.`task`.`user` = `shomop_group1`.`user`.`id`) and (`shomop_group1`.`task`.`type` = 0)) order by `shomop_group1`.`user`.`creationDate` desc limit 20 ;

-- ----------------------------
-- View structure for `trade_order`
-- ----------------------------
DROP VIEW IF EXISTS `trade_order`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `trade_order` AS select `t`.`buyerNick` AS `buyernick`,`t`.`created` AS `created`,`o`.`numIid` AS `numiid`,`t`.`status` AS `status`,`t`.`user` AS `user`,`i`.`cid` AS `cid`,`i`.`popId` AS `popId` from ((`trade` `t` left join `orders` `o` on((`t`.`tid` = `o`.`tid`))) left join `item` `i` on((`i`.`numIid` = `o`.`numIid`))) ;

-- ----------------------------
-- View structure for `ut`
-- ----------------------------
DROP VIEW IF EXISTS `ut`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `ut` AS select from_unixtime((`shomop_group1`.`task`.`createDate` / 1000)) AS `createDate`,cast((((unix_timestamp() * 1000) - `shomop_group1`.`task`.`createDate`) / 60000) as unsigned) AS `t`,from_unixtime((`shomop_group1`.`user`.`expiresTime` / 1000)) AS `expiresTime`,`shomop_group1`.`user`.`username` AS `username`,`shomop_group1`.`task`.`taskStatus` AS `taskStatus`,`shomop_group1`.`user`.`accessToken` AS `accessToken`,`shomop_group1`.`task`.`taskId` AS `taskId` from (`task` join `user`) where ((`shomop_group1`.`user`.`id` = `shomop_group1`.`task`.`user`) and (`shomop_group1`.`task`.`taskStatus` <> 'TASK_COMPLETE') and (`shomop_group1`.`task`.`type` = 0)) order by from_unixtime((`shomop_group1`.`task`.`createDate` / 1000)) desc limit 20 ;

-- ----------------------------
-- Function structure for `addsms`
-- ----------------------------
DROP FUNCTION IF EXISTS `addsms`;
DELIMITER ;;
CREATE  FUNCTION `addsms`(`usernameString` varchar(100),`smsnum` int) RETURNS varchar(100) CHARSET utf8
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
CREATE  FUNCTION `extractNum`(address VARCHAR(100)) RETURNS varchar(11) CHARSET utf8
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
CREATE  FUNCTION `GetNum`(Varstring varchar(50)) RETURNS varchar(30) CHARSET utf8
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
CREATE  FUNCTION `insertZMKM`(usernameString varchar(300)) RETURNS varchar(32) CHARSET utf8
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
CREATE  FUNCTION `th`(Varstring bigint) RETURNS bigint(20)
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
CREATE  FUNCTION `timer`(stampe bigint(20),format VARCHAR(20)) RETURNS varchar(50) CHARSET utf8
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
CREATE  FUNCTION `u`(usernameString varchar(50)) RETURNS varchar(1000) CHARSET utf8
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

-- ----------------------------
-- Function structure for `partTime`
-- ----------------------------
DROP FUNCTION IF EXISTS `partTime`;
DELIMITER ;;
CREATE FUNCTION `partTime`(millisecond bigint(20),type VARCHAR(10)) RETURNS int(11)
BEGIN
DECLARE returnValue int(11) DEFAULT 0;
DECLARE time varchar(20) DEFAULT from_unixtime(millisecond/1000,'%Y-%m-%d');
IF(STRCMP(type,'month') = 0) THEN
set returnValue = month(time);
ELSEIF(STRCMP(type,'day') = 0) THEN
set returnValue = day(time);
ELSEIF(STRCMP(type,'year') = 0) THEN
set returnValue = year(time);
END IF;
RETURN returnValue;
END ;;
DELIMITER ;