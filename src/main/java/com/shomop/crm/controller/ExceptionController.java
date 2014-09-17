package com.shomop.crm.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shomop.exception.BaseException;
import com.shomop.exception.BusinessException;

@Controller
public class ExceptionController {

	/** 
     * 用于处理异常的 
     * @return 
     */  
    //@ExceptionHandler({BaseException.class})  
    public String exception(BaseException e,ModelMap model) {  
        System.out.println(e.getMessage());  
        model.addAttribute("errorInfo",e.getMessage());
        return "exception";
    }  
     
    /**
     * http://localhost:8080/jd-service/test.do?method=testExc
     */
	@RequestMapping(params={"method=testExc"} ,value = "test", method = RequestMethod.GET)
    public void test() {  
        throw new BaseException(BaseException.ERROR_SYSTEM,"出错了！");  
    }
	
	
	@RequestMapping(value = "/listExc", method = RequestMethod.GET)
	public void controller(@RequestParam Integer id) throws Exception {
		switch (id) {
		case 1:
			throw new BaseException(1000,"system Exception");
		case 2:
			throw new BusinessException("Business Exception");
		default:
			throw new IllegalArgumentException("Unchecked Exception");
		}
	}
	
	@RequestMapping(value = "/ajaxExc", headers = "content-type=application/json", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getjson() throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("content", "123");
			map.put("result", true);
			map.put("account", 1);
			throw new Exception();
		} catch (Exception e) {
			throw new BusinessException("detail of ajax exception information");
		}
	}
	
	public static void main(String[] args) {
		
		
		
		
		
		
		
		
		
		
		
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.put("1","a");
		map.put("3","c");
		map.put("2","b");
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			System.out.println(key + "=" + map.get(key));
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
