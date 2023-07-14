package com.sbs.java.ssg.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.dto.Article;

public class ArticleDao extends Dao {
	public List<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public void add(Article article) {
		articles.add(article);
		lastId = article.id;
	}

	public List<Article> getArticles() {
		return articles;
	}
	
	public int getArticleIndexById(int id) {
		int i = 0;

		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}

			i++;
		}

		return -1;
	}

	public Article getArticleById(int id) {
		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}

		return null;
	}

	public List<Article> getForPrintArticles(String searchkeyword) {
		if (searchkeyword != null && searchkeyword.length() != 0) {
			List<Article> forPrintArticles = new ArrayList<>();
			
			if (searchkeyword.length() > 0) {
				for (Article article : articles) {
					if (article.title.contains(searchkeyword)) {
						forPrintArticles.add(article);
					}
				}
			}

			return forPrintArticles;
		}

		return articles;
	}

	public void remove(Article foundArticle) {
		articles.remove(foundArticle);
	}

}