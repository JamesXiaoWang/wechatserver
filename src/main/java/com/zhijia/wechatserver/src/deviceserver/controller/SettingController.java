package com.zhijia.wechatserver.src.deviceserver.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.DeviceChat;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceClock;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceMqtt;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceStatus;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年10月16日 下午4:01:39
 * @description: 公众号设置
 *
 */
@Controller
@RequestMapping(value = "/setting")
public class SettingController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SettingController.class);

	/**
	 * 加载微信公众底部设备界面
	 * @param map	参数集合
	 * @param appid	微信唯一ID
	 * @param code	页面授权code
	 * @param openid	微信用户唯一ID
	 * @return	设置界面
	 */
	@RequestMapping(value = "/setting/{appid}")
	public String setting(Map<String, Object> map, @PathVariable("appid") String appid, String code, String openid) {
		try {
			map.put("appid", appid);
			if (!Utils.isObjNull(code) && Utils.isObjNull(openid)) {
				WechatAuthorize wa = wechatAuthorizeService.selectWechatAuthorizeByParams(appid, null);
				logger.info("####################################################");
				logger.info("# code #：" + code);
				logger.info("####################################################");
				String secret = wa.getAppsecret();

				String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
				String url = tokenUrl.replaceAll("APPID", appid).replaceAll("SECRET", secret).replaceAll("CODE", code);
				JSONObject json = JSONObject.fromObject(HttpUtils.sendGet(url, "UTF-8"));

				if (json.toString().indexOf("openid") != -1) {
					map.put("openid", json.getString("openid"));
					
					openid = json.getString("openid");
				}
			}
			map.put("openid", openid);

			JSONArray items = new JSONArray();
			DeviceMqtt dmt = deviceMqttService.selectMqttBindForDevice(openid);
			if (!Utils.isObjNull(dmt)) {
				map.put("isbind", 1);

				map.put("dmt", dmt);

				List<DeviceMqtt> dmts = deviceMqttService.selectMqttBindForDeviceList(openid);
				if (!Utils.isObjNull(dmts) && dmts.size() > 0) {
					for (int i = 0; i < dmts.size(); i++) {
						DeviceMqtt dmqt = dmts.get(i);

						JSONObject jObj = new JSONObject();

						jObj.put("title", dmqt.getDeviceName());
						jObj.put("value", dmqt.getDevice());
						jObj.put("isOnline", dmqt.getIsOnline());
						jObj.put("isbind", dmqt.getMqttBind());

						items.add(jObj);
					}

					map.put("items", items);
				}

				DeviceStatus status = deviceStatusService.selectDeviceStatusByDeviceId(dmt.getZjkey(), dmt.getDeviceId());
				if (!Utils.isObjNull(status)) {
					map.put("status", status);
				}
			} else {
				map.put("isbind", 0);
			}
		} catch (Exception e) {
			logger.info("加载设置界面时异常：" + e.getMessage());
		}
		return "setting/setting";
	}

	/**
	 * 推送设备绑定MQTT
	 * 
	 * @param map
	 * @param devices
	 *            绑定的设备数组
	 * @return 绑定结果
	 */
	@ResponseBody
	@RequestMapping(value = "/bindPushDevice/{appid}")
	public JSONResult bindPushDevice(Map<String, Object> map, @PathVariable("appid") String appid, String openid, String device) {
		JSONResult result = null;
		try {
			if (Utils.isObjNull(device)) {
				return new JSONResult("绑定服务器失败，请重试！");
			}
			//获取微信用户与设备-mqtt绑定的有效记录
			List<DeviceMqtt> dms = deviceMqttService.selectMqttBindForDeviceListByOpenId(openid);
			for (DeviceMqtt deviceMqtt : dms) {
				deviceMqtt.setMqttBind(0);

				//update 微信-设备-mqtt的绑定状态
				deviceMqttService.updateDeviceMqttBindForParams(deviceMqtt);
			}

			String[] res = device.split("_");
			//根据zjkey和deviceId获取mqtt绑定信息
			DeviceMqtt mqtt = deviceMqttService.selectMqttBindForDevice(openid, res[0], res[1]);
			mqtt.setMqttBind(1);
			mqtt.setStatus(1);
			mqtt.setIsOnline(1);

			deviceMqttService.insertDeviceMqtt(mqtt);

			result = new JSONResult("SUCCESS", "所选设备服务器连接成功！", device);
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONResult("绑定服务器失败，请重试！");
		}
		return result;
	}

	/**
	 * 加载声音设置界面
	 * @param map	参数集合
	 * @param appid	微信唯一ID
	 * @param device	设备标识
	 * @param openid	微信用户唯一ID
	 * @return
	 */
	@RequestMapping(value = "/getVolControl/{appid}")
	public String getVolControl(Map<String, Object> map, @PathVariable("appid") String appid, String device, String openid) {
		map.put("appid", appid);
		map.put("openid", openid);
		try {
			DeviceStatus status = deviceStatusService.selectDeviceStatusByDeviceId(device.split("_")[0], device.split("_")[1]);
			map.put("status", status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "setting/volcontrol";
	}

	/**
	 * 设置界面各个功能设置
	 * @param param		参数值
	 * @param clock		闹钟
	 * @param type		功能类型
	 * @param repeat	闹钟重复参数
	 * @param openid	微信用户唯一ID
	 * @return
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/controlConfig/{appid}")
	public JSONResult controlConfig(int param,String type,String openid, String clock, String repeat, String ctype, Integer clockid) {
		JSONResult result = null;
		try {
			DeviceMqtt dmt = deviceMqttService.selectMqttBindForDevice(openid);
			if (Utils.isObjNull(dmt)) {
				return new JSONResult("未绑定设备");
			}

			DeviceStatus status = deviceStatusService.selectDeviceStatusByDeviceId(dmt.getZjkey(), dmt.getDeviceId());
			if (Utils.isObjNull(status)) {
				return new JSONResult("未绑定设备");
			}

			JSONObject control = new JSONObject();
			control.put("openid", openid);
			switch (type) {
			case "VOL":
				control.put("vol", param);
				logger.info("正在推送的音量大小是：" + param + "%.");

				status.setVol(param);
				break;
			case "BLN":
				control.put("bln", param);
				logger.info("正在推送的指令是：呼吸灯控制开关[" + param + "].");

				status.setBln(param);
				break;
			case "LBI":
				control.put("lbi", param);
				logger.info("正在推送的指令是：低电量提醒开关[" + param + "].");

				status.setLbi(param);
				break;
			case "FMT":
				control.put("fmt", param);
				logger.info("正在推送的指令是：格式化存储[" + param + "].");
				break;
			case "RCV":
				control.put("rcv", param);
				logger.info("正在推送的指令是：恢复出厂设置[" + param + "].");
				break;
			case "CLO":
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = "2019-02-14 ";
				JSONArray clock_msg = new JSONArray();
				
				// 格式化时间
				Date date = simpleDateFormat.parse(dateStr + clock.replaceAll(" ", "") + ":00");
				long ts = date.getTime() / 1000;
				DeviceClock deviceClock = null;
				if("ins".equals(ctype)){
					// 保存闹钟记录
					deviceClock = new DeviceClock();
					deviceClock.setOpenid(openid);
					deviceClock.setZjkey(dmt.getZjkey());
					deviceClock.setDeviceId(dmt.getDeviceId());
					deviceClock.setDeleted(0);
					deviceClock.setTitle("闹钟");
					deviceClock.setClock(clock);
					deviceClock.setTime(ts);
					deviceClock.setStatus(param);
					deviceClock.setRepeatTime(repeat);
					deviceClockService.insertDeviceClock(deviceClock);
				}else{
					deviceClock = deviceClockService.selectDeviceClockById(clockid);
					deviceClock.setClock(clock);
					deviceClock.setTime(ts);
					deviceClock.setStatus(param);
					deviceClock.setRepeatTime(repeat);
					deviceClock.setDeleted(param == 0 ? 1 : 0);
					
					deviceClockService.updateDeviceClock(deviceClock);
				}
				// 返回参数封装成JSONObject
				JSONObject obj = new JSONObject();
				obj.put("id", deviceClock.getId());
				obj.put("title", "闹钟");
				obj.put("status", param);
				obj.put("time", ts);
				obj.put("repeat", repeat);
				clock_msg.add(obj);

				control.put("clo", clock_msg);
				logger.info("正在推送的指令是：闹钟[" + clock_msg + "].");

				status.setClock(clock_msg.toString());
				break;
			default:
				logger.info("未指定指令类型.");
				break;
			}

			JSONObject json_message = new JSONObject();
			json_message.put("sender", 1);
			json_message.put("type", 2);
			json_message.put("message", control);

			String username = openid;
			mqttSendMsg(wechatCoreService.mqttServer.client, username, username, username, dmt.getTopic(), json_message, status);

			result = new JSONResult("SUCCESS", "指令推送成功。", control);
		} catch (Exception e) {
			e.printStackTrace();
			result = new JSONResult("推送指令时出错： " + e.getMessage());
		}
		return result;
	}

	/**
	 * 加载闹钟设置界面
	 * @param map		参数集合
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param device	设备标识
	 * @return	闹钟设置界面
	 */
	@RequestMapping(value = "/getAlarmClock/{appid}")
	public String getAlarmClock(Map<String, Object> map, @PathVariable("appid") String appid, String openid, String device) {
		try {
			map.put("appid", appid);
			map.put("openid", openid);
			map.put("device", device);

			String zjkey = device.split("_")[0];
			String deviceId = device.split("_")[1];

			List<DeviceClock> clocks = deviceClockService.selectDeviceClockListByParams(openid, zjkey, deviceId);
			if (!Utils.isObjNull(clocks) && clocks.size() > 0) {
				for (DeviceClock deviceClock : clocks) {
					if(!Utils.isObjNull(deviceClock.getRepeatTime())){
						String repeatTime = deviceClock.getRepeatTime().replaceAll("1", "一").replaceAll("2", "二").replaceAll("3", "三").replaceAll("4", "四").replaceAll("5", "五")
						.replaceAll("6", "六").replaceAll("0", "日");
						deviceClock.setRepeatStr(repeatTime);
					}else{
						deviceClock.setRepeatStr("");
					}
				}
				map.put("clocks", clocks);
				map.put("length", clocks.size());
			} else {
				map.put("clocks", null);
				map.put("repeatTime", "");
				map.put("length", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("加载闹钟设置界面时异常：" + e.getMessage());
		}
		return "setting/alarmclock";
	}
	
	/**
	 * 处理闹钟参数设置
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param clockid	闹钟唯一标识
	 * @param status	闹钟状态
	 * @param device	设备标识
	 * @return
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/applyClock/{appid}")
	public JSONResult applyClock(@PathVariable("appid") String appid, String openid, int clockid, int status, String device){
		JSONResult result = null;
		try {
			DeviceClock deviceClock = deviceClockService.selectDeviceClockById(clockid);
			if(!Utils.isObjNull(deviceClock)){
				deviceClock.setStatus(status);
				deviceClockService.updateDeviceClock(deviceClock);
			}
			
			JSONObject control = new JSONObject();
			control.put("openid", openid);
			
			JSONArray clock_msg = new JSONArray();
			// 返回参数封装成JSONObject
			JSONObject obj = new JSONObject();
			obj.put("id", deviceClock.getId());
			obj.put("title", "闹钟");
			obj.put("status", status);
			obj.put("time", deviceClock.getTime());
			obj.put("repeat", deviceClock.getRepeatTime());
			clock_msg.add(obj);
			
			control.put("clo", clock_msg);
			
			JSONObject json_message = new JSONObject();
			json_message.put("sender", 1);
			json_message.put("type", 2);
			json_message.put("message", control);

			String username = openid;
			mqttSendMsg(wechatCoreService.mqttServer.client, username, username, username, device, json_message, null);

			result = new JSONResult("SUCCESS", "指令推送成功。", control);
		} catch (Exception e) {
			e.printStackTrace();
			result = new JSONResult("推送指令时出错： " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 加载添加（编辑）闹钟页面
	 * @param map		参数集合
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param clockid	闹钟唯一标识
	 * @param device	设备标识
	 * @return
	 */
	@RequestMapping(value = "/getAddClocks/{appid}")
	public String getAddClocks(Map<String, Object> map, @PathVariable("appid") String appid, String openid, int clockid, String device){
		map.put("appid", appid);
		map.put("openid", openid);
		map.put("device", device);
		
		if(clockid != 0){
			DeviceClock deviceClock = deviceClockService.selectDeviceClockById(clockid);
			map.put("deviceClock", deviceClock);
			map.put("ctype", "upd");
		}else{
			map.put("deviceClock", new DeviceClock());
			map.put("ctype", "ins");
		}
		
		return "setting/addclocks";
	}

	/**
	 * 修改设备名称
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param zjkey		
	 * @param deviceId	设备ID
	 * @param deviceName	设备名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDeviceName/{appid}")
	public JSONResult updateDeviceName(@PathVariable("appid") String appid, String openid, String zjkey, String deviceId, String deviceName) {
		JSONResult result = null;
		try {
			DeviceMqtt mqtt = deviceMqttService.selectMqttBindForDevice(openid, zjkey, deviceId);
			if (!Utils.isObjNull(mqtt)) {
				mqtt.setDeviceName(deviceName.trim());

				deviceMqttService.updateDeviceMqttBindForParams(mqtt);

				result = new JSONResult("SUCCESS", "修改成功！", null);
			} else {
				result = new JSONResult("ERROR", "修改失败，请重试或联系管理员！", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new JSONResult("ERROR", "服务请求失败，请重试或联系管理员！", null);
		}
		return result;
	}

	/**
	 * MQTT 消息推送
	 * @param client		MQTT客户端
	 * @param username		用户名
	 * @param password		密码
	 * @param deviceId		设备ID
	 * @param topic			订阅（发布）的猪蹄
	 * @param json_message	推送的消息（JSON封装）
	 * @param status		设备状态更新对象
	 * @throws MqttException
	 */
	@SuppressWarnings("static-access")
	public void mqttSendMsg(MqttClient client, String username, String password, String deviceId, String topic,
			JSONObject json_message, DeviceStatus status) throws MqttException {
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
		if(!Utils.isObjNull(status)){
			deviceStatusService.insertAndUpdateDeviceStatus(status);
		}
	}
	
	/**
	 * 加载对话记录时间列表
	 * @param map		参数集合
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param device	设备标识
	 * @return
	 */
	@RequestMapping(value = "/getTimeRecord/{appid}")
	public String getTimeRecord(Map<String, Object> map, @PathVariable("appid") String appid, String openid, String device){
		map.put("appid", appid);
		map.put("openid", openid);
		map.put("device", device);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M月d日");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		JSONArray timelist = new JSONArray();
		for (int i = 0; i < 7; i++) {
			JSONObject obj = new JSONObject();
			cal.setTime(date);// 设置起时间
			cal.add(Calendar.DAY_OF_MONTH, -i);
			obj.put("times", simpleDateFormat.format(cal.getTime()));
			obj.put("chattime", format.format(cal.getTime()));
			timelist.add(obj);
		}
		map.put("timelist", timelist);
		
		return "setting/timerecord";
	}
	
	/**
	 * 加载对话记录
	* @param map		参数集合
	 * @param appid		微信唯一ID
	 * @param openid	微信用户唯一ID
	 * @param device	设备标识
	 * @param chattime	对话时间
	 * @return
	 */
	@RequestMapping(value = "/tochatmsg/{appid}")
	public String tochatmsg(Map<String, Object> map, @PathVariable("appid") String appid, String openid, String device, String chattime){
		try {
			String zjkey = device.split("_")[0];
			String deviceId = device.split("_")[1];
			List<DeviceChat> chatmsgList = deviceChatService.selectDeviceChatMsgByParams(zjkey, deviceId, chattime);
			map.put("chatmsgList", chatmsgList);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(chattime);
			SimpleDateFormat format1 = new SimpleDateFormat("MM月dd日");
			
			map.put("title", format1.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "setting/chatMsg";
	}
}
