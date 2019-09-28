package com.zhijia.wechatserver.src.deviceserver.entity.wechat;

import java.io.Serializable;

/**
 * @author Administrators
 * @date 2018年11月6日 下午7:29:11
 * @description: 微信授权
 *
 */
public class WechatAuthorize implements Serializable {
	private static final long serialVersionUID = 1064425586797610493L;

	private Integer id;
	private String zjkey;
	private String gzhname;		// 公众号名称
	private String wechatid;	// 微信原始ID
	private String appid;		// appid
	private String appsecret;	// APPsecret密钥
	private String apptoken;	// token
	private String qrcodelink;	// 型号二维码
	private String authfile;	// 授权文件

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGzhname() {
		return gzhname;
	}

	public void setGzhname(String gzhname) {
		this.gzhname = gzhname;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getApptoken() {
		return apptoken;
	}

	public void setApptoken(String apptoken) {
		this.apptoken = apptoken;
	}

	public String getQrcodelink() {
		return qrcodelink;
	}

	public void setQrcodelink(String qrcodelink) {
		this.qrcodelink = qrcodelink;
	}

	public String getAuthfile() {
		return authfile;
	}

	public void setAuthfile(String authfile) {
		this.authfile = authfile;
	}

	public String getZjkey() {
		return zjkey;
	}

	public void setZjkey(String zjkey) {
		this.zjkey = zjkey;
	}

}
