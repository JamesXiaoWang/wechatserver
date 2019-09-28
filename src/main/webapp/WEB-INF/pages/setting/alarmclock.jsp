<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	
	<title>闹钟设置</title>
	
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
	<style type="text/css">
		.weui-grid {
			position: relative;
			float: left;
			width: 33.33333333%;
			box-sizing: border-box
		}
		
		.clock{
			font-size: 24px;
		}
	</style>
</head>

<body>
	<header class='demos-header'>
	<h1 class="demos-title">设备闹钟</h1>
	</header>
	
	<div class="bd">
      <div class="page__bd">
        <div class="weui-cells">
        	<c:if test="${clocks != null }">
				<c:forEach items="${clocks }" var="item" varStatus="index">
		          <a class="weui-cell weui-cell_access" href="<%=basePath %>setting/getAddClocks/${appid}?openid=${openid }&clockid=${item.id}&device=${device}">
		            <div class="weui-cell__bd">
		              <p class="clock">${item.clock }</p>
		              <p class="weui-footer__text">${item.repeatStr }</p>
		            </div>
		            <div class="weui-cell__ft">
		            	<label id="clock-switch-${index.index + 1 }" for="ss${index.index + 1}" class="weui-switch-cp" data="${item.id }">
				    		<c:choose>
				    			<c:when test="${item.status == 1 }">
				    				<input id="ss${index.index + 1}" class="weui-switch-cp__input"  type="checkbox" checked="checked">
				    			</c:when>
				    			<c:otherwise>
				    				<input id="ss${index.index + 1}" class="weui-switch-cp__input"  type="checkbox">	
				    			</c:otherwise>
				    		</c:choose>
			            	<div class="weui-switch-cp__box"></div>
			          	</label>
		            </div>
		          </a>
		        </c:forEach>
	        </c:if>
        </div>
      
        <div class="weui-cells__title">设置闹钟</div>
      	<div class="weui-cells" id="add-div">
      		<c:choose>
      			<c:when test="${length < 5 }">
      				<a href="<%=basePath %>setting/getAddClocks/${appid}?openid=${openid }&clockid=0&device=${device}" class="weui-cell weui-cell_link"> 
						<img src="<%=basePath%>images/icon-clock.png" alt="" style="width:20px;margin-right:5px;display:block">
						<span class="weui-cell__bd">添加闹钟</span>
					</a>
      			</c:when>
      			<c:otherwise>
      				<a href="javascript:void(0);" id="add-clock" class="weui-cell weui-cell_link"> 
						<img src="<%=basePath%>images/icon-clock.png" alt="" style="width:20px;margin-right:5px;display:block">
						<span class="weui-cell__bd">添加闹钟</span>
					</a>
      			</c:otherwise>
      		</c:choose>
		</div>
	  </div>
    </div>
	<script>
		$(function() {
			FastClick.attach(document.body);
		});
	</script>

	<script type="text/javascript">
		$("#add-clock").click(function(){
			alert("设置的闹钟个数不能超过五个。");
		});
	
		$(".weui-switch-cp").change(function(){
			var status = 1;
			var st = $("#" + $(this).attr("for")).prop('checked');
			if(st == true){
				status = 1;
			}else{
				status = 0;
			}
			var clockid = $(this).attr("data");

			$.ajax({
	        	url: "<%=basePath %>setting/applyClock/${appid}",
	        	type: "post",
	        	data: {	
	        		"openid" : "${openid}",
	        		"status" : status,
	        		"device" : "${device}",
	        		"clockid" : clockid
	        	},
	        	beforeSend: function(res){
	        		$.showLoading("数据处理中");
	        	},
	        	success: function(res){
	        		setTimeout(function() {
	        		  if(res.status == "SUCCESS"){
	        				console.log(res.msg);
	        		  }
			          $.hideLoading();
			        }, 1000);
	        	}, error: function(res){
	        		$.toast("网络连接失败，请联系管理员!", "forbidden");
	        		$.hideLoading();
	        	}
	        });
			return false;
		});
	</script>
</body>
</html>
