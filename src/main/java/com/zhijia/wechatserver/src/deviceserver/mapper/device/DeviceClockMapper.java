package com.zhijia.wechatserver.src.deviceserver.mapper.device;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceClock;

/**
 * @author Administrators
 * @date 2018年12月22日 下午12:18:21
 * @description:
 *
 */
@Mapper
public interface DeviceClockMapper {
	/**
	 * 获取某个用户对某台设备的闹钟列表
	 * @param openid
	 * @param zjkey
	 * @param deviceId
	 * @return
	 */
	List<DeviceClock> selectDeviceClockListByParams(@Param("openid")String openid, @Param("zjkey")String zjkey, @Param("deviceId")String deviceId);
	
	/**
	 * 添加闹钟
	 * @param deviceClock
	 */
	int insertDeviceClock(DeviceClock deviceClock);
	
	/**
	 * 删除闹钟项
	 * @param deviceClock
	 */
	int deleteDeviceClock(DeviceClock deviceClock);
	
	/**
	 * 更新闹钟项
	 * @param deviceClock
	 */
	int updateDeviceClock(DeviceClock deviceClock);
	
	DeviceClock selectDeviceClockById(@Param("id")Integer id);
}
