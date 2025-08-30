package kr.co.iei.member.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.member.model.vo.MemberListData;

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
	@Transactional
	public int join(Member m) {
		int result = memberDao.join(m);
		return result;
	}
	public List allMember() {
		List listMember = memberDao.allMember();
		return listMember;
	}
	public List allAnimal() {
		List listAnimal = memberDao.allAnimal();
		return listAnimal;
	}
	public MemberListData selectMemberList(int reqPage) {
		int numPerPage = 10;
		
		int end = reqPage * numPerPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int totalCount = memberDao.selectMemberCount();
		int totalPage = totalCount / numPerPage;
		if(totalCount % numPerPage != 0) {
			totalPage += 1;
		}
		
		int pageNaviSize = 5;
		
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		String pageNavi = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/admin/adminPageFrm?reqPage="+(pageNo-1)+"&reqPageAni="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/admin/adminPageFrm?reqPage="+pageNo+"&reqPageAni="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/admin/adminPageFrm?reqPage="+pageNo+"&reqPageAni="+pageNo+"'>";
			}
			pageNavi += pageNo;
			pageNavi += "</a>";
			pageNavi += "</li>";
			
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/admin/adminPageFrm?reqPage="+pageNo+"&reqPageAni="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>";
		
		List list = memberDao.selectMemberList(param);
		
		MemberListData mld = new MemberListData(list, pageNavi);
		
		return mld;
	}
}

