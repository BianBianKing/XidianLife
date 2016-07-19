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
		<h1 class="myhead">竞赛公告</h1>
	</header>
	
	<div class="row" style="margin:5px">
	<div class="row">
	    <div class="col-lg-12">
	        <h3 class="page-header">${competition.title }</h3>
	    </div>
	</div>
	<br>
		<div class="form-group">
			<div>${competition.content }</div>
		</div>
  		<br>
  		<button class="btn btn-block btn-primary pull-right" onclick="window.location='stuNewInfo.htm'">返回列表</button>
	</div>
	<br><br><br><br><br><br>
	<div class="panel-footer text-center" style="position:fixed;bottom:0px;lift:0px;rigth:0px;width:100%">
		<small>本竞赛公告由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm">
	</div>
	<script type="text/javascript">
  	var temp = document.getElementsByTagName("img")
  	for (var i = 0; i < temp.length-1; i++) {
  		if (temp[i].width > $(document).width())
  		temp[i].width = $(document).width();
  	}
  	
    </script>
</body>
</html>
