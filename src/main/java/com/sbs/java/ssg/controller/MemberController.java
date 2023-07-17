package com.sbs.java.ssg.controller;

import java.util.Scanner;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.dto.Member;
import com.sbs.java.ssg.service.MemberService;
import com.sbs.java.ssg.util.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private MemberService memberService;
	private Session session;
	
	public MemberController(Scanner sc) {
		this.sc = sc;
		memberService = Container.memberService;
		session = Container.getSession();
	}
	
	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		
		switch ( actionMethodName ) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			break;
		}
	}

	private boolean isJoinableLoginId(String loginId) {
		int index = memberService.getMemberIndexByLoginId(loginId);
		
		if ( index == -1 ) {
			return true;
		}
		
		return false;
	}

	public void doJoin() {
		int id = Container.memberDao.getNewId();
		String regDate = Util.getNowDateStr();
		
		String loginId = null;
		
		while ( true ) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine();
			
			if ( isJoinableLoginId(loginId) == false ) {
				System.out.printf("%s(은)는 이미 사용중인 아이디입니다.\n", loginId);
				continue;
			}
			
			break;
		}
		
		String loginPw = null;
		String loginPwConfirm = null;
		
		while ( true ) {
			System.out.printf("로그인 비번 : ");
			loginPw = sc.nextLine();
			System.out.printf("로그인 비번확인 : ");
			loginPwConfirm = sc.nextLine();
			
			if ( loginPw.equals(loginPwConfirm) == false ) {
				System.out.println("비밀번호를 다시 입력해주세요.");
				continue;
			}
			
			break;
		}
		
		System.out.printf("이름 : ");
		String name = sc.nextLine();

		Member member = new Member(id, regDate, loginId, loginPw, name);
		memberService.join(member);

		System.out.printf("%d번 회원이 생성되었습니다. 환영합니다^^\n", id);
	}
	
	private void doLogin() {		
		System.out.printf("로그인 아이디 : ");
		String loginId = sc.nextLine();
		System.out.printf("로그인 비번 : ");
		String loginPw = sc.nextLine();
		
		// 입력받은 아이디에 해당하는 회원이 존재하는지
		Member member = memberService.getMemberByLoginId(loginId);
		
		if ( member == null ) {
			System.out.println("해당 회원은 존재하지 않습니다.");
			return;
		}
		
		if ( member.loginPw.equals(loginPw) == false ) {
			System.out.println("비밀번호가 맞지 않습니다.");
			return;
		}
		
		session.setLoginedMember(member);
		Member loginedMember = session.getLoginedMember();
		
		System.out.printf("로그인 성공! %s님 환영합니다!\n", loginedMember.name);
	}
	private void doLogout() {
		session.setLoginedMember(null);
		
		System.out.println("로그아웃 되었습니다.");
	}

}