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
	        <h1 class="page-header">添加课表</h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<br>
	<form action="addTimetable.action" method="POST" enctype="multipart/form-data">
	 <div class="panel panel-info">
            <!-- /.panel-heading -->
            <div class="panel-body" id="panelBody">
	            <div class="form-group">
	            
		            <select id="semester_id" name="semester_id" class="form-control">
		            	<c:forEach items="${semesterList }" var="node">
		            		<option value="${node.id }">${node.semester_name }</option>
		            	</c:forEach>
						
					</select>
		            <select id="college_id" name="college_id" class="form-control">
		            	<c:forEach items="${collegeList }" var="node">
		            		<option value="${node.id }">${node.college_name }</option>
		            	</c:forEach>
					</select>
					<select id="class_id" name="class_id" class="form-control">
		            	<c:forEach items="${classtableList }" var="node">
		            		<option value="${node.id }">${node.class_name }</option>
		            	</c:forEach>
					</select>
					<span id="timetableExistence"><c:if test="${timetableExistence == 1}">
						这个课表已经存在
					</c:if></span>
					<!-- <input type="text" name="class_id" /> -->
					<input class="form-control {required:true,,messages:{required:'请选择照片'}" id="photo" name="photo" type="file"/>
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
<script>
	$("#semester_id").change(function(){
		var semester = $("#semester_id").find("option").length;
		var college = $("#college_id").find("option").length;
		var theclass = $("#class_id").find("option").length;
		if((semester==0)||(college==0)||(theclass==0)){
			$("#timetableExistence").html("选项不能为空!");
		}else{
			var semesterId = $("#semester_id  option:selected").val();
			var classId = $("#class_id  option:selected").val();
			$.ajax({
				type:"get",
				url:"checkTimetableExistence.action",
				data:{
					semester_id : semesterId,
					classtable_id : classId
				},
				success:function(data){
					//alert(JSON.stringify(data));
					if(data.status=="1000"){
						$("#timetableExistence").html("");
					}
					else{
						$("#timetableExistence").html("这个课表已经存在");
					}
				}
			});
		}
	});
	
	$("#college_id").change(function(){
		var collegeId = $("#college_id  option:selected").val();
		$.ajax({
			type:"get",
			url:"../getClasstableByCollegeId",//应该不用这个了吧，只根据学院查找，自己改吧
			data:{
				college_id : collegeId
			},
			success:function(data){
				//alert(JSON.stringify(data));
				$("#class_id").empty();
				for(var i=0;i<data.ids.length;i++){
					$("#class_id").append("<option value="+data.ids[i]+">"+data.result[i]+"</option>");
				}
				var semester = $("#semester_id").find("option").length;
				var college = $("#college_id").find("option").length;
				var theclass = $("#class_id").find("option").length;
				if((semester==0)||(college==0)||(theclass==0)){
					$("#timetableExistence").html("选项不能为空!");
				}else{
					var semesterId = $("#semester_id  option:selected").val();
					var classId = $("#class_id  option:selected").val();
					$.ajax({
						type:"get",
						url:"checkTimetableExistence.action",
						data:{
							semester_id : semesterId,
							classtable_id : classId
						},
						success:function(data){
							//alert(JSON.stringify(data));
							if(data.status=="1000"){
								$("#timetableExistence").html("");
							}
							else{
								$("#timetableExistence").html("这个课表已经存在");
							}
						}
					});
				}
			}
		});
	});
	
	$("#class_id").change(function(){
		var semester = $("#semester_id").find("option").length;
		var college = $("#college_id").find("option").length;
		var theclass = $("#class_id").find("option").length;
		if((semester==0)||(college==0)||(theclass==0)){
			$("#timetableExistence").html("选项不能为空!");
		}else{
			var semesterId = $("#semester_id  option:selected").val();
			var classId = $("#class_id  option:selected").val();
			$.ajax({
				type:"get",
				url:"checkTimetableExistence.action",//应该不用这个了吧，只根据学院查找，自己改吧
				data:{
					semester_id : semesterId,
					classtable_id : classId
				},
				success:function(data){
					//alert(JSON.stringify(data));
					if(data.status=="1000"){
						$("#timetableExistence").html("");
					}
					else{
						$("#timetableExistence").html("这个课表已经存在");
					}
				}
			});
		}
	});
	
</script>
</html>