package com.zhijia.wechatserver.src.deviceserver.entity.device;

import java.io.Serializable;

/**
 * @author Administrators
 * @date 2019年1月10日 下午6:01:27
 * @description: 设备音频状态
 *
 */
public class DeviceAudioStatus implements Serializable {
	private static final long serialVersionUID = 3479133600264074111L;

	private String zjkey;
	private String deviceId;
	private Integer play;
	private Integer audioId;
	
	private String section;
	private Integer resId;
	private String res;

	public String getZjkey() {
		return zjkey;
	}

	public void setZjkey(String zjkey) {
		this.zjkey = zjkey;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getPlay() {
		return play;
	}

	public void setPlay(Integer play) {
		this.play = play;
	}

	public Integer getAudioId() {
		return audioId;
	}

	public void setAudioId(Integer audioId) {
		this.audioId = audioId;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

}
