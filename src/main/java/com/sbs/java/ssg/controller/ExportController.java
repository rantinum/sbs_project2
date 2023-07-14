package com.sbs.java.ssg.controller;

import java.util.Scanner;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.dto.Member;
import com.sbs.java.ssg.service.ExportService;
import com.sbs.java.ssg.service.MemberService;
import com.sbs.java.ssg.util.Util;

public class ExportController extends Controller {
	private Scanner sc;
	private ExportService exportService;
	
	public ExportController(Scanner sc) {
		this.sc = sc;
		exportService = Container.exportService;
	}

	@Override
	public void doAction(String command, String actionMethodName) {
		switch ( actionMethodName ) {
		case "html":
			doHtml();
			break;
		default:
			System.out.println("존재하지 않은 명령어입니다.");
			break;
		}
	}

	private void doHtml() {
		System.out.println("html 생성을 시작합니다.");
		exportService.makeHtml();
	}
}