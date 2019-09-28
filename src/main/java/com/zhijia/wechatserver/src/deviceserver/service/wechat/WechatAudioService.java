package com.zhijia.wechatserver.src.deviceserver.service.wechat;

import com.zhijia.wechatserver.src.deviceserver.entity.wechat.Audio;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;

import java.util.List;

/**
 * @author Administrators
 * @date 2018年11月6日 下午7:30:08
 * @description:
 *
 */
public interface WechatAudioService {

	List<Audio> selectWechatAudioFile();

	int addWechatAudioFile(Audio audio);
}
