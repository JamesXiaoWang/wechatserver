package com.zhijia.wechatserver.src.deviceserver.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijia.wechatserver.src.common.JSONResult;
import com.zhijia.wechatserver.src.common.base.BaseController;
import com.zhijia.wechatserver.src.common.utils.HttpUtils;
import com.zhijia.wechatserver.src.common.utils.TTSUtils;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAudioStatus;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceMqtt;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceAlbum;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceList;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceTag;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;

import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年9月19日 上午9:55:15
 * @description: 处理公众号点播菜单
 *
 */
@Controller
@RequestMapping(value = "/play")
public class PlayController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(PlayController.class);

	/**
	 * 加载点播界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	@RequestMapping(value = "/zjdbr/{appid}")
	public String zjdbr(Map<String, Object> map, @PathVariable("appid") String appid, String code, String openid) {
		map.put("appid", appid);
		try {
			if(!Utils.isObjNull(code) && Utils.isObjNull(openid)){
				WechatAuthorize wa = wechatAuthorizeService.selectWechatAuthorizeByParams(appid, null);
				logger.info("####################################################");
				logger.info("# code #：" + code);
				logger.info("####################################################");
				String secret = wa.getAppsecret(); 
				
				String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
				String url = tokenUrl.replaceAll("APPID", appid).replaceAll("SECRET", secret).replaceAll("CODE", code);
				JSONObject json = JSONObject.fromObject(HttpUtils.sendGet(url, "UTF-8"));
				
				if(json.toString().indexOf("openid") != -1){
					openid = json.getString("openid");
				}
				
				logger.info("##########################################################################");
				logger.info("##########################################################################");
				logger.info("##########################################################################");
				logger.info("openid：" + openid);
				logger.info("##########################################################################");
				logger.info("##########################################################################");
			}
			map.put("openid", openid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("加载点播界面时异常：" + e.getMessage());
		}
		return "zjdbr";
	}
//	@RequestMapping(value = "/soundAuth/{appid}")
//   public String soundAuth(Map<String, Object> map, @PathVariable("appid") String appid, String code, String openid) {
//        try {
//          *//*  map.put("appid", appid);
//            List<WechatAuthorize> wechatAuthorizes = wechatAuthorizeService.selectWechatAuthorizeByAppId(appid);
//            System.out.println("list"+wechatAuthorizes.size());
//            map.put("wechatAuthorizes", wechatAuthorizes);*//*
//            System.out.println("appid>>>>>>>>>>>>>>>>>>>"+appid);
//            logger.info("logger appid>>>>>>>>>>>>>>>>>>>"+appid);
//            System.out.println("openid>>>>>>>>>>>>>>>>>>"+openid);
//            logger.info("logger openid>>>>>>>>>>>>>>>>>>>"+openid);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("加载声波Auth页面出错：" + e.getMessage());
//        }
//        return "wifi/auth";
//    }
	/**
	 * 获取资源大类
	 * @param appid 微信唯一ID
	 * @return  大类列表 【json格式封装】
	 */
	@ResponseBody
	@RequestMapping(value = "/getZjdbr/{appid}")
	public JSONResult getZjdbr(@PathVariable("appid") String appid) {
		JSONResult result = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			List<PlayResourceTag> tags = resourceService.selectPlayResourceTag();
			if(!Utils.isObjNull(tags) && tags.size() > 0){
				map.put("items", tags);
			}
			result = new JSONResult(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据点击页面资源大类类别加载资源专辑
	 * @param map		参数集合
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param tagName	类别名称
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	@RequestMapping(value = "/mycloudDetail/{appid}")
	public String mycloudDetail(Map<String, Object> map, @PathVariable("appid") String appid, String openid, String tagName) throws HttpException, IOException {
		map.put("appid", appid);
		try {
			List<String> tags = resourceService.selectPlayResourceTagName();
			if(!Utils.isObjNull(tags) && tags.size() > 0){
				map.put("items", tags);
			}
			map.put("indx", tags.indexOf(tagName));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("根据点击页面资源大类类别加载资源专辑时异常：" + e.getMessage());
		}
		map.put("openid", openid);
		
		return "detail";
	}
	
	/**
	 * 根据导航栏类型动态加载资源专辑
	 * @param appid		微信唯一ID
	 * @param tagName	类别名称
	 * @param tagId		类别ID
	 * @return	资源专辑列表 【json格式封装】
	 */
	@ResponseBody
	@RequestMapping(value = "/getResourceDetail/{appid}")
	public JSONResult getResourceDetail(@PathVariable("appid") String appid, String tagName, Integer tagId){
		JSONResult result = null;
		try {
			List<PlayResourceAlbum> resources = resourceService.selectPlayResourceAlbumList(tagId);
			
			result = new JSONResult(resources);
		} catch (Exception e) {
			e.printStackTrace();
			result = new JSONResult("服务器请求出错： " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 实现点播推送（设备与mqtt关联）
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param audioId	资源ID
	 * @param resId		资源专辑ID
	 * @param playType	播放类型（播放/暂停）
	 * @return 点播推送状态 【json格式封装】
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/playAudio/{appid}")
	public JSONResult playAudio(@PathVariable("appid") String appid, String openid, Integer audioId, Integer resId, int playType) {
		JSONResult result = null;
		try {
			DeviceMqtt dmt = deviceMqttService.selectMqttBindForDevice(openid);
			if (Utils.isObjNull(dmt)) {
				return new JSONResult("未绑定设备");
			}
			
			if(dmt.getIsOnline() == null || dmt.getIsOnline() == 0){
				return new JSONResult("设备未连接网络，请先给设备联网。");
			}
			
			JSONObject json_message = new JSONObject();
			//json_message.put("sender", openid);
			json_message.put("sender", 1);
			json_message.put("type", 1);
			
		    JSONObject audio = new JSONObject();
		    audio.put("openid", openid);
		    audio.put("status", 1);
		    audio.put("audioId", audioId);
		    audio.put("resId", resId);
		    
		    PlayResourceList resourceList = resourceService.selectPlayResourceById(audioId);
		    String section = resourceList.getSection();
		    
		    // 获取微信平台调用凭据，上传title
			String token_key = appid + "_atoken";
			String access_token = (String) redisService.get(token_key);
			String title = TTSUtils.getTTS(access_token, "AUDIOMSG", section);
		    
			audio.put("title", title);
			
			audio.put("url", resourceList.getHost() + resourceList.getResUrl() + resourceList.getRes());
			
			logger.info("正在推送的内容是：" + audio);
			json_message.put("message", audio);
			
			DeviceAudioStatus audioStatus = new DeviceAudioStatus();
			audioStatus.setZjkey(dmt.getZjkey());
			audioStatus.setDeviceId(dmt.getDeviceId());
			audioStatus.setPlay(playType);
			audioStatus.setAudioId(audioId);
			
			mqttSendMsg(wechatCoreService.mqttServer.client, openid, openid, openid, dmt.getTopic(), json_message, audioStatus);
			
			result = new JSONResult("SUCCESS", "点播内容推送成功。", audio);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("推送点播内容时出错： " + e.getMessage());
			result = new JSONResult("点播内容处理出错，请重试或联系管理员!");
		}
		return result;
	}
	
	/**
	 * 加载专辑【内容】【资源列表】页面
	 * @param map		参数集合
	 * @param appid 	微信唯一ID
	 * @param openid	用户唯一ID
	 * @param resId		专辑ID
	 * @return
	 */
	@RequestMapping(value = "/resourceMore/{appid}")
	public String resourceMore(Map<String, Object> map, @PathVariable("appid") String appid, String openid, Integer resId){
		try {
			map.put("appid", appid);
			map.put("openid", openid);
			
			PlayResourceAlbum resourceAlbum = resourceService.selectPlayResourceAlbum(resId);
			if(!Utils.isObjNull(resourceAlbum)){
				map.put("resourceAlbum", resourceAlbum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "zymore";
	}
	
	/**
	 * 实时刷新加载页面专辑内容
	 * @param appid 	微信唯一ID
	 * @param openid	用户唯一ID
	 * @param resId		专辑ID
	 * @return json内容
	 */
	@ResponseBody
	@RequestMapping(value = "/getMoreResource/{appid}")
	public JSONResult getMoreResource(@PathVariable("appid") String appid, String openid, Integer resId){
		JSONResult result = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appid", appid);
			
			List<PlayResourceList> resourceLists = resourceService.selectPlayResourceList(resId);
			if(!Utils.isObjNull(resourceLists)){
				map.put("resourceLists", resourceLists);
			
				DeviceMqtt dmt = deviceMqttService.selectMqttBindForDevice(openid);
				if (!Utils.isObjNull(dmt)) {
					String deviceId = dmt.getDevice();
					DeviceAudioStatus audioStatus = audioStatusService.selectDeviceAudioStatusByDeviceId(deviceId);
					if(!Utils.isObjNull(audioStatus)){/* && audioStatus.getResId() == resId*/
						map.put("play", audioStatus.getPlay());
						map.put("audioId", audioStatus.getAudioId());
						map.put("section", audioStatus.getSection());
					}else{
						map.put("play", 0);
						map.put("audioId", 0);
						map.put("section", "");
					}
				}else{
					map.put("play", 0);
					map.put("audioId", 0);
					map.put("section", "");
				}
			}
			result = new JSONResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("请求出错，请重试或联系管理员！");
			result = new JSONResult("请求出错，请重试或联系管理员！");
		}
		return result;
	}
	
	/**
	 * 加载【联系我们】界面
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	@RequestMapping(value = "/mycloud/{appid}")
	public String mycloud(Map<String, Object> map, @PathVariable("appid") String appid) throws HttpException, IOException {
		/*JSONObject classify = JSONObject.fromObject(HttpUtils.sendGet(QMZRESOURCE_URL_CLASSIFY, Conts.CHARSET, Conts.APP_NAME));
		JSONArray items = new JSONArray();
		if(!Utils.isObjNull(classify) && classify.getInt("code") == 0){
			items = classify.getJSONArray("list");
			
			map.put("items", items);
		}
		map.put("appid", appid);*/
		
		return "mycloud";
	}
	
	/**
	 * 根据底部菜单  控制功能  推送响应的内容
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param content	控制推送的内容（resId__audioId【专辑ID__资源ID】）
	 * @param operation 控制功能类型【0-上一曲、1-下一曲、2-播放、3-暂停】
	 * @return	
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/operationAudio/{appid}")
	public JSONResult operationAudio(@PathVariable("appid") String appid, String openid, String content, Integer operation){
		JSONResult result = null;
		try {
			DeviceMqtt dmt = deviceMqttService.selectMqttBindForDevice(openid);
			if (Utils.isObjNull(dmt)) {
				return new JSONResult("未绑定设备");
			}
			if(dmt.getIsOnline() == null || dmt.getIsOnline() == 0){
				return new JSONResult("设备未连接网络，请先给设备联网。");
			}
			
			logger.info("#################################################################");
			logger.info("operation:" + operation);
			logger.info("content:" + content);
			logger.info("#################################################################");
			
			String[] palycontent = content.split("__");
			Integer resId = Integer.parseInt(palycontent[0]);
			Integer audioId = !Utils.isObjNull(palycontent[1]) ? Integer.parseInt(palycontent[1]) : 0;
			
			DeviceAudioStatus audioStatus = new DeviceAudioStatus();
			audioStatus.setZjkey(dmt.getZjkey());
			audioStatus.setDeviceId(dmt.getDeviceId());
			
			MqttClient client = wechatCoreService.mqttServer.client;
			String username = openid;
			String topic = dmt.getTopic();
			
			List<PlayResourceList> resourceLists = null;
			PlayResourceList resource = null;
			// 如果audioId不为0，即播放此资源，否则则默认播放专辑的第一首资源
			if(!audioId.equals(0)){
				resource = resourceService.selectPlayResourceById(audioId);
				resourceLists = resourceService.selectPlayResourceList(resource.getResId());
			}else{
				resourceLists = resourceService.selectPlayResourceList(resId);
				resource = resourceLists.get(0);
			}
			
			JSONObject json_message = new JSONObject();
			// json_message.put("sender", username);
			json_message.put("sender", 1);
			json_message.put("type", 1);

			JSONObject audio = new JSONObject();
			switch (operation) {
				case 0:
					PlayResourceList lastprl = resourceLists.get(resourceLists.size() - 1);
					if (audioId.equals(lastprl.getId()) && resId.equals(lastprl.getResId())) {
						resource = resourceLists.get(0);
					} else {
						for (int i = 0; i < resourceLists.size(); i++) {
							PlayResourceList resourceList = resourceLists.get(i);
							if (audioId.equals(resourceList.getId())) {
								if (resourceLists.size() > i + 1) {
									resource = resourceLists.get(i + 1);
								}
							}
						}
					}
					audio = getAudioMsg(resource, 1, appid, dmt.getZjkey());
					json_message.put("message", audio);
	
					logger.info("MQTT连接信息：" + username + ", 主题：" + topic);
					audioStatus.setPlay(2);
					audioStatus.setAudioId(resource.getId());
					
					mqttSendMsg(client, username, username, username, topic, json_message, audioStatus);
					result = new JSONResult("SUCCESS", "点播内容推送成功。", audio);
					break;
				case 1:
					PlayResourceList firstprl = resourceLists.get(0);
					if (audioId.equals(firstprl.getId()) && resId.equals(firstprl.getResId())) {
						resource = resourceLists.get(resourceLists.size() - 1);
					} else {
						for (int i = 0; i < resourceLists.size(); i++) {
							PlayResourceList resourceList = resourceLists.get(i);
							if (audioId.equals(resourceList.getId())) {
								if (i - 1 >= 0) {
									resource = resourceLists.get(i - 1);
								}
							}
						}
					}
					audio = getAudioMsg(resource, 1, appid, dmt.getZjkey());
					json_message.put("message", audio);
	
					logger.info("MQTT连接信息：" + username + ", 主题：" + topic);
					audioStatus.setPlay(2);
					audioStatus.setAudioId(resource.getId());
					
					mqttSendMsg(client, username, username, username, topic, json_message, audioStatus);
					result = new JSONResult("SUCCESS", "点播内容推送成功。", audio);
					break;
				case 2:
					audio = getAudioMsg(resource, 1, appid, dmt.getZjkey());
					json_message.put("message", audio);
					
					logger.info("MQTT连接信息：" + username + ", 主题：" + topic);
					audioStatus.setPlay(2);
					audioStatus.setAudioId(audioId);
					
					mqttSendMsg(client, username, username, username, topic, json_message, audioStatus);
					result = new JSONResult("SUCCESS", "点播内容推送成功。", audio);
					break;
				case 3:
					audio = getAudioMsg(resource, 0, appid, dmt.getZjkey());
					json_message.put("message", audio);
					
					logger.info("MQTT连接信息：" + username + ", 主题：" + topic);
					audioStatus.setPlay(3);
					audioStatus.setAudioId(audioId);
					
					mqttSendMsg(client, username, username, username, topic, json_message, audioStatus);
					result = new JSONResult("SUCCESS", "点播内容推送成功。", audio);
					break;
	
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("推送点播内容时出错： " + e.getMessage());
			result = new JSONResult("内容处理出错，请重试或联系管理员!");
		}
		return result;
	}

	/**
	 * 处理MQTT推送的内容
	 * @param client	MQTT客户端（连接MQTT服务器）
	 * @param username	连接用户名
	 * @param password	连接密码
	 * @param deviceId	连接的设备ID（客户端ID）
	 * @param topic		订阅的主题
	 * @param json_message	推送发布的内容
	 * @throws MqttException
	 */
	@SuppressWarnings("static-access")
	public void mqttSendMsg(MqttClient client, String username, String password, String deviceId, String topic, JSONObject json_message, DeviceAudioStatus audioStatus){
		logger.info("##########################开始MQTT处理推送消息######################");
		logger.info("# username #" + username);
		logger.info("# password #" + password);
		logger.info("# deviceId #" + deviceId);
		logger.info("# topic #" + topic);
		try {
			if(client != null && client.isConnected()){
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("MQTT处理推送消息异常:" + e.getMessage());
		}
		logger.info("##########################结束MQTT处理推送消息######################");
	}
	
	/**
	 * 处理推送的音频信息
	 * @param resource	资源对象
	 * @param status	音频状态
	 * @param access_token	前奏title上传微信服务器所需的访问Token
	 * @return	推送的音频信息【json格式封装】
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private JSONObject getAudioMsg(PlayResourceList resource, Integer status, String appid, String zjkey) throws HttpException, IOException{
		JSONObject audio = new JSONObject();
		if(!Utils.isObjNull(resource)){
			// 获取微信平台调用凭据，上传title
			String token_key = appid + "_atoken";
			String access_token = (String) redisService.get(token_key);
			String title = TTSUtils.getTTS(access_token, "AUDIOMSG", resource.getSection());
			
			audio.put("audioId", resource.getId());
			audio.put("resId", resource.getResId());
			audio.put("title", title);
			audio.put("url", resource.getHost() + resource.getResUrl() + resource.getRes());
			audio.put("status", status);
		}else{
			audio.put("audioId", 0);
			audio.put("resId", 0);
			audio.put("title", "");
			audio.put("url", "");
			audio.put("status", 0);
		}
		return audio;
	}
}
