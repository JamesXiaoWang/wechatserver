package com.zhijia.wechatserver.src.mqttserver.entity;

import java.io.Serializable;

public class MqttAcl implements Serializable {
	private static final long serialVersionUID = 7566881415780510342L;
	
	private Integer id;
	private Integer allow;
	private String ipaddr;
	private String username;
	private String clientid;
	private Integer access;
	private String topic;
	
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAllow() {
		return allow;
	}

	public void setAllow(Integer allow) {
		this.allow = allow;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public Integer getAccess() {
		return access;
	}

	public void setAccess(Integer access) {
		this.access = access;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "MqttUser{" + "id=" + id + ", allow=" + allow + ", ipaddr='" + ipaddr + '\'' + ", username='" + username
				+ '\'' + ", clientid='" + clientid + '\'' + ", access=" + access + ", topic='" + topic + '\'' + '}';
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
