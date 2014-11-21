<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jstl/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
   <h3>未知异常，服务器出错。</h3>
       ${errorInfo}<br />
       webInfo: ${webInfo}<br />
        <h4><c:out value="绝对路径引用的实例" /></h4>
        <c:catch var="error1">
		  <c:import url="http://www.baidu.com" />
        </c:catch>
        <c:out value="${error1}"></c:out> <hr />
</body>
</html>