<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="bg-dark">
<head> 
	<meta charset="utf-8" /> 
<title>西电小蘑菇</title>
<link href="resources/images/xd_logo.png" rel="shortcut icon"/>
	<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" /> <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> 
	<link rel="stylesheet" href="resources/css/app.v2.css" type="text/css" /> 
	<link rel="stylesheet" href="resources/css/font.css" type="text/css" cache="false" /> <!--[if lt IE 9]> <script src="js/ie/html5shiv.js" cache="false"></script> <script src="js/ie/respond.min.js" cache="false"></script> <script src="js/ie/excanvas.js" cache="false"></script> <![endif]-->
	<script src="resources/resources/bower_components/jquery/dist/jquery.min.js"></script>
	<script src="resources/resources/js/jquery.validate.min.js"></script>
	
	<script src="resources/resources/bower_components/bootstraresources/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script 
		src="resources/resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>
		
	
</head>
<body> 
	<section id="content" class="m-t-lg wrapper-md animated fadeInUp"> 
		<div class="container aside-xxl"> 
			<a class="navbar-brand block" href="#">西电小蘑菇</a> 
			<section class="panel panel-default bg-white m-t-lg"> 
				<header class="panel-heading text-center"> 
					<strong><img src="resources/images/xd_logo.png" class="text-center"></strong> 
				</header>  
					<form class="panel-body wrapper-lg" role="form" action="register" method="post" id="register" >
                            <fieldset>
                            	<div class="form-group">
                                    <input class="form-control" placeholder="注册码（必填）" name="key" id="key"autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="账号（必填）" name="email" id="email"autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码（必填）" name="password" id="password" type="password" value="">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="重新输入密码（必填）" name="confirm_password" type="password" value="">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="姓名" name="nickName" type="text" value="">
                                </div>
                                
                               <div class="form-group">
                                    <label class="radio-inline">
                                        <input type="radio" name="sex" id="optionsRadiosInline1" value="0" checked="">男
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="sex" id="optionsRadiosInline2" value="1">女
                                    </label>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="地址" name="address" type="text" value="">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="学校" name="schoolAddress" type="text" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
		                                <a href="bbk.htm" >已有账号，登录</a>
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="submit" class="btn btn-lg btn-success btn-block" >注册</button>
                            
                            <!-- Modal -->
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
                                <!-- /.modal-dialog -->
                            </div>
                            <!-- /.modal -->
                      		</fieldset>
				</form> 
				<div class="line line-dashed"></div>
				
			</section> 
		</div> 
	</section> <!-- footer --> 
	<footer id="footer"> 
		<div class="text-center padder"> 
			<p> <small>BBK & BBF<br>&copy; 2015</small> </p> 
		</div> 
	</footer> <!-- / footer --> 
	
	<script type="text/javascript">
	var success = 0;
	$().ready(function() {
		$("#register").validate({
			rules: {
				key: {
					required: true
				},
				email: {
					required: true
				},
				password: {
					required: true,
					minlength: 6
				},
				name: {
					required: true,
					minlength: 2
				},
				confirm_password: {
					required: true,
					minlength: 5,
					equalTo: "#password"
				},
				
				id_card: {
					required: true,
					minlength: 15,
				},
				tel: {
					required: true,
					number: true,
					minlength: 11 
				}, 
				birthday : {
					date: true
				}
			},
			messages: {
				key: {
					required:"请输入注册码",
				},
				email: {
					required:"请输入账号",
				},
				password: {
					required: "请输入密码",
					minlength: "密码至少6个字符"
				},
				confirm_password: {
					required: "请输入密码",
					minlength: "密码至少6个字符",
					equalTo: "两次输入的密码要相同"
				},
				name: {
					required: "请输入姓名",
					minlength: "姓名至少两个字"
				},
				id_card: {
					required: "请输入身份证号",
					minlength: "身份证号码至少15位"
				},
				
				tel: "请输入电话号码"
			},
			 
			submitHandler:function(form){
    		    var txtHtml;
    			$.ajax({
    				url:'Register?isTeacher=1',
    				data:$('#register').serialize(),
    				type:"POST",
    				success:function(data){
    					if(data.Status == "success"){
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