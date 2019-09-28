<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
	<script type="text/javascript" src="<%=basePath %>js/tab/flexible.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/tab/iscroll.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/tab/navbarscroll.js"></script>
	<style type="text/css">
		html{color:#000;overflow-y:scroll;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%}
		html *{outline:0;-webkit-text-size-adjust:none;-webkit-tap-highlight-color:rgba(0,0,0,0)}
		ul,ol {list-style:none;}
		.clearfix:after{content:".";display:block;height:0;clear: both;visibility:hidden;}
		.clearfix{*zoom:1;}
		.wrapper {position:relative;height: 1rem;width: 100%;overflow: hidden;margin:0 auto;border-bottom:1px solid #ccc}
		.wrapper .scroller {position:absolute}
		.wrapper .scroller li {height: 1rem;color:#333;float: left;line-height: 1rem;font-size: .5rem;text-align: center}
		.wrapper .scroller li a{color:#333;display:block;margin:0 .3rem;padding:0 .1rem}
		.wrapper .scroller li.cur a{color:#1cbb9b;height:.9rem;border-bottom:.1rem solid #1cbb9b}
		
		.content-box{
			margin-top: 10px;
		}
		
		.weui-grid {
			position: relative;
			float: left;
			padding: 20px 10px;
			width: 33.33333333%;
			box-sizing: border-box
		}
		
		.weui-grid__icon {
			width: 2.75rem;
			height: 2.75rem;
			margin: 0 auto;
		}
	</style>
  </head>
  
  <body>
	<div class="wrapper" id="wrapper">
		<div class="scroller">
			<ul class="clearfix">
				<c:forEach items="${items }" var="item" varStatus="index">
			      	<li><a href="javascript:void(0)">${item }</a></li>
		      	</c:forEach>
			</ul>
		</div>
	</div>
	<div class="content-box">
		<div class="weui-grids">
			
	    </div>
    </div>
	<script type="text/javascript">
		$(function(){
			$('.wrapper').navbarscroll({
				defaultSelect: ${indx},
				endClickScroll:function(obj){
					$.ajax({
						url: "<%=basePath%>play/getResourceDetail/${appid}",
						type: "post",
						data: {
							"tagName": obj.text(),
							"tagId" : obj.index() + 1
						},
						dataType: 'json',
						success: function(res){
							var htm = "";
							jQuery.each(res.data, function(i,item){  
				                htm += "<a href=\"javascript:void(0);\" data-id=\"" + item.id + "\" class=\"weui-grid js_grid resouce\">";
							    htm += "    <div class=\"weui-grid__icon\">";
							    htm += "      <img src=\"" + item.host + item.imgUrl + item.img + "\">";
							    htm += "    </div>";
							    htm += "   	<p class=\"weui-grid__label\">";
							    htm += 			item.name;
							    htm += "   	</p>";
						      	htm += "</a>";
				            });  
							$(".weui-grids").html(htm);					
						}
					});
				}
			});
		});
		
		$(document).on("click", ".resouce", function(){
			var rand = parseInt(Math.random() * 10000);
			var tagId = $(this).attr("data-id");
			window.location.href="<%=basePath%>play/resourceMore/${appid}?resId=" + tagId + "&openid=${openid}"  + "&id=" + rand;
		});
	</script>
</body>
</html>