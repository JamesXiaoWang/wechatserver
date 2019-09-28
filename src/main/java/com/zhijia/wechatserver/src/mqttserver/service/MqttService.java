package com.zhijia.wechatserver.src.mqttserver.service;

import com.zhijia.wechatserver.src.mqttserver.entity.MqttAcl;
import com.zhijia.wechatserver.src.mqttserver.entity.MqttUser;

public interface MqttService {
	int insertMqttAcl(MqttAcl acl);
	
	int insertMqttUser(MqttUser user);
}
