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
	        <h2 class="page-header">修改校车时间</h2>
	    </div>
	</div>
	<br>
	<div class="panel panel-info">
       	<div class="panel-body" id="panelBody">
        	<div class="form-group">
				<input class="form-control" id="title" name="title" placeholder="请输入标题" />
			</div>
			<script id="container" name="content" type="text/plain"></script>
		</div>
	</div>
	<div class="col-lg-10">
		<button type="button" class="btn btn-large btn-success" onclick="get()">提交</button>
	</div>
	<br>
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
	    </div>
	</div>
	<script type="text/javascript">
		var ue = UE.getEditor('container');
		var json = {};
		$("#container").width($("#panelBody").width());
		function get() {
			console.warn(ue.getContent());
			json.content = ue.getContent();
			json.title = document.getElementById("title").value;
			var content = ue.getContent();
			$.ajax({ 
				type: "POST", 
				url: "modifBus.action?id=${id}", 
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
		ue.ready(function() {
		    ue.setContent('${content}');
		});
		$("#title").val('${title}');
	</script>
	<%@ include file="../navTail.jsp" %>
</body>
</html>