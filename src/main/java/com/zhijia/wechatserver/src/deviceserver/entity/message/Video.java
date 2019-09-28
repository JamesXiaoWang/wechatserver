package com.zhijia.wechatserver.src.deviceserver.entity.message;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:46:27
 * @description:
 *
 */
public class Video {
	// 媒体文件ID
	private String MediaId;
	// 视频消息缩略图的媒体ID
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

}
