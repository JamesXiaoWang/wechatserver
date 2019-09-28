package com.zhijia.wechatserver.src.deviceserver.entity.play;

import java.io.Serializable;

/**
 * @author Administrators
 * @date 2018年12月27日 下午7:38:59
 * @description:
 *
 */
public class PlayResourceTag implements Serializable {
	private static final long serialVersionUID = -5648928401329333944L;
	private Integer id;
	private String tagName;
	private String tagIco;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagIco() {
		return tagIco;
	}

	public void setTagIco(String tagIco) {
		this.tagIco = tagIco;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
