package com.zhijia.wechatserver.src.deviceserver.service.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.DeviceChat;
import com.zhijia.wechatserver.src.deviceserver.mapper.device.DeviceChatMapper;

/**
 * @author Administrators
 * @date 2019年2月18日 下午12:29:26
 * @description:
 *
 */
@Transactional
@Service
public class DeviceChatServiceImpl implements DeviceChatService {
	@Autowired
	private DeviceChatMapper deviceChatMapper;

	public int insertDeviceChatMsg(DeviceChat chatMsg) {
		return deviceChatMapper.insertDeviceChatMsg(chatMsg);
	}

	public List<DeviceChat> selectDeviceChatMsgByParams(String zjkey, String deviceId, String chattime) {
		return deviceChatMapper.selectDeviceChatMsgByParams(zjkey, deviceId, chattime);
	}

}
