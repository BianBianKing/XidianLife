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
	<div class="row" style="margin:-10px;margin-top:-20px">
	    <div class="col-lg-12">
	        <h3 class="page-header">类型选择<img src="resources/images/xd_logo.png" class="m-r-sm pull-right"></h3>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	
	
		<div class="col-lg-3 col-md-6" style="margin:-10px">
	        <a href="stuCardsList.htm">
	        <div class="panel"style="background-color:#B4CDCD;color:#ffffff">
	            <div class="panel-heading">
	                <div class="row">
	                    <div class="col-xs-3">
	                        
	                        <i class="fa fa-minus-square-o fa-5x"></i>
	                    </div>
	                    
	                    <div class="col-xs-9 text-right">
	                        <h3>遗    失</h3>
	                    </div>
	                    
	                </div>
	            </div>
	                
	        </div>
	        </a>
	    </div>
	    <div class="col-lg-3 col-md-6" style="margin:-10px">
	        <a href="stuAddCards.htm">
	        <div class="panel " style="background-color:#CDB79E;color:#ffffff">
	            <div class="panel-heading">
	                <div class="row">
	                    <div class="col-xs-3">
	                    <i class="fa fa-plus-square-o fa-5x"></i>
	                                    
	                    </div>
	                    <div class="col-xs-9 text-right">
	                        <h3>拾    到</h3>
	                    </div>
	                </div>
	            </div>
	        </div>
	        </a>
	    </div>

	<div class="panel-footer text-center" style="position:fixed;bottom:0px;lift:0px;rigth:0px;width:100%">
		<small>本一卡通拾遗由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
	</div>
</body>
</html>
