<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>西电小蘑菇</title>
<link href="resources/images/xd_logo.png" rel="shortcut icon"/>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="resources/css/app.v2.css" type="text/css" />
<link rel="stylesheet" href="resources/js/calendar/bootstrap_calendar.css"
	type="text/css" cache="false" />
<script src="resources/js/app.v2.js"></script>
<script src="resources/resources/js/jquery.validate.min.js"></script>
<!-- MetisMenu CSS -->
<link href="resources/resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="resources/resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="resources/resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<style  type="text/css">
	.myhead
	{
		height: 44px;
		text-align:center;
		font-size:18px;
		line-height: 44px;
		font-family: "Microsoft Yahei",Arial;
		font-weight: lighter;
		letter-spacing: 0;
		color: #FFFFFF;
		margin-top: 0px;
		background-color: #099fde;
		font-color:#000000
	}
</style>
</head>
<body>
	<header>
		<h1 class="myhead">最新公告</h1>
	</header>
	<br>
	<div class="row" style="margin:5px;font-size:16px">
		<c:forEach items="${list }" var="node" varStatus="s">
		<div>
		<c:if test='${node.type eq "Courses"}'>
		<a href="stuDetailCourses.htm?id=${node.id }"><c:out value="${node.title }"></c:out></a>
		</c:if> 
		<c:if test='${node.type eq "Competition"}'>
		<a href="stuDetailCompetition.htm?id=${node.id }"><c:out value="${node.title }"></c:out></a>
		</c:if> 
		<c:if test='${node.type eq "Others"}'>
		<a href="stuDetailOthers.htm?id=${node.id }"><c:out value="${node.title }"></c:out></a>
		
		</c:if> 
		</div>
		<div style="margin-top:2px;font-size:10px;color:#9AA0B1">
		<small class="pull-right"><c:out value="${node.date }"></c:out></small>
		
		</div>
		<hr>
		</c:forEach>
	</div>
</body>
</html>
