<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>refresh</title>
</head>
<body>
	<p align="center">刷新授权码</p>
	<form action="<%=path%>/oauth/refreshToken.do" enctype="application/x-www-form-urlencoded" method="post">
	 <input name="refreshToken" maxlength="50" type="text" width="50"/>
	 <input type="submit" />
	</form>

</body>
</html>