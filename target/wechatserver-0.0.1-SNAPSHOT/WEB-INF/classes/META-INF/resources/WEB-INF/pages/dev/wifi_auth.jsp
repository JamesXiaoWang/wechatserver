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
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
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
<form action="/wechatserver/wifi/loginMethod">
<div data-role="page">
        <div data-role="main" class="ui-content">
            <div id="step_title">
                <span class="view_k1"><strong>第一步</strong></span>&nbsp;&nbsp;&nbsp;
                <span class="view_k2">请确保您的设备已按说明书开启配网模式</span>
            </div>
            <div class="ui-field-contain">
                <label for="myCheck">我的设备已开机且处于配网模式</label>
                <input type="checkbox" id="myCheck" checked onclick="check()">
            </div>
            <input type="submit" id="btn" style="background-color: green" value="下一步">
        </div>
</div>
    <input name="openid" value="${openid}" style="display: none">
    <input name="appid" value="${appid}" style="display: none">
</form>
</body>
<script type="text/javascript">
    function check() {
        var myCheck = document.getElementById('myCheck');
        if (myCheck.checked) {
            document.getElementById("btn").style.backgroundColor = "#0bb20c";
            document.getElementById('btn').removeAttribute('disabled');
        } else {
            document.getElementById("btn").style.backgroundColor = "#666666";
            document.getElementById('btn').setAttribute('disabled', 'disabled');
        }
    }
</script>
</html>
