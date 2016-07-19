<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="../resources/js/calendar/bootstrap_calendar.css"
	type="text/css" cache="false" />
<script src="../resources/js/app.v2.js"></script>
<script src="../resources/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../resources/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="../resources/resources/ueditor/ueditor.all.js"></script>
</head>
<body>
	<%@ include file="../navHead.jsp" %>
	<div class="row">
	    <div class="col-lg-12">
	        <h1 class="page-header">编辑课表</h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<br>
	<form action="editTimetable.action?timetable_id=${timetable.id }" method="POST" enctype="multipart/form-data">
	 <div class="panel panel-info">
            <!-- /.panel-heading -->
            <div class="panel-body" id="panelBody">
	            <div class="form-group">
	            	<span>学期：${timetable.semester.semester_name }</span>
	            	<span>学院：${timetable.classtable.college.college_name } </span>
	            	<span>班级：${timetable.classtable.class_name }</span>
					<c:if test="${timetable.photo != null && timetable.photo != ''}">
						<img src="<c:url value='${timetable.photo }'/>" style="width:100%">
						
					</c:if>
					<c:if test="${timetable.photo == null || timetable.photo == ''}">
						图片为空
					</c:if>
					<input class="form-control {required:true,,messages:{required:'请选择照片'}" id="photo" name="photo" type="file"/>
					
					<c:if test="${timetable.pdf != null && timetable.pdf != ''}">
						<a href="<c:url value="${timetable.pdf }"/>">点击下载PDF</a>
					</c:if>
					<c:if test="${timetable.photo == null || timetable.pdf == ''}">
						PDF为空
					</c:if>
					<input class="form-control" id="pdf" name="pdf" type="file"/>
				
				</div>
			</div>
	</div>
	<div class="col-lg-10">
	<input type="submit" class="btn btn-large btn-success"  value="提交">
	</div>
	</form>
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
	            <div class="modal-footer" id="jump">
	            </div>
	        </div>
	        <!-- /.modal-content -->
	    </div>
	</div>

	
	<%@ include file="../navTail.jsp" %>
</body>
</html>