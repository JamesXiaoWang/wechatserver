package com.zhijia.wechatserver.src.deviceserver.entity.message;

/**
 * @author Administrators
 * @date 2018年9月11日 上午10:49:52
 * @description:
 *
 */
public class TextMessage extends WechatBaseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3754423183486342413L;
	private String Content;
	private String MsgId;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
