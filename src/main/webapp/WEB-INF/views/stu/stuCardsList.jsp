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
	.flight-btnsrh {
	  width: 100%;
	  height: 44px;
	  line-height: 44px;
	  font-size: 18px;
	  color: #fff;
	  background: #ff7d13;
	  border-radius: 3px;
	  margin: 30px 0 15px;
	}
	button{
	font: 400 14px/1.5 "Hiragino Sans GB","Microsoft YaHei",hei,Arial,"Lucida Grande",Verdana;
	border: 0;
	}
	ul, ol {
	  padding-left: 0;
	  list-style-type: none;
	}
	.h{
		text-align:center;
		font-size:20px;
		border-bottom: 1px solid #d9dadc;
		padding-bottom: 10px;
	}
	span{
	color: #8d8d8d;
	}
	li{
	margin-bottom: 5px;
	list-style-type:none;
	}
	.label{
	
		color: #000000;
		font-size:20px;
	}
	h4{
		border-bottom: 1px solid #d9dadc;
		padding-bottom: 15px;
	}
</style>
</head>
<body>
	<header style="font-color:#000000">
		<h1 class="myhead">一卡通中心</h1>
	</header>
	
        <div class="panel-body" style="margin:-10px"> 
            <form id="one" action="stuSearchCards" method="post">  
            	<div class="input-group m-b"> 
					<div class="input-group-btn"> 
						<button id="label" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">学号 <span class="caret"></span>
						</button> 
						<ul class="dropdown-menu"> 
						<li><a onclick="num()">学号</a></li> 
						<li><a onclick="lostname()">姓名</a></li> 
						</ul> 
					</div><!-- /btn-group --> 
					<input type="text" id="input" class="form-control" name="num" placeholder="请输入失主学号"> 
				</div>
				
                <img src="resources/images/xd_logo.png" class="m-r-sm pull-left">
                <a href="stuCardsList.htm" class="btn btn-info pull-right">全    部</a> 
                <button type="submit" class="btn btn-success pull-right">搜    索</button>
            </form> 
        </div> 
  
	<div class="row" style="margin:5px">
	<table class="table table-striped table-bordered">
		<thead>
		    <tr>
		        <!-- th>#</th-->
		        <th>失主学号</th>
		        <th>失主姓名</th>
		        <th>联系人姓名</th>
		        <th>联系方式</th>
		    </tr>
		</thead>
		<tbody>
			<c:forEach items="${cardsList }" var="node" varStatus="s">
				<tr>
				<!-- td><c:out value="${s.index+1 }"></c:out></td-->
				<td><c:out value="${node.lostNum }"></c:out></td>  
				<td><c:out value="${node.lostName }"></c:out></td>  
				<td><c:out value="${node.keeperName }"></c:out></td> 
				<td><c:out value="${node.keeperPhone }"></c:out></td> 
				
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
</body>
<script>
	function num() {
		document.getElementById("label").innerHTML = '学号 <span class="caret"></span>';
		document.getElementById("input").name = "num";
		document.getElementById("input").placeholder = "请输入失主学号";
	}
	function lostname() {
		document.getElementById("label").innerHTML = '姓名 <span class="caret"></span>';
		document.getElementById("input").name = "name";
		document.getElementById("input").placeholder = "请输入失主姓名";
	}
</script>
</html>
