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
</head>
<body>
	<%@ include file="../navHead.jsp" %>
	<div class="row">
	    <div class="col-lg-12">
	        <h1 class="page-header">本科课表信息</h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<div class="panel panel-default">
        <div class="panel-heading">
          课表信息
        </div>
        
        <!-- /.panel-heading -->
        <div class="panel-body">
        <c:if test="${level == 0 }">
			<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='timetableList.htm'">全部</button>
			<c:forEach items="${semesterList }" var="node">
				<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='timetableList.htm?semester_id=${node.id}'">${node.semester_name }</button>
			</c:forEach>
		</c:if>
		<c:if test="${level == 1 }">
			<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='timetableList.htm'">全部</button>
			<c:forEach items="${collegeList }" var="node">
				<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='timetableList.htm?semester_id=${semester_id }&college_id=${node.id}'">${node.college_name }</button>
			</c:forEach>
		</c:if>
		<c:if test="${level == 2 }">
			<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='timetableList.htm'">全部</button>
			<c:forEach items="${classtableList }" var="node">
				<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='timetableList.htm?semester_id=${semester_id }&college_id=${college_id}&class_id=${node.id }'">${node.class_name }</button>
			</c:forEach>
		</c:if>
		<c:if test="${level == 3 }">
			<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='timetableList.htm'">全部</button>
		</c:if>
		<a href="addTimetable.htm" class="btn btn-s-md btn-dark pull-right" style="margin-top:23px">添加课程表</a> 
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>学期名称</th>
                            <th>学院名称</th>
                            <th>班级名称</th>
                            <th>图片</th>
                            <th>PDF</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
	                    <c:forEach items="${timetableList }" var="node" varStatus="s">
				        	<tr>
			          	    <td><c:out value="${s.index+1 }"></c:out></td>
			          	    
					        <td><c:out value="${node.semester.semester_name }"></c:out></td>  
					        <td><c:out value="${node.classtable.college.college_name }"></c:out></td>  
					        <td><c:out value="${node.classtable.class_name }"></c:out></td>  
					        <td><c:out value="${node.photo }"></c:out></td>  
					        <td><c:out value="${node.pdf }"></c:out></td>  
					        <td>
					        	<a href="editTimetable.htm?timetable_id=${node.id }" class="btn btn-info btn-xs">
                                    <i class="fa fa-pencil"></i>修改
                                </a>
                                <a href="delTimetable.action?timetable_id=${node.id }&semester_id=${semester_id }&college_id=${college_id}&class_id=${class_id }" class="btn btn-danger btn-xs">
                                    <i class="fa fa-trash-o"></i>删除
                                </a>
                            </td>  
	                        </tr>
				        </c:forEach>
                    </tbody>
                </table>
				<div class="btn-toolbar">
					<div class="btn-group">
						<button type="button" class="btn btn-default">1</button>
						<button type="button" class="btn btn-default active">2</button>
						<button type="button" class="btn btn-default">3</button>
						<button type="button" class="btn btn-default">4</button>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-default">5</button>
						<button type="button" class="btn btn-default">6</button>
						<button type="button" class="btn btn-default">7</button>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-default">8</button>
					</div>
				</div>
			</div>
            <!-- /.table-responsive -->
        </div>
        <!-- /.panel-body -->
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
	<%@ include file="../navTail.jsp" %>
</body>
<script>
	if('${status}' == "success") {
		txtHtml="<p class='text-warning'>" + "${info}" + "</p>";
    	$("#result").html(txtHtml);
		$("#myModal").modal("show");
	}
</script>
</html>