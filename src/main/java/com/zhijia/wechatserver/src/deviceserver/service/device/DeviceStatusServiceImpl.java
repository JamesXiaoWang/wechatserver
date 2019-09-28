package com.zhijia.wechatserver.src.deviceserver.service.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceStatus;
import com.zhijia.wechatserver.src.deviceserver.mapper.device.DeviceStatusMapper;

/**
 * @author Administrators
 * @date 2018年12月14日 下午12:12:52
 * @description:
 *
 */
@Service
@Transactional
public class DeviceStatusServiceImpl implements DeviceStatusService {
	@Autowired
	private DeviceStatusMapper deviceStatusMapper;

	/**
	 * 根据参数获取设备的状态信息
	 * @param zjkey
	 * @param deviceId
	 * @return
	 */
	public DeviceStatus selectDeviceStatusByDeviceId(String zjkey, String deviceId) {
		return deviceStatusMapper.selectDeviceStatusByDeviceId(zjkey, deviceId);
	}

	/**
	 * 更新设备状态
	 * @param status
	 */
	public void insertAndUpdateDeviceStatus(DeviceStatus status) {
		deviceStatusMapper.insertAndUpdateDeviceStatus(status);
	}

}
