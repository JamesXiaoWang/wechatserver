package com.zhijia.wechatserver.src.deviceserver.service.mutualMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.mutualMsg.VoiceMsg;
import com.zhijia.wechatserver.src.deviceserver.mapper.mutualMsg.MutualMsgMapper;

/**
 * @author Administrators
 * @date 2018年9月30日 上午10:57:03
 * @description:
 *
 */
@Service
@Transactional
public class MutualMsgServiceImpl implements MutualMsgService {
	@Autowired
	private MutualMsgMapper mutualMsgMapper;

	public void insertVoiceMsg(VoiceMsg voiceMsg) {
		mutualMsgMapper.insertVoiceMsg(voiceMsg);
	}

}
