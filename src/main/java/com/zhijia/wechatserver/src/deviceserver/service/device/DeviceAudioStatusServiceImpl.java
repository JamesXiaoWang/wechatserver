package com.zhijia.wechatserver.src.deviceserver.service.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAudioStatus;
import com.zhijia.wechatserver.src.deviceserver.mapper.device.DeviceAudioStatusMapper;

/**
 * @author Administrators
 * @date 2019年1月10日 下午6:23:23
 * @description:
 *
 */
@Service
@Transactional
public class DeviceAudioStatusServiceImpl implements DeviceAudioStatusService {

	@Autowired
	private DeviceAudioStatusMapper deviceAudioStatusMapper;
	
	public int insertOrUpdateDeviceAudioStatus(DeviceAudioStatus audioStatus) {
		return deviceAudioStatusMapper.insertOrUpdateDeviceAudioStatus(audioStatus);
	}

	public DeviceAudioStatus selectDeviceAudioStatusByDeviceId(String deviceId) {
		return deviceAudioStatusMapper.selectDeviceAudioStatusByDeviceId(deviceId);
	}

}
