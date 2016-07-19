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
		<h1 class="myhead">本科课表查询</h1>
	</header>
	<div class="row" style="margin:0px;margin-top:-20px">
	    <div class="col-lg-12">
	        <h3 class="page-header">请选择<img src="resources/images/xd_logo.png" class="m-r-sm pull-right"></h3>
	    </div>
	</div>
	
	<div class="row" style="padding-left:10px;padding-right:10px" >
		<div class="col-lg-4">
			<div class="row" style="margin-left:-20px">
				<div class="panel-body text-center">
					<form action="bachelorTimetableResult" role="form"  method="post" id="login"> 
						<div class="form-group"> 
							<select id="semester_id" name="semester_id" class="form-control">
			            	<c:forEach items="${semesterList }" var="node">
			            		<option value="${node.id }">${node.semester_name }</option>
			            	</c:forEach>
							</select>
						</div> 
						<div class="form-group"> 
							<select id="college_id" name="college_id" class="form-control">
			            		<c:forEach items="${collegeList }" var="node">
			            			<option value="${node.id }">${node.college_name }</option>
			            		</c:forEach>
							</select>
						</div> 
						<div class="form-group"> 
							<select id="class_id" name="class_id" class="form-control">
			            		<c:forEach items="${classtableList }" var="node">
			            			<option value="${node.id }">${node.class_name }</option>
			            		</c:forEach>
							</select>
						</div> 
						
						<button type="submit" class="btn btn-success  btn-s-md pull-right">查    询</button>
					</form> 
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
		<small>本科课表查询由西电小蘑菇独家提供</small><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:20px">
	</div>
	<script src="resources/js/app.v2.js"></script>
	<!-- Easy Pie Chart -->
	<script src="resources/js/charts/easypiechart/jquery.easy-pie-chart.js"
		cache="false"></script>
</body>
<script>
	if('${status}' == "fail") {
		txtHtml="<p class='text-warning'>" + "${info}" + "</p>";
    	$("#result").html(txtHtml);
		$("#myModal").modal("show");
	}
	
	$("#semester_id").change(function(){
		//alert($("#semester_id  option:selected").val());
		var semesterId = $("#semester_id  option:selected").val();
		$.ajax({
			type:"get",
			url:"getCollegeListBySemesterId",
			data:{
				semester_id : semesterId
			},
			success:function(data){
				//alert(JSON.stringify(data));
				var firstCollegeId = data.ids[0];
				$("#college_id").empty();
				for(var i=0;i<data.ids.length;i++){
					$("#college_id").append("<option value="+data.ids[i]+">"+data.result[i]+"</option>");
				}
				
				//获取第一个学院对应的班级添加到其中
				$.ajax({
					type:"get",
					url:"getClasstableBySemesterIdAndCollegeId",
					data:{
						semester_id : semesterId,
						college_id : firstCollegeId
					},
					success:function(data){
						//alert(JSON.stringify(data));
						$("#class_id").empty();
						for(var i=0;i<data.ids.length;i++){
							$("#class_id").append("<option value="+data.ids[i]+">"+data.result[i]+"</option>");
						}
						
					}
				});
			}
		});
		
		
	});
	
	
	$("#college_id").change(function(){
		var semesterId = $("#semester_id  option:selected").val();
		var collegeId = $("#college_id  option:selected").val();
		//alert(semesterId+" "+collegeId);
		$.ajax({
			type:"get",
			url:"getClasstableBySemesterIdAndCollegeId",
			data:{
				semester_id : semesterId,
				college_id : collegeId
			},
			success:function(data){
				//alert(JSON.stringify(data));
				$("#class_id").empty();
				for(var i=0;i<data.ids.length;i++){
					$("#class_id").append("<option value="+data.ids[i]+">"+data.result[i]+"</option>");
				}
				
			}
		});
		
	});
</script>
</html>