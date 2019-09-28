package com.zhijia.wechatserver.src.deviceserver.entity.device;

import java.io.Serializable;

/**
 * @author Administrators
 * @date 2018年10月15日 上午10:19:12
 * @description: 设备绑定状态
 *
 */
public class DeviceMqtt implements Serializable {
	private static final long serialVersionUID = 7671567253965681497L;
	
	private Integer id;
	private String openid;
	private String zjkey;
	private String deviceId;
	private String topic;
	private Integer mqttBind;
	private Integer status;
	
	private String deviceName;
	private String device;
	
	private Integer isOnline;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Integer getMqttBind() {
		return mqttBind;
	}

	public void setMqttBind(Integer mqttBind) {
		this.mqttBind = mqttBind;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getZjkey() {
		return zjkey;
	}

	public void setZjkey(String zjkey) {
		this.zjkey = zjkey;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}
}
