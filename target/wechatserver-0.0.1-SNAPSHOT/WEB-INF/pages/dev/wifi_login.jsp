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
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
    <meta HTTP-EQUIV="expires" CONTENT="0">
    <link rel="stylesheet" href="http://47.106.172.221:8082/wechatserver/js/dev/jquery.mobile-1.4.5.min.css">
    <script src="http://47.106.172.221:8082/wechatserver/js/dev/jquery.min.js"></script>
    <script src="http://47.106.172.221:8082/wechatserver/js/dev/jquery.mobile-1.4.5.min.js"></script>
    <style type="text/css">
        .view_k1 {
            color: #00a3f0;
            font-size: 1rem;
        }
    </style>
</head>
<body>
<div data-role="page">
    <div data-role="main" class="ui-content">
        <div id="step_title">
            <span class="view_k1"><strong>第二步</strong></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span class="view_k2">连接网络</span>
        </div>
        <img src="http://47.106.172.221:8082/wechatserver/images/new_wifi.png" width="50px" height="50px"
             style="margin-top: 20px;position:relative;left:40%">
        <div class="ui-field-contain">
            <label for="username">网络名称：</label>
            <input type="text" name="username" id="username" placeholder="请输入您的无线网络名称">
            <label for="password">WiFi-密码：</label>
            <input type="password" name="password" id="password" placeholder="请输入对应的WiFi-密码">
        </div>
        <div id="tips">
            <p>注意：</p>
            <p>1.当前只支持2.4GWi-Fi网络（是否支持5G网络请参考产品使用说明）</p>
            <p>2.请务必确保网络名称和密码输入正确，区分大小写，如有密码长度应大于等于8位，且当前网络连接畅通</p>
        </div>
        <a href="#myPopup1" data-rel="popup" id="check_A" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window" style="display: none"></a>
        <div data-role="popup" id="myPopup1" class="ui-content">
            <span style="font-size: 1.2em;color: #00a3f0;"><strong>请输入您的无线网络名称！</strong></span>
        </div>
        <a href="#myPopup2" data-rel="popup" id="check_B" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window" style="display: none"></a>
        <div data-role="popup" id="myPopup2" class="ui-content">
            <span style="font-size: 1.2em;color: #00a3f0;"><strong>请输入对应的WiFi-密码！</strong></span>
        </div>
        <a href="#myPopup3" data-rel="popup" id="check_C" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window" style="display: none"></a>
        <div data-role="popup" id="myPopup3" class="ui-content">
            <span style="font-size: 1.2em;color: #00a3f0;"><strong>无线网络密码不能少于8位哟！</strong></span>
        </div>
        <input type="button" id="btn" onclick="check()" style="background-color: green" value="下一步">
        <div style="display: none">
            <input name="openId" id="openId" value="${openid}">
            <input name="appid" id="appid" value="${appid}">
        </div>
    </div>
<script type="text/javascript">
    function check() {
        var name = document.getElementById("username").value;
        var pwd = document.getElementById("password").value;
        var openId = document.getElementById("openId").value;
        // var appid = document.getElementById("appid").value;
        if (name == "" || name == null) {
            $("#check_A").click();
            // $.toast("请填写无线网络名称！","text");
            // alert("请填写无线网络名称！");
            return false;
        } else if (pwd == "" || pwd == null) {
            $("#check_B").click();
            // $.toast("请填写无线网络密码！","text");
            // alert("请填写无线网络密码！");
            return false;
        } else if (pwd.length < 8) {
            $("#check_C").click();
            // $.toast("无线网络密码不能少于8位哟！","text");
            // alert("无线网络密码不能少于8位哟！");
            return false;
        } else {
            $.post("/wechatserver/wifi/login", {
                    "name": name,
                    "pwd": pwd,
                    "openId": openId
                },
                function (result) {
                    window.location.href = "/wechatserver/wifi/testAudio" + '?deviceId=' + name + "_" + pwd;

                    /*window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe2c15d10dd6983bf&redirect_uri=http%3A%2F%2Fwww.cszjsmart.com%2Fwechatserver%2Fwifi%2FsonicBroadcast%2Fwxe2c15d10dd6983bf&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect";*/
                    /*window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe2c15d10dd6983bf&redirect_uri=http%3A%2F%2Fwww.cszjsmart.com%2Fwechatserver%2Fplay%2Fmycloud%2Fwxe2c15d10dd6983bf&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect"+'?time='+((new Date()).getTime());*/
                },
                'text');
        }
    }
</script>
</div>
</body>
</html>
