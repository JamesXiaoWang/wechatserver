package com.zhijia.wechatserver.src.deviceserver.entity.mutualMsg;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrators
 * @date 2018年9月30日 上午10:36:06
 * @description: 交互信息 - 语音信息
 *
 */
public class VoiceMsg implements Serializable {
	private static final long serialVersionUID = 1017284209086709318L;

	// 消息id，64位整型
	private Long msgId;
	// 接收方账号(收到的OpenID)
	private String toUserName;
	// 开发者微信号
	private String fromUserName;
	// 消息类型(voice)
	private String msgType;
	// 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String mediaId;
	// 语音格式，如amr，speex等
	private String format;
	// 语音识别结果
	private String recognition;
	// 消息创建时间(整型)
	private long createTime;
	// 创建时间转换
	private Date createTimeTf;
	// 消息发送状态（mqtt相关）
	private boolean ratained;
	
	// 设备ID
	private String deviceId;

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public Date getCreateTimeTf() {
		return createTimeTf;
	}

	public void setCreateTimeTf(Date createTimeTf) {
		this.createTimeTf = createTimeTf;
	}

	public boolean isRatained() {
		return ratained;
	}

	public void setRatained(boolean ratained) {
		this.ratained = ratained;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
