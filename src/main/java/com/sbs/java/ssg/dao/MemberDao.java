package com.sbs.java.ssg.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.ssg.container.Container;
import com.sbs.java.ssg.db.DBConnection;
import com.sbs.java.ssg.dto.Member;

public class MemberDao extends Dao {
	public List<Member> members;
	private DBConnection dbConnection;

	public MemberDao() {
		members = new ArrayList<>();
		dbConnection = Container.getDBConnection();
	}

	public void add(Member member) {
		members.add(member);
		lastId = member.id;
	}

	public int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}

			i++;
		}
		return -1;
	}

	public Member getMemberByLoginId(String loginId) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `member` "));
		sb.append(String.format("WHERE loginId = '%s' ", loginId));

		Map<String, Object> memberRow = dbConnection.selectRow(sb.toString());

		if ( memberRow.isEmpty() ) {
			return null;
		}

		return new Member(memberRow);
	}

	public String getMemberByNameId(int id) {
		for (Member member : members) {
			if (member.id == id) {
				return member.name;
			}
		}
		return "";
	}
}