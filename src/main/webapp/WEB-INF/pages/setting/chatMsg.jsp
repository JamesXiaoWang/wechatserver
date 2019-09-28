<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		
		<title>${title }对话记录</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
		<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
		<meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<meta HTTP-EQUIV="expires" CONTENT="0">
		
		<style type="text/css">
		div.speech {
			float: left;
			margin: 10px 0;
			padding: 8px;
			table-layout: fixed;
			word-break: break-all;
			position: relative;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, from(#ffffff),
				color-stop(0.1, #ececec), color-stop(0.5, #dbdbdb),
				color-stop(0.9, #dcdcdc), to(#8c8c8c));
			border: 1px solid #989898;
			border-radius: 8px;
		}
		
		div.speech:before {
			content: '';
			position: absolute;
			width: 0;
			height: 0;
			left: 15px;
			top: -20px;
			border: 10px solid;
			border-color: transparent transparent #989898 transparent;
		}
		
		div.speech:after {
			content: '';
			position: absolute;
			width: 0;
			height: 0;
			left: 17px;
			top: -16px;
			border: 8px solid;
			border-color: transparent transparent #ffffff transparent;
		}
		
		div.speech.right {
			display: inline-block;
			box-shadow: -2px 2px 5px #CCC;
			margin-right: 10px;
			max-width: 75%;
			float: right;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, from(#e4ffa7),
				color-stop(0.1, #bced50), color-stop(0.4, #aed943),
				color-stop(0.8, #a7d143), to(#99BF40));
		}
		
		div.speech.right:before {
			content: '';
			position: absolute;
			width: 0;
			height: 0;
			top: 9px;
			bottom: auto;
			left: auto;
			right: -10px;
			border-width: 9px 0 9px 10px;
			border-color: transparent #989898;
		}
		
		div.speech.right:after {
			content: '';
			position: absolute;
			width: 0;
			height: 0;
			top: 10px;
			bottom: auto;
			left: auto;
			right: -8px;
			border-width: 8px 0 8px 9px;
			border-color: transparent #bced50;
		}
		
		div .left {
			display: inline-block;
			box-shadow: 2px 2px 2px #CCCCCC;
			margin-left: 10px;
			max-width: 75%;
			position: relative;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, from(#ffffff),
				color-stop(0.1, #eae8e8), color-stop(0.4, #E3E3E3),
				color-stop(0.8, #DFDFDF), to(#D9D9D9));
		}
		
		div .left:before {
			content: '';
			position: absolute;
			width: 0;
			height: 0;
			top: 9px;
			bottom: auto;
			left: -10px;
			border-width: 9px 10px 9px 0;
			border-color: transparent #989898;
		}
		
		div .left:after {
			content: '';
			position: absolute;
			width: 0;
			height: 0;
			top: 10px;
			bottom: auto;
			left: -8px;
			border-width: 8px 9px 8px 0;
			border-color: transparent #eae8e8;
		}
		
		.leftimg {
			float: left;
			margin-top: 10px;
		}
		
		.rightimg {
			float: right;
			margin-top: 10px;
		}
		
		.leftd {
			clear: both;
			float: left;
			margin-left: 10px;
		}
		
		.rightd {
			clear: both;
			float: right;
			margin-right: 10px;
		}
		
		.leftd_h {
			width: 39px;
			height: 39px;
			border-radius: 100%;
			display: block;
			float: left;
			overflow: hidden;
			margin: 10px auto 0;
		}
		
		.leftd_h img {
			display: block;
			width: 100%;
			height: auto;
		}
		
		.rightd_h {
			width: 39px;
			height: 39px;
			border-radius: 100%;
			display: block;
			float: right;
			overflow: hidden;
			margin: 10px auto 0;
		}
		
		.rightd_h img {
			display: block;
			width: 100%;
			height: auto;
		}
		</style>
	</head>
	<body>
		<c:forEach items="${chatmsgList }" var="cml">
			<div class="leftd">
				<span class="leftd_h"> 
					<img src="<%=basePath %>images/icon-baby.jpg" />
				</span>
				<div class="speech left" class="speech left">[${cml.sfm}] ${cml.asr }</div>
			</div>
			<c:if test="${cml.tts != null && cml.tts != '' }">
				<div class="rightd">
					<span class="rightd_h"> 
						<img src="<%=basePath %>images/icon-zhijia.jpg" />
					</span>
					<div class="speech right" class="speech left">${cml.tts }</div>
				</div>
			</c:if>
		</c:forEach>
	</body>
</html>
