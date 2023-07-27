package com.sbs.java.ssg.service;

import java.util.List;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.dto.Article;
import com.sbs.java.ssg.dto.Member;
import com.sbs.java.ssg.util.Util;

public class ExportService {
	ArticleService articleService;
	MemberService memberService;
	
	public ExportService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void makeHtml() {
		List<Article> articles = articleService.getArticles();
		
		for ( Article article : articles ) {
			Member member = memberService.getMember(article.memberId);
			
			String fileName = article.id + ".html";
			String html = "<meta charset=\"UTF-8\">";
			html += "<div>번호 : " + article.id + "</div>";
			html += "<div>날짜 : " + article.regDate + "</div>";
			html += "<div>작성자 : " + member.name + "</div>";
			html += "<div>제목 : " + article.title + "</div>";
			if ( article.id > 1 ) {
				html += "<div><a href=\"" + (article.id - 1) + ".html\">이전글</a></div>";
			}
			html += "<div><a href=\"" + (article.id + 1) + ".html\">다음글</a></div>";
			Util.writeFileContents("exprtHtml/" + fileName, html);
		}
	} 

}