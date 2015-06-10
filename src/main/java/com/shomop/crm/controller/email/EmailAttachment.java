package com.shomop.crm.controller.email;

/**
 * SpringMVC中的文件上传
 * @see 第一步：由于SpringMVC使用的是commons-fileupload实现，故将其组件引入项目中
 * @see 这里用到的是commons-fileupload-1.2.2.jar和commons-io-2.0.1.jar
 * @see 第二步：在####-servlet.xml中配置MultipartResolver处理器。可在此加入对上传文件的属性限制
 * @see 第三步：在Controller的方法中添加MultipartFile参数。该参数用于接收表单中file组件的内容
 * @see 第四步：编写前台表单。注意enctype="multipart/form-data"以及<input type="file" name="****"/>
 */
public class EmailAttachment {

	
	
}
