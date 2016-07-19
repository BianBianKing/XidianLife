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
	.select
	{
		border-bottom:4px solid #1491C5;
	}
</style>
</head>
<body>
	<header>
		<h3 class="myhead">查询结果</h3>
	</header>
	<div class="row" style="margin:0px;margin-top:-20px">
	    <div class="col-lg-12">
	        <h4 class="page-header" style="color:#9966ff">
	        	<b>
		        <c:out value="${userinfo }"></c:out>
		        </b>
		        <div onclick="window.location='queryGradeOut'" class=" pull-right">
		        <i class="fa fa-fw fa-sign-out"></i>
		        </div>
	        </h4>
	    </div>
	</div>
	<small class="text-muted block text-center">
	<c:if test="${hours eq 0}">${minutes }分钟前更新</c:if>
	<c:if test="${hours ne 0}">${hours }小时${minutes }分钟前更新</c:if>
	<img style="height:30px" src="<c:out value="resources/images/reload.png" />" onclick="window.location='queryGrade.htm?refresh=true'" />
	</small> 
	<section class="panel panel-default"> 
		<div class="list-group no-radius alt"> 
		<a class="list-group-item" href="#"> 
			<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
			额定总学分 :  
			<div class="pull-right"><c:out value="${grade.lowestTotalCredit }"></c:out></div>
		</a> 
		<a class="list-group-item" href="#"> 
			<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
			额定学位分 : 
			<div class="pull-right"><c:out value="${grade.lowestDegreeCredit }"></c:out></div>
		</a> 
		<a class="list-group-item" href="#"> 
			<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
			已完成学分 : 
			<div class="pull-right"><c:out value="${grade.hadCredit }"></c:out></div>
		</a> 
		<a class="list-group-item" href="#"> 
			<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
			已完成学位课学分 : 
			<div class="pull-right"><c:out value="${grade.hadDegreeCredit }"></c:out></div>
		</a> 
		<a class="list-group-item" href="#"> 
			<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
			学位课加权平均分 : 
			<div class="pull-right"><c:out value="${grade.averageScore }"></c:out></div>
		</a> 
		</div>
	</section>
	
	<div class="row" style="margin:5px;font-size:14px">
		<table class="table table-striped table-bordered">
	    <thead>
	        <tr>
	            <th>课程</th>
	            <th style="width:50px">学分</th>
	            <th style="width:50px">属性</th>
	            <th style="width:50px">成绩</th>
	        </tr>
	    </thead>
        <tbody>
        <c:forEach items= "${grade.courseInfos }" var="info" varStatus="s">
        	<tr> 
        		<td><c:out value="${info.courseName }"></c:out></td>
        		<td><c:out value="${info.courseCredit }"></c:out></td>
        	 	<td><c:out value="${info.courseSort }"></c:out></td>
        		<td><c:out value="${info.courseScore }"></c:out></td>
        	</tr>
        </c:forEach>

        </tbody>
    	</table>
		</div>
	<br><br><br><br>
	<div class="panel-footer text-center" style="position:fixed;bottom:0px;lift:0px;rigth:0px;width:100%">
		<small>本成绩查询由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
	</div>
</body>
</html>
