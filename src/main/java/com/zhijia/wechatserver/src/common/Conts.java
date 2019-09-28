package com.zhijia.wechatserver.src.common;
/**
 * @author Administrators
 * @date 2018年9月13日 下午5:53:41
 * @description: 公共类
 *
 */
public class Conts {
	// 字符集编码
	public static final String CHARSET = "utf-8";
	// app name
	public static final String APP_NAME = "zhijia";
	
	// 微信唯一标识APPID，通过微信公众号获取
	public static final String APP_ID = "wxe2c15d10dd6983bf";
	// 微信唯一密钥，通过微信公众号获取
	public static final String APP_SECRET = "87dd09e944868071dcd7a356a5270c76";
	// 微信服务器定于的TOKEN
	public static final String APP_TOKEN = "zjdb1024";// 根据实际情况自己定义token与基本配置/填写服务器配置Token(令牌)相同
	// 产品编号（用于设备授权）
	public static final String PRODUCT_ID = "50408";
	// MQTT服务器的host
	public static final String MQTT_HOST = "tcp://47.106.172.221:8081";

	// 公众号点播资源的根URL
	public static final String RESOURCE_ACCESS_ADDR  = "http://47.106.172.221:8080/prod/zjresource/";

	// 服务器临时文件存储(文字转语音文件)
	public static final String WORD_TO_VOICE_FILEPATH = "/cszjdata/resources/wechat-server/wordtovoice/";
}
