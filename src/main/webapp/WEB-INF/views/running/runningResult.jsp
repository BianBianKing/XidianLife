<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en" class="app">
  <head>
    <meta charset="utf-8" /> 
    <base href="<%=basePath%>">
    <title>西电小蘑菇</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link rel="stylesheet" href="resources/css/app.v2.css" type="text/css" /> 
	<link rel="stylesheet" href="resources/css/bootstrap_calendar.css" type="text/css" cache="false" /> 
	<link rel="stylesheet" href="resources/css/running.css" type="text/css" /> 
  </head>
  
  <body>
  <p id="dayListP"> ${dayList}</p>
  <p id="dateListP" style="display:none;">${dateList }</p>
  <div class="wrapper">
	    <header id="titleHeader">
			<strong>阳光长跑</strong>
		</header>
		
		<div class="container">
			<div class="row">
				<div class="col-md-12" id="stu">
					<label>欢迎你：${runningUserInfo.name }</label>
			        <div onclick="window.location='queryRunningOut'" class="pull-right">
				        <button type="button" class="btn btn-default das" aria-label="Left Align">
							<span class="glyphicon glyphicon-log-out" aria-hidden="true" style="font-size: 15px;color:blue;"></span>
						</button>
					</div>
				</div>
			</div>
			<hr>
			<div class="row">
				<div class="col-md-12" id="refresh">
					<small>
					<c:if test="${hours eq 0}">${minutes }分钟前更新</c:if>
					<c:if test="${hours ne 0}">${hours }小时${minutes }分钟前更新</c:if>
					</small> 
				</div>
			</div>
			<div class="row">
				<div class="col-md-3">
					<div class="panel panel-info">
                        <div class="panel-heading">
                      	      长跑成绩汇总
                        </div>
                        <div class="panel-body">
							<table class="table table-hover">
								<tr>
									<td>总里程</td>
									<td>${runningUserInfo.distanceNum }</td>
								</tr>
								<tr>
									<td>平均速度</td>
									<td>${runningUserInfo.speed }</td>
								</tr>
								<tr>
									<td>有效次数</td>
									<td>${runningUserInfo.count }</td>
								</tr>
							</table>
                        </div>
                    </div>
					<div class="panel panel-info">
                        <div class="panel-heading">
                            	成绩认定标准
                        </div>
                        <div class="panel-body">
							<table class="table table-hover">
								<tr>
									<td>所在分组</td>
									<td>${runningUserInfo.group }</td>
								</tr>
								<tr>
									<td>最低速度</td>
									<td>${runningUserInfo.minSpeed }</td>
								</tr>
								<tr>
									<td>最低里程</td>
									<td>${runningUserInfo.maxSpeed }</td>
								</tr>
							</table>
                        </div>
                    </div>
					<div class="panel panel-info">
                        <div class="panel-heading">
                           	 分数情况
                        </div>
                        <div class="panel-body">
							<table class="table table-hover">
								<tr>
									<td>奖励加分</td>
									<td>${runningUserInfo.addNum }</td>
								</tr>
								<tr>
									<td>惩罚扣分</td>
									<td>${runningUserInfo.decNum }</td>
								</tr>
								<tr>
									<td>最终得分</td>
									<td>${runningUserInfo.finalNum }</td>
								</tr>
							</table>
                        </div>
                    </div>
					<!-- ./ score information -->
				</div>
	
				<div class="col-md-6">
					<div class="row Calendar">
						<section class="panel b-light"> 
							<header class="panel-heading bg-primary dker no-border" id="calendar-heading">
								<strong>Calendar</strong>
							</header>
							<div id="calendar" class="bg-primary m-l-n-xxs m-r-n-xxs"></div> 
						</section>
					</div>
					<!-- ./ score information --> 
				</div>
				<!-- ./ third col -->
	
			</div>
			<!-- end of row -->
			<div class="row temp"></div>
		</div>
		<!-- end of container -->
		
	</div>
	<!-- end of wrapper -->
	
	<div class="panel-footer text-center" style="position:fixed;bottom:0px;lift:0px;rigth:0px;width:100%">
		<small>阳光长跑查询由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m -r-sm" style="width:20px">
	</div>
	
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/app.v2.js"></script>
	<script src="resources/js/calendar/bootstrap_calendar.js" cache="false"></script> 
	<script src="resources/js/calendar/data.js" cache="false"></script> 
	
    <script>
		var src = window.location.href; 
		if(src.indexOf("index.jsp") > 0 ){
		    $('#nav1').css("color","#FDDD9B");
		}
		if(src.indexOf("achievements.jsp") > 0 ){
		    $('#nav2').css("color","#FDDD9B");
		}
		if(src.indexOf("password.jsp") > 0 ){
		    $('#nav3').css("color","#FDDD9B");
		}
		if(src.indexOf("board.jsp") > 0 ){
		    $('#nav4').css("color","#FDDD9B");
		}
	</script>
	<script>
		function highlight(){
			$('.navbar-toggle').css("background-color","#3879D9");
		};
		function lowlight(){
			$('.navbar-toggle').css("background-color","#079DFB");
		}
	</script>
  </body>
</html>
