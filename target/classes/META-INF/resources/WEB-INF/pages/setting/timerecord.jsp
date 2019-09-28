<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	
	<title>对话记录</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
	<meta HTTP-EQUIV="expires" CONTENT="0">
	
	<link rel="stylesheet" href="<%=basePath%>css/weui.min.css">
	<link rel="stylesheet" href="<%=basePath%>css/jquery-weui.css">
	<link rel="stylesheet" href="<%=basePath%>css/demos.css">
	
	<script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/fastclick.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery-weui.js"></script>
</head>

<body>
	<div class="bd">
		<div class="page__bd">
			<div class="weui-cells__title">系统将为您保存最近7天的设备对话记录</div>
			<div class="weui-cells">
				<c:forEach items="${timelist }" var="times">
					<a class="weui-cell weui-cell_access" href="<%=basePath%>setting/tochatmsg/${appid}?openid=${openid}&device=${device}&chattime=${times.chattime}">
						<div class="weui-cell__bd">
							<p>${times.times }</p>
						</div>
						<div class="weui-cell__ft"></div>
					</a>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
