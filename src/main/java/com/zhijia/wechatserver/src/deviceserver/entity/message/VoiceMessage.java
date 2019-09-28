package com.zhijia.wechatserver.src.deviceserver.entity.message;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:26:34
 * @description:
 *
 */
public class VoiceMessage extends WechatBaseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -257437792312228249L;
	
	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}
	
}
