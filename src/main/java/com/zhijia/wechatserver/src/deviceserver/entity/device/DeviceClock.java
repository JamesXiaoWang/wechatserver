package com.zhijia.wechatserver.src.deviceserver.entity.device;

import java.io.Serializable;

/**
 * @author Administrators
 * @date 2018年12月22日 下午12:18:51
 * @description: 设备锁
 *
 */
public class DeviceClock implements Serializable {
	private static final long serialVersionUID = 401749530656805572L;

	private Integer id;
	private String openid;
	private String zjkey;
	private String deviceId;
	private String title;
	private String clock;
	private long time;
	private Integer status;
	private String repeatTime;						//重复时间
	private String repeatStr;						//重复频率
	private Integer deleted;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getClock() {
		return clock;
	}

	public void setClock(String clock) {
		this.clock = clock;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRepeatTime() {
		return repeatTime;
	}

	public void setRepeatTime(String repeatTime) {
		this.repeatTime = repeatTime;
	}

	public String getRepeatStr() {
		return repeatStr;
	}

	public void setRepeatStr(String repeatStr) {
		this.repeatStr = repeatStr;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

}
