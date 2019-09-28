package com.zhijia.wechatserver.src.deviceserver.service.wechat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;
import com.zhijia.wechatserver.src.deviceserver.mapper.wechat.WechatAuthorizeMapper;

/**
 * @author Administrators
 * @date 2018年11月6日 下午7:30:54
 * @description:
 *
 */
@Service
@Transactional
public class WechatAuthorizeServiceImpl implements WechatAuthorizeService {
	@Autowired
	private WechatAuthorizeMapper wechatAuthorizeMapper;

	public WechatAuthorize selectWechatAuthorizeByParams(String appid, String zjkey) {
		return wechatAuthorizeMapper.selectWechatAuthorizeByParams(appid, zjkey);
	}

	public List<WechatAuthorize> selectWechatAuthorizeByAppId(String appid) {
		return wechatAuthorizeMapper.selectWechatAuthorizeByAppId(appid);
	}

	public WechatAuthorize selectWechatAuthorizeByWechatid(String wechatid) {
		return wechatAuthorizeMapper.selectWechatAuthorizeByWechatid(wechatid);
	}

	public List<WechatAuthorize> selectWechatAuthorizeList() {
		return wechatAuthorizeMapper.selectWechatAuthorizeList();
	}

}
