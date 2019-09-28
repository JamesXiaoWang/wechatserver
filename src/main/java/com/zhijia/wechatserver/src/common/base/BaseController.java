package com.zhijia.wechatserver.src.common.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhijia.wechatserver.src.common.message.WechatMessageInfo;
import com.zhijia.wechatserver.src.common.redis.RedisService;
import com.zhijia.wechatserver.src.deviceserver.service.WechatCoreService;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceAudioStatusService;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceAuthorizeService;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceChatService;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceClockService;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceMqttService;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceStatusService;
import com.zhijia.wechatserver.src.deviceserver.service.play.PlayResourceService;
import com.zhijia.wechatserver.src.deviceserver.service.user.UserService;
import com.zhijia.wechatserver.src.deviceserver.service.wechat.WechatAuthorizeService;
import com.zhijia.wechatserver.src.mqttserver.service.MqttService;

/**
 * @author Administrators
 * @date 2019年2月13日 上午11:01:12
 * @description:
 *
 */
public class BaseController {
	@Autowired
	protected WechatMessageInfo wechatMessageInfo;
	@Autowired
	protected RedisService redisService;
	
	@Autowired
	protected WechatCoreService wechatCoreService;
	@Autowired
	protected DeviceAuthorizeService deviceAuthorizeService;
	@Autowired
	protected DeviceMqttService deviceMqttService;
	@Autowired
	protected UserService userService;
	@Autowired
	protected WechatAuthorizeService wechatAuthorizeService;	//微信授权服务
	@Autowired
	protected DeviceStatusService deviceStatusService;
	@Autowired
	protected DeviceClockService deviceClockService;
	@Autowired
	protected DeviceAudioStatusService audioStatusService;
	@Autowired
	protected PlayResourceService resourceService;
	@Autowired
	protected MqttService mqttService;
	@Autowired
	protected DeviceChatService deviceChatService;
}
