package com.sbs.java.ssg.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.db.DBConnection;
import com.sbs.java.ssg.dto.Article;
import com.sbs.java.ssg.dto.Board;

public class ArticleDao extends Dao {
	public List<Article> articles;
	private DBConnection dbConnection;

	public ArticleDao() {
		articles = new ArrayList<>();
		dbConnection = Container.getDBConnection();
	}

	public int write(Article article) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("INSERT INTO article "));
		sb.append(String.format("SET regDate = NOW(), "));
		sb.append(String.format("updateDate = NOW(), "));
		sb.append(String.format("title = '%s', ", article.title));
		sb.append(String.format("body = '%s', ", article.body));
		sb.append(String.format("memberId = '%d', ", article.memberId));
		sb.append(String.format("boardId = '%d' ", article.boardId));


		return dbConnection.insert(sb.toString());
	}

	public List<Article> getArticles() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * FROM article"));

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

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

	public Board getBoard(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `board` "));
		sb.append(String.format("WHERE id = '%d' ", id));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());

		if (row.isEmpty()) {
			return null;
		}

		return new Board(row);
	}

	public Article getForPrintArticle(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT A.*, M.name AS extra__writerName "));
		sb.append(String.format("FROM article AS A "));
		sb.append(String.format("INNER JOIN `member` as M "));
		sb.append(String.format("ON A.memberId = M.id "));
		sb.append(String.format("WHERE A.id = '%d' ", id));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());

		if (row.isEmpty()) {
			return null;
		}

		return new Article(row);
	}

}