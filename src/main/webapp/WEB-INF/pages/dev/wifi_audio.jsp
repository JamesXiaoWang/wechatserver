<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>声波配网</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://47.106.172.221:8082/wechatserver/js/dev/jquery.mobile-1.4.5.min.css">
    <script src="http://47.106.172.221:8082/wechatserver/js/dev/jquery.min.js"></script>
    <script src="http://47.106.172.221:8082/wechatserver/js/dev/jquery.mobile-1.4.5.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
    <style>
        .view_k1 {
            color: #00a3f0;
            font-size: 1rem;
        }
    </style>
</head>
<body style="background-color: white">
<div data-role="page">
    <div data-role="main" class="ui-content">
        <div id="step_title">
            <span class="view_k1"><strong>第三步</strong></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span class="view_k2">请调大手机音量并靠近设备</span>
        </div>
        <audio id="bgMusic" src="http://47.106.172.221:8089/zhijia/unload/acousticWave/${deviceId}/encoder.mp3"
               loop="loop"
               autoplay style="display: none">你的浏览器不支持audio标签。
        </audio>
        <a id="audio_btn" onclick="check()"><img src="http://47.106.172.221:8082/wechatserver/images/index.gif"
                                                 id="music_btn" style="margin-top: 20px;position:relative;left:35%"></a>
        <div id="tips">
            <p>注意：</p>
            <p>1.如果由于非网络原因导致联网失败，请点击“播放”再发送一次声波</p>
            <p>2.声波播放完成后请留意设备端的语音提示，根据语音提示进行相应操作</p>
            <p>3.声波有效期为两小时，请及时使用，超时请返回第二步重新合成</p>
        </div>
        <input type="submit" id="btn" onclick="check()" style="background-color: green" value="播放">
    </div>
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
            wx.ready(function () {
                var globalAudio = document.getElementById("bgMusic");
                globalAudio.play();
            });
        };
        // 解决ios音乐不自动播放的问题
        autoPlayAudio();

        function check() {
            var bgMusic = document.getElementById("bgMusic");
            if (bgMusic.paused) {
                document.getElementById('bgMusic').play();

                $("#music_btn").attr("src", "http://47.106.172.221:8082/wechatserver/images/index.gif");

            } else {
                document.getElementById('bgMusic').pause();

                $("#music_btn").attr("src", "http://47.106.172.221:8082/wechatserver/images/index.png");
            }
        }

        setTimeout("myFunction()", 10000);

        function myFunction() {
            var r = confirm("设备收到网络信息了吗？");
            if (r == true) {
                var appId = "wxe2c15d10dd6983bf";
                var zjKey = "3838ee3782904b7a94abf1e9d4621ce6";

                // window.location.href = "/wechatserver/authorize/operation/" + appId + "?zjkey=" + zjKey + "";
                window.location.href="http://shp.qpic.cn/hd_priv_pic/0/1546521209fdd664dd14fb6f5a659e3e90f73c0c99/0";
            } else {
                setTimeout("myFunction()", 6000);
            }
        }
    </script>
</div>
</body>
</html>
