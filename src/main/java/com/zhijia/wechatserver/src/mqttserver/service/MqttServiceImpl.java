package com.zhijia.wechatserver.src.mqttserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.mqttserver.entity.MqttAcl;
import com.zhijia.wechatserver.src.mqttserver.entity.MqttUser;
import com.zhijia.wechatserver.src.mqttserver.mapper.MqttMapper;

@Service
@Transactional
public class MqttServiceImpl implements MqttService {
    @Autowired
    private MqttMapper mqttMapper;

	public int insertMqttAcl(MqttAcl acl) {
		return mqttMapper.insertMqttAcl(acl);
	}

	public int insertMqttUser(MqttUser user) {
		return mqttMapper.insertMqttUser(user);
	}
    
}
