package com.shomop.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shomop.crm.dao.CrownShopDao;
import com.shomop.crm.model.shop.Classify;
import com.shomop.crm.model.shop.CrownShop;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Location;
import com.taobao.api.domain.User;
import com.taobao.api.domain.UserCredit;
import com.taobao.api.request.UsersGetRequest;
import com.taobao.api.response.UsersGetResponse;

/**
 * 按类目获取店铺信息
 * @author spencer.xue
 * @date 2015-1-21
 */
public class ShopCrawlerUtil {
	
	
	private static Log logger = LogFactory.getLog(ShopCrawlerUtil.class);
	private static final Pattern pattern = Pattern.compile("\\d{4,}");
    private static final String baseUrl = "http://www.hgdpjx.com/haodian/cat_1.html";
	private static final String shopUrlTemp = "http://shop[sid].taobao.com";
	private static TaobaoClient client;
	
	static {
	   if(File.separator.equals("\\")){
		   client = new DefaultTaobaoClient("http://crm-admin.shomop.com/rest","21391189","46cbfa7400232e911838932b6224d183");
	   }else{
		   client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest","21391189","46cbfa7400232e911838932b6224d183");
	   }
	}
	
	
	/**
	 * 获取类目列表
	 * @return
	 * @throws Exception
	 */
	public static List<Classify> fetchClassifyList() throws Exception {
		Document doc = Jsoup.connect(baseUrl).timeout(3000).get();
		Elements classifyList = doc.select("div.categorylist > ul > li > a");
		//System.out.println(classifyList.html());
		List<Classify> classifies = new ArrayList<Classify>();
		for (Element category : classifyList) {
			Classify classify = new Classify();
			classifies.add(classify);
			classify.setPath(category.attr("abs:href").toString());
			classify.setName(category.getElementsByTag("span").first().text());
			classify.setLogo(category.getElementsByTag("img").first().attr("src"));
		}
		logger.info(">>> 类目下载完成。共计："+classifies.size());
		return classifies;
	}
	
	
	/**
	 * 遍历一个类目下的所有店铺
	 * 最大500页（500*20）
	 * @param classify
	 * @param shopDao
	 * @param maxPage
	 * @return 1 同步完成
	 *        -1 淘宝中断
	 *         0 正常终止
	 */
	public static Integer fetchShopListByCId(Classify classify,CrownShopDao shopDao,int maxPage){
		if(classify == null || classify.getStatus() != 0){
			return -1;
		}
		int lastPage = classify.getPage() + 1;
		int retryTimes = 0;
		String baseUrl = classify.getPath();
		long startTime = System.currentTimeMillis();
		logger.info(String.format(">>> 类目：%s 从第 %s 页，开始同步.....",classify.getName(),lastPage));
		for (int page = lastPage; page < maxPage + lastPage; page++) {
			try {
				String path = baseUrl + "?&p=" + page;
				List<CrownShop> shops = fethShopListByPath(path,classify.getId());
				retryTimes = 0;
				shopDao.saveOrIgnoreByJdbc(shops,true);// 保存数据
				if(shops == null){ // 淘宝返回登录页面了
					logger.info("\t 类目："+classify.getName()+" 第：" + page+ " 页同步失败.淘宝禁止访问!");
					return -1;
				}
				logger.info("\t 类目："+classify.getName()+" 第：" + page+ " 页同步成功. size: "+shops.size());
				if (shops.size() == 0) {
					classify.setStatus(1);
					logger.info("\t 类目："+classify.getName()+" 所有页面同步完成。总页面数："+page);
					return 1;
				}
				classify.setPage(page);// 竟让成功了。记录一下
				Thread.sleep(10000L);
			} catch (Exception e) {
				if (retryTimes++ <= 3){
					logger.error("\t 类目："+classify.getName()+" 第：" + page+ " 页同步失败. 重试： "+retryTimes, e);
					page --;
				}
			}
		}
		logger.info(">>> 类目："+classify.getName()+" 本次同步完成。耗时："+(System.currentTimeMillis() - startTime)+",页面："+(--lastPage)+"——>"+classify.getPage());
		return 0;
	}
	
	/**
	 * 指定一页的店铺信息
	 * example http://www.hgdpjx.com/haodian/cat_2.html?p=2
	 * @param path
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	private static List<CrownShop> fethShopListByPath(String path,Integer cid) throws Exception{
		Document doc = Jsoup.connect(path).timeout(3000).get();
        Elements shopList = doc.select("div.shoplist > ul > li > a"); 
        //System.out.println(shopList.html());
        List<CrownShop> shops = new ArrayList<CrownShop>(shopList.size());
        for (Element shopInfo : shopList) {
        	CrownShop shop = new CrownShop();
        	shops.add(shop);
        	shop.setcId(cid);
        	shop.setSid(Long.valueOf(getKeyValue(shopInfo.attr("href"))));
        	shop.setPicPath(shopInfo.select("img.shop_pic").first().attr("src"));
        	shop.setTitle(shopInfo.select("span.shop_title").first().text().trim());
			String locus = shopInfo.select("span.shop_location").first().text().trim();
			if(StringUtils.isNotBlank(locus)){
				shop.setLocus(locus.replace("地区:",""));
			}
			String value = getKeyValue(shopInfo.select("img.seller_credit").first().attr("src"));
			try {
				if (!"tmall".equals(value)) {
					shop.setType("C");
					shop.setLevel(Integer.valueOf(value));
					// 店铺不存在
					if(!setNickOfShop(shop)){ // market title ≠ nick
						// no nick no everything
						shops.remove(shop);
					} 
				} else {
					shop.setType("B");
					shop.setNick(shop.getTitle());
					if (setNickOfShop(shop)) { // tmall title ≠ nick ?
						setLevelOfShop(shop);
					}else{
						// 店铺不存在
						shops.remove(shop);
					}
				}
				Thread.sleep(1500L);
			} catch (Exception e) {
				logger.error("fetch shop nick error.shop sid: "+shop.getSid()+"\t value: "+value,e);
				if (e instanceof NullPointerException) {
					try {
						// 尝试搜索
						long sid = shop.getSid();
						StringBuilder log = new StringBuilder();
						log.append("search shop title: ").append(shop.getTitle()).append(",sid: ").append(sid);
						if (setNickBysearch(shop)) {
							log.append(" search shop success. search sid: ").append(shop.getSid());
							if (sid == shop.getSid()) {
								log.append(" the same shop.");
								System.out.println(log.toString());
								return shops;
							}
							log.append(" but another shop.");
						}
						log.append(" search shop failed.");
						System.out.println(log.toString());
					} catch (Exception ee) {
						System.out.println("淘宝不让下载了,当页下载作废。");
					}
					return null;
				}
			}
		}
        return shops;
	}
	
	/**
	 * 设置卖家昵称
	 * @param shop
	 * @return 
	 * @throws IOException
	 */
	public static boolean setNickOfShop(CrownShop shop) throws Exception{
		if(shop == null || shop.getSid() <= 0){
			return false;
		}
		String url = shopUrlTemp.replace("[sid]", String.valueOf(shop.getSid()));
		//url = "http://www.hgdpjx.com/tbshop/"+shop.getSid()+".html";
		Document doc = Jsoup.connect(url).timeout(3000).get();
		//System.out.println(doc.html());
		Element error = doc.select("div.error-notice-hd").first();
		if(error != null){
			logger.info("店铺不存在.url: "+url);
			return false;
		}
		System.out.println(doc);
		if ("B".equals(shop.getType())) {
			tmall(doc, shop);
		} else if ("C".equals(shop.getType())) {
			taobao(doc, shop);
		}
		return true;
	}
	
	/**
	 * 设置店铺等级(淘宝API)
	 * @param shop
	 * @return
	 * @throws IOException
	 */
	public static boolean setLevelOfShop(CrownShop shop){
		if (shop == null || StringUtils.isBlank(shop.getNick())) {
			return false;
		}
		UsersGetRequest request = new UsersGetRequest();
		request.setFields("seller_credit,type,location");
		request.setNicks(shop.getNick());
		try {
			UsersGetResponse rsp = client.execute(request);
			if (rsp.isSuccess()) {
				User user = rsp.getUsers().get(0);
				if (user != null) {
					UserCredit credit = user.getSellerCredit();
					shop.setLevel(credit.getLevel().intValue());
					shop.setType(user.getType());
					if (StringUtils.isBlank(shop.getLocus())) {
						Location locus = user.getLocation();
						shop.setLocus(locus.getState()+","+locus.getCity());
					}
					return true;
				}
			}
		} catch (ApiException e) {
			logger.error(">>> get level of shop error.shop nick:"+shop.getNick());
		}
		return false;
	}
	
	private static void tmall(Document doc,CrownShop shop) throws Exception {
		Element shopDesc = doc.select("textarea.ks-datalazyload").first();
		// System.out.println(shopDesc.html());
		if(shopDesc == null){
			logger.info(">>> 天猫店铺信息获取失败。sid: "+shop.getSid());
			// 这个网站太不专业了
			taobao(doc,shop);
			return;
		}
		Element title = doc.select("div.slogo > a > strong").first();
		shop.setTitle(title.text().trim());
		String shopInfo = StringEscapeUtils.unescapeHtml(shopDesc.html());
		Document shopDoc = Jsoup.parseBodyFragment(shopInfo);
		Elements elements = shopDoc.select("div.extend > ul > li");
		for (Element element : elements) {
			String clazz = element.attr("class");
			if("shopkeeper".equals(clazz)){
				Element value = element.select("div > a").first();
				shop.setNick(value.text().trim());
			}else if("locus".equals(clazz)){
				Element value = element.getElementsByTag("div").first();
				shop.setLocus(value.text());
			}else{
				String label = element.getElementsByTag("label").first().text();
				if(label!= null && label.contains("公 司 名")){
					Element value = element.getElementsByTag("div").first();
					shop.setCompany(value.text().trim());
				}
			}
		}
	}
	
	private static void taobao(Document doc,CrownShop shop) throws Exception {
		// nick
		Element shopDesc = doc.select("span.seller").first();
		if(shopDesc == null){
			shopDesc = doc.select("div.shop-info-simple").first();
		}
		if(shopDesc == null){
			System.out.println(doc);
			System.exit(0);
		}
		Element nick = shopDesc.getElementsByTag("a").first();
		shop.setNick(nick.text().replace("掌柜：","").trim());
		// title
		Element shopName = doc.select("span.shop-name").first();
		if (shopName != null) {
			Element title = shopName.getElementsByTag("a").first();
			String shopTitle = title.text().replace("进入店铺", "").trim();
			if (StringUtils.isNotBlank(shopTitle)) {
				// 淘宝字号认证的店铺名
				// title = shopName.getElementsByTag("span").first();
				// shopTitle = title.text().replace("店铺：","").replace("TB 进入店铺","").trim();
				shop.setTitle(shopTitle);
			}
		}
	}
	
	/**
	 * 提取sid 生成店铺地址
	 * @param path /tbshop/34398981.html
	 * @return 店铺地址 http://shop34398981.taobao.com
	 */
	public static String makeShopUrl(String path) {
		if (StringUtils.isNotBlank(path)) {
			Matcher m = pattern.matcher(path);
			if (m.find()) {
				return shopUrlTemp.replace("[sid]", m.group());
			}
		}
		return path;
	}
	
	/**
	 * 提取店铺的sid,level
	 * @param path
	 * @return
	 */
	public static String getKeyValue(String path){
		if (StringUtils.isNotBlank(path)) {
			int last = path.lastIndexOf("/") + 1;
			int dot = path.lastIndexOf(".");
			if (last > 0 && dot > last) {
				return path.substring(last, dot);
			}
		}
		return "0";
	}
	
	public static boolean setNickBysearch(CrownShop shop) throws IOException{
		Document doc = Jsoup.connect("http://s.taobao.com/search?app=shopsearch&q="+shop.getTitle()).timeout(3000).get();
		Element listItem = doc.getElementById("list-container");
		if(listItem != null){
			Element item = listItem.getElementsByTag("li").first();
			Element shopInfo = item.select("p.shop-info").first();
			Element nick = shopInfo.select("span.shop-info-list>a").first();
			shop.setNick(nick.text().trim());
			Element locus = shopInfo.select("span.shop-address").first();
			shop.setLocus(locus.text().trim());
			Element type = item.select("a.mall-icon").first();
			if(type != null){
				shop.setType("B");
			}else{
				shop.setType("C");
			}
			return true;
		}
		return false;
	}
	
	private static final DefaultHttpClient httpClient = new DefaultHttpClient();
	
	public static void setNick() throws ClientProtocolException, IOException{
		HttpGet httpGet = new HttpGet("http://www.hgdpjx.com/tbshop/36935183.html");
//		httpGet.setHeader("Cookie","cna=Br93CiQ4pj0CAX13mH3zoqcH; miid=5301427749129260792; _cc_=VT5L2FSpdA%3D%3D; tg=0; lzstat_uv=3293419823372082513|1544272; thw=cn; uc3=nk2=&id2=&lg2=; tracknick=; mt=ci=0_0; ck1=; isg=FD8B557E9B1741E6EA9A179955ADEAC5; v=0; cookie2=1c46aa4044d50b7ef55b601d0a0d87af; t=1f4d6ba2f8fab0421314ca4e8560c9c1");
//		httpGet.setHeader("Referer","http://www.baidu.com/s?ie=utf-8&wd=%E7%89%A9%E6%B5%81");
//		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
//		httpGet.setHeader("Host","shop34837848.taobao.com");
		// response
		httpClient.setRedirectStrategy(new MyRedirecthandler());
		HttpResponse rsp = httpClient.execute(httpGet);
		StatusLine status = rsp.getStatusLine();
		/*if (status.getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
			// close stream
			EntityUtils.consume(rsp.getEntity());
			throw new HttpResponseException(status.getStatusCode(),
					status.getReasonPhrase());
		}*/
		HttpEntity entity = rsp.getEntity();
		String body = EntityUtils.toString(entity);
		System.out.println(body);
		
	}
	
	public static void main(String[] args) throws Exception {
		// http://amos.alicdn.com/getcid.aw?spm=a1z10.1-c.0.0.TMQgeY&v=3&site=cntaobao&groupid=0&s=1&fromid=cntaobao&uid=健康联盟68
		// http://s.taobao.com/search?app=shopsearch&q=健康联盟68
		// 2015-01-23 17:12:30,775
		CrownShop shop = new CrownShop();
		shop.setType("C");
		shop.setSid(58068456L);
		shop.setTitle("纳帕佳官方旗舰店");
		setNick();
    }  


}
