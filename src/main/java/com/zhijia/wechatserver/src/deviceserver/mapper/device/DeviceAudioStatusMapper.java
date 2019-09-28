package com.zhijia.wechatserver.src.deviceserver.mapper.device;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAudioStatus;

/**
 * @author Administrators
 * @date 2019年1月10日 下午6:11:00
 * @description:
 *
 */
@Mapper
public interface DeviceAudioStatusMapper {
	int insertOrUpdateDeviceAudioStatus(DeviceAudioStatus audioStatus);
	
	DeviceAudioStatus selectDeviceAudioStatusByDeviceId(@Param("deviceId")String deviceId);
}
