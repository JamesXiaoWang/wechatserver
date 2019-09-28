package com.zhijia.wechatserver.src.deviceserver.mapper.wechat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;

/**
 * @author Administrators
 * @date 2018年11月6日 下午7:29:39
 * @description:
 *
 */
@Mapper
public interface WechatAuthorizeMapper {
	/**
	 * 根据微信原始ID获取公众号基础信息
	 * @param wechatid
	 * @return
	 */
	WechatAuthorize selectWechatAuthorizeByWechatid(@Param("wechatid")String wechatid);
	
	/**
	 * 根据微信用户ID和zjkey获取公众号基础信息
	 * @param appid
	 * @param zjkey
	 * @return
	 */
	WechatAuthorize selectWechatAuthorizeByParams(@Param("appid")String appid, @Param("zjkey")String zjkey);
	/**
	 * 根据微信用户ID获取关联的公众号产品列表
	 * @param appid
	 * @return
	 */
	List<WechatAuthorize> selectWechatAuthorizeByAppId(@Param("appid")String appid);
	
	List<WechatAuthorize> selectWechatAuthorizeList();
}
