package com.zhijia.wechatserver.src.deviceserver.service.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceMqtt;
import com.zhijia.wechatserver.src.deviceserver.mapper.device.DeviceMqttMapper;

/**
 * @author Administrators
 * @date 2018年10月15日 下午12:25:43
 * @description:
 *
 */
@Service
@Transactional
public class DeviceMqttServiceImpl implements DeviceMqttService {
	
	@Autowired
	private DeviceMqttMapper deviceMqttMapper;

	public void insertDeviceMqtt(DeviceMqtt deviceMqtt) {
		try {
			deviceMqttMapper.insertDeviceMqtt(deviceMqtt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DeviceMqtt selectMqttBindForDevice(String openid, String zjkey, String deviceId) {
		return deviceMqttMapper.selectMqttBindForDevice(openid, zjkey, deviceId);
	}
	
	public DeviceMqtt selectMqttBindForDevice(String openid) {
		return deviceMqttMapper.selectMqttBindForDeviceByOpenId(openid);
	}

	public List<DeviceMqtt> selectMqttBindForDeviceList(String openid) {
		return deviceMqttMapper.selectMqttBindForDeviceList(openid);
	}

	public List<DeviceMqtt> selectMqttBindForDeviceListByOpenId(String openid) {
		return deviceMqttMapper.selectMqttBindForDeviceListByOpenId(openid);
	}

	public int updateDeviceMqttBindForOpenId(String openid, Integer status, String clientid) {
		return deviceMqttMapper.updateDeviceMqttBindForOpenId(openid, status, clientid);
	}

	public void updateDeviceMqttBindForParams(DeviceMqtt deviceMqtt) {
		deviceMqttMapper.updateDeviceMqttBindForParams(deviceMqtt);
	}

	public DeviceMqtt selectLastMqttBindForDeviceByOpenId(String openid) {
		return deviceMqttMapper.selectLastMqttBindForDeviceByOpenId(openid);
	}

	public void updateDeviceIsOnline(Integer online, String clientid) {
		deviceMqttMapper.updateDeviceIsOnline(online, clientid);
	}

	public List<DeviceMqtt> selectMqttBindForDeviceIsExist(String openid) {
		return deviceMqttMapper.selectMqttBindForDeviceIsExist(openid);
	}

	public List<DeviceMqtt> selectDeviceBindListForOpenId(String zjkey, String deviceId) {
		return deviceMqttMapper.selectDeviceBindListForOpenId(zjkey, deviceId);
	}
}
