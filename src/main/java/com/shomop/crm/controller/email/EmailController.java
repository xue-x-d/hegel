package com.shomop.crm.controller.email;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.shomop.crm.model.email.MailTask;
import com.shomop.crm.validator.MailInfoValidator;
import com.shomop.util.mail.MailSender;

/**
 * 发送邮件
 * @author spencer.xue
 * @date 2014-11-24
 */
@Controller
@RequestMapping("email")
public class EmailController {

	private static Gson gson = new Gson();
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public String sendEmail(@Validated MailTask mailTask,BindingResult result,Model map){
		if (result.hasErrors()) {
	        return "failed";
	    }
		System.out.println("-----> "+gson.toJson(mailTask));
		try {
			MailSender.sendMail(mailTask);
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
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(){
		
		return "sendMail";
	} 
	
	
}
