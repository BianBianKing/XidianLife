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
	        <h1 class="page-header">添加学期</h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<br>
	 <div class="panel panel-info">
            <!-- /.panel-heading -->
            <div class="panel-body" id="panelBody">
	            <div class="form-group">
				<input class="form-control {required:true,,messages:{required:'请输入标题'}" id="semester_name" name="semester_name" placeholder="请输入学期名称" />
				</div>
			</div>
	</div>
	<div class="col-lg-10">
	<button type="button" class="btn btn-large btn-success" onclick="get()">提交</button>
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
	            <div class="modal-footer" id="jump">
	            </div>
	        </div>
	        <!-- /.modal-content -->
	    </div>
	</div>

	
	<script type="text/javascript">
	var json = {};
	function get() {
		json.semester_name = document.getElementById("semester_name").value;
		$.ajax({ 
			type: "POST", 
			url: "addSemester.action", 
			data: {"content":JSON.stringify(json)}, 
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
	
		
</script>
	<%@ include file="../navTail.jsp" %>
</body>
</html>