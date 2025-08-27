package kr.co.iei.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public Member login(Member m) {
		Member member = memberDao.login(m);
		return member;
	}
	public Member selectMemberId(String memberId) {
		 return memberDao.selectMemberId(memberId);
	}
	public Member checkId(String memberId) {
		Member m = new Member();
		m.setMemberId(memberId);
		
		Member member = memberDao.checkId(m);
		return member;
	}
}

