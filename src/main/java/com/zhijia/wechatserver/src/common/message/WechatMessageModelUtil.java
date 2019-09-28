package com.zhijia.wechatserver.src.common.message;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhijia.wechatserver.src.common.redis.RedisService;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceClock;
import com.zhijia.wechatserver.src.deviceserver.entity.message.TextMessage;
import com.zhijia.wechatserver.src.deviceserver.entity.user.UserBind;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceClockService;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceMqttService;
import com.zhijia.wechatserver.src.deviceserver.service.user.UserService;
import com.zhijia.wechatserver.src.deviceserver.service.wechat.WechatAuthorizeService;
import com.zhijia.wechatserver.src.mqttserver.entity.MqttUser;
import com.zhijia.wechatserver.src.mqttserver.service.MqttService;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:48:36
 * @description:封装微信回复消息，各种回复消息对应不同的方法
 *
 */
@Component
public class WechatMessageModelUtil {
	private static Logger logger = LoggerFactory.getLogger(WechatMessageModelUtil.class);
	
	@Autowired
	private WechatMessageUtil wechatMessageUtil;
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private WechatAuthorizeService wechatAuthorizeService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceMqttService deviceMqttService;
	@Autowired
	private MqttService mqttService;
	@Autowired
	private DeviceClockService deviceClockService;

	/**
	 * @Description: 当系统出错时，默认回复的文本消息
	 * @Parameters: WeixinMessageModelUtil
	 * @Return: 系统出错回复的消息
	 * @Version: V1.00
	 */
	public String systemErrorResponseMessageModel(WechatMessageInfo wechatMessageInfo) {
		// 回复文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(wechatMessageInfo.getToUserName());
		textMessage.setFromUserName(wechatMessageInfo.getFromUserName());
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(wechatMessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent("系统出错啦，请稍后再试");
		return wechatMessageUtil.textMessageToXml(textMessage);
	}

	/**
	 * @Description: 用户关注时发送的图文消息
	 * @Parameters: WeixinMessageModelUtil
	 * @Return: 用户关注后发送的提示绑定用户的图文消息
	 * @Version: V1.00
	 */
	public String followResponseMessageModel(WechatMessageInfo wechatMessageInfo) {
		// 关注时发送图文消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(wechatMessageInfo.getFromUserName());
		textMessage.setFromUserName(wechatMessageInfo.getToUserName());
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(wechatMessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		// 设置图文消息的标题
		String string = "尊敬的用户，欢迎关注智佳点播机器人微信公众号.";
		textMessage.setContent(string);
		textMessage.setMsgId("");
		
		MqttUser mqttUser = new MqttUser();
		mqttUser.setUsername(wechatMessageInfo.getFromUserName());
		mqttUser.setPassword(wechatMessageInfo.getFromUserName());
		mqttUser.setSalt("");
		mqttUser.setIs_superuser(0);
		mqttUser.setStatus(1);
		mqttUser.setCreated(new Date());
		mqttService.insertMqttUser(mqttUser);
		
		List<UserBind> userBinds = userService.selectUserIsBindForDevice(wechatMessageInfo.getFromUserName());
		if(!Utils.isObjNull(userBinds) && userBinds.size() > 0){
			for (UserBind userBind : userBinds) {
				deviceMqttService.updateDeviceMqttBindForOpenId(userBind.getOpenid(), 1, userBind.getDeviceId());
			}
		}
		logger.info("用户【" + wechatMessageInfo.getFromUserName() + "】关注。update 【" + userBinds.size() + "】台设备状态");
		return wechatMessageUtil.textMessageToXml(textMessage);
	}

	/**
	 * @Description: 用户取消关注，先判断用户是否绑定，如果已经绑定则解除绑定
	 * @Parameters: WeixinMessageModelUtil
	 * @Return: void
	 * @Version: V1.00
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public void cancelAttention(String fromUserName, String toUserName) {
		try {
			System.out.println("#########################取消关注############################");
			WechatAuthorize wa = wechatAuthorizeService.selectWechatAuthorizeByWechatid(toUserName);
			
			// 获取微信平台调用凭据，上传title
			String token_key = wa.getAppid() + "_atoken", ticket_key = wa.getAppid() + "_ticket";
			String access_token = (String) redisService.get(token_key);
			String ticket = (String) redisService.get(ticket_key);			
			
			if (userService.isAlreadyBinding(access_token, fromUserName)) {
				userService.userUnbinding(access_token, ticket, fromUserName);
			} else {
				System.out.println("取消关注, 用户{" + fromUserName + "}还未绑定任何设备。");
			}
			redisService.remove(new String[]{fromUserName + "_atoken", fromUserName + "_ticket"});
			
			// 取消关注后，断开MQTT连接，并此用户下的设备则失效
			List<UserBind> userBinds = userService.selectUserIsBindForDevice(fromUserName);
			if(!Utils.isObjNull(userBinds) && userBinds.size() > 0){
				for (UserBind userBind : userBinds) {
					deviceMqttService.updateDeviceMqttBindForOpenId(userBind.getOpenid(), 0, userBind.getDeviceId());
				}
			}		
			
			// 清空取关用户的设备闹钟记录
			List<DeviceClock> clocks = deviceClockService.selectDeviceClockListByParams(fromUserName, null, null);
			if(!Utils.isObjNull(clocks) && clocks.size() > 0){
				for (DeviceClock deviceClock : clocks) {
					deviceClockService.deleteDeviceClock(deviceClock);
				}
			}
			logger.info("【用户" + fromUserName + "】取消关注。update所有相关设备的连接有效状态：" + userBinds.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
