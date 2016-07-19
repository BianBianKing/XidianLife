<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>西电小蘑菇真是吊</h1>
<table border="1">
<c:forEach items="${course }" var="node">
<tr>
	<c:forEach items="${node }" var="node2">
		<td>${node2 }</td>
	</c:forEach>
	<br/>
</tr>
</c:forEach>
</table>
</body>
</html>