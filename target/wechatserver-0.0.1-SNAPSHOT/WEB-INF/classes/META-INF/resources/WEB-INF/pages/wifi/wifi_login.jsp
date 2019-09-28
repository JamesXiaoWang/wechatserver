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
    <script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
    <style type="text/css">
        .view_k1 {
            color: #00a3f0;
            font-size: 18px;
        }

        #name {
            border-color: white;
            font-family: "Segoe UI Light";
        }

        #pwd {
            border-color: white;
        }

        input {
            width: 250px;
            padding: 0 .25em;
            border-radius: 4px;
            -web-kit-appearance: none;
            -moz-appearance: none;
            font-size: 1.0em;
            height: 1.8em;
            border-radius: 4px;
            border: 1px solid #c8cccf;
            color: #6a6f77;
            outline: 0;
        }

        /*p {*/
            /*display: block;*/
            /*-webkit-margin-before: 1em;*/
            /*-webkit-margin-after: 1em;*/
            /*-webkit-margin-start: 0px;*/
            /*-webkit-margin-end: 0px;*/
        /*}*/

        #test {
            content: "*";
            position: relative;
            margin-left: -8%;
            /*font-size: 14px;*/
            color: #f44
        }

        #tips {
            font-size: 0.8em;
            text-align: left;
            margin-left: 10%;
            margin-top: 10%;
            line-height: 14px;
        }

        #btn {
            color: #ffffff;
            /*padding: 0 .25em;*/
            width: 280px;
            height: 50px;
            margin-left: 20px;
            margin-top: 35px;
            border-radius: 10px;
            overflow: visible;
            background-color: #0cbb08;
            font-size: 16px
        }

        #view_body {
            background-color: white;
            margin-top: 35px;
        }
    </style>
</head>
<body>
<div id="container" align="center">
    <div id="step_title" style="margin-right: 170px;margin-top: 20px">
        <span class="view_k1" style="margin-left: -35px"><strong>第二步</strong></span>
        <span class="view_k2" style="margin-left: 25px">连接网络</span>
    </div>
    <p></p>
    <img src="http://47.106.172.221:8082/wechatserver/images/new_wifi.png" width="50px" height="50px"
         style="margin-top: 20px">
    <p></p>
    <table id="view_body">
        <div style="background-color: #ffffff">
            <tr>
                <td><span id="test">*</span>网络名称：</td>
                <td><input type="text" id="name" placeholder="请输入您的无线网络名称"></td>
            </tr>
            <tr>
                <td> Wi-Fi密码：</td>
                <td><input type="password" id="pwd" placeholder="请输入对应的WiFi密码"></td>
            </tr>
        </div>
    </table>
    <div id="tips">
        <p>注意：</p>
        <p>1.当前只支持2.4GWi-Fi网络（是否支持5G网络请参考产品使用说明）</p>
        <p>2.请务必确保网络名称和密码输入正确，区分大小写，如有密码长度应大于等于8位，且当前网络连接畅通</p>
    </div>
    <p></p>
    <input type="button" id="btn" value="下一步" onclick="check()">
    <input name="openId" id="openId" value="${openid}" style="display: none">
    <input name="appid" id="appid" value="${appid}" style="display: none">
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/1.8.2/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-weui.js"></script>
<script type="text/javascript" src="<%=basePath %>js/fastclick.js"></script>
<script type="text/javascript">
    function check() {
        var name = document.getElementById("name").value;
        var pwd = document.getElementById("pwd").value;
        var openId = document.getElementById("openId").value;
        // var appid = document.getElementById("appid").value;
        if (name == "" || name == null) {
            $.toast("请填写无线网络名称！","text");
            // alert("请填写无线网络名称！");
            return false;
        } else if (pwd == "" || pwd == null) {
            $.toast("请填写无线网络密码！","text");
            // alert("请填写无线网络密码！");
            return false;
        } else if (pwd.length < 8) {
            $.toast("无线网络密码不能少于8位哟！","text");
            // alert("无线网络密码不能少于8位哟！");
        } else {
            $.showLoading("数据处理中");
            $.post("/wechatserver/wifi/login", {
                    "name": name,
                    "pwd": pwd,
                    "openId":openId
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
</html>
