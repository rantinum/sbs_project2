package com.sbs.java.ssg.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Reply extends Dto {
	public String body;
	public int memberId;
	public int articleId;
	
	public Reply(String body, int memberId, int articleId) {
		this.body = body;
		this.memberId = memberId;
		this.articleId = articleId;
	}
	
	public Reply(Map<String, Object> row) {
		super(row);
		this.body = (String) row.get("body");
		this.memberId = (int) row.get("memberId");
		this.articleId = (int) row.get("articleId");
	}
}