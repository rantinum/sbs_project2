package com.sbs.java.ssg.controller;

import com.sbs.java.ssg.dto.Board;
import com.sbs.java.ssg.dto.Member;

// Session
// 현재 사용자가 이용중인 정보.
// 이 안의 정보는 사용자가 프로그램을 사용 할 때 동안은 계속 유지됨.
public class Session {
	private Member loginedMember;
	private Board currentBoard;

	public Member getLoginedMember() {
		return loginedMember;
	}

	public void setLoginedMember(Member loginedMember) {
		this.loginedMember = loginedMember;
	}

	public void setCurrentBoard(Board currentBoard) {
		this.currentBoard = currentBoard;
	}

	public Board getCurrentBoard() {
		return currentBoard;
	}

	public boolean isLogined() {
		return loginedMember != null;
	}
}