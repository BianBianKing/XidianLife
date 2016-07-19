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
</style>
</head>
<body>
	<header>
		<h1 class="myhead">一卡通中心</h1>
	</header>
	<div class="row" style="margin:0px;margin-top:-20px">
	    <div class="col-lg-12">
	        <h3 class="page-header">信息录入<img src="resources/images/xd_logo.png" class="m-r-sm pull-right"></h3>
	    </div>
	</div>
	<div class="row" style="margin:5px">
	<form id="one" action="addCards" method="post"> 
		<div class="form-group">
			<input class="form-control" name="lostNum" placeholder="请输入失主学号" />
		</div>
		<div class="form-group">
			<input class="form-control" name="lostName" placeholder="请输入失主姓名" />
		</div>
		<div class="form-group">
			<input class="form-control" name="keeperName" placeholder="请输入联系人姓名" />
		</div>
		<div class="form-group">
			<input class="form-control" name="keeperPhone" placeholder="请输入联系方式" />
		</div>
  		<br>
  		<button type = "submit" class="btn btn-s-md btn-primary pull-right">提交信息</button>
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
		            </div>
		        </div>
		        <!-- /.modal-content -->
		    </div>
		</div>
	</form>
	</div>
	<script>
	$().ready(function() {
		// validate signup form on keyup and submit
		$("#one").validate({
			rules: {
				lostNum: {
					required: true,
					minlength:6
				},
				lostName: {
					required: true,
					minlength:2
				},
				keeperName: {
					required: true,
					minlength:2
				},
				keeperPhone: {
					required: true,
					minlength:7
				}
			},
			messages: {
				lostNum: {
					required: "请输入失主学号",
					minlength: "最小长度为6"
				},
				lostName: {
					required: "请输入失主姓名",
					minlength: "最小长度为2"
				},
				keeperName: {
					required: "请输入联系人姓名",
					minlength: "最小长度为2"
				},
				keeperPhone: {
					required: "请输入联系方式",
					minlength: "最小长度为7"
				}
			},
			submitHandler:function(form){
    		    var txtHtml;
    			$.ajax({
    				url:'addCards',
    				data:$('#one').serialize(),
    				type:"POST",
    				success:function(data){
    					if(data.status == "success"){
    						var temp = "'";
    						jump='<button type="button" class="btn btn-default" onclick="window.location='+temp+data.url+temp+'">关闭</button>';
    						$("#jump").html(jump);
    						txtHtml="<p class='text-warning'>" + data.info + "</p>";
    	                	$("#result").html(txtHtml);
        					$("#myModal").modal("show");
    	                }else{
    	                	txtHtml="<p class='text-warning'>" + data.info + "</p>";
    	                	jump="<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>";
    						$("#jump").html(jump);
    	                	$("#result").html(txtHtml);
        					$("#myModal").modal("show");
    	                }
    	            }
    			});
	        }
		});
	});
	</script>
</body>
</html>
