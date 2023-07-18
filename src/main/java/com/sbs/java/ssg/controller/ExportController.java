package com.sbs.java.ssg.controller;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.service.ExportService;

public class ExportController extends Controller {
	private ExportService exportService;
	
	public ExportController() {
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