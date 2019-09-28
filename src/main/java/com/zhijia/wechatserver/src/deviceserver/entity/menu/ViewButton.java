package com.zhijia.wechatserver.src.deviceserver.entity.menu;
/**
 * @author Administrators
 * @date 2018年9月11日 下午3:55:31
 * @description: 视图按钮
 *
 */
public class ViewButton extends Button {
	private String url;// 网页链接

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
