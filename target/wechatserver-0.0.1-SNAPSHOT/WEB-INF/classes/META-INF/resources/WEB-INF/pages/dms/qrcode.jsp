<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="background-color:#f0eff5;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/link_device.css">
    <title>识别二维码并进行绑定</title>
</head>

<body>
<div class="main wx_main"><br><br><br>
    <p class="wx_msg">
        	长按识别二维码，并选择 <span style="color:#1aac19">“配置设备上网”</span><br>
    </p>
    <img src="${qrcode }" width="65%"><br><br><br><br>
    
    <!-- <p style="color:#999;">提示：扫描设备背部二维码可跳过此前步骤</p> -->
</div>
</body>
</html>
