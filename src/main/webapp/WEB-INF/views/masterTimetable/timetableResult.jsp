<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>西电小蘑菇</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="resources/css/timetable.css" rel="stylesheet" media="screen">
	<link rel="stylesheet" href="resources/css/app.v2.css" type="text/css" />
  </head>
  
  <body>
    
  <%
    List weekList = new ArrayList();
    weekList.add("周一");
    weekList.add("周二");
    weekList.add("周三");
    weekList.add("周四");
    weekList.add("周五");
    weekList.add("周六");
    weekList.add("周日");
    request.setAttribute("weekList",weekList);
    
   /*  String[][] courses = new String[5][7];
    courses[0][1] = "算法";
    courses[3][3] = "数学";
    courses[2][4] = "英语";
    request.setAttribute("courses",courses); */
  %>
    
    <header style="font-color:#000">
		<h1 class="myhead"><b>研究生课表查询</b></h1>
	</header>
	<div onclick="window.location='queryMasterTimetableOut'" class=" pull-right">
    <label>现在是：第${semesterWeekNum }周　</label><i class="fa fa-fw fa-sign-out"></i>
    </div>
    
    <!-- 模态框 -->
	<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      ...
	    </div>
	  </div>
	</div>
	
	<table class="table table-bordered">
		<!-- 第1行 -->
		<tr class="weekTime">
			<td class=""></td>
			<c:forEach var="y" items="${weekList}" begin="0" end="6" step="1"> 
			<td class="weekTimeTD"><c:out value="${y}"/></td>
			</c:forEach>
		</tr>
		<!-- end of 第1行 -->
		
		<!-- 2~11行 -->
		<c:forEach items="${courses }" var="course">
		<tr class="tr1">
			<!-- 第一列 -->
			<td class="dayTime"><span>1</span><hr><span>2</span></td>
			<!-- end of 第一列 -->
			
			<!-- 其余列 -->
			<c:forEach items="${course }" var="node">
				<td class="courseTD"><div class="courseDIV">${node}</div></td>
			</c:forEach>
			<!-- end of 其余列 -->
			
		</tr>
		</c:forEach>
		<!-- end of 2~11行 -->
		
	</table>
	<div style="height:100px;"></div>
	
	<div class="panel-footer text-center" style="position:fixed;bottom:0px;left:0px;right:0px;width:100%">
		<label>研究生课表查询由西电小蘑菇独家提供</label><br>
		<img src="resources/images/xd_logo.png" class="m-r-sm" style="width:30px">
	</div>
	
	<div id="mask"></div>
	<div id="searchTip">
		<div class="iteach">点击课程名称 可以查看详情哦</div>
		<button class="iknow">　我知道了　</button>
	</div>
	
    <script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	
	
	<script>
		//第1-10节课标号
		var allDayTime = document.getElementsByTagName("span");
		for(var i=1; i<=allDayTime.length; i++){
			allDayTime[i-1].innerHTML = i;
		}
	</script>
	<script>
		//读每个课程
		var allCourses = document.getElementsByClassName("courseDIV");
		var jsonArr = [];
		var idIndex = 0;
		
		for(var i=0;i < allCourses.length; i++){
			if(!allCourses[i].innerHTML == ""|| !allCourses[i].innerHTML == " "){
        		$(allCourses[i]).css({"border":"1px solid #FDDD9B","background-color":"#FDDD9B"});
        		
        		var info = allCourses[i].innerHTML;  //每个courseDIV的信息，可能包含几条课程信息.
        		var courseInfos = info.split("\\|");
        		var infos = info.split(" ");
        		var infos1 = infos[1].split("\/");
        		var time = "";
        		for(var k=2; k<infos.length; k++){
        			time += infos[k]+" ";
        		}
        		
        		if(infos[0].length>3){   //button内容换行
        			if(infos[0].substring(3,infos[0].length).length>3)
        				infos[0] = infos[0].substring(0,3)+"<br>"+infos[0].substring(3,6)+"<br>"+infos[0].substring(6,infos[0].length);
       				else
       					infos[0] = infos[0].substring(0,3)+"<br>"+infos[0].substring(3,infos[0].length);
        		}
        		
        		var theFirstClass = "";
        		//判断是否有2节课重复
        		var ifTwoClass = 0;
        		for(var k=0; k<time.length; k++){
        			if(/.*[\u4e00-\u9fa5]+.*$/.test(time.substring(k,k+1))) {
        				ifTwoClass = 1;
        				var theSecondClass = time.substring(k,time.length);
        				theFirstClass = time.substring(0,k);
        				break;
        			}
        		}
        		
        		if(ifTwoClass == 0){
        			$(allCourses[i]).html("<button type='button' class='btn btn-primary' id='"+idIndex+"' style='position:relative;top:25%;background-color:#FDDD9B;border:0px;' data-toggle='modal' data-target='.bs-example-modal-sm'><b>"+infos[0]+"</b></button>"); 
        			var json = {
	        					"teacher": infos1[0], 
	        					"classroom": infos1[1], 
	        					"classtime": time
	        					};
	        		jsonArr[idIndex] = json;  //将json数据存入数组
	        		idIndex++; 
        		}
        		else{
	        		var secondClass = theSecondClass.split(" ");
	        		var secondClassName = secondClass[0];
	        		var temp = secondClass[1].split("/");
	        		var secondClassTeacher = temp[0];
	        		var secondClassRoom = temp[1];
	        		var secondClassTime = "";
	        		for(var k=2; k<secondClass.length; k++){
        				secondClassTime += secondClass[k]+" ";
        			}
	        		
	        		if(secondClassName.length>3)   //button内容换行
	        			secondClassName = secondClassName.substring(0,3)+"<br>"+secondClassName.substring(3,infos[0].length);
	        		/* if(secondClassName.length>3){   //button内容换行
	        			if(secondClassName.substring(3,secondClassName.length).length>3)
	        				secondClassName = secondClassName.substring(0,3)+"<br>"+secondClassName.substring(3,6)+"<br>"+secondClassName.substring(6,secondClassName.length);
	       				else
	       					secondClassName = secondClassName.substring(0,3)+"<br>"secondClassName.substring(3,secondClassName.length);
	        		} */
	        			
	        			
	        		$(allCourses[i]).html("<button type='button' class='btn btn-primary' id='"+idIndex+"' style='background-color:#FDDD9B;border:0px;' data-toggle='modal' data-target='.bs-example-modal-sm'><b>"+infos[0]+"</b></button>"+
	        		                  "<br><button type='button' class='btn btn-primary' id='"+(idIndex+1)+"' style='background-color:#FDDD9B;border:0px;' data-toggle='modal' data-target='.bs-example-modal-sm'><b>"+secondClassName+"</b></button>"); 

	        		var json = {
	        					"teacher": infos1[0], 
	        					"classroom": infos1[1], 
	        					"classtime": theFirstClass 
	        					};
	        		jsonArr[idIndex] = json;  //将json数据存入数组
	        		idIndex++;
	        		
	        		var json = {
	        					"teacher": secondClassTeacher, 
	        					"classroom": secondClassRoom, 
	        					"classtime": secondClassTime 
	        					};
	        		jsonArr[idIndex] = json;  //将json数据存入数组
	        		idIndex++;
        		}
			}
		}
	</script>
	<script>
		//往模态框里填充数据
		$(".btn-primary").click(function(event){
			var id = $(this).attr("id");
			$(".modal-content").empty().append("<p>教师："+jsonArr[id].teacher+"</p>");
			$(".modal-content").append("<p>教室："+jsonArr[id].classroom+"</p>");
			$(".modal-content").append("<p>时间："+jsonArr[id].classtime+"</p>");
		});
	</script>
	
	<script>
		//新手引导
		var intro = ${firstCheck };//1为进行导航
		if(intro == 1){
			$("#mask").show();
			$("#searchTip").show();

			$(".iknow").bind("click",function(event){
				$("#mask").hide();
				$("#searchTip").hide();
			});
		}
	</script>
	
  </body>
</html>
