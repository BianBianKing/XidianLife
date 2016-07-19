<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>西电小蘑菇</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">

	<style type="text/css">
	header{
		position:absolute;
		top:0px;
		padding:10px 0;
		background-color:#079DFB;
		display:block;
		width:100%;
		text-align:center;
		color:white;
		font-size:20px;
	}
	.container{
		position:relative;
		top:70px;
	}
	#refresh{
		position:relative;
		bottom:10px;
		text-align:center;
	}
	#stu>label{
		color:#9700FF;
	}
	.das{
		color:#9700FF;
		border:0px;
	}
	footer{
		position:fixed;
		bottom:0px;
		padding:10px 0;
		background-color:#F3F3F3;
		display:block;
		width:100%;
		text-align:center;
	}
	</style>
  </head>
  
  <body>
  <div class="wrapper">
	    <header>
			<strong>阳光长跑</strong>
		</header>
		<div class="container">
			<div class="row">
				<div class="col-md-12" id="stu">
					<label>欢迎你：${runningUserInfo.name } </label>
			        <div onclick="window.location='queryRunningOut'" class="pull-right">
				        <button type="button" class="btn btn-default das" aria-label="Left Align">
							<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
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
				<div class="col-md-2">
					<div class="thumbnail">
						<div class="lead">
							<label><img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">个人信息</label>
						</div>
						<div>
							<label>${runningUserInfo.name }</label>
						</div>
						<div>
							<label>${runningUserInfo.number }</label>
						</div>
						<div>
							<label>${runningUserInfo.sex }</label>
						</div>
					</div>
	
					<div class="thumbnail">
						<div class="lead">
							<label><img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">课程信息</label>
						</div>
						<div>
							<label></label>
						</div>
						<div>
							<label></label>
						</div>
						<div>
							<label></label>
						</div>
						<div>
							<label></label>
						</div>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="thumbnail">
						<div class="lead">
							<label><img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">长跑成绩汇总</label>
						</div>
						<div class="lead">
							<table class="table table-striped">
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
					<div class="thumbnail">
						<div class="lead">
							<label><img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">成绩认定标准</label>
						</div>
						<div class="lead">
							<table class="table table-striped">
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
					<div class="thumbnail">
						<div class="lead">
							<label><img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">分数情况</label>
						</div>
						<div class="lead">
							<table class="table table-striped">
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
	
				<div class="col-md-3">
					
					
				</div>
				<!-- ./ third col -->
				<div class="col-md-6">
					<div class="thumbnail">
						<div class="lead">
							<label>长跑成绩</label>
						</div>
						<div class="lead">
							<table class="table table-striped">
								<tr>
									<td>#</td>
									<td>日期</td>
									<td>时段</td>
									<td>里程</td>
									<td>平均速度</td>
									<td>是否有效</td>
									<td>备注</td>
								</tr>
								<c:forEach items="${infoList }" var="info">
								<tr>
									<td>${info.num}</td>
									<td>${info.date }</td>
									<td>${info.time}</td>
									<td>${info.distance}</td>
									<td>${info.speed}</td>
									<td>${info.ok}</td>
									<td>${info.info}</td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</div>
					<!-- ./ score information -->
				</div>
				<!-- ./ third col -->
			</div>
			<!-- end of row -->
		</div>
		<!-- end of container -->
		
		<div class="push">
			<!-- // -->
		</div>
	</div>
	<!-- end of wrapper -->
	
	<div class="panel-footer text-center" style="position:fixed;bottom:0px;lift:0px;rigth:0px;width:100%">
		<small>本成绩查询由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
	</div>
	
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
