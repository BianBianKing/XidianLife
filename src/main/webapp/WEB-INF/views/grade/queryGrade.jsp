<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<title>西电小蘑菇</title>
<link href="resources/images/xd_logo.png" rel="shortcut icon"/>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="resources/css/app.v2.css" type="text/css" />
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
	<header style="font-color:#000000">
		<h1 class="myhead">成绩查询</h1>
	</header>
	<div class="btn-group btn-group-justified" style="margin-top:-10px;"> 
		<a id="one" onclick="show(1)" class="btn select" style="color:#808285">本科</a> 
		<a id="two" onclick="show(2)" class="btn " style="color:#808285">研究生</a>
	</div>
	<div class="row" style="margin:0px;margin-top:-20px">
	    <div class="col-lg-12">
	        <h3 class="page-header">请登录<img src="resources/images/xd_logo.png" class="m-r-sm pull-right"></h3>
	    </div>
	</div>
		
	<div class="row" style="padding-left:10px;padding-right:10px" >
		<div class="col-lg-4">
			<div class="row" style="margin-left:-20px">
				<div class="panel-body text-center">
					<div id="form1" style="margin-top:-13px">
							<form  action="queryGrade" method="post" id="login"> 
								<div class="form-group"> 
									<input name="username" placeholder="请输入本科生账号" class="form-control input-lg"> 
								</div> 
								<div class="form-group"> 
									<input type="password" name="password" id="inputPassword" placeholder="请输入密码" class="form-control input-lg"> 
								</div> 
								<button type="submit" class="btn btn-success  btn-s-md pull-right">查    询</button>
							</form> 
					</div>
					<div id="form2" style="margin-top:-13px;display:none">
							<form action="queryMasterGrade" method="post" id="login"> 
								<div class="form-group"> 
									<input name="username" placeholder="请输入研究生账号" class="form-control input-lg"> 
								</div> 
								<div class="form-group"> 
									<input type="password" name="password" id="inputPassword" placeholder="请输入密码" class="form-control input-lg"> 
								</div> 
								<button type="submit" class="btn btn-success  btn-s-md pull-right">查    询</button>
							</form> 
						
					
					</div>
				</div>
			</div>
		</div>
		<!--弹出显示框-->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <br><br><br><br><br>
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                <h5 class="modal-title" id="myModalLabel">提示</h5>
		            </div>
		            <div class="modal-body">
		           		<h5><div id="result"></div></h5>
		            </div>
		            <div class="modal-footer">
		            	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </div>
		        <!-- /.modal-content -->
		    </div>
		</div>
	</div>
					
	<div class="panel-footer text-center" style="position:fixed;bottom:0px;lift:0px;rigth:0px;width:100%">
		<small>本成绩查询由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
	</div>
	<script src="resources/js/app.v2.js"></script>
	<!-- Easy Pie Chart -->
	<script src="resources/js/charts/easypiechart/jquery.easy-pie-chart.js"
		cache="false"></script>
</body>
<script>
function show(num) {
	var temp1 = document.getElementById("form1");
	var temp2 = document.getElementById("form2");
	var button1 = document.getElementById("one");
	var button2 = document.getElementById("two");
	if (num == "1") {
		temp1.style.display="";
		temp2.style.display="none";
		button1.className= "btn select";
		button2.className= "btn";
		button1.style.color="#329ECB";
		button2.style.color="#808285";
	} else {
		temp1.style.display="none";
		temp2.style.display="";
		button1.className= "btn";
		button2.className= "btn select";
		button1.style.color="#808285";
		button2.style.color="#329ECB";
	}
}
if('${status}' == "fail") {
	txtHtml="<p class='text-warning'>" + "${info}" + "</p>";
	$("#result").html(txtHtml);
	$("#myModal").modal("show");
}
</script>
</html>