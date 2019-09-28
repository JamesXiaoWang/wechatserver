package com.zhijia.wechatserver.src.deviceserver.entity.device;

import java.io.Serializable;

/**
 * @author Administrators
 * @date 2018年12月27日 下午7:39:10
 * @description: 歌曲资源
 *
 */
public class PlayResource implements Serializable {
	private static final long serialVersionUID = -3508732522251322014L;
	private String tagName;		//标签名
	private String name;
	private String img;
	private String introduce;	//介绍
	private Integer total;
	
	private String host;
	private String resUrl;
	private String imgUrl;
	
	private String isFavorite;	//最喜欢的
	private String favoriteId;	//收藏夹

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

}
