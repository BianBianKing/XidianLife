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
	<div class="row" style="margin:5px;font-size:14px">
		<small class="text-muted block text-center">
		<c:if test="${hours eq 0}">${minutes }分钟前更新</c:if>
		<c:if test="${hours ne 0}">${hours }小时${minutes }分钟前更新</c:if>
		<img style="height:30px" src="<c:out value="resources/images/reload.png" />" onclick="window.location='queryGrade.htm?refresh=true'" />
		</small> 
		<div class="btn-group btn-group-justified"> 
			<a id="one" onclick="select(one)" class="btn select">本学期</a> 
			<a id="two" onclick="select(two)" class="btn ">及格(全部)</a>
			<a id="three" onclick="select(three)" class="btn ">未及格</a>
		</div>
		<br>
		<div id="tableOne" style="display:">
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
		        <c:forEach items= "${gradeOne }" var="info" varStatus="s">
		        	<tr> 
		        		<td><c:out value="${info.courseName }"></c:out></td>
		        		<td><c:out value="${info.credit }"></c:out></td>
		        	 	<td><c:out value="${info.courseSort }"></c:out></td>
		        		<td><c:out value="${info.courseScore }"></c:out></td>
		        	</tr>
		        </c:forEach>
		        </tbody>
		    </table>
		</div>
		<div id="tableTwo" style="display:none">
			<c:forEach items="${gradeTwo }" var="node" varStatus="s">
			<div onclick="show(${s.index })" class=" pull-right">
	        	<i class="fa fa-fw fa-sort"></i>
	        </div>
				<div style="color:#669933">
				<b>
				<a onclick="show(${s.index })"><i class="fa fa-fw fa-star-o"></i><c:out value="${node.semesterName }"></c:out></a>
				</b>
				</div>
				<br>
				<table class="table table-striped table-bordered" id="table${s.index }" style="display:none">
				    <thead>
				        <tr>
				            <th>课程</th>
				            <th style="width:50px">学分</th>
				            <th style="width:50px">属性</th>
				            <th style="width:50px">成绩</th>
				        </tr>
				    </thead>
			        <tbody>
			        <c:forEach items= "${node.gradeInfos }" var="info" varStatus="s">
			        	<tr> 
			        		<td><c:out value="${info.courseName }"></c:out></td>
			        		<td><c:out value="${info.credit }"></c:out></td>
			        	 	<td><c:out value="${info.courseSort }"></c:out></td>
			        		<td><c:out value="${info.courseScore }"></c:out></td>
			        	</tr>
			        </c:forEach>
	
			         </tbody>
			    </table>
			</c:forEach>
		</div>
		<div id="tableThree" style="display:none">
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
			        <c:forEach items= "${gradeThree.failGradeInfos }" var="info" varStatus="s">
			        	<tr> 
			        		<td><c:out value="${info.courseName }"></c:out></td>
			        		<td><c:out value="${info.credit }"></c:out></td>
			        	 	<td><c:out value="${info.courseSort }"></c:out></td>
			        		<td><c:out value="${info.courseScore }"></c:out></td>
			        	</tr>
			        </c:forEach>
	
			         </tbody>
			    </table>
		</div>
	</div>
	<br><br><br><br><br><br><br>
	<div class="panel-footer text-center" style="position:fixed;bottom:0px;lift:0px;rigth:0px;width:100%">
		<small>本成绩查询由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
	</div>
	<script>
	function show(num) {
		var temp = document.getElementById("table"+num);
		if (temp.style.display == "none") {
			temp.style.display="";
		} else {
			temp.style.display="none";
		}
	}
	function select(type) {
		document.getElementById("one").className= "btn";
		document.getElementById("two").className= "btn";
		document.getElementById("three").className= "btn";
		if (type == one) {
			document.getElementById("one").className= "btn select";
			document.getElementById("tableOne").style.display="";
			document.getElementById("tableTwo").style.display="none";
			document.getElementById("tableThree").style.display="none";
		} else if (type == two) {
			document.getElementById("two").className= "btn select";
			document.getElementById("tableOne").style.display="none";
			document.getElementById("tableTwo").style.display="";
			document.getElementById("tableThree").style.display="none";
			
		} else {
			document.getElementById("three").className= "btn select";
			document.getElementById("tableOne").style.display="none";
			document.getElementById("tableTwo").style.display="none";
			document.getElementById("tableThree").style.display="";
		}
		
	}
	</script>
</body>
</html>
