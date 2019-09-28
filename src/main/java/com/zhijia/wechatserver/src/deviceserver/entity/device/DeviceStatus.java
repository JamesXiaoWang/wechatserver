package com.zhijia.wechatserver.src.deviceserver.entity.device;

import java.io.Serializable;

/**
 * @author Administrators
 * @date 2018年12月14日 上午11:12:11
 * @description: 设备状态
 *
 */
public class DeviceStatus implements Serializable {
	private static final long serialVersionUID = 2923740774878949826L;

	private String zjkey;
	private String deviceId;
	private Integer vol; // 音量（0-100）
	private Integer battery;// 电量（0-100）
	private Integer sfree; // 可用空间
	private Integer stotal; // 总空间
	private Integer shake;
	private Integer power;
	private Integer bln; // 呼吸灯控制开关
	private Integer play;
	private Integer charging;// 是否充电
	private Integer lbi; // 低电量提醒
	private Integer tcard; // T卡状态（0-未插入，1-已插入）
	private Integer online;
	private String clock;

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

	public Integer getVol() {
		return vol;
	}

	public void setVol(Integer vol) {
		this.vol = vol;
	}

	public Integer getBattery() {
		return battery;
	}

	public void setBattery(Integer battery) {
		this.battery = battery;
	}

	public Integer getSfree() {
		return sfree;
	}

	public void setSfree(Integer sfree) {
		this.sfree = sfree;
	}

	public Integer getStotal() {
		return stotal;
	}

	public void setStotal(Integer stotal) {
		this.stotal = stotal;
	}

	public Integer getShake() {
		return shake;
	}

	public void setShake(Integer shake) {
		this.shake = shake;
	}

	public Integer getBln() {
		return bln;
	}

	public void setBln(Integer bln) {
		this.bln = bln;
	}

	public Integer getPlay() {
		return play;
	}

	public void setPlay(Integer play) {
		this.play = play;
	}

	public Integer getCharging() {
		return charging;
	}

	public void setCharging(Integer charging) {
		this.charging = charging;
	}

	public Integer getLbi() {
		return lbi;
	}

	public void setLbi(Integer lbi) {
		this.lbi = lbi;
	}

	public Integer getTcard() {
		return tcard;
	}

	public void setTcard(Integer tcard) {
		this.tcard = tcard;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getClock() {
		return clock;
	}

	public void setClock(String clock) {
		this.clock = clock;
	}

}
