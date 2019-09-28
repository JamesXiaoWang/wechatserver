package com.zhijia.wechatserver.src.deviceserver.service.mutualMsg;

import com.zhijia.wechatserver.src.deviceserver.entity.mutualMsg.VoiceMsg;

/**
 * @author Administrators
 * @date 2018年9月30日 上午10:56:25
 * @description:
 *
 */
public interface MutualMsgService {
	/**
	 * insert 语音消息保存
	 * @param voiceMsg
	 * @return
	 */
	void insertVoiceMsg(VoiceMsg voiceMsg);
}
