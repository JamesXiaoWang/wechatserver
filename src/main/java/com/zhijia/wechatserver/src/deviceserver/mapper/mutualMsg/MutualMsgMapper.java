package com.zhijia.wechatserver.src.deviceserver.mapper.mutualMsg;

import org.apache.ibatis.annotations.Mapper;

import com.zhijia.wechatserver.src.deviceserver.entity.mutualMsg.VoiceMsg;

/**
 * @author Administrators
 * @date 2018年9月30日 上午10:40:09
 * @description: 交互信息 Mapper
 *
 */
@Mapper
public interface MutualMsgMapper {
	/**
	 * insert 语音消息保存
	 * @param voiceMsg
	 * @return
	 */
	int insertVoiceMsg(VoiceMsg voiceMsg);
}
