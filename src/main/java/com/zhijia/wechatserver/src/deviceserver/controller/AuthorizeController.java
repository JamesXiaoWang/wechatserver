package com.zhijia.wechatserver.src.deviceserver.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhijia.wechatserver.src.common.base.BaseController;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;

/**
 * @author Administrators
 * @date 2018年9月13日 下午12:02:32
 * @description: 与微信服务器交互控制类
 *
 */
@Controller
@RequestMapping(value = "/authorize")
public class AuthorizeController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AuthorizeController.class);
	
	/**
	 * 加载操作指示
	 * @param request
	 * @param response
	 * @param map
	 * @return	指示页面
	 */
	@RequestMapping(value = "/networkingBinding/{appid}")
	public String getNetworkingBinding(@PathVariable("appid") String appid, Map<String, Object> map) {
		try {
			map.put("appid", appid);
			List<WechatAuthorize> wechatAuthorizes = wechatAuthorizeService.selectWechatAuthorizeByAppId(appid);
			map.put("wechatAuthorizes", wechatAuthorizes);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("加载操作指示页面出错：" + e.getMessage());
		}
		return "dms/networkingBinding";
	}
	
	/**
	 * 加载操作指示
	 * @param request
	 * @param response
	 * @param map
	 * @return	指示页面
	 */
	@RequestMapping(value = "/operation/{appid}")
	public String operation(Map<String, Object> map, @PathVariable("appid") String appid, String zjkey) {
		try {
			WechatAuthorize authorize = wechatAuthorizeService.selectWechatAuthorizeByParams(appid, zjkey);
			map.put("qrcode", authorize.getQrcodelink());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("加载操作指示页面出错：" + e.getMessage());
		}
		return "dms/qrcode";
	}
}
