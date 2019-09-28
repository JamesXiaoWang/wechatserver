package com.zhijia.wechatserver.src.deviceserver.service.device;

import java.util.List;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceClock;

/**
 * @author Administrators
 * @date 2018年12月22日 下午12:45:16
 * @description:
 *
 */
public interface DeviceClockService {
	/**
	 * 获取某个用户对某台设备的闹钟列表
	 * @param openid
	 * @param zjkey
	 * @param deviceId
	 * @return
	 */
	List<DeviceClock> selectDeviceClockListByParams(String openid, String zjkey, String deviceId);
	
	/**
	 * 添加闹钟
	 * @param deviceClock
	 */
	int insertDeviceClock(DeviceClock deviceClock);
	
	/**
	 * 删除闹钟项
	 * @param deviceClock
	 */
	void deleteDeviceClock(DeviceClock deviceClock);
	
	/**
	 * 更新闹钟项
	 * @param deviceClock
	 */
	void updateDeviceClock(DeviceClock deviceClock);
	
	DeviceClock selectDeviceClockById(Integer id);
}
