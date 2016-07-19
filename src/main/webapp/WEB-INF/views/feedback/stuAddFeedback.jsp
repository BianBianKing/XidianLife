<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>西电小蘑菇</title>
<link href="p/images/xd_logo.png" rel="shortcut icon"/>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="p/css/app.v2.css" type="text/css" />
<link rel="stylesheet" href="p/js/calendar/bootstrap_calendar.css"
	type="text/css" cache="false" />
<script src="p/js/app.v2.js"></script>
<script src="p/resources/js/jquery.validate.min.js"></script>
<!-- MetisMenu CSS -->
<link href="p/resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="p/resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="p/resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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
		<h1 class="myhead">意见反馈</h1>
	</header>
	<div class="row" style="margin:0px;margin-top:-20px">
	    <div class="col-lg-12">
	        <h3 class="page-header">您的意见<img src="p/images/xd_logo.png" class="m-r-sm pull-right"></h3>
	    </div>
	</div>
	<div class="row" style="margin:5px">
	<form id="one" action="addFeedback" method="post"> 
		<div class="form-group">
			<textarea class="form-control" name="content" placeholder="请输入您对小蘑菇的意见" rows="4"></textarea>
		</div>
  		<br>
  		<button type = "submit" class="btn btn-s-md btn-primary pull-right">提交意见</button>
	  	<!--弹出显示框-->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <br><br><br><br><br>
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                <h3 class="modal-title" id="myModalLabel">提示</h3>
		            </div>
		            <div class="modal-body">
		           		<div id="result"></div>
		            </div>
		            <div class="modal-footer" id="jump">
		            	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </div>
		        <!-- /.modal-content -->
		    </div>
		</div>
	</form>
	</div>
	<script>
	if('${status}' == "success") {
		txtHtml="<p class='text-warning'>" + "${info}" + "</p>";
    	$("#result").html(txtHtml);
		$("#myModal").modal("show");
	}
	$().ready(function() {
		$("#one").validate({
			rules: {
				content: {
					required: true,
					minlength:6
				},
			},
			messages: {
				content: {
					required: "请输入您的意见",
					minlength: "最小长度为6"
				},
			},
			
		});
	});
	</script>
</body>
</html>
