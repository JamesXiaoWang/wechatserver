package com.zhijia.wechatserver.src.deviceserver.service.device;

import java.util.List;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceMqtt;

/**
 * @author Administrators
 * @date 2018年10月15日 下午12:24:14
 * @description: 设备 - MQTT Service
 *
 */
public interface DeviceMqttService {
	/**
	 * 插入mqtt-device关联记录
	 * @param deviceMqtt
	 * @return
	 */
	void insertDeviceMqtt(DeviceMqtt deviceMqtt);
	
	/**
	 * 根据zjkey和deviceId获取mqtt绑定信息
	 * @param zjkey
	 * @param deviceId
	 * @return
	 */
	DeviceMqtt selectMqttBindForDevice(String openid, String zjkey, String deviceId);
	
	/**
	 * 根据微信用户ID（openId）与mqtt绑定的设备记录
	 * @param openid
	 * @return
	 */
	DeviceMqtt selectMqttBindForDevice(String openid);
	
	/**
	 * 根据微信用户ID（openId）与mqtt绑定的最新设备记录
	 * @param openid
	 * @return
	 */
	DeviceMqtt selectLastMqttBindForDeviceByOpenId(String openid);
	
	/**
	 * 获取微信用户与设备绑定的有效记录
	 * @param openid
	 * @return
	 */
	List<DeviceMqtt> selectMqttBindForDeviceList(String openid);
	
	/**
	 * 获取微信用户与设备-mqtt绑定的有效记录
	 * @param openid
	 * @return
	 */
	List<DeviceMqtt> selectMqttBindForDeviceListByOpenId(String openid);
	
	/**
	 * 取消关注公众号时，update微信用户的绑定状态
	 * @param openid
	 * @return
	 */
	int updateDeviceMqttBindForOpenId(String openid, Integer status, String clientid);
	
	/**
	 * update 微信-设备-mqtt的绑定状态
	 * @param deviceMqtt
	 */
	void updateDeviceMqttBindForParams(DeviceMqtt deviceMqtt);
	
	/**
	 * update 设备在线状态
	 * @param online
	 * @param clientid
	 */
	void updateDeviceIsOnline(Integer online, String clientid);
	
	/**
	 * 获取系统中用户已存在关联过的设备
	 * @param openid
	 * @param zjkey
	 * @return
	 */
	List<DeviceMqtt> selectMqttBindForDeviceIsExist(String openid);
	
	/**
	 * 获取设备对应绑定的用户列表
	 * @param zjkey
	 * @param deviceId	设备ID
	 * @return
	 */
	List<DeviceMqtt> selectDeviceBindListForOpenId(String zjkey, String deviceId);
}
