package kr.co.iei.member.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.Member;

@Mapper
public interface MemberDao {

	Member login(Member m);

	Member selectMemberId(String memberId);

	Member checkId(Member m);

	int join(Member m);

	int selectMemberCount();

	List selectMemberList(HashMap<String, Object> param);

	int changeLevel(Member m);

	List selectMemberNameList(HashMap<String, Object> param);
	
	int selectMemberNameCount(String memberName);

	int updateInfo(Member m);


}
