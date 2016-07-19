<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!-- MetisMenu CSS -->
<link href="../resources/resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../resources/resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../resources/resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<section class="vbox">
	<header class="bg-dark dk header navbar navbar-fixed-top-xs">
		<div class="navbar-header aside-md">
			<a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen" data-target="#nav"> 
				<i class="fa fa-bars"></i>
			</a> 
			<a href="#" class="navbar-brand" data-toggle="fullscreen">
				<img src="../resources/images/xd_logo.png" class="m-r-sm">西电小蘑菇
			</a> 
			<a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".nav-user"> 
				<i class="fa fa-cog"></i>
			</a>
		</div>
		<ul class="nav navbar-nav navbar-right hidden-xs nav-user">
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
					<span class="thumb-sm avatar pull-left">
						<img src="../resources/images/head.png">
					</span><security:authentication property="principal.name" /><b class="caret"></b>
				</a>
				<ul class="dropdown-menu animated fadeInRight">
					<li>
						<a href="logout">退出</a>
					</li>
				</ul>
			</li>
		</ul>
	</header>
	<section>
		<section class="hbox stretch">
			<!-- .aside -->
			<aside class="bg-dark lter aside-md hidden-print" id="nav">
				<section class="vbox">
					<section class="w-f scrollable">
						<div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="5px" data-color="#333333">
							<!-- nav -->
							<nav class="nav-primary hidden-xs">
								<ul class="nav">
									<li>
										<a href="mainPage.htm"> 
											<i class="fa fa-dashboard icon"><b class="bg-danger"></b></i> 
											<span>概览</span>
										</a>
									</li>
									<li>
										<a href=""> 
											<i class="fa fa-columns icon"><b class="bg-warning"></b></i> 
											<span class="pull-right"> 
												<i class="fa fa-angle-down text"></i> 
												<i class="fa fa-angle-up text-active"></i>
											</span> 
											<span>学习</span>
										</a>
										<ul class="nav lt">
											<li>
												<a href="coursesList.htm"> <i class="fa fa-angle-right"></i> 
													<span>选课通知</span>
												</a>
											</li>
											<li>
												<a href="competitionList.htm"> <i class="fa fa-angle-right"></i> 
													<span>竞赛公告</span>
												</a>
											</li>
											<li>
												<a href="othersList.htm"> <i class="fa fa-angle-right"></i> 
													<span>其他公告</span>
												</a>
											</li>
										</ul>
									</li>
									<li>
										<a href=""> 
											<i class="fa fa-suitcase icon"><b class="bg-primary"></b></i> 
											<span class="pull-right"> 
												<i class="fa fa-angle-down text"></i> 
												<i class="fa fa-angle-up text-active"></i>
											</span> 
											<span>生活</span>
										</a>
										<ul class="nav lt">
											
											<li>
												<a href="busList.htm"> <i class="fa fa-angle-right"></i> 
													<span>校车时间</span>
												</a>
											</li>
											<li>
												<a href="cardsList.htm"> <i class="fa fa-angle-right"></i> 
													<span>一卡通</span>
												</a>
											</li>
										</ul>
									</li>
									<li>
										<a href=""> 
											<i class="fa fa-columns icon"><b class="bg-warning"></b></i> 
											<span class="pull-right"> 
												<i class="fa fa-angle-down text"></i> 
												<i class="fa fa-angle-up text-active"></i>
											</span> 
											<span>查询统计</span>
										</a>
										<ul class="nav lt">
											<li>
												<a href="showGradeStatistic.htm"> <i class="fa fa-angle-right"></i> 
													<span>成绩查询信息</span>
												</a>
											</li>
											<li>
												<a href="showFlowStatistic.htm"> <i class="fa fa-angle-right"></i> 
													<span>流量查询信息</span>
												</a>
											</li>
										</ul>
									</li>
									<li>
										<a href=""> 
											<i class="fa fa-columns icon"><b class="bg-warning"></b></i> 
											<span class="pull-right"> 
												<i class="fa fa-angle-down text"></i> 
												<i class="fa fa-angle-up text-active"></i>
											</span> 
											<span>本科课表管理</span>
										</a>
										<ul class="nav lt">
											<li>
												<a href="semesterList.htm"> <i class="fa fa-angle-right"></i> 
													<span>学期管理</span>
												</a>
											</li>
											<li>
												<a href="collegeList.htm"> <i class="fa fa-angle-right"></i> 
													<span>学院及班级管理</span>
												</a>
											</li>
											<li>
												<a href="timetableList.htm"> <i class="fa fa-angle-right"></i> 
													<span>课表管理</span>
												</a>
											</li>
											<li>
												<a href="setSemesterWeekNum.htm"> <i class="fa fa-angle-right"></i> 
													<span>教学周管理</span>
												</a>
											</li>
										</ul>
									</li>
									<li>
										<a href="feedbackList.htm"> 
											<i class="fa fa-eye icon"><b class="bg-primary"></b></i> 
											<span>意见</span>
										</a>
									</li>
								</ul>
							</nav>
							<!-- / nav -->
						</div>
					</section>
					<footer class="footer lt hidden-xs b-t b-dark">
						<div id="chat" class="dropup">
							<section class="dropdown-menu on aside-md m-l-n">
								<section class="panel bg-white">
									<header class="panel-heading b-b b-light">西电生活</header>
									<div class="panel-body animated fadeInRight">
										<p class="text-sm">BBK & BBF</p>
										<p>
											<a href="#" class="btn btn-sm btn-default">智能化校园全方位支持</a>
										</p>
									</div>
								</section>
							</section>
						</div>						
						<a href="#nav" data-toggle="class:nav-xs" class="pull-right btn btn-sm btn-dark btn-icon" > 
							<i class="fa fa-angle-left text"></i> 
							<i class="fa fa-angle-right text-active"></i>
						</a>
						<div class="btn-group hidden-nav-xs">
							<button type="button" title="Chats"
								class="btn btn-icon btn-sm btn-dark" data-toggle="dropdown"
								data-target="#chat">
								<i class="fa fa-comment-o"></i>
							</button>	
						</div>
					</footer>
				</section>
			</aside>
			<!-- /.aside -->
		<section id = "content">
		<section class= "vbox">
			<section class= "scrollable padder">
			<div style="margin:20px">