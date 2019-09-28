package com.zhijia.wechatserver.src.deviceserver.entity.menu;
/**
 * @author Administrators
 * @date 2018年9月11日 下午3:55:00
 * @description: 点击按钮
 *
 */
public class ClickButton extends Button {
	private String key;// 菜单KEY值

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
