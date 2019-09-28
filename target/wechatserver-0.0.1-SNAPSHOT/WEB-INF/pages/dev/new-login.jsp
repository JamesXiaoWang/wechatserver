<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--  <link rel="stylesheet" href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">-->
    <link rel="stylesheet" href="http://47.106.172.221:8082/wechatserver/js/dev/jquery.mobile-1.4.5.min.css">
    <script src="http://47.106.172.221:8082/wechatserver/js/dev/jquery.min.js"></script>
    <script src="http://47.106.172.221:8082/wechatserver/js/dev/jquery.mobile-1.4.5.min.js"></script>
    <style>
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
        <img src="http://47.106.172.221:8082/wechatserver/images/new_wifi.png" width="50px" height="50px" style="margin-top: 20px;position:relative;left:40%">
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
        <input type="submit" id="btn" onclick="check()" style="background-color: green" value="下一步">
    </div>
</div>
</body>
<script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
<script>
    $(document).ready(function () {
        alert("一开始就加载")
    })
</script>
</html>