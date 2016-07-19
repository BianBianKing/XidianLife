<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>西电小蘑菇</title>
<link href="../resources/images/xd_logo.png" rel="shortcut icon"/>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="../resources/css/app.v2.css" type="text/css" />
<link rel="stylesheet" href="../resources/css/font.css" type="text/css" cache="false" />
<link rel="stylesheet" href="../resources/js/calendar/bootstrap_calendar.css"
	type="text/css" cache="false" />
<script src="../resources/js/app.v2.js"></script>
</head>
<body>
	<%@ include file="navHead.jsp" %>
	<div class="row">
	    <div class="col-lg-12">
	        <h1 class="page-header">概览</h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-3 col-md-6">
	        <a href="coursesList.htm">
	        <div class="panel panel-primary">
	            <div class="panel-heading">
	                <div class="row">
	                    <div class="col-xs-3">
	                        <i class="fa fa-comments fa-5x"></i>
	                    </div>
	                    <div class="col-xs-9 text-right">
	                        <div class="huge">选课通知</div>
	                    </div>
	                </div>
	            </div>
	                <div class="panel-footer">
	                    <span class="pull-left">查看详情</span>
	                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                    <div class="clearfix"></div>
	                </div>
	        </div>
	        </a>
	    </div>
	    <div class="col-lg-3 col-md-6">
	        <a href="competitionList.htm">
	        <div class="panel panel-red">
	            <div class="panel-heading">
	                <div class="row">
	                    <div class="col-xs-3">
	                        <i class="fa fa-caret-square-o-right fa-5x"></i>
	                    </div>
	                    <div class="col-xs-9 text-right">
	                        <div class="huge">竞赛公告</div>
	                    </div>
	                </div>
	            </div>
	            <div class="panel-footer">
	                <span class="pull-left">查看详情</span>
	                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                <div class="clearfix"></div>
	            </div>
	        </div>
	        </a>
	    </div>
	    
	    <div class="col-lg-3 col-md-6">
	        <a href="busList.htm">
	        <div class="panel panel-yellow">
	            <div class="panel-heading">
	                <div class="row">
	                    <div class="col-xs-3">
	                        <i class="fa fa-list-alt fa-5x"></i>
	                    </div>
	                    <div class="col-xs-9 text-right">
	                        <div class="huge">校车时间</div>
	                    </div>
	                </div>
	            </div>
	                <div class="panel-footer">
	                    <span class="pull-left">查看详情</span>
	                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                    <div class="clearfix"></div>
	                </div>
	        </div>
	        </a>
	    </div>
	    
	    <div class="col-lg-3 col-md-6">
	        <a href="cardsList.htm">
	        <div class="panel panel-green">
	            <div class="panel-heading">
	                <div class="row">
	                    <div class="col-xs-3">
	                        <i class="fa fa-tasks fa-5x"></i>
	                    </div>
	                    <div class="col-xs-9 text-right">
	                        <div class="huge">一卡通</div>
	                    </div>
	                </div>
	            </div>
	                <div class="panel-footer">
	                    <span class="pull-left">查看详情</span>
	                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                    <div class="clearfix"></div>
	                </div>
	        </div>
	        </a>
	    </div>
	</div>
	<!--弹出显示框-->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">提示</h4>
	            </div>
	            <div class="modal-body">
	           		<div id="result"></div>
	            </div>
	            <div class="modal-footer">
	            	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
	        </div>
	        <!-- /.modal-content -->
	    </div>
	</div>
	<%@ include file="navTail.jsp" %>
</body>
<script>
	if('${status}' == "success") {
		txtHtml="<p class='text-warning'>" + "${info}" + "</p>";
    	$("#result").html(txtHtml);
		$("#myModal").modal("show");
	}
</script>
</html>