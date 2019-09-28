package com.zhijia.wechatserver.src.deviceserver.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;

import com.zhijia.wechatserver.src.common.mqtt.MQTTConnectDevice;

import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:07:49
 * @description:
 *
 */
public interface WechatCoreService {
	
	public MQTTConnectDevice mqttServer = new MQTTConnectDevice();
	
	public String weixinMessageHandelCoreService(HttpServletRequest request, HttpServletResponse response, String appid);
	
	/**
	 * 获取access_token
	 * @param appid   公众号唯一id
	 * @param secret  密钥
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public JSONObject getAccess_token(String appid, String secret) throws HttpException, IOException;
	
	/**
	 * 获取设备二维码
	 * @param accesstoken 
	 * @param params
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public JSONObject getDeviceQrcode(String accesstoken, JSONObject params) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException; 
	
	/**
	 * 设备授权
	 * @param accesstoken
	 * @param params
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws URISyntaxException 
	 */
	public JSONObject deviceAuthorize(String accesstoken, JSONObject params) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException, URISyntaxException;

	/**
	 * 设备绑定
	 * @param accesstoken
	 * @param params
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JSONObject deviceBind(String accesstoken, JSONObject params) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException;

	/**
	 * 获取设备状态
	 * @param accesstoken
	 * @param deviceid
	 * @return
	 */
	public int getDeviceStatuc(String accesstoken, String deviceid);
	
}
