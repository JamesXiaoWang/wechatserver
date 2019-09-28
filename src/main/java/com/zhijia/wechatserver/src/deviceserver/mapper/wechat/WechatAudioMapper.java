package com.zhijia.wechatserver.src.deviceserver.mapper.wechat;

import com.zhijia.wechatserver.src.deviceserver.entity.wechat.Audio;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrators
 * @date 2018年11月6日 下午7:29:39
 * @description:
 *
 */
@Mapper
public interface WechatAudioMapper {
	List<Audio> selectWechatAudioFile();

	int addWechatAudioFile(Audio audio);
}
