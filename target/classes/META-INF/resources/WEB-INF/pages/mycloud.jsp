<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>智佳云点播</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	<!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
	<meta HTTP-EQUIV="expires" CONTENT="0">
	
	<link rel="stylesheet" href="<%=basePath %>css/weui.min.css">
	<link rel="stylesheet" href="<%=basePath %>css/jquery-weui.css">
	<link rel="stylesheet" href="<%=basePath %>css/demos.css">
	
	<script type="text/javascript" src="<%=basePath %>js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/fastclick.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jquery-weui.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/swiper.js"></script>
	<style type="text/css">
		.content{
		    margin: 10px 10px;
		}
		
		.content .swiper-container {
			width:100%;
		    height:8.2rem;
		    margin: 10px 0;
		    padding-bottom: 0;			
			border-radius: 25px;
		    -moz-border-radius: 25px;
		}
		
		.weui-grid {
			position: relative;
			float: left;
			padding: 20px 10px;
			width: 33.33333333%;
			box-sizing: border-box
		}		
	</style>
</head>
<body>
	<div class="content">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="<%=basePath%>images/banner1.png">
				</div>
				<div class="swiper-slide">
					<img src="<%=basePath%>images/banner2.png">
				</div>
				<div class="swiper-slide">
					<img src="<%=basePath%>images/banner3.png">
				</div>
				<div class="swiper-slide">
					<img src="<%=basePath%>images/banner2.png">
				</div>
			</div>
		</div>
		<div class="b">
			<div class="weui-grids">
				<c:forEach items="${items }" var="item" varStatus="index">
			      	<a href="<%=basePath %>play/mycloudDetail/${appid}?tagName=${item }" class="weui-grid js_grid">
				        <div class="weui-grid__icon">
				          <img src="<%=basePath %>images/story_ico.png" alt="">
				        </div>
				        <p class="weui-grid__label">
				          ${item }
				        </p>
			      	</a>
		      	</c:forEach>
		    </div>
	    </div>
    </div>
	<script type="text/javascript">
      $(".swiper-container").swiper({
	        loop: true,
	        autoplay: 5000,
	        speed: 400,
    		spaceBetween: 0
      });
    </script>
</body>
</html>
