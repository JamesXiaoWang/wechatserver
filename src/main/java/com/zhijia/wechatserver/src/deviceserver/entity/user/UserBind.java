package com.zhijia.wechatserver.src.deviceserver.entity.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrators
 * @date 2018年9月26日 上午11:29:35
 * @description: 用户表
 *
 */
public class UserBind implements Serializable {
	private static final long serialVersionUID = -2793196888730745965L;

	private Integer id;
	private String openid;
	private String deviceId;
	private Integer isBind;
	private Date bindDate;
	private Date unbindDate;

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

	public Integer getIsBind() {
		return isBind;
	}

	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public Date getUnbindDate() {
		return unbindDate;
	}

	public void setUnbindDate(Date unbindDate) {
		this.unbindDate = unbindDate;
	}
}
