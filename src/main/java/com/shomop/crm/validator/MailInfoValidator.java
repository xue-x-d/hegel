package com.shomop.crm.validator;

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
		ValidationUtils.rejectIfEmpty(errors, "fromAddress", "user.fromAddress.required", "邮箱不能为空");
		ValidationUtils.rejectIfEmpty(errors, "content", "user.content.required", "邮件内容不能为空");
		MailInfo mailInfo = (MailInfo)target;
		int index = mailInfo.getFromAddress().indexOf("@");
		if(index == -1){
			errors.rejectValue("email", "user.email.invalid_email", "邮箱格式错误");
		}
	}

}
