package com.zhijia.wechatserver.src.deviceserver.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSON;
import org.apache.http.HttpException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhijia.wechatserver.src.common.JSONResult;
import com.zhijia.wechatserver.src.common.base.BaseController;
import com.zhijia.wechatserver.src.common.utils.HttpUtils;
import com.zhijia.wechatserver.src.common.utils.TTSUtils;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.DeviceChat;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAudioStatus;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAuthorize;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceMqtt;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceStatus;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceList;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;
import com.zhijia.wechatserver.src.mqttserver.entity.MqttAcl;
import com.zhijia.wechatserver.src.mqttserver.entity.MqttUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2019年1月11日 下午2:35:59
 * @description: 设备交互控制器
 *
 */
@RestController
@RequestMapping(value = "/deviceInteraction")
public class DeviceInteractionController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(DeviceInteractionController.class);
	
	private static final String AUDIO_MQTT_USERNAME = "equipment_operato";	// 设备操作者username（MQTT）
	private static final String AUDIO_MQTT_PASSWORD = "equipment_password";	// 设备操作者password（MQTT）
	private static final String AUDIO_MQTT_CLIENTID = "equipment_client";

	/**
	 * 微信设备授权
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deviceauthorize/{appid}")
	public JSONResult deviceauthorize(@PathVariable("appid") String appid, @RequestBody JSONObject obj){
		logger.info("#设备授权开始");
		JSONResult result = null;
		try {
			// 智佳key
			String zjkey = obj.getString("zjkey");
			// 产品ID
			String productId = obj.getString("productId");
			// 设备ID
			String deviceid = obj.getString("deviceid");
			/* 连接方式：
			 * 3 - BLE 蓝牙
			 * 4 - WIFI
			 * */
			//String connectProtocol = obj.getString("connectProtocol");
			String connectProtocol = "4";
			
			// 获取微信平台调用凭据，上传title
			String token_key = appid + "_atoken";
			String access_token = (String) redisService.get(token_key);

			String checkdevice = zjkey.trim() + "_" + deviceid;
			String status_url = "https://api.weixin.qq.com/device/get_stat?access_token=" + access_token + "&device_id=" + checkdevice;
			JSONObject status_json = JSONObject.fromObject(HttpUtils.sendGet(status_url, "utf-8"));
			logger.info("# 设备状态 #" + status_json);
			int status = status_json.getInt("status");
			if (status == 0) {
				String auth_url = "https://api.weixin.qq.com/device/authorize_device?access_token=" + access_token;
				// 设备授权
				JSONObject jsonb = new JSONObject();
				jsonb.put("id", checkdevice);
				jsonb.put("mac", deviceid.substring(4, deviceid.length()));
				jsonb.put("connect_protocol", connectProtocol); // 连接方式
				jsonb.put("auth_key", "");
				jsonb.put("close_strategy", "1");
				jsonb.put("conn_strategy", "1");
				jsonb.put("crypt_method", "0");
				jsonb.put("auth_ver", "0");
				jsonb.put("manu_mac_pos", "-1");
				jsonb.put("ser_mac_pos", "-2");

				JSONArray json_array = new JSONArray();
				json_array.add(jsonb);

				JSONObject jsona = new JSONObject();
				jsona.put("device_num", json_array.size());
				jsona.put("device_list", json_array);
				jsona.put("op_type", "0");
				jsona.put("product_id", productId.trim());
				JSONObject resp = JSONObject.fromObject(HttpUtils.sendPost(auth_url, jsona.toString(), "application/json"));
				logger.info("# 设备授权结果 #" + resp);
				int auth_errcode = resp.getJSONArray("resp").getJSONObject(0).getInt("errcode");
				if (auth_errcode == 0) {
					DeviceAuthorize deviceAuthorize = new DeviceAuthorize();
					deviceAuthorize.setZjkey(zjkey);
					deviceAuthorize.setProductId(productId);
					deviceAuthorize.setDeviceId(deviceid);
					deviceAuthorize.setMac(deviceid.substring(4, deviceid.length()));
					deviceAuthorize.setConnectProtocol("4");
					deviceAuthorize.setAuthKey("");
					deviceAuthorize.setCloseStrategy("1");
					deviceAuthorize.setConnStrategy("1");
					deviceAuthorize.setCryptMethod("0");
					deviceAuthorize.setAuthVer("0");
					deviceAuthorize.setManuMacPos("-1");
					deviceAuthorize.setSerMacPos("-2");
					deviceAuthorize.setIsAuthorize(1);
					int res = deviceAuthorizeService.insertAuthorizeDevice(deviceAuthorize);
					if(res <= 0){
						throw new RuntimeException("insert authorize device is error");
					}
					logger.info("[" + deviceid + "]授权成功。");
					result = new JSONResult("SUCCESS", "Authorization is successful.", "");
					
					// 注册MQTT信息
					registerMqtt(checkdevice, checkdevice, checkdevice);
				} else {
					logger.info(Utils.getRequestResultByErrorCode(auth_errcode));
					result = new JSONResult("ERROR", Utils.getRequestResultByErrorCode(auth_errcode), "");
				}
			}else{
				// 注册MQTT信息
				registerMqtt(checkdevice, checkdevice, checkdevice);

				logger.info("该设备[" + deviceid + "]已授权。");
				result = new JSONResult("SUCCESS", "This device [" + deviceid + "] is already licensed.", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("网络连接异常，设备授权失败，请重试或联系管理员！");
			result = new JSONResult("The network connection is abnormal and the device authorization fails. Please try again or contact the administrator!");
		}
		logger.info("#设备授权结束");
		return result;
	}
	
	/**
	 * 注册MQTT信息
	 * @param username	连接用户名
	 * @param password	连接密码
	 * @param topic		订阅/发布主题
	 */
	private void registerMqtt(String username, String password, String topic){
		MqttUser mqttuser = new MqttUser();
		mqttuser.setUsername(username);
		mqttuser.setPassword(password);
		mqttuser.setSalt("");
		mqttuser.setIs_superuser(0);
		mqttuser.setStatus(1);
		mqttuser.setCreated(new Date());
		mqttService.insertMqttUser(mqttuser);
		
		MqttAcl mqttacl = new MqttAcl();
		mqttacl.setUsername(username);
		mqttacl.setTopic(topic);
		mqttacl.setClientid(null);
		mqttacl.setAccess(3); // 默认可订阅/可发布
		mqttacl.setAllow(1);
		mqttacl.setIpaddr("");
		mqttacl.setStatus(1);
		mqttService.insertMqttAcl(mqttacl);

	}

	/**
	 * 设备上报状态
	 * 
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/status/{appid}")
	public JSONResult deviceStatusReport(@PathVariable("appid") String appid, @RequestBody JSONObject obj) {
		JSONResult result = null;
		try {
			String zjkey = obj.getString("zjkey");
			String deviceId = obj.getString("deviceId");
			int type = obj.getInt("type");

			if (Utils.isObjNull(zjkey) || Utils.isObjNull(deviceId)) {
				logger.info("必须请求参数缺失，无法正常请求！");
				return new JSONResult("The parameter must be missing and cannot be requested normally!");
			}

			logger.info("请求交互的主参数：zjkey = " + zjkey + ", deviceId = " + deviceId + ", type = " + type);
			
			JSONObject status = obj.getJSONObject("status");
			/*
			 * 当type=0：设备状态； 当type=1：音频状态；type=2: 设备消息；type=3: 过期token刷新； type=4: 对话消息记录
			 */

			switch (type) {
				case 0:
					applyDeviceStatus(zjkey, deviceId, status);
					result = new JSONResult("SUCCESS", "Status report success!", "");
					break;
				case 1:
					applyAudioStatus(zjkey, deviceId, appid, status);
					result = new JSONResult("SUCCESS", "audio status report success!", "");
					break;
				case 2:

					applyDeviceMessage(zjkey, deviceId, appid, status);
					result = new JSONResult("SUCCESS", "device message report success!", "");
					break;
				case 3:
					applyAccessTokenExpire(zjkey, deviceId, appid, status);
					result = new JSONResult("SUCCESS", "refresh access token success!", "");
					break;
				case 4:
					applyChatMessage(zjkey, deviceId, appid, status);
					result = new JSONResult("SUCCESS", "chat message report success!", "");
					break;
				default:
					logger.info("device request params type is error!");
					result = new JSONResult("ERROR", "device request params type is error!", "");
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("网络连接异常，设备状态上报失败，请重试或联系管理员！");
			result = new JSONResult("The network connection is abnormal and the device authorization fails. Please try again or contact the administrator!");
		}
		return result;
	}

	/**
	 * 处理设备上报的状态
	 * @param zjkey		zjkey唯一ID
	 * @param deviceId	设备ID
	 * @param status	状态信息【设备上报，json格式封装】
	 */
	public void applyDeviceStatus(String zjkey, String deviceId, JSONObject status) {
		logger.info("#");
		logger.info("设备状态信息：" + status.toString());
		logger.info("#");
		try {
			int vol = !Utils.isObjNull(status.get("vol")) ? Integer.parseInt(status.get("vol") + "") : 0;// 0 - 100 音量
			int battery = !Utils.isObjNull(status.get("battery")) ? Integer.parseInt(status.get("battery") + "") : 0;// 0 - 100 电量
			int sfree = !Utils.isObjNull(status.get("sfree")) ? Integer.parseInt(status.get("sfree") + "") : 0; // 当前设备存储量,单位KB
			int stotal = !Utils.isObjNull(status.get("stotal")) ? Integer.parseInt(status.get("stotal") + "") : 0; // 总容量,单位KB
			int shake = !Utils.isObjNull(status.get("shake")) ? Integer.parseInt(status.get("shake") + "") : 0;
			int power = !Utils.isObjNull(status.get("power")) ? Integer.parseInt(status.get("power") + "") : 0; // 1 低电量， 0 正常
			int bln = !Utils.isObjNull(status.get("bln")) ? Integer.parseInt(status.get("bln") + "") : 0;// 呼吸灯开关
			int play = !Utils.isObjNull(status.get("play")) ? Integer.parseInt(status.get("play") + "") : 0;// 歌曲播放状态
			int charging = !Utils.isObjNull(status.get("charging")) ? Integer.parseInt(status.get("charging") + "") : 0;// 0-未充电 1-充电中
			int lbi = !Utils.isObjNull(status.get("lbi")) ? Integer.parseInt(status.get("lbi") + "") : 0;// 低电量提醒开关 0-关闭 1-开启
			int tcard = !Utils.isObjNull(status.get("tcard")) ? Integer.parseInt(status.get("tcard") + "") : 0;// 0-T卡未插入 1-T卡已插入
			int online = !Utils.isObjNull(status.get("online")) ? Integer.parseInt(status.get("online") + "") : 0; // 设备是否在线（0-不在线，1-在线）
			JSONArray clock = status.getJSONArray("clock");

			DeviceStatus deviceStatus = new DeviceStatus();
			deviceStatus.setZjkey(zjkey);
			deviceStatus.setDeviceId(deviceId);
			deviceStatus.setVol(vol);
			deviceStatus.setBattery(battery);
			deviceStatus.setSfree(sfree);
			deviceStatus.setStotal(stotal);
			deviceStatus.setShake(shake);
			deviceStatus.setBln(bln);
			deviceStatus.setPlay(play);
			deviceStatus.setCharging(charging);
			deviceStatus.setLbi(lbi);
			deviceStatus.setTcard(tcard);
			deviceStatus.setPower(power);
			deviceStatus.setOnline(online);
			deviceStatus.setClock(!Utils.isObjNull(clock) ? clock.toString() : "");
			deviceStatusService.insertAndUpdateDeviceStatus(deviceStatus);
		} catch (Exception e) {
			logger.info("处理设备上报的状态时异常：" + e.getMessage());
		}
		logger.info("【" + deviceId + "】’Status report success! ");
	}

	/**
	 * 处理设备音频的状态
	 * @param zjkey		zjkey唯一ID
	 * @param deviceId	设备ID
	 * @param appid		微信唯一ID
	 * @param obj		状态信息【设备上报，json格式封装】
	 * @throws MqttException
	 * @throws HttpException
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public void applyAudioStatus(String zjkey, String deviceId, String appid, JSONObject obj) {
		try {
			logger.info("#");
			logger.info("设备音频请求参数：" + obj.toString());

			int play = obj.getInt("play");
			int audioId = obj.getInt("audioId");
			int resId = obj.getInt("resId");
			logger.info("#");

			DeviceAudioStatus audioStatus = new DeviceAudioStatus();
			audioStatus.setZjkey(zjkey);
			audioStatus.setDeviceId(deviceId);

			MqttClient client = wechatCoreService.mqttServer.client;

			//DeviceMqtt mqtt = deviceMqttService.selectMqttBindForDevice(username);
			String topic = zjkey + "_" + deviceId;
			
			JSONObject json_message = new JSONObject();
			json_message.put("sender", 1);
			json_message.put("type", 1);

			JSONObject audio = new JSONObject();

			// "play":0-下一首 ,1-上一首,2-播放，3-暂停
			PlayResourceList resource = resourceService.selectPlayResourceById(audioId);

			List<PlayResourceList> resourceLists = resourceService.selectPlayResourceList(resId);
			switch (play) {
			case 0:
				PlayResourceList lastprl = resourceLists.get(resourceLists.size() - 1);
				if (audioId == lastprl.getId() && resId == lastprl.getResId()) {
					resource = resourceLists.get(0);
				} else {
					for (int i = 0; i < resourceLists.size(); i++) {
						PlayResourceList resourceList = resourceLists.get(i);
						if (audioId == resourceList.getId()) {
							if (resourceLists.size() > i + 1) {
								resource = resourceLists.get(i + 1);
							}
						}
					}
				}
				audio = getAudioMsg(resource, 1, appid, zjkey);
				json_message.put("message", audio);

				logger.info("MQTT连接信息：" + AUDIO_MQTT_USERNAME + ", 主题：" + topic);
				audioStatus.setPlay(2);
				audioStatus.setAudioId(resource.getId());

				mqttSendMsg(client, AUDIO_MQTT_USERNAME, AUDIO_MQTT_PASSWORD, AUDIO_MQTT_CLIENTID, topic, json_message, audioStatus);

				logger.info("【" + deviceId + "】推送的内容时： " + json_message);
				
				break;
			case 1:
				PlayResourceList firstprl = resourceLists.get(0);
				if (audioId == firstprl.getId() && resId == firstprl.getResId()) {
					resource = resourceLists.get(resourceLists.size() - 1);
				} else {
					for (int i = 0; i < resourceLists.size(); i++) {
						PlayResourceList resourceList = resourceLists.get(i);
						if (audioId == resourceList.getId()) {
							if (i - 1 >= 0) {
								resource = resourceLists.get(i - 1);
							}
						}
					}
				}
				audio = getAudioMsg(resource, 1, appid, zjkey);
				json_message.put("message", audio);

				logger.info("MQTT连接信息：" + AUDIO_MQTT_USERNAME + ", 主题：" + topic);
				audioStatus.setPlay(2);
				audioStatus.setAudioId(resource.getId());
				mqttSendMsg(client, AUDIO_MQTT_USERNAME, AUDIO_MQTT_PASSWORD, AUDIO_MQTT_CLIENTID, topic, json_message, audioStatus);

				logger.info("【" + deviceId + "】推送的内容时： " + json_message);
				break;
			case 2:
				audioStatus.setPlay(2);
				audioStatus.setAudioId(audioId);
				audioStatusService.insertOrUpdateDeviceAudioStatus(audioStatus);
				break;
			case 3:
				audioStatus.setPlay(3);
				audioStatus.setAudioId(audioId);
				audioStatusService.insertOrUpdateDeviceAudioStatus(audioStatus);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("处理设备音频的状态时异常：" + e.getMessage());
		}
	}
	
	/**
	 * 处理设备发送到微信的消息
	 * @param zjkey		智佳唯一ID
	 * @param deviceId	设备ID
	 * @param appid		微信唯一ID
	 * @param object	参数（json格式封装）
	 */
	public void applyDeviceMessage(String zjkey, String deviceId, String appid, JSONObject object) {
		logger.info("处理设备消息");
		try {
			List<DeviceMqtt> mqtts = deviceMqttService.selectDeviceBindListForOpenId(zjkey, deviceId);
			// 获取微信平台调用凭据，上传title
			String token_key = appid + "_atoken";
			String access_token = (String) redisService.get(token_key);
			
			String mediaId = object.getString("mediaId");
			
			String sendmsg = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
			for (DeviceMqtt dmt : mqtts) {
				JSONObject voice = new JSONObject();
				voice.put("media_id", mediaId);
				
				JSONObject params = new JSONObject();
				params.put("touser", dmt.getOpenid());
				params.put("msgtype", "voice");
				params.put("voice", voice);
				logger.info("# 处理参数 #" + params.toString());
				
				JSONObject result = JSONObject.fromObject(HttpUtils.sendPost(sendmsg.replaceAll("ACCESS_TOKEN", access_token), params.toString(), "application/json"));
				logger.info("# 处理结果 #" + result);
				if(result.getInt("errcode") == 0){
					logger.info("设备语音发送成功。");
				}
			}
		} catch (Exception e) {
			logger.info("处理设备消息时异常：" + e.getMessage());
		}
		logger.info("#");
	}
	
	/**
	 * 处理设备端获取access token 过期, 请求刷新
	 * @param zjkey
	 * @param deviceId	设备ID
	 * @param appid		微信用户唯一ID
	 * @param object	请求参数体
	 */
	public void applyAccessTokenExpire(String zjkey, String deviceId, String appid, JSONObject object){
		logger.info("开始获取公众号的全局唯一接口调用凭据#");
		try {
			logger.info("请求的信息提示：" + object.toString());
			int errcode = object.getInt("errcode");
			if(errcode == 40001){
				WechatAuthorize authorize = wechatAuthorizeService.selectWechatAuthorizeByParams(appid, zjkey);
				String secret = authorize.getAppsecret();
				
				String token_key = appid + "_atoken";
				// 将访问的AccessTOKEN添加到Redis数据库中，超时7200s
				// 获取access——token（网页访问的唯一凭证）
				JSONObject jobj = wechatCoreService.getAccess_token(appid, secret);
				redisService.set(token_key, jobj.getString("access_token"), jobj.getLong("expires_in"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("处理设备端获取access token 过期时异常："  + e.getMessage());
		}
		logger.info("结束获取公众号的全局唯一接口调用凭据#");
	}

	@SuppressWarnings("static-access")
	public void mqttSendMsg(MqttClient client, String username, String password, String deviceId, String topic,
			JSONObject json_message, DeviceAudioStatus audioStatus) throws MqttException {
		if (client != null && client.isConnected()) {
			wechatCoreService.mqttServer.disconnect(client);
		}
		// MQTT 服务如果断开，即重新连接
		if (Utils.isObjNull(client) || !client.isConnected()) {
			client = wechatCoreService.mqttServer.connect(username, password, deviceId);
		}

		MqttTopic mqtt_tpoic = client.getTopic(topic);
		wechatCoreService.mqttServer.subscribe(mqtt_tpoic.getName(), 2);

		MqttMessage message = new MqttMessage();
		message.setQos(2);
		message.setRetained(true);
		message.setPayload(json_message.toString().getBytes());
		wechatCoreService.mqttServer.publish(mqtt_tpoic, message);

		audioStatusService.insertOrUpdateDeviceAudioStatus(audioStatus);
	}

	private JSONObject getAudioMsg(PlayResourceList resource, Integer status, String appid, String zjkey) throws HttpException, IOException {
		JSONObject audio = new JSONObject();
		if (!Utils.isObjNull(resource)) {
			WechatAuthorize wa = wechatAuthorizeService.selectWechatAuthorizeByParams(appid, zjkey);
			String secret = wa.getAppsecret();
			String token_key = appid + "_atoken";
			// 将访问的AccessTOKEN添加到Redis数据库中，超时7200s
			if (Utils.isObjNull(redisService.get(token_key))) {
				// 获取access token（微信接口访问访问的唯一凭证）
				JSONObject jobj = wechatCoreService.getAccess_token(appid, secret);
				redisService.set(token_key, jobj.getString("access_token"), (long) 6800);
			}
			String access_token = (String) redisService.get(token_key);
			String title = TTSUtils.getTTS(access_token, "AUDIOMSG", resource.getSection());
			if(Utils.isObjNull(title)){
				// access token 已过期，重新获取
				JSONObject jobj = wechatCoreService.getAccess_token(appid, secret);
				redisService.set(token_key, jobj.getString("access_token"), (long) 6800);
				
				title = TTSUtils.getTTS(access_token, "AUDIOMSG", resource.getSection());
			}
			
			audio.put("audioId", resource.getId());
			audio.put("resId", resource.getResId());
			audio.put("title", title);
			audio.put("url", resource.getHost() + resource.getResUrl() + resource.getRes());
			audio.put("status", status);
		} else {
			audio.put("audioId", 0);
			audio.put("resId", 0);
			audio.put("title", "");
			audio.put("url", "");
			audio.put("status", 0);
		}
		return audio;
	}

	/**
	 * 处理人机对话，并保存消息记录
	 * @param zjkey
	 * @param deviceId	设备ID
	 * @param appid		微信用户唯一ID
	 * @param object	请求参数体
	 */
	public void applyChatMessage(String zjkey, String deviceId, String appid, JSONObject object){
		logger.info("#");
		logger.info("人机对话参数：" + object.toString());
		// 说话的内容
		String asr = object.getString("asr");
		// 设备回答的内容
		String tts = object.getString("tts");
		
		DeviceChat chatmsg = new DeviceChat();
		chatmsg.setZjkey(zjkey);
		chatmsg.setDeviceId(deviceId);
		chatmsg.setAsr(asr);
		chatmsg.setTts(tts);
		chatmsg.setChattime(new Date());

		deviceChatService.insertDeviceChatMsg(chatmsg);
		logger.info("#");
	}
	
}
