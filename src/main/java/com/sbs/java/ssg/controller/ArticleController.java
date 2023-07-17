package com.sbs.java.ssg.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.dto.Article;
import com.sbs.java.ssg.dto.Board;
import com.sbs.java.ssg.dto.Member;
import com.sbs.java.ssg.service.ArticleService;
import com.sbs.java.ssg.service.MemberService;

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
		case "changeBoard":
			doChangeBoard();
			break;
		case "currentBoard":
			doCurrentBoard();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			break;
		}
	}

	private void doCurrentBoard() {
		Board board = session.getCurrentBoard();
		System.out.printf("현재 게시판 : %s게시판\n", board.getName());

	}

	private void doChangeBoard() {
		String[] commandBits = command.split(" ");
		int boardCode = Integer.parseInt(commandBits[2]);

		Board board = articleService.getBoard(boardCode);

		if (board == null) {
			System.out.println("해당 게시판이 존재하지 않습니다.");
		} else {
			System.out.printf("[%s게시판] 으로 게시판이 변경되었습니다.\n", board.getName());
			session.setCurrentBoard(board);
		}

	}

	public void doWrite() {
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int memberId = session.getLoginedMember().getId();
		int boardId = session.getCurrentBoard().getId();

		int newId = articleService.write(memberId, boardId, title, body);

		System.out.printf("%d번 글이 생성되었습니다.\n", newId);
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

		Article foundArticle = articleService.getForPrintArticle(id);

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

		Article foundArticle = articleService.getArticle(id);

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

		articleService.modify(foundArticle.id, title, body);

		System.out.printf("%d번 게시물이 수정되었습니다.\n", foundArticle.id);
	}

	public void doDelete() {
		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticle(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		Member loginedMember = session.getLoginedMember();

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}

		articleService.delete(foundArticle.id);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", foundArticle.id);
	}

}