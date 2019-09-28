package com.zhijia.wechatserver.src.deviceserver.entity.message;

import java.util.List;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:17:03
 * @description:
 *
 */
public class ArticleMessage extends WechatBaseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 376589707570582602L;
	private int ArticleCount;
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
