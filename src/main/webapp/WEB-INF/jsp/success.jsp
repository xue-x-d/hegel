<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 ${requestScope.user.id} ${requestScope.user.username} request success, ${sessionScope.user.id} ${sessionScope.user.username} session success!<br />
 ${requestScope.refuser.id} ${requestScope.refuser.username} request success, ${sessionScope.refuser.id} ${sessionScope.refuser.username} session success!<br />
</body>
</html>