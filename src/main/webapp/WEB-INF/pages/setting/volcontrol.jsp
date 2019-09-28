<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<title>音量调节</title>

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


	<style>
	.swiper-container {
		width: 100%;
	}
	
	.swiper-container img {
		display: block;
		width: 100%;
	}
	</style>
</head>

<body ontouchstart>
	<header class='demos-header'>
      <h1 class="demos-title">音量调节</h1>
    </header>
	<div class='demos-content-padded'>
		<div class="weui-slider-box" id='slider2'>
			<div class="weui-slider">
				<div id="sliderInner" class="weui-slider__inner">
					<div id="sliderTrack" style="width: ${status.vol}%;" class="weui-slider__track"></div>
					<div id="sliderHandler" style="left: ${status.vol}%;" class="weui-slider__handler"></div>
				</div>
			</div>
			<div id="sliderValue" class="weui-slider-box__value">${status.vol}</div>
		</div>
		<br>
		<a href="javascript:;" class="weui-btn weui-btn_primary" id="apply">确认</a>
	</div>

	<script>
	  $(function() {
	    FastClick.attach(document.body);
	  });
	</script>
	<script>
      $('#slider1').slider();
      $('#slider2').slider(function (percent) {
        console.log(percent);
      });
      
      $("#apply").click(function(){
      	 $.ajax({
        	url: "<%=basePath %>setting/controlConfig/${appid}",
        	type: "post",
        	data: {	
        		"openid" : "${openid}",
       			"param" : $("#sliderValue").text(),
       			"type" : "VOL"
        	},
        	beforeSend: function(res){
        		$.showLoading("数据处理中");
        	},
        	success: function(res){
        		setTimeout(function() {
	        		if(res.status == "SUCCESS"){
	        			location.href="<%=basePath%>setting/setting/${appid}?openid=${openid}"
	        		}
			        $.hideLoading();
		        }, 1000);
        	}, error: function(res){
        		alert("网络连接失败，请联系管理员!");
        		$.hideLoading();
        	}
        });
      });
    </script>
</body>
</html>
