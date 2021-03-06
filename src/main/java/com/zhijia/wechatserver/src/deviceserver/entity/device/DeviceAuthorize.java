package com.zhijia.wechatserver.src.deviceserver.entity.device;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrators
 * @date 2018年9月15日 上午9:31:27
 * @description: 设备授权信息
 *
 */
public class DeviceAuthorize implements Serializable {
	private static final long serialVersionUID = -4948821575139595379L;

	private Integer id;
	private String zjkey;
	private String productId; // 产品id
	private String deviceId;	// 设备Id
	private String mac;	
	/**
	 * 支持以下四种连接协议： 
	 * android classic bluetooth – 1 
	 * ios classic bluetooth – 2 
	 * ble – 3 
	 * wifi -- 4 
	 * 一个设备可以支持多种连接类型，用符号"|"做分割，客户端优先选择靠前的连接方式（优先级按|关系的排序依次降低），举例：
	 * 1：表示设备仅支持andiod classic bluetooth 
	 * 1|2：表示设备支持andiod 和ios 两种classic bluetooth，但是客户端优先选择andriod classic bluetooth 协议，如果andriod classic
	 * bluetooth协议连接失败，再选择ios classic bluetooth协议进行连接 （注：安卓平台不同时支持BLE和classic类型）
	 */
	private String connectProtocol;
	/*
	 * auth及通信的加密key，第三方需要将key烧制在设备上（128bit），格式采用16进制串的方式（长度为32字节），不需要0X前缀，如：
	 * 1234567890ABCDEF1234567890ABCDEF
	 */
	private String authKey;
	/*
	 * 断开策略，目前支持： 1：退出公众号页面时即断开连接 2：退出公众号之后保持连接不断开
	 */
	private String closeStrategy;
	/**
	 * 连接策略，32位整型，按bit位置位，目前仅第1bit和第3bit位有效（bit置0为无效，1为有效；第2bit已被废弃），且bit位可以按或置位（如1|4=5），各bit置位含义说明如下：
	 * 	 * 1：（第1bit置位）在公众号对话页面，不停的尝试连接设备
	 * 	 * 4：（第3bit置位）处于非公众号页面（如主界面等），微信自动连接。当用户切换微信到前台时，可能尝试去连接设备，连上后一定时间会断开
	 */
	private String connStrategy;
	/**
	 * uth加密方法，目前支持两种取值： 0：不加密 1：AES加密（CBC模式，PKCS7填充方式）
	 */
	private String cryptMethod;
	/**
	 * auth version，设备和微信进行auth时，会根据该版本号来确认auth buf和auth key的格式（各version对应的auth
	 * buf及key的具体格式可以参看“客户端蓝牙外设协议”），该字段目前支持取值： 0：不加密的version 1：version 1
	 */
	private String authVer;
	/**
	 * 表示mac地址在厂商广播manufature data里含有mac地址的偏移，取值如下： -1：在尾部、 -2：表示不包含mac地址
	 * 其他：非法偏移
	 */
	private String manuMacPos;
	/**
	 * 表示mac地址在厂商serial number里含有mac地址的偏移，取值如下： -1：表示在尾部 -2：表示不包含mac地址 其他：非法偏移
	 */
	private String serMacPos;
	/**
	 * 精简协议类型，取值如下：计步设备精简协议：1 （若该字段填1，connect_protocol 必须包括3。非精简协议设备切勿填写该字段）
	 */
	private String bleSimpleProtocol;
	
	private Date authDate;
	
	private Integer isAuthorize;
	
	private Integer isBind;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZjkey() {
		return zjkey;
	}

	public void setZjkey(String zjkey) {
		this.zjkey = zjkey;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getConnectProtocol() {
		return connectProtocol;
	}

	public void setConnectProtocol(String connectProtocol) {
		this.connectProtocol = connectProtocol;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getCloseStrategy() {
		return closeStrategy;
	}

	public void setCloseStrategy(String closeStrategy) {
		this.closeStrategy = closeStrategy;
	}

	public String getConnStrategy() {
		return connStrategy;
	}

	public void setConnStrategy(String connStrategy) {
		this.connStrategy = connStrategy;
	}

	public String getCryptMethod() {
		return cryptMethod;
	}

	public void setCryptMethod(String cryptMethod) {
		this.cryptMethod = cryptMethod;
	}

	public String getAuthVer() {
		return authVer;
	}

	public void setAuthVer(String authVer) {
		this.authVer = authVer;
	}

	public String getManuMacPos() {
		return manuMacPos;
	}

	public void setManuMacPos(String manuMacPos) {
		this.manuMacPos = manuMacPos;
	}

	public String getSerMacPos() {
		return serMacPos;
	}

	public void setSerMacPos(String serMacPos) {
		this.serMacPos = serMacPos;
	}

	public String getBleSimpleProtocol() {
		return bleSimpleProtocol;
	}

	public void setBleSimpleProtocol(String bleSimpleProtocol) {
		this.bleSimpleProtocol = bleSimpleProtocol;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public Integer getIsAuthorize() {
		return isAuthorize;
	}

	public void setIsAuthorize(Integer isAuthorize) {
		this.isAuthorize = isAuthorize;
	}

	public Integer getIsBind() {
		return isBind;
	}

	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}
	
}
