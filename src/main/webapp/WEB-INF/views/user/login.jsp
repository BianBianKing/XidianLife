<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en" class="bg-dark">
<head> 
	<meta charset="utf-8" /> 
<title>西电小蘑菇</title>
<link href="resources/images/xd_logo.png" rel="shortcut icon"/>
	<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" /> <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> 
	<link rel="stylesheet" href="resources/css/app.v2.css" type="text/css" /> 
	<link rel="stylesheet" href="resources/css/font.css" type="text/css" cache="false" />
	<script src="resources/js/app.v2.js"></script>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/jquery.validate.min.js"></script>
	<!-- Bootstrap Core CSS -->
    
	<!-- Bootstrap Core JavaScript -->
    <script src="resources/resources/bower_components/bootstraresources/dist/js/bootstrap.min.js"></script>
    <script src="resources/resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>
	
</head>
<body> 
	<section id="content" class="m-t-lg wrapper-md animated fadeInUp"> 
		<div class="container aside-xxl"> 
			<a class="navbar-brand block" href="#">西电小蘑菇</a> 
			
			<section class="panel panel-default bg-white m-t-lg"> 
				<header class="panel-heading text-center"> 
					<strong><img src="resources/images/xd_logo.png" class="text-center"></strong> 
				</header> 
				<form action="j_spring_security_check" id="login" class="panel-body wrapper-lg" method="POST"> 
					<div class="form-group"> 
						<label class="control-label">账号</label> 
						<input id="j_username" name="j_username" placeholder="请输入账号" class="form-control input-lg"> 
					</div> 
					<div class="form-group"> 
						<label class="control-label">密码</label> 
						<input type="password" name="j_password" id="j_password" placeholder="请输入密码" class="form-control input-lg"> 
					</div> 
					<h4 class="modal-title" id="myModalLabel">${error}</h4>
					<input id="_spring_security_remember_me" type="checkbox" name="_spring_security_remember_me" /><label>记住我一个月</label>
					<button type="submit" class="btn btn-lg btn-success btn-block">登录</button> 
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
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
	                    <!-- /.modal-dialog -->
	                </div>
	                <!-- /.modal -->
				</form> 
				<div class="line line-dashed"></div>
				<div class="btn btn-lg btn-block">
					<a href="bbf.htm" class="btn btn-s-md btn-facebook btn-rounded">注册</a>
				</div>
			</section> 
		</div> 
	</section> <!-- footer --> 
	<footer id="footer"> 
		<div class="text-center padder"> 
			<p> <small>BBK & BBF<br>&copy; 2015</small> </p> 
		</div> 
	</footer> <!-- / footer --> 
</body>
</html>