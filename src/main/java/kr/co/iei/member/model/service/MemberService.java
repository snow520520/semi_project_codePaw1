package kr.co.iei.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.animal.model.dao.AnimalDao;
import kr.co.iei.animal.model.vo.AnimalListData;
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
			pageNavi += "<a class='page-item' href='/admin/adminPageFrm?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/admin/adminPageFrm?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/admin/adminPageFrm?reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-item' href='/admin/adminPageFrm?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>";
		
		List list = memberDao.selectMemberList(param);
		
		MemberListData mld = new MemberListData(list, pageNavi);
		return mld;
	}
	@Transactional
	public int changeLevel(Member m) {
		int result = memberDao.changeLevel(m);
		return result;
	}
	@Transactional
	public boolean checkedChangeLevel(String no, String level) {
		StringTokenizer sT1 = new StringTokenizer(no, "/");
		StringTokenizer sT2 = new StringTokenizer(level, "/");
		
		int result = 0;
		int count = sT1.countTokens();
		
		while(sT1.hasMoreTokens()) {
			String stringNo = sT1.nextToken();
			int memberNo = Integer.parseInt(stringNo);
			String stringLevel = sT2.nextToken();
			int memberLevel = Integer.parseInt(stringLevel);
			
			Member m = new Member();
			m.setMemberNo(memberNo);
			m.setMemberLevel(memberLevel);
			result += memberDao.changeLevel(m);
		}
		return result == count;
	}
	
}

