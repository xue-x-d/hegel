<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jstl/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>business exception</title>
</head>
<body>
   <h3>业务逻辑出错。。。</h3>
   ${errorInfo}<br />
   <c:out value="&lt测试jstl是否可用取消jstl单独的pom配置（使用转义字符）&gt" escapeXml="false" default="默认值"></c:out>
</body>
</html>