<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>播放声波</title>
    <link rel="stylesheet" type="text/css" href="http://47.106.172.221:8082/wechatserver/css/main2.css" />
    <style>
      /*  #main{
            width:100%;
            height:100%;
            background-size:100% 100%;
            position:absolute;
            background-image: url("http://47.106.172.221:8082/wechatserver/images/earth-space.jpg");
        }*/
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
    <meta HTTP-EQUIV="expires" CONTENT="0">
</head>
<body>
<div id="main" align="center">
    <div style=" width:300px; height:120px; margin:320px auto">
        <div align="center">
            <audio id="bgMusic" src="http://47.106.172.221:8089/zhijia/unload/acousticWave/${deviceId}/encoder.mp3" loop="loop" autoplay  style="display: none">你的浏览器不支持audio标签。</audio>
            <a id="audio_btn"><img src="http://47.106.172.221:8082/wechatserver/images/index.gif" width="48" height="50" id="music_btn" border="0"></a>
            <br/> <br/>
            请调大手机音量并靠近设备
            <c:forEach items="${wechatAuthorizes }" var="wa" varStatus="index" >
                <input name="appid" id="appid" value="${appid}" style="display: none">
                <input name="zjkey" id="zjkey" value="${wa.zjkey}"  style="display: none">
            </c:forEach>
        </div>
    </div>
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/1.8.2/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
<script>
    function autoPlayAudio() {
        wx.config({
            // 配置信息, 即使不正确也能使用 wx.ready
            debug: false,
            appId: '',
            timestamp: 1,
            nonceStr: '',
            signature: '',
            jsApiList: []
        });
        wx.ready(function() {
            var globalAudio = document.getElementById("bgMusic");
            globalAudio.play();
        });
    };
    // 解决ios音乐不自动播放的问题
    autoPlayAudio();

    $("#audio_btn").click(function(){
        var bgMusic = document.getElementById("bgMusic");
        if(bgMusic.paused){
            document.getElementById('bgMusic').play();

            $("#music_btn").attr("src","http://47.106.172.221:8082/wechatserver/images/index.gif");

        }else{
            document.getElementById('bgMusic').pause();

            $("#music_btn").attr("src","http://47.106.172.221:8082/wechatserver/images/index.png");
        }
    });

    setTimeout("myFunction()",10000 );
    function myFunction(){
        var r=confirm("设备收到网络信息了吗？");
        if (r==true) {
            var appId = "wxe2c15d10dd6983bf";
            var zjKey = "3838ee3782904b7a94abf1e9d4621ce6";
            alert("appid"+appId)

            window.location.href = "/wechatserver/authorize/operation/"+appId+"?zjkey="+zjKey+"";

            // window.location.href="http://shp.qpic.cn/hd_priv_pic/0/1546521209fdd664dd14fb6f5a659e3e90f73c0c99/0";
        }
        else{
            setTimeout("myFunction()",6000 );
        }
    }
</script>
</html>