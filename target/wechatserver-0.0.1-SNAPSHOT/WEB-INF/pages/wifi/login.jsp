<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
    <title>输入Wi-Fi名称和密码</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
    <meta HTTP-EQUIV="expires" CONTENT="0">

    <link rel="stylesheet" href="http://47.106.172.221:8082/wechatserver/css/ui.min.css">
    <link rel="stylesheet" href="<%=basePath %>css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath %>css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath %>css/demos.css">

    <script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/fastclick.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery-weui.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/swiper.js"></script>
</head>
<body>
<div class="htmleaf-container">
    <div class="wrapper">
        <div class="container">
            <form class="form">
                <input name="name" id="name" placeholder="请输入您的无线网络名称">
                <input name="pwd"  type="password" id="pwd" placeholder="请输入您的无线网络密码">
                <input name="openId" id="openId" value="${openid}" style="display: none">
                <input name="appid" id="appid" value="${appid}" style="display: none">
                <button type="button" name="btn" onclick="check()" id="btn">提交</button>
            </form>
        </div>

        <ul class="bg-bubbles">
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div>
</div>
<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';color:#000000">
    <h1>声波配网</h1>
</div>
</body>
<script type="text/javascript">
    function check() {
        var name = document.getElementById("name").value;
        var pwd = document.getElementById("pwd").value;
        var openId = document.getElementById("openId").value;
        var appid = document.getElementById("appid").value;
        if(name == "" || name == null) {
            alert("请填写无线网络名称！");
            return false;
        } else if(pwd == "" || pwd == null) {
            alert("请填写无线网络密码！");
            return false;
        }else if (pwd.length < 8){
            alert("无线网络密码不能少于8位哟！");
        } else {
            $.post("/wechatserver/wifi/login", {
                    "name": name,
                    "pwd": pwd
                },
                function(result) {
                    window.location.href="/wechatserver/wifi/testAudio"+'?deviceId='+name+"_"+pwd;

                    /*window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe2c15d10dd6983bf&redirect_uri=http%3A%2F%2Fwww.cszjsmart.com%2Fwechatserver%2Fwifi%2FsonicBroadcast%2Fwxe2c15d10dd6983bf&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect";*/
                    /*window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe2c15d10dd6983bf&redirect_uri=http%3A%2F%2Fwww.cszjsmart.com%2Fwechatserver%2Fplay%2Fmycloud%2Fwxe2c15d10dd6983bf&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect"+'?time='+((new Date()).getTime());*/
                },
                'text');
        }
    }
</script>
</html>
