<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
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
    
	<script type="text/javascript" src="<%=basePath %>js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/fastclick.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jquery-weui.js"></script>
	
	<style type="text/css">
		.weui-cells-margin{
			margin-bottom: 25px;
		}
	</style>
</head>
<body ontouchstart>
	 <div class="bd">
	    <div class="page__bd">
	        <div class="weui-cells weui-cells_form">
		      <div class="weui-cell">
		        <div class="weui-cell__hd"><label for="name" class="weui-label">设备名称：</label></div>
		        <div class="weui-cell__bd">
		          	<p id='show-prompt'>
			          	${dmt.deviceName }
		          	</p>
		        </div>
		        <div class="weui-cell__ft" style="line-height: 40px;">
               		<div class="weui-cell__hd">
               			<a href="javascript:;" class="open-popup" data-target="#transfer-popup" >
               				<img src="<%=basePath %>images/transfer.png" alt="" style="width:20px;margin-right:5px;">
               			</a>
               		</div>
               </div>
		      </div>
		    </div>
	        
	        <c:if test="${dmt != null }">	
	        <div class="weui-cells__title">设备聊天记录</div>
		        <div class="weui-cells weui-cells-margin">
		          <a class="weui-cell weui-cell_access" href="<%=basePath %>setting/getTimeRecord/${appid }?openid=${openid }&device=${dmt.device }">
		            <div class="weui-cell__hd"><img src="<%=basePath %>images/icon-chat.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
		            <div class="weui-cell__bd">
		              <p>查看最近七天设备对话记录</p>
		            </div>
		            <div class="weui-cell__ft"></div>
		          </a>
		        </div>
		    </div>
	                
	      	<div class="weui-cells__title">设备状态</div>
	      	<div class="weui-cells weui-cells-margin">
	      		<div class="weui-cell">
	               <div class="weui-cell__hd"><img src="<%=basePath %>images/icon-tcard.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
	               <div class="weui-cell__bd">
	                   <p>T卡状态</p>
	               </div>
	               <div class="weui-cell__ft">
	               	<c:choose>
	               		<c:when test="${status.tcard == 1 }">
	               			已插入
	               		</c:when>
	               		<c:otherwise>
	               			未插入
	               		</c:otherwise>
	               	</c:choose>
	               </div>
	           </div>
	      		<div class="weui-cell">
	               <div class="weui-cell__hd"><img src="<%=basePath %>images/icon-battery.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
	               <div class="weui-cell__bd">
	                   <p>电池状态</p>
	               </div>
	               <div class="weui-cell__ft">
	               	<c:choose>
	               		<c:when test="${status.charging == 0 }">
	               			${status.battery }%
	               		</c:when>
	               		<c:otherwise>
	               			${status.battery }% - 充电中
	               		</c:otherwise>
	               	</c:choose>
	               </div>
	           </div>
	      	</div>
      	
      		<div class="weui-cells__title">存储空间</div>
	       	<div class="weui-cells weui-cells-margin">
	           <div class="weui-cell">
	               <div class="weui-cell__hd"><img src="<%=basePath %>images/icon-space.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
	               <div class="weui-cell__bd">
	                   <p>已用空间</p>
	               </div>
	               <div class="weui-cell__ft">${status.sfree }KB</div>
	           </div>
	           <div class="weui-cell">
	               <div class="weui-cell__hd"><img src="<%=basePath %>images/icon-room.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
	               <div class="weui-cell__bd">
	                   <p>可用空间</p>
	               </div>
	               <div class="weui-cell__ft">${status.stotal }KB</div>
	           </div>
	       	</div>
       
	       <div class="weui-cells__title">功能设置</div>
	       <div class="weui-cells">
      			<a class="weui-cell weui-cell_access" href="<%=basePath %>setting/getVolControl/${appid }?openid=${openid }&device=${dmt.device }">	
	       			<span class="weui-cell__hd"><img src="<%=basePath %>images/icon-voice.png" alt="" style="width:20px;margin-right:5px;display:block"></span>
	                <span class="weui-cell__bd">
	                    	音量设置
	                </span>
	                <span class="weui-cell__ft"><p id="settingvol">${status.vol }%</p></span>
	            </a>
      			<a class="weui-cell weui-cell_access" href="<%=basePath %>setting/getAlarmClock/${appid }?openid=${openid }&device=${dmt.device }">
      				<span class="weui-cell__hd"><img src="<%=basePath %>images/icon-clock.png" alt="" style="width:20px;margin-right:5px;display:block"></span>
	                <span class="weui-cell__bd">
	                    	闹钟设置
	                </span>
	                <span class="weui-cell__ft"></span>
	            </a>
	       			
	            <div class="weui-cell weui-cell_switch">
	           	   <span class="weui-cell__hd"><img src="<%=basePath %>images/icon-breath.png" alt="" style="width:20px;margin-right:5px;display:block"></span>
	               <span class="weui-cell__bd">
	                   	呼吸灯控制开关
	               </span>
	               <span class="weui-cell__ft">
		               	<label id="bln-switch" for="bln" class="weui-switch-cp">
		               		<c:choose>
			               		<c:when test="${status.bln == 1 }">
			               			<input id="bln" class="weui-switch-cp__input" type="checkbox" checked="checked">
			               		</c:when>
			               		<c:otherwise>
			               			<input id="bln" class="weui-switch-cp__input" type="checkbox">
			               		</c:otherwise>
			               	</c:choose>
			            	<div class="weui-switch-cp__box"></div>
			          	</label>
	               </span>
	           </div>
	           <div class="weui-cell weui-cell_switch">
	           	<span class="weui-cell__hd"><img src="<%=basePath %>images/icon-lowbattery.png" alt="" style="width:20px;margin-right:5px;display:block"></span>
	               <span class="weui-cell__bd">
	                   	低电量提醒开关
	               </span>
	               <span class="weui-cell__ft">
	               		<label id="lbi-switch" for="lbi" class="weui-switch-cp">
		               		<c:choose>
			               		<c:when test="${status.lbi == 1 }">
			               			<input id="lbi" class="weui-switch-cp__input" type="checkbox" checked="checked">
			               		</c:when>
			               		<c:otherwise>
			               			<input id="lbi" class="weui-switch-cp__input" type="checkbox">
			               		</c:otherwise>
			               	</c:choose>
			            	<div class="weui-switch-cp__box"></div>
			          	</label>
	               </span>
	           </div>
	       </div>
	       <div class="weui-cells__tips weui-cells-margin">开启此功能后，当设备电量小于20%时，设备会语音提醒您充电</div>
	       
	       <div class="weui-cells__title">系统设置</div>
	       <div class="weui-cells weui-cells-margin">
	       		<a class="weui-cell weui-cell_access" id="format-storage" href="javascript:;">
	                <span class="weui-cell__hd"><img src="<%=basePath %>images/icon-storage.png" alt="" style="width:20px;margin-right:5px;display:block"></span>
	                <span class="weui-cell__bd">
	                    	格式化存储
	                </span>
	                <span class="weui-cell__ft"></span>
	            </a>
	            <a class="weui-cell weui-cell_access" id="recovery-setting" href="javascript:;">
	                <span class="weui-cell__hd"><img src="<%=basePath %>images/icon-recovery.png" alt="" style="width:20px;margin-right:5px;display:block"></span>
	                <span class="weui-cell__bd">
	                   		 恢复出厂设置
	                </span>
	                <span class="weui-cell__ft"></span>
	            </a>
	       </div>
	       
	       <div class="weui-cells__title">OTA升级</div>
	       <div class="weui-cells">
				<div class="weui-cell">
					<div class="weui-cell__bd">
					  <p>固件版本</p>
					</div>
					<div class="weui-cell__ft">暂无信息</div>
				</div>
	       </div>
	       <div class="weui-cells__tips weui-cells-margin">当显示有最新升级版本时，可点击版本号处完成升级</div>
	       </c:if>
	       
	       <div class="weui-cells__title">设备解绑</div>
	       <div class="weui-cells weui-cells-margin">
	           <a class="weui-cell weui-cell_access" id='show-custom'>
	           	   	<span class="weui-cell__hd"><img src="<%=basePath %>images/icon-unbind.png" alt="" style="width:20px;margin-right:5px;display:block"></span>
	               	<span class="weui-cell__bd">
	                   	解除绑定
	               	</span>
	               	<span class="weui-cell__ft"></span>
	           </a>
	       </div>
	       
	       <div id="transfer-popup" class='weui-popup__container popup-bottom'>
		      <div class="weui-popup__overlay"></div>
		      <div class="weui-popup__modal">
		        <div class="toolbar">
		          <div class="toolbar-inner">
		            <a href="javascript:;" class="picker-button close-popup">关闭</a>
		            <h1 class="title">切换绑定设备</h1>
		          </div>
		        </div>
		        <div class="modal-content">
		        	<div class="weui-cells weui-cells_radio" id="show-devices">
		        	<c:forEach items="${items }" var="item" varStatus="index">
				        <label class="weui-cell weui-check__label" for="x${index.index }">
				          <div class="weui-cell__hd">
				          	<img src="<%=basePath %>images/icon-device.png" alt="" style="width:20px;margin-right:5px;display:block">
				          </div>
				          <div class="weui-cell__bd">
				            ${item.title }
				          </div>
				          <div class="weui-cell__ft">
				          	<c:choose>
				          		<c:when test="${item.isbind == 1 }">
				          			<input type="radio" class="weui-check transfer-conn" name="${openid }" id="x${index.index }" value="${item.value }" checked="checked">
				          		</c:when>
				          		<c:otherwise>
				          			<input type="radio" class="weui-check transfer-conn" name="${openid }" id="x${index.index }" value="${item.value }">
				          		</c:otherwise>
				          	</c:choose>
				            <span class="weui-icon-checked"></span>
				            	<c:choose>
				            		<c:when test="${item.isOnline == 1 }">
				            			在线
				            		</c:when>
				            		<c:otherwise>
				            			离线
				            		</c:otherwise>
				            	</c:choose>
				          </div>
				        </label>
		        	</c:forEach>
		        	</div>
		        </div>
		      </div>
		    </div>
		</div>
   </div>
</body>
<script>
  $(function() {
    FastClick.attach(document.body);
  });
</script>
<script type="text/javascript">
	$(document).on('WeixinJSBridgeReady',function(){ 
		if("${isbind}" == 0){
			alert("公众号未绑定设备，请先绑定设备!");
        	// WeixinJSBridge.call('closeWindow');
        	location.href="<%=basePath%>authorize/networkingBinding/${appid}";
		}
    });
    
    $(document).on("click", "#show-prompt", function() {
        $.prompt({
          text: "设备名称不能超过50个字符，不得出现不和谐文字",
          title: "输入设备名称",
          input: $.trim($("#show-prompt").text()),
          onOK: function(text) {
             $.ajax({
             	url: "<%=basePath%>setting/updateDeviceName/${appid}",
             	type: "post",
             	data: {
             		"openid" : "${openid}",
             		"zjkey": "${dmt.zjkey}",
             		"deviceId": "${dmt.deviceId}",
             		"deviceName": text,
             	}, success: function(res){
             		if("SUCCESS" == res.status){
             			$("#show-prompt").html(text);
             		}else{
             			alert(res.msg);
             		}
             	}, error: function(res){
             		alert(res.msg);
             	}
             });            	
          },
          onCancel: function() {
            	console.log("取消了");
          }          
        });
    });
    
    $(".transfer-conn").click(function(){
    	var tc_device = $('input:radio[name="${openid}"]:checked').val();
    	$.ajax({
			url: "<%=basePath%>setting/bindPushDevice/${appid}",
			type: "post",
			data: {
				"openid" : "${openid}",
				"device" : tc_device
			},
			success: function(res){
				location.href="<%=basePath%>setting/setting/${appid}?openid=${openid}"
			}, error: function(res){
				$.toast(res.msg, "forbidden");
			}
		});
    	return false;
    });
    
	$("#bln-switch").change(function(){
		var bln = "${status.bln}";
		if($("#bln").prop('checked') == true){
			bln = 1;
		}else{
			bln = 0;
		}
		
		$.ajax({
        	url: "<%=basePath %>setting/controlConfig/${appid}",
        	type: "post",
        	data: {	
        		"openid" : "${openid}",
        		"param" : bln,
        		"type" : "BLN"
        	},
        	beforeSend: function(res){
        		$.showLoading("数据处理中");
        	},
        	success: function(res){
        		setTimeout(function() {
        		  if(res.status == "SUCCESS"){
        				<%-- location.href="<%=basePath%>setting/setting/${appid}" --%>
        				console.log(res.data.bln)
        		  }
		          $.hideLoading();
		        }, 1000);
        	}, error: function(res){
        		$.toast("网络连接失败，请联系管理员!", "forbidden");
        		$.hideLoading();
        	}
        });
		return false;
	});
	
	$("#lbi-switch").change(function(){
		var lbi = "${status.lbi}";
		if($("#lbi").prop('checked') == true){
			lbi = 1;
		}else{
			lbi = 0;
		}
		
		$.ajax({
        	url: "<%=basePath %>setting/controlConfig/${appid}",
        	type: "post",
        	data: {
       			"openid" : "${openid}",
       			"param" : lbi,
       			"type" : "LBI"
       		},
        	beforeSend: function(res){
        		$.showLoading("数据处理中");
        	},
        	success: function(res){
        		setTimeout(function() {
	        		if(res.status == "SUCCESS"){
		        		console.log(res.data.lbi)
		        	}
			        $.hideLoading();
		        }, 1000);
        	}, error: function(res){
        		$.toast("网络连接失败，请联系管理员!", "forbidden");
        		$.hideLoading();
        	}
        });
        return false;
	});
	
	$(document).on("click", "#recovery-setting", function() {
        $.confirm("恢复出厂设置将清除设备除网络设置外的其他设置，请确定这么做吗?", "确认恢复出厂设置", function() {
        	$.ajax({
	        	url: "<%=basePath %>setting/controlConfig/${appid}",
	        	type: "post",
	        	data: {
	        		"openid" : "${openid}",
        			"param" : 1,
        			"type" : "RCV"
	        	},
	        	beforeSend: function(res){
	        		$.showLoading("数据处理中");
	        	},
	        	success: function(res){
	        		setTimeout(function() {
	        		  if(res.status == "SUCCESS"){
		        			$.toast("设备已恢复出厂设置!");
		        	  }else{
		        			$.toast("设备恢复出厂设置失败!", "forbidden");
		        	  }
			          $.hideLoading();
			        }, 1000);
	        	}, error: function(res){
	        		$.toast("网络连接失败，请联系管理员!", "forbidden");
	        		$.hideLoading();
	        	}
	        });
	        return false;
        }, function() {
          	$.toast("取消操作", "cancel", function(toast) {
	          console.log(toast);
	        });
        });
    });
    
    $(document).on("click", "#format-storage", function() {
        $.confirm("格式化存储将清除存储卡上的所有内容，请确定这么做吗?", "确认格式化存储", function() {
          	$.ajax({
	        	url: "<%=basePath %>setting/controlConfig/${appid}",
	        	type: "post",
	        	data: {
	        		"openid" : "${openid}",
        			"param" : 1,
        			"type" : "FMT"
	        	},
	        	beforeSend: function(res){
	        		$.showLoading("数据处理中");
	        	},
	        	success: function(res){
	        		setTimeout(function() {
		        		if(res.status == "SUCCESS"){
		        			$.toast("设备存储已格式化!");
			        	}else{
			        		$.toast("设备存储格式化失败!", "forbidden");
			        	}
				        $.hideLoading();
			        }, 1000);
	        	}, error: function(res){
	        		$.toast("网络连接失败，请联系管理员!", "forbidden");
	        		$.hideLoading();
	        	}
	        });
	        return false;
        }, function() {
          	//取消操作
          	$.toast("取消操作", "cancel", function(toast) {
	          console.log(toast);
	        });
        });
    });
    
    $(document).on("click", "#show-custom", function() {
        $.modal({
          title: "",
          text: "请前往【微信-我-设备-设备页面】，找到该设备并点击“删除设备”。",
          buttons: [
            { text: "我知道了", onClick: function(){console.log("我知道了"); } }
          ]
        });
    });
    
</script>
</html>