package com.shomop.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.shomop.crm.validator.MailInfoValidator;
import com.shomop.util.mail.MailInfo;
import com.shomop.util.mail.MailSender;

/**
 * SpringMVC中的文件上传
 * @see 第一步：由于SpringMVC使用的是commons-fileupload实现，故将其组件引入项目中
 * @see 这里用到的是commons-fileupload-1.2.2.jar和commons-io-2.0.1.jar
 * @see 第二步：在####-servlet.xml中配置MultipartResolver处理器。可在此加入对上传文件的属性限制
 * @see 第三步：在Controller的方法中添加MultipartFile参数。该参数用于接收表单中file组件的内容
 * @see 第四步：编写前台表单。注意enctype="multipart/form-data"以及<input type="file" name="****"/>
 */
@Controller
@RequestMapping("email")
public class EmailController {

	private static Gson gson = new Gson();
	
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public String sendEmail(@Validated MailInfo mailInfo,BindingResult result,ModelMap map){
		if (result.hasErrors()) {
	        return "failed";
	    }
		System.out.println("-----> "+gson.toJson(mailInfo));
		try {
			MailSender.sendMail(mailInfo);
			map.addAttribute("info","邮件发送成功");
		} catch (Exception e) {
			map.addAttribute("info","邮件发送失败");
			map.addAttribute("error",e.getMessage());
		}
		return "";
	}
	
	@InitBinder  
    protected void initBinder(WebDataBinder binder){
		System.out.println("init binder........");
        binder.setValidator(new MailInfoValidator());  
    }
	
	 
	
	
}
