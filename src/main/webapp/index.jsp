<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*" %>
<%@ include file="/WEB-INF/jstl/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
</head>
<body>
	<h3>夏猫会员宝</h3>
	<div>
		客户机的IP地址是:<sp:viewIP />
		<sp:demo3>haha</sp:demo3>
		<sp:demo count="<%=new Date()%>">ee</sp:demo>
		<sp:demo2> 剩下JSP不执行</sp:demo2>
		<sp:demo1> 输出标签体内容</sp:demo1>
	</div>
</body>
</html>