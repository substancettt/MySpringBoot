package com.wellshang.data.entity;

import java.util.List;

public class MsgResponseNews extends MsgResponse {

	private static final long serialVersionUID = -472806392674241312L;

	private int ArticleCount;
	private List<Article> Articles;

	public int getArticleCount() {
		ArticleCount = Articles.size();
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
