package com.zhijia.wechatserver.src.deviceserver.mapper.device;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhijia.wechatserver.src.deviceserver.entity.DeviceChat;

/**
 * @author Administrators
 * @date 2019年2月18日 上午11:47:01
 * @description:
 *
 */
@Mapper
public interface DeviceChatMapper {
	// 记录人机对话记录
	int insertDeviceChatMsg(DeviceChat chatMsg);
	
	/**
	 * 根据参数获取对话记录列表
	 * @param zjkey
	 * @param deviceId	设备ID
	 * @param chattime 对话时间
	 * @return
	 */
	List<DeviceChat> selectDeviceChatMsgByParams(@Param("zjkey")String zjkey, @Param("deviceId")String deviceId, @Param("chattime")String chattime);
}
