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
	<div class="row" style="margin-top:-30px">
	    <div class="col-lg-12">
	        <h2 class="page-header">一卡通管理</h2>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<section class="panel panel-default"> 
        <div class="panel-body"> 
            <form id="one" action="searchCards.action" method="post">  
                <div class="form-group col-lg-3"> 
                    <input type="text" class="form-control {required:true,,messages:{required:'请输入学号'}" name="num" placeholder="请输入学号"> 
                </div> 
                <a href="cardsList.htm" class="btn btn-s-md btn-dark pull-right col-lg-2">查看全部</a> 
                <button class="btn btn-s-md btn-dark  col-lg-1 " >搜   索</button>
            </form> 
        </div> 
    </section>
	<div class="panel panel-default">
        <div class="panel-heading">
            一卡通列表
        </div>
        <!-- /.panel-heading -->
        <div class="panel-body">

            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>失主学号</th>
                            <th>失主姓名</th>
                            <th>联系人姓名</th>
                            <th>联系方式</th>
                            <th>时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
	                    <c:forEach items="${cardsList }" var="node" varStatus="s">
				        	<tr>
			          	    <td><c:out value="${s.index+1 }"></c:out></td>
			          	    
					        <td><c:out value="${node.lostNum }"></c:out></td>  
					        <td><c:out value="${node.lostName }"></c:out></td>  
					        <td><c:out value="${node.keeperName }"></c:out></td> 
					        <td><c:out value="${node.keeperPhone }"></c:out></td> 
					         <td><c:out value="${node.date }"></c:out></td> 
					        <td>
                                <a href="deleteCards.action?id=${node.id }" class="btn btn-danger btn-xs">
                                    <i class="fa fa-trash-o"></i>删除
                                </a>
                            </td>  
	                        </tr>
				        </c:forEach>
                    </tbody>
                </table>
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