package com.zhijia.wechatserver.src.deviceserver.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import org.apache.http.HttpException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhijia.wechatserver.src.common.Conts;
import com.zhijia.wechatserver.src.common.base.BaseController;
import com.zhijia.wechatserver.src.common.utils.SHA1;
import com.zhijia.wechatserver.src.common.utils.Utils;

import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年9月11日 上午10:11:21
 * @description: 微信服务器消息验证
 *
 */
@RestController
@RequestMapping(value = "/weixin")
public class WechatServerController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(WechatServerController.class);

	@RequestMapping(value = "/platform/{appid}", produces = "text/html;charset=UTF-8")
	public String platform(HttpServletRequest request, HttpServletResponse response, @PathVariable("appid") String appid)
			throws ServletException, IOException, HttpException, KeyManagementException, NoSuchAlgorithmException, MqttException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");

		logger.info("【platform】: appid [" + appid + "]; signature is [" + signature + "]; timestamp is [" + timestamp + "]; nonce is [" + nonce + "]; echostr is [" + echostr + "]");
		
		String[] str = { Conts.APP_TOKEN, timestamp, nonce };
		// 字典序排序
		Arrays.sort(str);

		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
		
		if (digest.equals(signature)) {
			// 微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
			request.setCharacterEncoding("UTF-8");
			// 在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
			response.setCharacterEncoding("UTF-8");
			String respXml = wechatCoreService.weixinMessageHandelCoreService(request, response, appid);
			if (!Utils.isObjNull(respXml)) {
				return respXml;
			}else{
				return echostr;
			}
		} else {
			// 此处可以实现其他逻辑
			logger.info("不是微信服务器发过来的请求，请小心！");
			return null;
		}
	}

	/**
	 * 验证与设备交互的消息来自于微信服务器
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws HttpException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value = "/iot/{appid}", produces = "text/html;charset=UTF-8")
	public String iot(HttpServletRequest request, HttpServletResponse response, @PathVariable("appid") String appid)
			throws ServletException, IOException, HttpException, KeyManagementException, NoSuchAlgorithmException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");

		String[] str = { Conts.APP_TOKEN, timestamp, nonce };
		
		logger.info("【iot】: appid [" + appid + "]; signature is [" + signature + "]; timestamp is [" + timestamp + "]; nonce is [" + nonce + "]; echostr is [" + echostr + "]");

		// 字典序排序
		Arrays.sort(str);
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
		if (digest.equals(signature)) {
			// 微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
			request.setCharacterEncoding("UTF-8");
			// 在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
			response.setCharacterEncoding("UTF-8");
			String respXml = wechatCoreService.weixinMessageHandelCoreService(request, response, appid);
			if (!Utils.isObjNull(respXml)) {
				return respXml;
			}else{
				return echostr;
			}
		} else {
			// 此处可以实现其他逻辑
			logger.info("不是微信服务器发过来的请求，请小心！");
			return null;
		}
	}
	
	/**
	 * 设备端获取访问微信公众号服务器请求token
	 * @param zjkey
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	@RequestMapping(value = "/accessToken/{appid}")
	public JSONObject accessToken(@PathVariable("appid")String appid) throws HttpException, IOException{
		String token_key = appid + "_atoken";
		String access_token = (String) redisService.get(token_key);
		JSONObject json = new JSONObject();
		json.put("access_token", access_token);
		
		return json;
	}
}
