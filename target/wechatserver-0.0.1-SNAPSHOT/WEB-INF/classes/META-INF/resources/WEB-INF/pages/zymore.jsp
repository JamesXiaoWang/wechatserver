<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="UTF-8">
    <title>智佳云点播</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
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
	<script type="text/javascript" src="<%=basePath %>js/jweixin-1.4.0.js"></script>
	<script src='<%=basePath%>js/jaudio.js'></script>
	<style type="text/css">
		.weui-panel{
			width: 100%;
		}
		
		.content {
			height: 85%;
			-webkit-overflow-scrolling: touch;
			overflow: scroll;
		}
		
		.weui-media-box_appmsg .weui-media-box__hd {
		    margin-right: .8em;
		    width: 100px;
		    height: 100px;
		    line-height: 60px;
		    text-align: center;
		}
		
		.weui-cells{
			font-size: 14px;
		    color: #000;
		    white-space: nowrap;
		    text-overflow: ellipsis;
		}
		
		.audition{
			float:left;
			width:1.2rem;
			height:1.2rem;
			vertical-align: middle;
			margin-right:0.8rem;
			-webkit-background-size:1.2rem!important;
			background-size:1.2rem!important;			
		}
		.audition-ico{
			background:url(<%=basePath %>images/audition-ico.png) no-repeat;
		}
		.listen-ico{
			background:url(<%=basePath %>images/listen_ico.png) no-repeat;
		}
		
		.play{
			float:left;
			width:1.2rem;
			height:1.2rem;
			vertical-align: middle;
			margin-right:0.8rem;
			-webkit-background-size:1.2rem!important;
			background-size:1.2rem!important;	
		}
		.play-ico{
			background:url(<%=basePath %>images/play-ico.png) no-repeat;
		}
		.pause-ico{
			background:url(<%=basePath %>images/pause-ico.png) no-repeat;
		}
		
		.ft {
			width: 100%;
			height: 14%;
			position: fixed;
			bottom: 0;
		}
		
		.ft-top {
			width: 100%;
			height: 50%;
			float: left;
		}
		
		.div-top{
			float: left;
			text-align: center;
		}

		.ft-bot {
			width: 100%;
			height: 50%;
			float: left;
		}

		.div-inline{			
			width: 33.33333%;
			float: left;
			text-align: center;
		}
	</style>
</head>
<body>
	<div class="bd content">
		<div class="page__bd">
			<div class="weui-panel weui-panel_access">
				<div class="weui-panel__bd">
					<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
						<div class="weui-media-box__hd">
							<img class="weui-media-box__thumb" src="${resourceAlbum.host }${resourceAlbum.imgUrl}${resourceAlbum.img}" alt="">
						</div>
						<div class="weui-media-box__bd">
							<h4 class="weui-media-box__title">${resourceAlbum.tagName }</h4>
							<p class="weui-media-box__desc">
								
							</p>
							<input type="hidden" id="classify" value="${resourceAlbum.id }" />
							<input type="hidden" id="tagName" value="${resourceAlbum.tagName }" />
						</div>
					</a>
				</div>
			</div>
			<div class="weui-cells__tips weui-cells-margin">${resourceAlbum.introduce }</div>
	
			<div class="weui-cells__title">专辑列表</div>
			<div class="weui-cells album">
				
			</div>
			<audio src="" id="myaudio" controls="controls" hidden="true" >
		</div>
	</div>
	<div class="weui-flex ft">
		<div class="weui-flex__item">
			<div class="ft-top">
				<div class="div-top" style="width:25%;">
					<span>播放内容：</span>					
				</div> 
				<div class="div-top" style="width:75%;">
					<marquee direction="left">
						<p id="pc">暂无播放资源。。。。</p>
					</marquee>
				</div>
			</div>
			<div class="ft-bot">
				<div class="div-inline">
					<a href="javascript:;" id="prev" class="weui-btn weui-btn_mini weui-btn_default operation" data="1">上一曲</a>					
				</div> 
				<div class="div-inline">
					<a href="javascript:;" id="play" class="weui-btn weui-btn_mini weui-btn_default operation" data="2">播放</a>
				</div> 
				<div class="div-inline">
					<a href="javascript:;" id="next" class="weui-btn weui-btn_mini weui-btn_default operation" data="0">下一曲</a>
				</div>  
				<input type="hidden" id="content" value="" />
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function(){
			realTimeQuery();
		});
		
		function realTimeQuery(){
			var audiosrc = $("#myaudio").attr("src");
			$.ajax({
				url: "<%=basePath%>play/getMoreResource/${appid}",
				type: "post",
				data: {
					"resId" : "${resourceAlbum.id }",
    				"openid" : "${openid}"
				},
				dataType:"json",
				success: function(res){
					var html = "";
					jQuery.each(res.data.resourceLists, function(i,item){
						html += "<div class=\"weui-cell\">";
						html += "	<div class=\"weui-cell__hd playsong\" style=\"cursor: pointer;\">";
						if(res.data.play == 2 && (item.id == res.data.audioId)){
							html += "	<i class=\"play pause-ico\" data=\"" + item.id + "\"></i>";
						}else{
							html += "	<i class=\"play play-ico\" data=\"" + item.id + "\"></i>";
						}
						html += "	</div>";
						html += "	<div class=\"weui-cell__bd\">";
						html += "		<p>" + item.section + "</p>";
						html += "	</div>";
						
						html += "	<div class=\"weui-cell__ft\">";
						if(audiosrc != '' && audiosrc == ("${resourceAlbum.host}${resourceAlbum.resUrl}" + item.res)){
							html += "	<i class=\"audition listen-ico\" data=\"" + item.section + "_" + item.res + "\"></i>";
						}else{
							html += "	<i class=\"audition audition-ico\" data=\"" + item.section + "_" + item.res + "\"></i>";
						}
						html += "	</div>";
						html += "</div>";
					});
					$(".album").html(html);
					
					if(res.data.section != ""){
						if(res.data.play == 2){
							$("#play").attr("data", 3);
							$("#play").text("暂停");
						}else if(res.data.play == 3){
							$("#play").attr("data", 2);
							$("#play").text("播放");
						}
						
						$("#pc").html(res.data.section);
						$("#content").val($("#classify").val() + "__" + res.data.audioId);
					}else{
						$("#pc").html("暂无资源播放");
					}
				}, error: function(res){
					clearTimeout(t);
				}
			});
			t = setTimeout(function() {
				realTimeQuery();
			}, 1000);
		}
	
	    $("body").on("click", ".play", function(){
	    	var data = $(this).attr("data");
	    	var cls = $(this).attr("class");
	    	
	    	var playType = "2";
	    	
			if(cls.indexOf("play-ico") != -1){
				$(".play").attr("class", "play play-ico");
				$(this).attr("class", "play pause-ico");
				playType = 2;
			}else{
				$(this).attr("class", "play play-ico");
				playType = 3;
			}	
			
			$.ajax({
    			url: "<%=basePath%>play/playAudio/${appid}",
    			type: "post",
    			data: {"audioId" : data,
    				"resId" : $("#classify").val(),
    				"playType" : playType,
    				"openid" : "${openid}"	
    			},
    			beforeSend: function(res){
    				$.showLoading("内容推送中...");
    			},
    			success: function(res){
    				setTimeout(function() {
		        	  alert(res.msg);
			          $.hideLoading();
			        }, 1000);
    			}, error: function(res){
    				alert("服务器连接失败，请联系管理员");
    			}
    		});
    		return false;
	    });
	    
	    function PlayAudio(myAuto) {
               myAuto.load(); // iOS 9  需要load一下, 否则直接play无效
               myAuto.play(); // iOS 7/8 需要play一下
        }
         
	    $("body").on("click", ".audition", function(){
	    	var myAuto = document.getElementById('myaudio');
	    	
	    	myAuto.addEventListener('play', function() {
		        // 当 audio 能够播放后, 移除这个事件
		        document.removeEventListener('touchstart', function(e){
	        		PlayAudio(myAuto);
	       		}, false);
		    }, false);
	    	document.addEventListener('touchstart', function(e){
	        	PlayAudio(myAuto);
	        }, false);
	    	
	        document.addEventListener("WeixinJSBridgeReady",function(e){
	        	PlayAudio(myAuto);
	        }, false);
	    
	    	var data = $(this).attr("data");
	    	$("#myaudio").attr("src", "${resourceAlbum.host}${resourceAlbum.resUrl}" + data.split("_")[1]);
	    	
	    	myAuto.onended = function() {
			    $(this).attr("class", "audition audition-ico");
			    $("#myaudio").attr("src", "");
			};
			
			var cls = $(this).attr("class");
			if(cls.indexOf("audition-ico") != -1){
				$(".audition").attr("class", "audition audition-ico");
				$(this).attr("class", "audition listen-ico");
            	myAuto.play();
			}else{
				$(this).attr("class", "audition audition-ico");
	            myAuto.pause();
	            myAuto.load();
	            
	            $("#myaudio").attr("src", "");
			}
						
			$("#pc").html($("#tagName").val() + "    " + data.split("_")[0]);
		});
		
		$("body").on("click", ".operation", function(){
			var operation = $(this).attr("data");
			var content = $("#content").val();
			if(content == ""){
				content = $("#classify").val() + "__" + 0;
			}
			$.ajax({
				url: "<%=basePath%>play/operationAudio/${appid}",
				typr: "post",
				data: {
					"content" : content,
					"operation" : operation,
    				"openid" : "${openid}"
				},
				beforeSend: function(res){
    				$.showLoading("操作处理中...");
    			},
				success: function(res){
					setTimeout(function() {
			        	if(operation == 2){
							$("#play").attr("data", 3);
							$("#play").text("暂停");
						}else if(operation == 3){
							$("#play").attr("data", 2);
							$("#play").text("播放");
						}
						$("#content").val(res.data.resId + "__" + res.data.audioId);
				        $.hideLoading();
			        }, 1000);
				}, error: function(res){
					$.hideLoading();
				}
			});
		});
	</script>
</body>
</html>