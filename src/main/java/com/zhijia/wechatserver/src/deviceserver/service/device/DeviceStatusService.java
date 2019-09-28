package com.zhijia.wechatserver.src.deviceserver.service.device;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceStatus;

/**
 * @author Administrators
 * @date 2018年12月14日 下午12:12:32
 * @description:
 *
 */
public interface DeviceStatusService {
	/**
	 * 根据参数获取设备的状态信息
	 * @param zjkey
	 * @param deviceId
	 * @return
	 */
	DeviceStatus selectDeviceStatusByDeviceId(String zjkey, String deviceId);
	
	/**
	 * 更新设备状态
	 * @param status
	 */
	void insertAndUpdateDeviceStatus(DeviceStatus status);
}
