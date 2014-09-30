package com.shomop.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class AllInterceptor implements WebRequestInterceptor {

	@Override
	public void preHandle(WebRequest request) throws Exception {
		 System.out.println("AllInterceptor...............................");  
	     request.setAttribute("webInfo", "you are be traced", WebRequest.SCOPE_SESSION);
	     
	     
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		 
		
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex)
			throws Exception {
		 
		
		
	}

}
