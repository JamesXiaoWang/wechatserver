package com.zhijia.wechatserver.src.deviceserver.service.wechat;

import com.zhijia.wechatserver.src.deviceserver.entity.wechat.Audio;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;
import com.zhijia.wechatserver.src.deviceserver.mapper.wechat.WechatAudioMapper;
import com.zhijia.wechatserver.src.deviceserver.mapper.wechat.WechatAuthorizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrators
 * @date 2018年11月6日 下午7:30:54
 * @description:
 *
 */
@Service
@Transactional
public class WechatAudioServiceImpl implements WechatAudioService {
	@Autowired
	private WechatAudioMapper wechatAudioMapper;

	@Override
	public List<Audio> selectWechatAudioFile() {
		return wechatAudioMapper.selectWechatAudioFile();
	}

	@Override
	public int addWechatAudioFile(Audio audio) {
		return wechatAudioMapper.addWechatAudioFile(audio);
	}
}
