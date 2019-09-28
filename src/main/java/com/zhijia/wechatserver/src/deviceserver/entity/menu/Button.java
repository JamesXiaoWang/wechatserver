package com.zhijia.wechatserver.src.deviceserver.entity.menu;

/**
 * @author Administrators
 * @date 2018年9月11日 下午3:54:18
 * @description: 按钮
 *
 */
public class Button {
	private String name;
	private String type;
	private Button[] sub_button;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}

}
