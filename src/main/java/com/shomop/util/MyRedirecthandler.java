package com.shomop.util;


import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class MyRedirecthandler extends DefaultRedirectStrategy {


	@Override
	public boolean isRedirected(HttpRequest request, HttpResponse response,
			HttpContext context) throws ProtocolException {
		Header[] headers = request.getHeaders("Host");
		for (Header header : headers) {
			if(header.getValue().equals("www.hgdpjx.com")){
				HttpEntity entity = response.getEntity();
				try {
					String body = EntityUtils.toString(entity);
					int first = body.indexOf("'")+1;
					int last = body.lastIndexOf("'");
					response.setHeader("location",body.substring(first,last));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return super.isRedirected(request,response,context);
	}


}