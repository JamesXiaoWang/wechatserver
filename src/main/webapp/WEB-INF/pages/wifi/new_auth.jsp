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
    <link href="http://47.106.172.221:8082/wechatserver/css/app.de3911b57d365dd475c51004a4cd5c70.css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" async="" src="http://47.106.172.221:8082/wechatserver/js/9.46a26c947849504852ec.js"></script>
    <script type="text/javascript" charset="utf-8" async="" src="http://47.106.172.221:8082/wechatserver/js/52.0b7f9dcb606cdd2fa7d6.js"></script>
    <script type="text/javascript" charset="utf-8" async="" src="http://47.106.172.221:8082/wechatserver/js/29.b8b66bf7e1db44c0d63c.js"></script>
    <style>
        .van-checkbox {
            overflow: hidden;
            -webkit-user-select: none;
            user-select: none
        }

        .van-checkbox__icon, .van-checkbox__label {
            display: inline-block;
            vertical-align: middle;
            line-height: 20px
        }

        .van-checkbox__icon {
            font-size: 12px;
            color: transparent;
            text-align: center;
            border: 1px solid #aaa;
            width: 20px;
            height: 20px;
            box-sizing: border-box
        }

        .van-checkbox__label {
            margin-left: 10px
        }

        .van-checkbox--round {
            border-radius: 100%
        }

        .van-checkbox--checked {
            color: #fff;
            border-color: #06bf04;
            background-color: #06bf04
        }

        .van-checkbox--disabled {
            color: #f8f8f8;
            border-color: #e5e5e5;
            background-color: currentColor
        }

        .van-checkbox--disabled.van-checkbox--checked {
            border-color: #e5e5e5;
            background-color: #e5e5e5
        }
    </style>
    <style type="text/css">
        .van-row:after {
            content: "";
            display: table;
            clear: both
        }
    </style>
    <style>
        #step_first[x9] {
            padding: 32px 16px 0;
            font-size: 16px
        }

        #step_first .step_title[x9] {
            margin-bottom: 32px
        }

        #step_first .step_title .step[x9] {
            font-weight: 700;
            color: #00a3f0
        }

        #step_first .step_bottom[x9] {
            text-align: center
        }

        #step_first .btn[x9] {
            margin-top: 60px
        }
    </style>
</head>
<body>
<div id="app">
    <form action="/wechatserver/wifi/loginMethod">
        <div x9="" id="step_first">
            <div x9="" class="van-row step_title">
                <div x9="" class="van-col step van-col-6">第一步</div>
                <div x9="" class="van-col van-col-18">请确保您的设备已按说明书开启配网模式</div>
            </div>
            <section x9="" class="step_bottom">
                <div x9 class="van-checkbox">
                    <i class="van-icon van-checkbox__icon van-icon-success van-checkbox--round van-checkbox--checked" id="bb">
                        <!---->
                    </i>
                    <span class="van-checkbox__label">我的设备已开机且处于配网模式</span>
                </div>
                <button x9 class="van-button btn van-button--primary van-button--large" type="submit">
                    <!---->
                    <span class="van-button__text">下一步</span>
                </button>
                <input name="openid" value="${openid}" style="display: none">
                <input name="appid" value="${appid}" style="display: none">
            </section>
        </div>
    </form>
</div>
<script type="text/javascript" src="http://47.106.172.221:8082/wechatserver/js/manifest.9823c9a4aa48e058d788.js"></script>
<script type="text/javascript" src="http://47.106.172.221:8082/wechatserver/js/vendor.4fab767be56907510c1b.js"></script>
</html>
