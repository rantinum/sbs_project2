package com.sbs.java.ssg.container;

import java.util.Scanner;

import com.sbs.java.ssg.controller.Session;
import com.sbs.java.ssg.dao.ArticleDao;
import com.sbs.java.ssg.dao.MemberDao;
import com.sbs.java.ssg.db.DBConnection;
import com.sbs.java.ssg.service.ArticleService;
import com.sbs.java.ssg.service.ExportService;
import com.sbs.java.ssg.service.MemberService;

public class Container {
	public static Scanner sc;
	public static Session session;
	public static DBConnection dbConnection;
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	public static ArticleService articleService;
	public static MemberService memberService;
	public static ExportService exportService;
	
	static {
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		articleService = new ArticleService();
		memberService = new MemberService();
		exportService = new ExportService();
	}
	
	public static Scanner getScanner() {
		if ( sc == null ) {
			sc = new Scanner(System.in);
		}
		
		return sc;
	}
	
	public static Session getSession() {
		if ( session == null ) {
			session = new Session();
		}
		
		return session;
	}
	
	public static DBConnection getDBConnection() {
		if ( dbConnection == null ) {
			dbConnection = new DBConnection();
		}
		
		return dbConnection;
	}

}