package com.zhijia.wechatserver.src.deviceserver.mapper.device;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceStatus;

/**
 * @author Administrators
 * @date 2018年12月14日 上午11:52:06
 * @description: 设备状态Mapper接口
 *
 */
@Mapper
public interface DeviceStatusMapper {
	/**
	 * 根据参数获取设备的状态信息
	 * @param zjkey
	 * @param deviceId
	 * @return
	 */
	DeviceStatus selectDeviceStatusByDeviceId(@Param("zjkey")String zjkey, @Param("deviceId")String deviceId);

	/**
	 * 更新设备状态
	 * @param status
	 */
	void insertAndUpdateDeviceStatus(DeviceStatus status);
}

