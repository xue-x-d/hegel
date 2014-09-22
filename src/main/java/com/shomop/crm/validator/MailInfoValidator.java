package com.shomop.crm.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.shomop.util.mail.MailInfo;

public class MailInfoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(MailInfo.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "fromAddress", "email.fromAddress.required", "邮箱不能为空");
		ValidationUtils.rejectIfEmpty(errors, "content", "email.content.required", "邮件内容不能为空");
		MailInfo mailInfo = (MailInfo)target;
		int index = mailInfo.getFromAddress().indexOf("@");
		if(index == -1){
			errors.rejectValue("email", "email.required", "邮箱格式错误");
		}
		if (StringUtils.isNotBlank(mailInfo.getServerHost())
				&& StringUtils.isBlank(mailInfo.getServerPort())) {
			errors.rejectValue("serverPort", "email.serverPort", "邮件服务器主机、端口必须同时存在");
		}
	}

}
