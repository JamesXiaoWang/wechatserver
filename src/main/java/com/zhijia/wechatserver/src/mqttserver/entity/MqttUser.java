package com.zhijia.wechatserver.src.mqttserver.entity;

import java.io.Serializable;
import java.util.Date;

import com.zhijia.wechatserver.src.common.DateUtil;

public class MqttUser implements Serializable {
	private static final long serialVersionUID = 3483877007424110387L;
	
	private Integer id;
	private String username;
	private String password;
	private String salt;
	private Integer is_superuser;
	private Integer status;
	private Date created;
	private String createDateStr;

	public String getCreateDateStr() {
		createDateStr = DateUtil.dateToStr(getCreated(), 12);
		return createDateStr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getIs_superuser() {
		return is_superuser;
	}

	public void setIs_superuser(Integer is_superuser) {
		this.is_superuser = is_superuser;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MqttUser{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\''
				+ ", salt='" + salt + '\'' + ", is_superuser=" + is_superuser + ", created=" + created + '}';
	}
}
