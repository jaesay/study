<?php

/************************************************************************************************/
/* header 출력 */
/* 로그인 되어 있는 지 확인 */
/* 관리자로 로그인 되었는 지 맴버로 로그인 되었는 지에 따라 네비게션 바 수정  */
/* 로그인 되었는지 안 되었는지에 따라 네비게이션 바에 login sign up 변경 여부 결정 */
/* 카테고리와 카트 정보를 계속 디비에 접속해서 가져오면 느리기 때문에 세션변수에 저장해서 사용 */
session_start();
if(empty($_SESSION['items'])) {
	$_SESSION['items'] = 0; 
}
?>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<title><?php echo htmlspecialchars($data['title']); ?></title>
	<!-- Bootstrap -->
    	<link href="/bookshop-mvc/public/inc/css/bootstrap.min.css" rel="stylesheet">
    	<!-- 외부 스타일시트 -->
		<link rel="stylesheet" href="/bookshop-mvc/public/inc/css/bookshop.css">
		<!-- 내부 스타일시트 -->
		<style>
			
		</style>
    	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    	<!--[if lt IE 9]>
      	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    	<![endif]-->
</head>
  	<body>
    	<div class="container">
    	<?php
    	/* 관리자이면 */
    	if (isset($_SESSION['memberid'])) {
    		if($_SESSION['memberid'] == 'admin') {
    	?>
			<!-- 네비게이션 바 시작-->
			<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a href="/bookshop-mvc/public/" class="navbar-brand">BOOKSHOP</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li><a href="display_best.html">MEMBER</a></li>		
						<li><a href="display_best.html">BOOK</a></li>
						<li><a href="display_best.html">REVIEW</a></li>
						<li><a href="show_questions.html">QUESTION</a></li>
					</ul>
		<?php
    		}
    	} else {
    	?>
    	<!-- 네비게이션 바 시작-->
	  		<nav class="navbar navbar-default">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
						<a href="/bookshop-mvc/public/" class="navbar-brand">BOOKSHOP</a>
					</div>
					<div class="collapse navbar-collapse" id="myNavbar">
						<ul class="nav navbar-nav">
							<li><a href="display_new.html">NEW</a></li>		
							<li><a href="display_best.html">BEST</a></li>
							<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">CATEGORY<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="display_cate.html">ALL</a></li>
									<li><a href="display_cate.html?cate=programming">PROGRAMMING</a></li>
									<li><a href="display_cate.html?cate=database">DATABASE</a></li>
									<li><a href="display_cate.html?cate=network">NETWORK</a></li>
								</ul>
							</li>
							<li><a href="show_questions.html">QUESTION</a></li>
						</ul>
    	<?php
    	}
    	?>
					<ul class="nav navbar-nav navbar-right">
					<?php 
					if (!isset($_SESSION['memberid'])) { // 로그인이 안돼있으면
					?>						
						<li><a href="/bookshop-mvc/public/member/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li><a href="/bookshop-mvc/public/member/signup"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					<?php
					} else { // 로그인되어 있다면
					?>
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span><?php echo htmlspecialchars($_SESSION['memberid']) ?><span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/bookshop-mvc/public/member/auth/modify">Modify</a></li>
								<li><a href="/bookshop-mvc/public/member/auth/remove">Remove</a></li>
								<li><a href="/bookshop-mvc/public/member/logout">Logout</a></li>
							</ul>
						</li>
					<?php
					}
					?>
						<li><a href="show_cart.html"><span class="glyphicon glyphicon-shopping-cart"></span> Cart: <?php echo htmlspecialchars($_SESSION['items']); ?></a></li>
					</ul>
					<form class="navbar-form navbar-left">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search">
						</div>
						<button type="submit" class="btn btn-default">Search</button>
					</form>
				</div>
			</div>
  		</nav>
		<!-- 네비게이션 바 끝 -->
