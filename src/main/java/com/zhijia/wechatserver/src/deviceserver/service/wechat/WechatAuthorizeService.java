package com.zhijia.wechatserver.src.deviceserver.service.wechat;

import java.util.List;

import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;

/**
 * @author Administrators
 * @date 2018年11月6日 下午7:30:08
 * @description:
 *
 */
public interface WechatAuthorizeService {
	/**
	 * 根据微信原始ID获取公众号基础信息
	 * @param wechatid
	 * @return
	 */
	WechatAuthorize selectWechatAuthorizeByWechatid(String wechatid);
	
	/**
	 * 根据微信用户ID和zjkey获取公众号基础信息
	 * @param appid
	 * @param zjkey
	 * @return
	 */
	WechatAuthorize selectWechatAuthorizeByParams(String appid, String zjkey);
	
	/**
	 * 根据微信用户ID获取关联的公众号产品列表
	 * @param appid
	 * @return
	 */
	List<WechatAuthorize> selectWechatAuthorizeByAppId(String appid);
	
	List<WechatAuthorize> selectWechatAuthorizeList();
}
