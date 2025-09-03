package kr.co.iei.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

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
	public MemberListData selectMemberList(int memberPage) {
		int numPerPage = 10;
		
		int end = memberPage * numPerPage;
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
		
		int pageNo = ((memberPage-1)/pageNaviSize)*pageNaviSize+1;
		
		String pageNaviMember = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNaviMember += "<li>";
			pageNaviMember += "<a class='page-item' href='/admin/adminPage?memberPage="+(pageNo-1)+"'>";
			pageNaviMember += "<span class='material-icons'>chevron_left</span>";
			pageNaviMember += "</a>";
			pageNaviMember += "</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNaviMember += "<li>";
			if(pageNo == memberPage) {
				pageNaviMember += "<a class='page-item active-page' href='/admin/adminPage?memberPage="+pageNo+"'>";
			}else {
				pageNaviMember += "<a class='page-item' href='/admin/adminPage?memberPage="+pageNo+"'>";
			}
			pageNaviMember += pageNo;
			pageNaviMember += "</a>";
			pageNaviMember += "</li>";
			
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNaviMember += "<li>";
			pageNaviMember += "<a class='page-item' href='/admin/adminPage?memberPage="+pageNo+"'>";
			pageNaviMember += "<span class='material-icons'>chevron_right</span>";
			pageNaviMember += "</a>";
			pageNaviMember += "</li>";
		}
		pageNaviMember += "</ul>";
		
		List list = memberDao.selectMemberList(param);
		
		MemberListData mld = new MemberListData(list, pageNaviMember);
		return mld;
	}
	
	public MemberListData selectMemberNameList(int memberPage, String memberName) {
		
		int numPerPage = 10;
		
		int end = memberPage * numPerPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int totalCount = memberDao.selectMemberNameCount(memberName);
		
		int totalPage = totalCount / numPerPage;
		if(totalCount % numPerPage != 0) {
			totalPage += 1;
		
			int pageNaviSize = 5;
			
			int pageNo = ((memberPage-1)/pageNaviSize)*pageNaviSize+1;
			
			String pageNaviMember = "<ul class='pagination circle-style'>";
			if(pageNo != 1) {
				pageNaviMember += "<li>";
				pageNaviMember += "<a class='page-item' href='/admin/adminPage?memberName="+memberName+"&memberPage="+(pageNo-1)+"'>";
				pageNaviMember += "<span class='material-icons'>chevron_left</span>";
				pageNaviMember += "</a>";
				pageNaviMember += "</li>";
			}
			for(int i=0;i<pageNaviSize;i++) {
				pageNaviMember += "<li>";
				if(pageNo == memberPage) {
					pageNaviMember += "<a class='page-item active-page' href='/admin/adminPage?memberName="+memberName+"&memberPage="+pageNo+"'>";
				}else {
					pageNaviMember += "<a class='page-item' href='/admin/adminPage?memberName="+memberName+"&memberPage="+pageNo+"'>";
				}
				pageNaviMember += pageNo;
				pageNaviMember += "</a>";
				pageNaviMember += "</li>";
				
				pageNo++;
				if(pageNo > totalPage) {
					break;
				}
			}
			if(pageNo <= totalPage) {
				pageNaviMember += "<li>";
				pageNaviMember += "<a class='page-item' href='/admin/adminPage?memberName="+memberName+"&memberPage="+pageNo+"'>";				pageNaviMember += "<span class='material-icons'>chevron_right</span>";
				pageNaviMember += "</a>";
				pageNaviMember += "</li>";
			}
			pageNaviMember += "</ul>";
			param.put("memberName", memberName);
			List list = memberDao.selectMemberNameList(param);
			
			MemberListData mld = new MemberListData(list, pageNaviMember);
			return mld;
		}else {
			MemberListData mld = null;
			
			return mld;
		}
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
	@Transactional
	public int updateInfo(Member m) {
		int result = memberDao.updateInfo(m);
		
		return result;
	}
	public Member selectMemberNo(int memberNo) {
		return memberDao.selectMemberNo(memberNo);
	}
}

