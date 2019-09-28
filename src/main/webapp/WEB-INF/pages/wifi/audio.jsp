<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>播放声波</title>
    <link rel="stylesheet" type="text/css" href="http://47.106.172.221:8082/wechatserver/css/main2.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
    <meta HTTP-EQUIV="expires" CONTENT="0">
    <style>
        body {
            margin: 0;
        }

        .musicPlay {
            position: fixed;
            width: 100vw;
            top: 20vh;
        }

        .musicPlay>p {
            width: 64vw;
            margin-left: 18vw;
            font-size: 1.5rem;
            background-color: rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            box-shadow: 0 0 12px 0 #aaa;
            height: 7vh;
            line-height: 7vh;
        }

        .musicPlay>p>img {
            float: left;
            margin-left: 1vw;
            height: 5vh;
            margin-top: 1vh;
        }

        .musicPlay>p>span {
            float: left;
        }

        .musicPlay>p>span>em {
            color: #d81e06;
        }
    </style>
</head>
<body>
<div class="musicPlay">
    <audio id="voice" src="http://47.106.172.221:8089/zhijia/unload/acousticWave/aiJames/encode.mp3" loop="loop" autoplay="autoplay"></audio>
    <p><img src="http://upload-images.jianshu.io/upload_images/6171922-4d23a92a9c256d0d.gif?imageMogr2/auto-orient/strip"><span><em></em>播放/暫停</span></p>
    <button id="ceshi" type="button">测试</button>
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/1.8.2/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        var voice = document.getElementById('voice');
        //调用 <audio> 元素提供的方法 play()
        voice.play();
        //判斷 WeixinJSBridge 是否存在
        if (typeof WeixinJSBridge == "object" && typeof WeixinJSBridge.invoke == "function") {
            alert("shifou cun zai")
            voice.play();
        } else {
            //監聽客户端抛出事件"WeixinJSBridgeReady"
            if (document.addEventListener) {
                document.addEventListener("WeixinJSBridgeReady", function() {
                    alert("Weixin Js")
                    voice.play();
                }, false);
            } else if (document.attachEvent) {
                document.attachEvent("WeixinJSBridgeReady", function() {
                    voice.play();
                });
                document.attachEvent("onWeixinJSBridgeReady", function() {
                    voice.play();
                });
            }
        }

        //voiceStatu用來記録狀態,使 touchstart 事件只能觸發一次有效,避免與 click 事件衝突
        var voiceStatu = true;
        //监听 touchstart 事件进而调用 <audio> 元素提供的 play() 方法播放音频
        document.addEventListener("touchstart", function(e) {
            if (voiceStatu) {
                voice.play();
                voiceStatu = false;
            }
        }, false);
        document.addEventListener('loadedmetadata', function (e) {
            $(".musicPlay>p>span>em").html(parseInt(voice.duration)+'" ');
        })

        $('.musicPlay').click(function() {
            // 依據 audio 的 paused 属性返回音频是否已暂停來判斷播放還是暫停音频。
            if (voice.paused) {
                voice.play();
                $('.musicPlay>p>img').attr('src', 'http://upload-images.jianshu.io/upload_images/6171922-4d23a92a9c256d0d.gif?imageMogr2/auto-orient/strip');
            } else {
                voice.pause();
                $('.musicPlay>p>img').attr('src', 'http://upload-images.jianshu.io/upload_images/6171922-e5206046b43e1efe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240');
            }
        });
        //监听浏览器能够开始播放这段音频时，发生的 canplay 事件来获取 <audio> 元素的 duration 属性.
       /* $("#voice").on("canplay", function() {
            $(".musicPlay>p>span>em").html(parseInt(voice.duration)+'" ');
        });*/
    });
    $("#ceshi").click(function () {
                window.location.href="/wechatserver/wifi/testAudio"+'?time='+((new Date()).getTime());
    });
</script>
</html>
