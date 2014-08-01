package com.shomop.crm.model.notify;

import java.util.HashMap;
import java.util.Map;

public class TradeStatus {

	/**
	 * 交易状态。可选值: TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款)
	 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功)
	 * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
	 */
	public static final Map<String,Integer> status2Type = new HashMap<String,Integer>();
	static {
		status2Type.put("WAIT_BUYER_PAY",0);
		status2Type.put("TRADE_CLOSED_BY_TAOBAO",1);
		status2Type.put("WAIT_SELLER_SEND_GOODS",2);
		status2Type.put("WAIT_BUYER_CONFIRM_GOODS",3);
		status2Type.put("TRADE_BUYER_SIGNED",4);
		status2Type.put("TRADE_FINISHED",5);
		status2Type.put("TRADE_CLOSED",6);
	}
}
