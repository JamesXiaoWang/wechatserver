package com.zhijia.wechatserver.src.common.message;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:39:56
 * @description: 微信消息实体模板
 *
 */
@Component
public class WechatMessageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 922929268286201250L;

	private String fromUserName; // 发送发微信账号

	private String toUserName; // 接收方微信账号

	private String weixinUserName; // 微信用户名

	private String messageType; // 消息类型

	private String deviceId; // 绑定的设备ID

	/**
	 * @return the fromUserName
	 */
	public String getFromUserName() {
		return fromUserName;
	}

	/**
	 * @param fromUserName
	 *            the fromUserName to set
	 */
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	/**
	 * @return the toUserName
	 */
	public String getToUserName() {
		return toUserName;
	}

	/**
	 * @param toUserName
	 *            the toUserName to set
	 */
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	/**
	 * @return the weixinUserName
	 */
	public String getWeixinUserName() {
		return weixinUserName;
	}

	/**
	 * @param weixinUserName
	 *            the weixinUserName to set
	 */
	public void setWeixinUserName(String weixinUserName) {
		this.weixinUserName = weixinUserName;
	}

	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType
	 *            the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "WeixinMessageInfo [fromUserName=" + fromUserName + ", toUserName=" + toUserName + ", weixinUserName="
				+ weixinUserName + ", messageType=" + messageType + "]";
	}
}
