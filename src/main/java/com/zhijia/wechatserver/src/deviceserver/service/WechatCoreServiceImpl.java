package com.zhijia.wechatserver.src.deviceserver.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijia.wechatserver.src.common.message.WechatMessageInfo;
import com.zhijia.wechatserver.src.common.message.WechatMessageModelUtil;
import com.zhijia.wechatserver.src.common.message.WechatMessageUtil;
import com.zhijia.wechatserver.src.common.redis.RedisService;
import com.zhijia.wechatserver.src.common.utils.HttpUtils;
import com.zhijia.wechatserver.src.common.utils.TTSUtils;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAuthorize;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceMqtt;
import com.zhijia.wechatserver.src.deviceserver.entity.message.TextMessage;
import com.zhijia.wechatserver.src.deviceserver.entity.mutualMsg.VoiceMsg;
import com.zhijia.wechatserver.src.deviceserver.entity.user.UserBind;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceAuthorizeService;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceMqttService;
import com.zhijia.wechatserver.src.deviceserver.service.mutualMsg.MutualMsgService;
import com.zhijia.wechatserver.src.deviceserver.service.user.UserService;
import com.zhijia.wechatserver.src.mqttserver.entity.MqttAcl;
import com.zhijia.wechatserver.src.mqttserver.entity.MqttUser;
import com.zhijia.wechatserver.src.mqttserver.service.MqttService;

import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:06:21
 * @description: 微信消息处理核心service实现类
 *
 */
@Service("weixinCoreService")
public class WechatCoreServiceImpl implements WechatCoreService {
	private static Logger logger = LoggerFactory.getLogger(WechatCoreServiceImpl.class);

	@Autowired
	private WechatMessageUtil weixinMessageUtil;
	@Autowired
	private WechatMessageInfo wechatMessageInfo;
	@Autowired
	private WechatMessageModelUtil wechatMessageModelUtil;
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private MutualMsgService mutualMsgService;
	@Autowired
	private DeviceMqttService deviceMqttService;
	@Autowired
	private WechatCoreService wechatCoreService;
	@Autowired
	private DeviceAuthorizeService deviceAuthorizeService;
	@Autowired
	private MqttService mqttService;
	
	/**
	 * 处理微信服务器响应的消息，并返回到微信公众号
	 */
	public String weixinMessageHandelCoreService(HttpServletRequest request, HttpServletResponse response, String appid) {
		// 返回给微信服务器的消息,默认为null
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = null;
			// xml分析
			// 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> map = weixinMessageUtil.parseXml(request);
			
			// 发送方账号
			wechatMessageInfo.setFromUserName(map.get("FromUserName"));
			// 接受方账号（公众号）
			wechatMessageInfo.setToUserName(map.get("ToUserName"));
			// 接收绑定的设备
			if(!Utils.isObjNull(map.get("DeviceID"))){
				wechatMessageInfo.setDeviceId(map.get("DeviceID"));
			}
			
			// 消息类型
			String msgType = map.get("MsgType");
			wechatMessageInfo.setMessageType(msgType);
			logger.info("fromUserName is:" + map.get("FromUserName") + " toUserName is:" + map.get("ToUserName") + " msgType is:" + msgType);

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(map.get("FromUserName"));
			textMessage.setFromUserName(map.get("ToUserName"));
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(weixinMessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 分析用户发送的消息类型，并作出相应的处理
			if(msgType.equals(weixinMessageUtil.EVENT_TYPE_BIND)){
				String[] devices = map.get("DeviceID").split("_");
				logger.info("################################################");
				logger.info("###################【绑定设备】####################");
				logger.info("绑定对象：" + devices);
				logger.info("################################################");
				DeviceMqtt deviceMqtt = new DeviceMqtt();
				deviceMqtt.setOpenid(map.get("OpenID"));
				deviceMqtt.setZjkey(devices[0]);
				deviceMqtt.setDeviceId(devices[1]);
				deviceMqtt.setTopic(map.get("DeviceID"));
				deviceMqtt.setMqttBind(1);
				deviceMqtt.setStatus(1);
				deviceMqtt.setIsOnline(1);
				
				List<DeviceMqtt> dms = deviceMqttService.selectMqttBindForDeviceListByOpenId(map.get("OpenID"));
				for (DeviceMqtt dm : dms) {
					dm.setMqttBind(0);	
					
					deviceMqttService.updateDeviceMqttBindForParams(dm);
				}
				deviceMqttService.insertDeviceMqtt(deviceMqtt);
				//tb_user_bind
				UserBind userbind = new UserBind();
				userbind.setOpenid(map.get("OpenID"));
				userbind.setDeviceId(map.get("DeviceID"));
				userbind.setIsBind(1);
				userbind.setBindDate(new Date());
				userService.insertUserBind(userbind);
				
				DeviceAuthorize device = deviceAuthorizeService.selectDevciceByParams(devices[0], devices[1]); 
				if(!Utils.isObjNull(device)){
					device.setIsBind((device.getIsBind() == null || device.getIsBind() == 0) ? 1 : device.getIsBind() + 1);
					deviceAuthorizeService.updateAuthorizeDevice(device);
				}
				
				MqttAcl mqttAcl = new MqttAcl();
				mqttAcl.setUsername(map.get("OpenID"));
				mqttAcl.setClientid(null);
				mqttAcl.setTopic(map.get("DeviceID"));
				mqttAcl.setAccess(3); // 默认可订阅/可发布
				mqttAcl.setAllow(1);
				mqttAcl.setIpaddr("");
				mqttAcl.setStatus(1);
				mqttService.insertMqttAcl(mqttAcl);
				
				logger.info("############################微信用户绑定设备通知####################################");
				JSONObject text = new JSONObject();
				text.put("content", "绑定成功");
				
				JSONObject params = new JSONObject();
				params.put("touser", map.get("OpenID"));
				params.put("msgtype", "text");
				params.put("text", text);
				String sendmsg = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
				// 获取微信平台调用凭据，上传title
				String token_key = appid + "_atoken";
				String access_token = (String) redisService.get(token_key);
				logger.info(HttpUtils.sendPost(sendmsg.replaceAll("ACCESS_TOKEN", access_token), params.toString(), "application/json"));
				logger.info("#########################################################################");
			}
			// 解绑设备
			else if (msgType.equals(weixinMessageUtil.EVENT_TYPE_UNBIND)){
				String[] devices = map.get("DeviceID").split("_");
				DeviceAuthorize device = deviceAuthorizeService.selectDevciceByParams(devices[0], devices[1]); 
				if(!Utils.isObjNull(device)){
					device.setIsBind((device.getIsBind() == null || device.getIsBind() == 0) ? 0 : device.getIsBind() - 1);
					deviceAuthorizeService.updateAuthorizeDevice(device);
					
					UserBind userbind = new UserBind();
					userbind.setOpenid(map.get("OpenID"));
					userbind.setDeviceId(map.get("DeviceID"));
					userbind.setIsBind(0);
					userbind.setUnbindDate(new Date());;
					userService.updateUserBind(userbind);
					
					// update设备状态
					DeviceMqtt deviceMqttUnBind = deviceMqttService.selectMqttBindForDevice(map.get("OpenID"), devices[0], devices[1]);
					deviceMqttUnBind.setMqttBind(0);
					deviceMqttUnBind.setStatus(0);
					deviceMqttService.insertDeviceMqtt(deviceMqttUnBind);
					
					// 删除解绑后的设备设置的闹钟信息
					/*List<DeviceClock> clocks = deviceClockService.selectDeviceClockListByParams(map.get("OpenID"), devices[0], devices[1]);
					if(!Utils.isObjNull(clocks) && clocks.size() > 0){
						for (DeviceClock deviceClock : clocks) {
							deviceClockService.deleteDeviceClock(deviceClock);
						}
					}*/
					
					// 默认将最近的一台设备设置为绑定关联状态
					List<DeviceMqtt> deviceMqttBinds = deviceMqttService.selectMqttBindForDeviceList(map.get("OpenID"));
					if(!Utils.isObjNull(deviceMqttBinds) && deviceMqttBinds.size() > 0){
						DeviceMqtt deviceMqtt = deviceMqttBinds.get(0);
						deviceMqtt.setMqttBind(1);
						deviceMqttService.insertDeviceMqtt(deviceMqtt);
					}
					
					MqttAcl mqttAcl = new MqttAcl();
					mqttAcl.setUsername(map.get("OpenID"));
					mqttAcl.setClientid(null);
					mqttAcl.setTopic(map.get("DeviceID"));
					mqttAcl.setAccess(3); // 默认可订阅/可发布
					mqttAcl.setAllow(1);
					mqttAcl.setIpaddr("");
					mqttAcl.setStatus(0);
					mqttService.insertMqttAcl(mqttAcl);
				}
				
				logger.info("############################微信用户解绑设备通知####################################");
				JSONObject text = new JSONObject();
				text.put("content", "解绑成功");
				
				JSONObject params = new JSONObject();
				params.put("touser", map.get("OpenID"));
				params.put("msgtype", "text");
				params.put("text", text);
				String sendmsg = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
				// 获取微信平台调用凭据，上传title
				String token_key = appid + "_atoken";
				String access_token = (String) redisService.get(token_key);
				logger.info(HttpUtils.sendPost(sendmsg.replaceAll("ACCESS_TOKEN", access_token), params.toString(), "application/json"));
				logger.info("#########################################################################");
			}
			// 文本消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				boolean isSend = false;
				DeviceMqtt dmt = deviceMqttService.selectMqttBindForDevice(wechatMessageInfo.getFromUserName());
				if (Utils.isObjNull(dmt)) {
					respContent = "没有绑定设备！";
					textMessage.setContent(respContent); 
					respMessage = weixinMessageUtil.textMessageToXml(textMessage);
				}else{
					String token_key = appid + "_atoken";
					String access_token = (String) redisService.get(token_key);
					String mediaId = TTSUtils.getTTS(access_token, "TEXTMSG", map.get("Content"));
					
					JSONObject msg = new JSONObject();
					msg.put("media_id", mediaId);
					
					isSend = sentMQTTMsg(dmt.getOpenid(), dmt.getOpenid(), dmt.getTopic(), msg, 3);
				}
		        if(isSend){
		        	respContent = "消息已成功发送！";
		        }else{
		        	respContent = "消息未成功发送，请检查设备是否连接！";
		        }
		        textMessage.setContent(respContent);
				respMessage = weixinMessageUtil.textMessageToXml(textMessage);
			}

			// 图片消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
				textMessage.setContent(respContent);
				respMessage = weixinMessageUtil.textMessageToXml(textMessage);
			}
			// 语音消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				// MQTT 服务如果断开，即重新连接
				boolean isSend = false;
				logger.info("获取微信{" + wechatMessageInfo.getFromUserName() + "}是否绑定设备");
				DeviceMqtt dmt = deviceMqttService.selectMqttBindForDevice(wechatMessageInfo.getFromUserName());
				if (Utils.isObjNull(dmt)) {
					respContent = "没有绑定设备！";
					textMessage.setContent(respContent); 
					respMessage = weixinMessageUtil.textMessageToXml(textMessage);
				}else{
					JSONObject msg = new JSONObject();
					msg.put("media_id", map.get("MediaId"));
					
					isSend = sentMQTTMsg(dmt.getOpenid(), dmt.getOpenid(), dmt.getTopic(), msg, 3);
				}
				
				// 保存语音记录
				VoiceMsg voiceMsg = new VoiceMsg();
				voiceMsg.setMsgId(Long.parseLong(map.get("MsgId")));
				voiceMsg.setToUserName(map.get("ToUserName"));
				voiceMsg.setFromUserName(map.get("FromUserName"));
				voiceMsg.setDeviceId(map.get("DeviceID"));
				voiceMsg.setMsgType(map.get("MsgType"));
				voiceMsg.setMediaId(map.get("MediaId"));
				voiceMsg.setFormat(map.get("Format"));
				voiceMsg.setRecognition(map.get("Recognition"));
				long createtime = Long.parseLong(map.get("CreateTime"));
				voiceMsg.setCreateTime(createtime);
				voiceMsg.setCreateTimeTf(new Date(createtime));
				voiceMsg.setRatained(isSend);
				mutualMsgService.insertVoiceMsg(voiceMsg);
			}

			// 视频消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
				textMessage.setContent(respContent);
				respMessage = weixinMessageUtil.textMessageToXml(textMessage);
			}

			// 地理位置消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您当前所在的位置是：" + map.get("Label");
				textMessage.setContent(respContent);
				respMessage = weixinMessageUtil.textMessageToXml(textMessage);
			}

			// 链接消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				textMessage.setContent(respContent);
				respMessage = weixinMessageUtil.textMessageToXml(textMessage);
			}

			// 事件推送(当用户主动点击菜单，或者扫面二维码等事件)
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_EVENT)) {

				// 事件类型
				String eventType = map.get("Event");
				// 关注
				if (eventType.equals(weixinMessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respMessage = wechatMessageModelUtil.followResponseMessageModel(wechatMessageInfo);
				}
				// 取消关注
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					if(!Utils.isObjNull(mqttServer.client)){
						mqttServer.disconnect(mqttServer.client);
					}
					
					wechatMessageModelUtil.cancelAttention(map.get("FromUserName"), map.get("ToUserName"));
				}
				// 扫描带参数二维码
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_SCAN)) {
					logger.info("扫描带参数二维码");
				}
				// 上报地理位置
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_LOCATION)) {
					logger.info("上报地理位置");
				}
				// 自定义菜单（点击菜单拉取消息）
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = map.get("EventKey");
					logger.info("eventKey------->" + eventKey);
					
				}
				// 自定义菜单（自定义菜单URl视图）
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_VIEW)) {
					logger.info("处理自定义菜单URI视图");
				}
			}
			// 设备点击事件
			else if(msgType.equals(weixinMessageUtil.EVENT_TYPE_DEVICE_EVENT)){
				// 事件类型
				String eventType = map.get("Event");
				logger.info("#####################################################");
				logger.info("设备点击事件:" + eventType);
				logger.info("#####################################################");
				if(weixinMessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)){
					MqttUser mqttUser = new MqttUser();
					mqttUser.setUsername(wechatMessageInfo.getFromUserName());
					mqttUser.setPassword(wechatMessageInfo.getFromUserName());
					mqttUser.setSalt("");
					mqttUser.setIs_superuser(0);
					mqttUser.setStatus(1);
					mqttUser.setCreated(new Date());
					mqttService.insertMqttUser(mqttUser);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统出错");
			System.err.println("系统出错");
			respMessage = null;
		} /*finally {
			if (null == respMessage) {
				respMessage = wechatMessageModelUtil.systemErrorResponseMessageModel(wechatMessageInfo);
			}
		}*/

		return respMessage;
	}

	public JSONObject getAccess_token(String appid, String secret) throws HttpException, IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
		JSONObject json = JSONObject.fromObject(HttpUtils.sendGet(url, "utf-8"));

		return json;
	}

	public JSONObject getDeviceQrcode(String accesstoken, JSONObject params) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		String url = "https://api.weixin.qq.com/device/create_qrcode?access_token=" + accesstoken;
		
		return JSONObject.fromObject(HttpUtils.sendPost(url, params.toString(), "application/json"));
	}

	public JSONObject deviceAuthorize(String accesstoken, JSONObject params) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException, URISyntaxException {
		String url = "https://api.weixin.qq.com/device/authorize_device?access_token=" + accesstoken;
		return JSONObject.fromObject(HttpUtils.sendPost(url, params.toString(), "application/json"));
	}

	public JSONObject deviceBind(String accesstoken, JSONObject params) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		String url = "https://api.weixin.qq.com/device/bind?access_token=" + accesstoken;
		return JSONObject.fromObject(HttpUtils.sendPost(url, params.toString(), "application/json"));
	}

	public int getDeviceStatuc(String accesstoken, String deviceid) {
		int status = 0;
		try {
			String url = "https://api.weixin.qq.com/device/get_stat?access_token=" + accesstoken + "&device_id=" + deviceid;
			JSONObject json = JSONObject.fromObject(HttpUtils.sendGet(url, "utf-8"));
			
			System.out.println("************************" + json);
			
			status = json.getInt("status");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	@SuppressWarnings("static-access")
	private boolean sentMQTTMsg(String username, String deviceId, String topic, JSONObject msg, int type) throws MqttException{
		boolean flag = false;
		try {
			// 每条信息睡眠半秒后在处理
			CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
			ExecutorService executorService = Executors.newFixedThreadPool(1);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread thread = new Thread(new Runnable() {
    			MqttClient client = wechatCoreService.mqttServer.client;
    			MqttMessage message = null;
    			@Override
    			public void run() {
    				try {
    					cyclicBarrier.await();
    					
    	    			if(client != null && client.isConnected()){
    	    				wechatCoreService.mqttServer.disconnect(client);
    	    			}
    	    			// MQTT 服务如果断开，即重新连接
    	    			if (Utils.isObjNull(client) || !client.isConnected()) {
    	    				client = wechatCoreService.mqttServer.connect(username, username, deviceId);
    	    			}
    	    			
    	    			MqttTopic mqtt_tpoic = client.getTopic(topic);
    	    			mqttServer.subscribe(mqtt_tpoic.getName(), 2);
    	    			
    	    			JSONObject json_message = new JSONObject();
    	    			json_message.put("sender", 1);
    	    			json_message.put("type", type);
    	    			json_message.put("message", msg);
    	    			
    	    			logger.info("推送的消息：" + json_message.toString());
    	    			
    	    			message = new MqttMessage();
    	    			message.setQos(2);
    	    			message.setRetained(true);
    	    			message.setPayload(json_message.toString().getBytes());
    	    			mqttServer.publish(mqtt_tpoic, message);
    	    			logger.info(message.isRetained() + "------ratained状态");
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				} catch (BrokenBarrierException e) {
    					e.printStackTrace();
    				} catch (MqttException e) {
    					e.printStackTrace();
    				}
    			}
    		});
    		thread.setName("name：" + type);
            executorService.submit(thread);
            flag = true;
            logger.info("*********************MQTT信息处理*********************");
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
}
