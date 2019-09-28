package com.zhijia.wechatserver.src.deviceserver.entity.message;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:26:53
 * @description:
 *
 */
public class MusicMessage extends WechatBaseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1189904186781856002L;
	private Music music;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

}
