package com.zhijia.wechatserver.src.deviceserver.service.device;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAudioStatus;

/**
 * @author Administrators
 * @date 2019年1月10日 下午6:22:26
 * @description:
 *
 */
public interface DeviceAudioStatusService {
	int insertOrUpdateDeviceAudioStatus(DeviceAudioStatus audioStatus);
	
	DeviceAudioStatus selectDeviceAudioStatusByDeviceId(String deviceId);
}
