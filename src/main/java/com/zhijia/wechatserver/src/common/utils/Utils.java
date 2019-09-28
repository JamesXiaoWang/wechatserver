package com.zhijia.wechatserver.src.common.utils;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

public class Utils {

	/*
	 * 判断对象是否空值
	 */
	public static boolean isObjNull(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj == "") {
			return true;
		} else if (obj == "null") {
			return true;
		} else if ("".equals(obj)) {
			return true;
		}
		return false;
	}

	private final static String ENCODE = "UTF-8";

	/**
	 * URL 解码
	 *
	 * @return String
	 */
	public static String getURLDecoderString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLDecoder.decode(str, ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * URL 转码
	 *
	 * @return String
	 */
	public static String getURLEncoderString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLEncoder.encode(str, ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 下载项目根目录下doc下的文件
	 * 
	 * @param response
	 *            response
	 * @param fileName
	 *            文件名
	 * @return 返回结果 成功或者文件不存在
	 */
	public static String downloadFile(HttpServletResponse response, String fileName) {
		InputStream stream = Utils.class.getClassLoader().getResourceAsStream("authfile/" + fileName);
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");
		try {
			String name = java.net.URLEncoder.encode(fileName, "UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLDecoder.decode(name, "ISO-8859-1"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			bis = new BufferedInputStream(stream);
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (FileNotFoundException e1) {
			return "系统找不到指定的文件";
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "success";
	}
	
	public static String getRequestResultByErrorCode(int errorcode){
		String result = "";
		switch (errorcode) {
			case 40003 :
				result = "openid 不合法";
				break;
			case 40013 :
				result = "appid 不合法";
				break;
			case 41009 :
				result = "缺少openID 参数";
				break;
			case 43001 :
				result = "要求使用GET请求";
				break;
			case 43002 :
				result = "要求使用POST请求";
				break;
			case 43003 :
				result = "要求使用HTTPS";
				break;
			case 43005 :
				result = "要求是好友关系";
				break;
			case 44002 :
				result = "post数据为空";
				break;
			case 47001 :
				result = "数据格式有误";
				break;
			case 0 :
				result = "成功";
				break;
			case -1 :
				result = "系统错误";
				break;
			case 100001 :
				result = "查询请求不存在";
				break;
			case 100002 :
				result = "新增请求已经存在";
				break;
			case 100003 :
				result = "请求中的数据大小不合法";
				break;
			case 100004 :
				result = "二维码不合法";
				break;
			case 100005 :
				result = "device type不合法";
				break;
			case 100006 :
				result = "device id不合法";
				break;
			case 100007 :
				result = "设备状态不合法";
				break;
			case 100008 :
				result = "mac地址不合法";
				break;
			case 100009 :
				result = "connect protocol 不合法";
				break;
			case 100010 :
				result = "auth key 不合法";
				break;
			case 100011 :
				result = "close strategy 不合法";
				break;
			case 100012 :
				result = "connect strategy 不合法";
				break;
			case 100013 :
				result = "crypt method 不合法";
				break;
			case 100014 :
				result = "auth version 不合法";
				break;
			case 100015 :
				result = "manufature mac position 不合法";
				break;
			case 100016 :
				result = "serial number mac position 不合法";
				break;
			case 100017 :
				result = "批量处理请求数量不合法";
				break;
			case 100018 :
				result = "optype 不合法";
				break;
			case 100019 :
				result = "账号状态不合法";
				break;
			case 100020 :
				result = "账号 quota 已用完";
				break;
			case 100021 :
				result = "用户和设备的绑定关系不存在";
				break;
			case 100022 :
				result = "消息类型不合法（msg type invalid）";
				break;
			case 100023 :
				result = "消息内容不合法（msg content invalid）";
				break;
			case 100024 :
				result = "用户当前没有订阅WIFI设备的状态（user not subscribe device status）";
				break;
			case 100025 :
				result = "设备属性未设置（device attr not set）";
				break;
			case 100026 :
				result = "票据不合法（ticket invalid）";
				break;
			default:
				break;
		}
		return result;
	}
}
