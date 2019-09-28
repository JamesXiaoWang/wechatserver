<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加闹钟</title>
    
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	<!-- // 配合 mate禁用 缓存标签，实现禁用浏览器缓存（实现原理，自动刷新） -->
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
	<meta HTTP-EQUIV="expires" CONTENT="0">
	
	<link rel="stylesheet" href="<%=basePath%>css/weui.min.css">
	<link rel="stylesheet" href="<%=basePath%>css/jquery-weui.css">
	<link rel="stylesheet" href="<%=basePath%>css/demos.css">
	
	<script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/fastclick.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery-weui.js"></script>
	<style type="text/css">
		.weui-grid {
			position: relative;
			float: left;
			width: 33.33333333%;
			box-sizing: border-box
		}
	</style>
  </head>
  
  <body>
    <div class='demos-content-padded'>
		<div class="weui-cells__title">时间</div>
		<div class="weui-cells">
			<div class="weui-cell">
	            <div class="weui-cell__bd">
	            	<c:choose>
	            		<c:when test="${deviceClock.clock != null }">
	            			<input class="weui-input datetimepicker" style="text-align: left; font-size: 24px;" name="clock" id="datetime-picker" type="text" value="${deviceClock.clock }" readonly="readonly">
	            		</c:when>
	            		<c:otherwise>
	            			<input class="weui-input datetimepicker" style="text-align: left; font-size: 24px;" name="clock" id="datetime-picker" type="text" value="00 : 00" readonly="readonly">
	            		</c:otherwise>
	            	</c:choose>
	            </div>
	        </div>
		</div>
		
		<div class="weui-cells__title">重复</div>
		<div class="weui-cells">
			<div class="weui-grids">
		      <div class="weui-grid js_grid">
		        	<div class="weui-cells_checkbox">
			      		<label class="weui-cell weui-check__label" for="s0">
				          <div class="weui-cell__hd">
				          	<c:choose>
				          		<c:when test="${fn:contains(deviceClock.repeatTime, '0')}">
				          			<input type="checkbox" class="weui-check" name="repeat" id="s0" value="0" checked="checked"> 
				          		</c:when>
				          		<c:otherwise>
				          			<input type="checkbox" class="weui-check" name="repeat" id="s0" value="0"> 
				          		</c:otherwise>
				          	</c:choose>
							<i class="weui-icon-checked"></i>			
				          </div>
				          <div class="weui-cell__bd">
				            <p>日</p>
				          </div>
				        </label>
			        </div>
		      </div>
		      <div class="weui-grid js_grid">
		        	<div class="weui-cells_checkbox">
			      		<label class="weui-cell weui-check__label" for="s1">
				          <div class="weui-cell__hd">
				          	<c:choose>
				          		<c:when test="${fn:contains(deviceClock.repeatTime, '1')}">
				          			<input type="checkbox" class="weui-check" name="repeat" id="s1" value="1" checked="checked"> 
				          		</c:when>
				          		<c:otherwise>
				          			<input type="checkbox" class="weui-check" name="repeat" id="s1" value="1"> 
				          		</c:otherwise>
				          	</c:choose>
							<i class="weui-icon-checked"></i>			
				          </div>
				          <div class="weui-cell__bd">
				            <p>一</p>
				          </div>
				        </label>
			        </div>
		      </div>
		      <div class="weui-grid js_grid">
		        	<div class="weui-cells_checkbox">
			      		<label class="weui-cell weui-check__label" for="s2">
				          <div class="weui-cell__hd">
				          	<c:choose>
				          		<c:when test="${fn:contains(deviceClock.repeatTime, '2')}">
				          			<input type="checkbox" class="weui-check" name="repeat" id="s2" value="2" checked="checked"> 
				          		</c:when>
				          		<c:otherwise>
				          			<input type="checkbox" class="weui-check" name="repeat" id="s2" value="2"> 
				          		</c:otherwise>
				          	</c:choose>
							<i class="weui-icon-checked"></i>			
				          </div>
				          <div class="weui-cell__bd">
				            <p>二</p>
				          </div>
				        </label>
			        </div>
		      </div>
		      <div class="weui-grid js_grid">
		        	<div class="weui-cells_checkbox">
			      		<label class="weui-cell weui-check__label" for="s3">
				          <div class="weui-cell__hd">
				          	<c:choose>
				          		<c:when test="${fn:contains(deviceClock.repeatTime, '3')}">
				          			<input type="checkbox" class="weui-check" name="repeat" id="s3" value="3" checked="checked"> 
				          		</c:when>
				          		<c:otherwise>
				          			<input type="checkbox" class="weui-check" name="repeat" id="s3" value="3"> 
				          		</c:otherwise>
				          	</c:choose>
							<i class="weui-icon-checked"></i>			
				          </div>
				          <div class="weui-cell__bd">
				            <p>三</p>
				          </div>
				        </label>
			        </div>
		      </div>
		      <div class="weui-grid js_grid">
		        	<div class="weui-cells_checkbox">
			      		<label class="weui-cell weui-check__label" for="s4">
				          <div class="weui-cell__hd">
				          	<c:choose>
				          		<c:when test="${fn:contains(deviceClock.repeatTime, '4')}">
				          			<input type="checkbox" class="weui-check" name="repeat" id="s4" value="4" checked="checked"> 
				          		</c:when>
				          		<c:otherwise>
				          			<input type="checkbox" class="weui-check" name="repeat" id="s4" value="4"> 
				          		</c:otherwise>
				          	</c:choose>
							<i class="weui-icon-checked"></i>			
				          </div>
				          <div class="weui-cell__bd">
				            <p>四</p>
				          </div>
				        </label>
			        </div>
		      </div>
		      <div class="weui-grid js_grid">
			     <div class="weui-cells_checkbox">
			    		<label class="weui-cell weui-check__label" for="s5">
				         <div class="weui-cell__hd">
				         	<c:choose>
				          		<c:when test="${fn:contains(deviceClock.repeatTime, '5')}">
				          			<input type="checkbox" class="weui-check" name="repeat" id="s5" value="5" checked="checked"> 
				          		</c:when>
				          		<c:otherwise>
				          			<input type="checkbox" class="weui-check" name="repeat" id="s5" value="5"> 
				          		</c:otherwise>
				          	</c:choose>
							<i class="weui-icon-checked"></i>			
				         </div>
				         	<div class="weui-cell__bd">
				           <p>五</p>
				         </div>
				       </label>
			      </div>
		      </div>
		      <div class="weui-grid js_grid">
			     <div class="weui-cells_checkbox">
			    		<label class="weui-cell weui-check__label" for="s6">
				         <div class="weui-cell__hd">
				         	<c:choose>
				          		<c:when test="${fn:contains(deviceClock.repeatTime, '6')}">
				          			<input type="checkbox" class="weui-check" name="repeat" id="s6" value="6" checked="checked"> 
				          		</c:when>
				          		<c:otherwise>
				          			<input type="checkbox" class="weui-check" name="repeat" id="s6" value="6"> 
				          		</c:otherwise>
				          	</c:choose>
							<i class="weui-icon-checked"></i>			
				         </div>
				         <div class="weui-cell__bd">
				           <p>六</p>
				         </div>
				       </label>
			      </div>
		      </div>
			</div>
		</div>
		<br/>
		<a href="javascript:;" class="weui-btn weui-btn_primary" id="apply" style="margin-top: 25px;">确认</a>
		<c:if test="${deviceClock.id != null }">
			<a href="javascript:;" class="weui-btn weui-btn_warn" id="delete">删除</a>
		</c:if>
	</div>
	<script>
	  $(function() {
	    	FastClick.attach(document.body);
	  });
	</script>
	<script type="text/javascript">
		$(document).on("click", ".datetimepicker", function(){
			var id_attr = $(this).attr("id");
			$("#" + id_attr).focus();
			
			$("#" + id_attr).picker({ //开始时间初始化
				title : "请选择闹钟时间",
				cols : [
					{
						textAlign : 'center',
						values : [ '00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23' ]
					},
					{
						textAlign : 'center',
						values : [ ':' ]
					},
					{
						textAlign : 'center',
						values : [ '00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50', '51', '52', '53', '54', '55', '56', '57', '58', '59' ]
					}
				]
			});
		});
		
		$("#apply").click(function(){
	        applyClock(1);
		});
		$("#delete").click(function(){
	        applyClock(0);
		});
		
		function applyClock(status){
			var clock = $("#datetime-picker").val();
	        if(clock == null){
	        	alert("闹钟时间不能为空！");
	        	return false;
	        }
	        
	        var repeat =[];
	        $("input:checkbox[name=repeat]:checked").each(function(){
	        	if($(this).val()!= ""){
	        		repeat.push($(this).val());
	        	}
	        });
			$.ajax({
	        	url: "<%=basePath %>setting/controlConfig/${appid}",
	        	type: "post",
	        	data: {	
	        		"openid" : "${openid}",
        			"param" : status,
        			"clock" : clock,
        			"repeat" : repeat.join(","),
        			"type" : "CLO",
        			"ctype" : "${ctype}",
        			"clockid" : "${deviceClock.id}"
	        	},
	        	beforeSend: function(res){
	        		$.showLoading("数据处理中");
	        	},
	        	success: function(res){
	        		setTimeout(function() {
	        		  if(res.status == "SUCCESS"){
	        			 location.href="<%=basePath%>setting/getAlarmClock/${appid}?openid=${openid}&device=${device}"
	        		  }
			          $.hideLoading();
			        }, 1000);
			        return false;
	        	}, error: function(res){
	        		alert("网络连接失败，请联系管理员!");
	        		$.hideLoading();
	        	}
	        });
	        return false;
		}
	</script>
  </body>
</html>
