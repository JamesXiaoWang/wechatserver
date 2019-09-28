package com.zhijia.wechatserver.src.deviceserver.service.device;

import java.util.List;

import com.zhijia.wechatserver.src.deviceserver.entity.DeviceChat;

/**
 * @author Administrators
 * @date 2019年2月18日 下午12:25:01
 * @description:
 *
 */
public interface DeviceChatService {
	// 记录人机对话记录
	int insertDeviceChatMsg(DeviceChat chatMsg);
	
	/**
	 * 根据参数获取对话记录列表
	 * @param zjkey
	 * @param deviceId	设备ID
	 * @param chattime 对话时间
	 * @return
	 */
	List<DeviceChat> selectDeviceChatMsgByParams(String zjkey, String deviceId, String chattime);
}
