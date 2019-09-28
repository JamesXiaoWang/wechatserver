<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>声波配网</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <%--<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">--%>
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
    <style>
        .view_k1 {
            color: #00a3f0;
            font-size: 16px;
        }
        #tips {
            font-size: 0.8em;
            text-align: left;
            margin-left: 30px;
            margin-top: 30px;
            line-height: 14px;
        }
        img{
            margin-top: 30px;
        }
        #btn {
            color: #ffffff;
            /*padding: 0 .25em;*/
            width: 280px;
            height: 50px;
            margin-left: 20px;
            margin-top: 40px;
            border-radius: 10px;
            overflow: visible;
            background-color: #0cbb08;
            font-size: 16px
        }
    </style>
</head>
<body style="background-color: white">
<div id="container" align="center">
    <div id="step_title" style="padding: 32px 16px 0;">
        <span class="view_k1"><strong>第三步</strong></span>
        <span class="view_k2" style="margin-left: 60px" >请调大手机音量并靠近设备</span>
    </div>
    <p></p>
    <audio id="bgMusic" src="http://47.106.172.221:8089/zhijia/unload/acousticWave/${deviceId}/encoder.mp3" loop="loop" autoplay  style="display: none">你的浏览器不支持audio标签。</audio>
    <a id="audio_btn" onclick="check()"><img src="http://47.106.172.221:8082/wechatserver/images/index.gif" id="music_btn"></a>
    <div id="tips">
        <p>注意：</p>
        <p>1.如果由于非网络原因导致联网失败，请点击“播放”再发送一次声波</p>
        <p>2.声波播放完成后请留意设备端的语音提示，根据语音提示进行相应操作</p>
        <p>3.声波有效期为两小时，请及时使用，超时请返回第二步重新合成</p>
    </div>
    <p></p>
    <input type="button" id="btn" onclick="check()" value="播放">
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/1.8.2/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-weui.js"></script>
<script type="text/javascript" src="<%=basePath %>js/fastclick.js"></script>
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
    function check(){
        var bgMusic = document.getElementById("bgMusic");
        if(bgMusic.paused){
            document.getElementById('bgMusic').play();

            $("#music_btn").attr("src","http://47.106.172.221:8082/wechatserver/images/index.gif");

        }else{
            document.getElementById('bgMusic').pause();

            $("#music_btn").attr("src","http://47.106.172.221:8082/wechatserver/images/index.png");
        }
    }
    setTimeout("myFunction()",10000 );
    function myFunction(){
        var r=confirm("设备收到网络信息了吗？");
        if (r==true) {
            var appId = "wxe2c15d10dd6983bf";
            var zjKey = "3838ee3782904b7a94abf1e9d4621ce6";

            window.location.href = "/wechatserver/authorize/operation/"+appId+"?zjkey="+zjKey+"";

            // window.location.href="http://shp.qpic.cn/hd_priv_pic/0/1546521209fdd664dd14fb6f5a659e3e90f73c0c99/0";
        }
        else{
            setTimeout("myFunction()",6000 );
        }
    }
</script>
</html>
