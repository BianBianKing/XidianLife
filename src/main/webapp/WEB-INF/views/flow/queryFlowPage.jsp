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
		<h1 class="myhead">流量查询</h1>
	</header>
	<div class="row" style="margin:0px;margin-top:-30px;margin-bottom:-20px;">
	    <div class="col-lg-12">
	        <h4 class="page-header">欢迎您：${username }<div onclick="window.location='queryOut'" class=" pull-right"><i class="fa fa-fw fa-sign-out"></i></div></h4>
	    </div>
	</div>
	<div>
	</div>
	<section class="vbox">	
		<section>		
			<section id="content">
				<section class="vbox">
					<section class="scrollable padder">		
						<div class="row">
							<div class="col-lg-4">
								<div class="row" style="margin-left:-20px">
									<div class="panel-body text-center" style="font-size:18px;">
										<p>全部流量 ${allFlow }</p> <hr>
										<p>已使用 ${usedFlow }</p> <hr>
										<p>剩余流量 ${leftFlow }</p> <hr>
										<small class="text-muted block">
										<c:if test="${hours eq 0}">${minutes }分钟前更新</c:if>
										<c:if test="${hours ne 0}">${hours }小时${minutes }分钟前更新</c:if>
										</small> 
										<div class="inline">
											<div class="easypiechart" data-percent="${percentFlow }"
												data-line-width="16" data-loop="false" data-size="188">
												<span class="h2 step">${percentFlow }</span>%
												<div class="easypie-text">已使用</div>
											</div>
										</div>
										<br>
									
									</div>
									
								</div>
							</div>
							
							<!-- div class="col-lg-4">
								<div class="row" style="margin-left:-20px">
									<div class="panel-body text-center">
										<h4>已使用<small> ${used }</small></h4> 
										<h4>剩余流量<small> ${remainder }</small></h4> 
										<small class="text-muted block">2小时前更新</small> 
										<div class="inline">
											<div class="easypiechart" data-percent="75"
												data-line-width="6" data-loop="false" data-size="188">
												<span class="h2 step">75</span>%
												<div class="easypie-text">已使用</div>
											</div>
										</div>
										<div class="panel-footer">
										<small>本流量查询由西电小蘑菇独家提供</small>
										</div>
									</div>
								</div>
							</div-->
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
					</section>
				</section>
			</section>
		</section>
		<section style="height:50px;"></section>
	</section>
	<div class="panel-footer text-center" style="position:fixed;bottom:0px;lift:0px;rigth:0px;width:100%">
		<small>本流量查询由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
	</div>
	<script src="resources/js/app.v2.js"></script>
	<!-- Easy Pie Chart -->
	<script src="resources/js/charts/easypiechart/jquery.easy-pie-chart.js"
		cache="false"></script>
</body>
<script>
	document.getElementById("p1").style.marginBottom="0px";
	if('${status}' == "fail") {
		txtHtml="<p class='text-warning'>" + "${info}" + "</p>";
    	$("#result").html(txtHtml);
		$("#myModal").modal("show");
	}
</script>
</html>