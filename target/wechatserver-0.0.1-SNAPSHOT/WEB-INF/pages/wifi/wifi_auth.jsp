<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/20
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>声波配网</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
    <meta HTTP-EQUIV="expires" CONTENT="0">
    <link href="http://47.106.172.221:8082/wechatserver/css/view.css" rel="stylesheet">
</head>
<body>
<div id="app" align="center">
    <form action="/wechatserver/wifi/loginMethod">
        <div id="content">
            <span class="view_k1"><strong>第一步</strong></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span class="view_k2">请确保您的设备已按说明书开启配网模式</span>
        </div><p></p>
        <div id="view_k3">
            <input type="checkbox" name="myCheck" id="myCheck" value="1" checked onclick="check()">我的设备已开机且处于配网模式
            <!--        <span class="view_k3">我的设备已开机且处于配网模式</span>-->
        </div>
        <p></p>
        <input type="submit" id="btn" value="下一步" style="background-color: #0cbb08;font-size: 16px">
        <input name="openid" value="${openid}" style="display: none">
        <input name="appid" value="${appid}" style="display: none">
    </form>
</div>
</body>
<script>
    function check(){
        var myCheck = document.getElementById('myCheck');
        if(myCheck.checked){
            document.getElementById("btn").style.backgroundColor="#0bb20c";
            document.getElementById('btn').removeAttribute('disabled');
        }else{
            document.getElementById("btn").style.backgroundColor="#666666";
            document.getElementById('btn').setAttribute('disabled','disabled');
        }
    }
</script>
</html>
