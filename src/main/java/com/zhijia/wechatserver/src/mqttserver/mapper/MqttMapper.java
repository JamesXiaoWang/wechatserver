package com.zhijia.wechatserver.src.mqttserver.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhijia.wechatserver.src.mqttserver.entity.MqttAcl;
import com.zhijia.wechatserver.src.mqttserver.entity.MqttUser;

@Mapper
public interface MqttMapper {
	int insertMqttAcl(MqttAcl acl);
	
	int insertMqttUser(MqttUser user);
}
