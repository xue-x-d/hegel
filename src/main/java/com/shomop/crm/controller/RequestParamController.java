package com.shomop.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.shomop.crm.model.User;

@Controller()
@RequestMapping(value="/req")
public class RequestParamController {

	//REST 风格 只能是get方式
	@RequestMapping("/user/{id}")
	public void restRequest(@PathVariable("id") String userId,PrintWriter writer){
		writer.write(userId);
	}
	
	// header 中的信息
	@RequestMapping("/headerInfo")
	public void displayHeaderInfo(@RequestHeader("Accept-Encoding") String encoding,
	                              @RequestHeader("Keep-Alive") long keepAlive,PrintWriter writer)  {
			
		writer.write(encoding+" "+keepAlive);
	}
	
	/**
	 * A） 常用来处理简单类型的绑定，通过Request.getParameter() 获取的String可直接转换为简单类型的情况（
	 * String-->简单类型的转换操作由ConversionService配置的转换器来完成）；因为使用request.getParameter()
	 * 方式获取参数，所以可以处理get 方式中queryString的值，也可以处理post方式中 body data的值；
	 * B）用来处理Content-Type:application/x-www-form-urlencoded编码的内容，提交方式GET、POST
	 * C) 该注解有两个属性： value、required； value用来指定要传入值的id名称，required用来指示参数是否必须绑定；
	 * @param userId
	 * @param writer
	 */
	@RequestMapping(method = RequestMethod.POST)  
    public void setupForm(@RequestParam("id") String userId,PrintWriter writer) {  
       
		writer.write(userId);
    }
	
	/**
	 * @RequestBody
	 * 
	 *              该注解常用来处理Content-Type:
	 *              不是application/x-www-form-urlencoded编码的内容，例如application/json,application/xml等；
	 *              它是通过使用HandlerAdapter配置的HttpMessageConverters来解析post body data，绑定到相应的bean上的。
	 * 
	 *              <mvc:annotation-driven /> 默认没有配置FormHttpMessageConverter
	 *              如果配置了FormHttpMessageConverter 转换器，就可以处理 application/x-www-form-urlencoded的内容，
	 *              处理完的结果放在一个MultiValueMap<String,String>里，这种情况在某些特殊需求下使用，
	 *              {@link FormHttpMessageConverter} FormHttpMessageConverter
	 * @param body
	 * @param writer
	 * @throws IOException
	 * Nodejs 测试通过
	 * post中存在"?子句",Post方式会保留这个子句 
	 */
	@RequestMapping(value = "/ajax", method = RequestMethod.POST)  
	public void handle(@RequestBody User user,@RequestParam("userId") long userId2, Writer writer) throws IOException {
	  System.out.println(new Gson().toJson(user));
	  System.out.println("userId:,userId2:"+userId2);
	  writer.write(user.getId()+"——> success");
	}
	/**
	 * 
	 * @param user
	 * @return
	 * GET方式请求，数据放在body中 Nodejs测试通过
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)  
	public @ResponseBody User handleBean(@RequestBody User user){
	   System.out.println("request: "+new Gson().toJson(user));
	   user.setUsername("hello");
	   return user;
	}
	
	/**
	 * @ModelAttribute
	 * 
	 *                 该注解有两个用法，一个是用于方法上，一个是用于参数上；
	 *                 用于方法上时： 通常用来在处理@RequestMapping之前，为请求绑定需要从后台查询的model
	 *                 用于参数上时： 用来通过名称对应，把相应名称的值绑定到注解的参数bean上；要绑定的值来源于：
	 *                 A） @SessionAttributes 启用的attribute 对象上；
	 *                 B） @ModelAttribute 用于方法上时指定的model对象；
	 *                 C） 上述两种情况都没有时，new一个需要绑定的bean对象，
	 *                 然后把request中按名称对应的方式把值绑定到bean中。
	 * @param user
	 * @return
	 */
	// Add one attribute  
	// The return value of the method is added to the model under the name "user"  
	// You can customize the name via @ModelAttribute("myUser")  
	/*@ModelAttribute  
	public User getUser(@RequestParam(value="id") String userId) {
		System.out.println("getUser——> id: "+userId);
		User user = new User();
		user.setId("getUser");
	    return user;  
	} */ 
	
	
}
