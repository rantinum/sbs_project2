package com.sbs.java.ssg.service;

import java.util.List;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.dao.ArticleDao;
import com.sbs.java.ssg.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;
	
	public ArticleService() {
		articleDao = Container.articleDao;
	}
	
	public List<Article> getForPrintArticles(String searchkeyword) {
		return articleDao.getForPrintArticles(searchkeyword);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void remove(Article foundArticle) {
		articleDao.remove(foundArticle);
	}

	public void write(Article article) {
		articleDao.add(article);
	}

	public List<Article> getForPrintArticles() {
		return articleDao.getForPrintArticles(null);
	}

}