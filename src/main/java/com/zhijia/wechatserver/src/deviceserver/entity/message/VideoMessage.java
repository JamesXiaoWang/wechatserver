package com.zhijia.wechatserver.src.deviceserver.entity.message;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:27:56
 * @description:
 *
 */
public class VideoMessage extends WechatBaseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4224772192522783594L;
	private Video video;
	
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}

}
