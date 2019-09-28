package com.zhijia.wechatserver.src.deviceserver.service.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceClock;
import com.zhijia.wechatserver.src.deviceserver.mapper.device.DeviceClockMapper;

/**
 * @author Administrators
 * @date 2018年12月22日 下午12:45:51
 * @description:
 *
 */
@Service
@Transactional
public class DeviceClockServiceImpl implements DeviceClockService {
	@Autowired
	private DeviceClockMapper deviceClockMapper;

	/**
	 * 获取某个用户对某台设备的闹钟列表
	 * @param openid
	 * @param zjkey
	 * @param deviceId
	 * @return
	 */
	public List<DeviceClock> selectDeviceClockListByParams(String openid, String zjkey, String deviceId) {
		return deviceClockMapper.selectDeviceClockListByParams(openid, zjkey, deviceId);
	}

	/**
	 * 添加闹钟
	 * @param deviceClock
	 */
	public int insertDeviceClock(DeviceClock deviceClock) {
		return deviceClockMapper.insertDeviceClock(deviceClock);
	}

	/**
	 * 删除闹钟项
	 * @param deviceClock
	 */
	public void deleteDeviceClock(DeviceClock deviceClock) {
		deviceClockMapper.deleteDeviceClock(deviceClock);
	}

	/**
	 * 更新闹钟项
	 * @param deviceClock
	 */
	public void updateDeviceClock(DeviceClock deviceClock) {
		deviceClockMapper.updateDeviceClock(deviceClock);
	}

	@Override
	public DeviceClock selectDeviceClockById(Integer id) {
		return deviceClockMapper.selectDeviceClockById(id);
	}

}
