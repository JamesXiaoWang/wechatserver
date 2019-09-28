package com.zhijia.wechatserver.src.deviceserver.entity;

import org.jrebirth.af.api.annotation.bean.Getter;
import org.jrebirth.af.api.annotation.bean.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrators
 * @date 2019年2月18日 上午11:44:27
 * @description: 人机对话记录
 *
 */
public class DeviceChat implements Serializable {
	private static final long serialVersionUID = -8226707165310666865L;

	private Integer id;
	private String zjkey;
	private String deviceId;
	private String asr;
	private String tts;
	private Date chattime;
	private String sfm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getAsr() {
		return asr;
	}

	public void setAsr(String asr) {
		this.asr = asr;
	}

	public String getTts() {
		return tts;
	}

	public void setTts(String tts) {
		this.tts = tts;
	}

	public Date getChattime() {
		return chattime;
	}

	public void setChattime(Date chattime) {
		this.chattime = chattime;
	}

	public String getSfm() {
		return sfm;
	}

	public void setSfm(String sfm) {
		this.sfm = sfm;
	}

}
