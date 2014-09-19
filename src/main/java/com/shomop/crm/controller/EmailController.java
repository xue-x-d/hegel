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

@Controller()
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
        binder.setValidator(new MailInfoValidator());  
    }  
	
}
