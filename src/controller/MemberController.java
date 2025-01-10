package controller;

import java.util.List;

import model.Member;
import model.MemberDAO;

public class MemberController {

	private MemberDAO memberDAO;
	
	public MemberController(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	public List<Member> getAllMembers() {
		return memberDAO.getAllMembers();
	}
	
}
