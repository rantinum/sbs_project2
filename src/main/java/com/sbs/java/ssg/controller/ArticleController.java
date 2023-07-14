package com.sbs.java.ssg.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.dto.Article;
import com.sbs.java.ssg.dto.Member;
import com.sbs.java.ssg.service.ArticleService;
import com.sbs.java.ssg.service.MemberService;
import com.sbs.java.ssg.util.Util;

public class ArticleController extends Controller {
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ArticleService articleService;
	private MemberService memberService;
	private Session session;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		articleService = Container.articleService;
		memberService = Container.memberService;
		session = Container.getSession();
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "write":
			doWrite();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			break;
		}
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");

		articleService.write(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 1, 2, "제목1", "내용1", 10));
		articleService.write(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, 1, "제목2", "내용2", 22));
		articleService.write(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, 1, "제목3", "내용3", 33));
	}

	public void doWrite() {
		int id = Container.articleDao.getNewId();
		String regDate = Util.getNowDateStr();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		
		Member loginedMember = session.getLoginedMember();
		int boardId = 1;

		Article article = new Article(id, regDate, loginedMember.id, boardId, title, body);
		articleService.write(article);

		System.out.printf("%d번 글이 생성되었습니다.\n", id);
	}

	public void showList() {
		List<Article> forPrintArticles = articleService.getArticles();
		
//		String searchKeyword = command.substring("article list".length()).trim();
//		
//		List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);
//		
//		if (forPrintArticles.size() == 0) {
//			System.out.println("검색결과가 존재하지 않습니다.");
//			return;
//		}

		System.out.println("번호 |   작성자  | 조회 | 제목");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			String writerName = memberService.getMemberByNameId(article.memberId);

			System.out.printf("%4d | %6s | %4d | %s\n", article.id, writerName, article.hit, article.title);
		}
	}

	public void showDetail() {
		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		foundArticle.increseHit();

		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("날짜 : %s\n", foundArticle.regDate);
		System.out.printf("작성자 : %s\n", foundArticle.memberId);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
		System.out.printf("조회 : %s\n", foundArticle.hit);
	}

	public void doModify() {
		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		
		Member loginedMember = session.getLoginedMember();

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;

		System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
	}

	public void doDelete() {
		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		
		Member loginedMember = session.getLoginedMember();

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}

		articleService.remove(foundArticle);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
	}

}