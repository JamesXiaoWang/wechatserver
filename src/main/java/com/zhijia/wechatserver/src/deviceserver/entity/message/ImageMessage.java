package com.zhijia.wechatserver.src.deviceserver.entity.message;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:20:59
 * @description:
 *
 */
public class ImageMessage extends WechatBaseMessage {
	private static final long serialVersionUID = -6545919728833531063L;
	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
