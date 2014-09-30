<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jstl/taglib.jsp"%>
<%@ include file="/WEB-INF/jstl/path.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>oauth failed</title>
</head>
<body>
    <h2 align="center">授权失败</h2>
     <p align="justify" style="">
        <!-- 是不是应该更简单直接从服务器传递url -->
		<c:choose>
			<c:when test="${requestScope.version=='dd'}">
				<!--  <a title="点击重试" href="<%=path%>/oauth/index.do">重试</a>-->
				<c:set var="url" scope="request" value="oauth/index.do"/>
   			</c:when>
			<c:otherwise>
				<c:set var="url" scope="request" value="jd/index.do"/>
			 	<!--  <a title="点击重试" href="<%=path%>/jd/index.do">重试</a>-->
   			</c:otherwise>
		</c:choose>
		<c:out value="${requestScope.error}" default="" /> <br />
		<c:forEach items="${map}" var="entry">
			<c:out value="${entry.key}" /> 
			<c:out value="${entry.value}" /><br/>
		</c:forEach>
		<c:url context="/jd-service" value="/${url}" var="refreshUrl" />
		<a href="${refreshUrl}" title="重试">retry</a>
	 </p>
</body>
</html>