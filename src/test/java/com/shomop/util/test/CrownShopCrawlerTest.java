package com.shomop.util.test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrownShopCrawlerTest {

	private static final Pattern pattern = Pattern.compile("\\d{4,}");
	private static final String shopUrlTemp = "http://shop[sid].taobao.com";
	
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
	
	public static String getLevelOrType(String path,boolean type){
		if (StringUtils.isNotBlank(path)) {
			int last = path.lastIndexOf("/") + 1;
			int dot = path.lastIndexOf(".");
			if (last > 0 && dot > last) {
				return path.substring(last, dot);
			}
		}
		return type ? "C" : path;
	}
	
	/**
	 * 类目以及店铺信息
	 * @throws IOException
	 */
	public static void fetchShopList() throws IOException {
		/* // 目标页面  
        String url = "http://www.hgdpjx.com/haodian/cat_1.html";  
        //使用Jsoup连接目标页面,并执行请求,获取服务器响应内容  
        String html = Jsoup.connect(url).execute().body(); 
        //打印页面内容  
        System.out.println(html);*/
        Document doc = Jsoup.connect("http://www.hgdpjx.com/haodian/cat_1.html?p=275").timeout(3000).get();
        
        /*// 使用dom方法来遍历一个Document对象
        Element main_content = doc.select("div.main_content").first();
        System.out.println(main_content.html());
		Attributes a = main_content.attributes();
		for (Attribute attribute : a) {
			System.out.println(attribute.getKey() + "  " + attribute.getValue());
		}
        // 使用Selector选择器
        Elements results = doc.select("div.main_content > div"); 
        for (Element element : results) {
        	 System.out.println(element.html());
		}*/
        // Selector选择器组合使用
        Elements categoryList = doc.select("div.categorylist > ul > li > a"); 
        System.out.println(categoryList.html());
        for (Element category : categoryList) {
        	System.out.print("\n\t");
        	String path = category.attr("abs:href").toString();
        	System.out.print("路径："+path+"\n\t");
        	String logo = category.getElementsByTag("img").first().attr("src");
        	System.out.print("logo："+logo+"\n\t");
        	String name = category.getElementsByTag("span").first().text();
        	System.out.print("名称："+name+"\n");
        }
        System.out.println("-----------------------------------------------------------------------------");
        Elements shopList = doc.select("div.shoplist > ul > li > a"); 
        //System.out.println(shopList.html());
        for (Element shopInfo : shopList) {
        	System.out.print("\n\t");
        	String sid = shopInfo.attr("href").toString();
        	System.out.print("地址："+makeShopUrl(sid)+"\n\t");
        	String pic = shopInfo.select("img.shop_pic").first().attr("src");
        	System.out.print("主图："+pic+"\n\t");
        	String level = shopInfo.select("img.seller_credit").first().attr("src");
        	System.out.print("等级："+level+"\n\t");
        	String title = shopInfo.select("span.shop_title").first().text();
        	System.out.print("名称："+title+"\n\t");
        	String location = shopInfo.select("span.shop_location").first().text();
        	System.out.print(location+"\n");
		}
		
	}
	
	public static void tmall() throws IOException {
		Document doc = Jsoup.connect("http://shop68966303.taobao.com").timeout(3000).get();
		//System.out.println(doc.html());
		Element shopDesc = doc.select("textarea.ks-datalazyload").first();
		//System.out.println(shopDesc.html());
		String shopInfo = StringEscapeUtils.unescapeHtml(shopDesc.html());
		/*
		 * 假如你可以让用户输入HTML内容，那么要小心避免跨站脚本攻击。
		 * 利用基于 Whitelist 的清除器和 clean(bodyHtml,whitelist)清除输入的恶意内容。
		 */
		//Document shop = Jsoup.parse(shopInfo);
		/*Cleaner cleaner = new Cleaner(Whitelist.basic());
		System.out.println(cleaner.clean(shop));*/
		Document shop = Jsoup.parseBodyFragment(shopInfo);
		
		Elements elements = shop.select("div.extend > ul > li");
		for (Element element : elements) {
			// System.out.println(element.html());
			/*Element key = element.getElementsByTag("label").first();
			Element value = element.getElementsByTag("div").first();
			System.out.println(key.html() + " \t " + value.html());*/
			String clazz = element.attr("class");
			Element value = null;
			if("shopkeeper".equals(clazz)){
				value = element.select("div > a").first();
			}else if("locus".equals(clazz)){
				value = element.getElementsByTag("div").first();
			}else{
				String label = element.getElementsByTag("label").first().text();
				if(label!= null && label.contains("公 司 名")){
					value = element.getElementsByTag("div").first();
				}
			}
			if(value != null){
				Element key = element.getElementsByTag("label").first();
				System.out.println(key.text() +"\t"+ value.text());
			}
		}
		
		/*Element element = shop.select("div.extend > ul").first();
		Element nick = element.select("li.shopkeeper > div > a").first();
		Element locus = element.select("li.locus > div").first();
		System.out.println(nick.text() + " \t " + locus.text());*/
	}
	
	public static void taobao() throws IOException {
		// http://amos.alicdn.com/getcid.aw?spm=a1z10.1-c.0.0.TMQgeY&v=3&site=cntaobao&groupid=0&s=1&fromid=cntaobao&uid=健康联盟68
		Document doc = Jsoup.connect("http://shop34398981.taobao.com").timeout(3000).get();
		Element shopDesc = doc.select("span.seller").first();
		Element nick = shopDesc.getElementsByTag("a").first();
		System.out.println(nick.text());
	}
	
	// http://s.taobao.com/search?app=shopsearch&q=健康联盟68
	public static void main(String[] args) throws IOException {
		fetchShopList();
    }  
	
}
