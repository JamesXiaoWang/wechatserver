<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
    <style type="text/css">
        #btn{
            background-color: #666666;
            color:#ffffff;
            font-size: 50px;
        }
        #app{
            max-width: 600px;
            margin: 0 auto;
            padding: 200px 0;
            height: 400px;
        }
    </style>
</head>
<body>
<div id=app align="center">
    <b>声波配网第一步</b><br>
    <b>请确保您的设备已按说明书开启配网模式</b><br>
    <form action="/wechatserver/wifi/loginMethod">
        <input id="myCheck" type="checkbox" onclick="check()" />我的设备已开机且处于配网模式<br><br>
        <input type="submit" value="下一步" id="btn" disabled="disabled"/>
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
