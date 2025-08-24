package kr.co.iei.member.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias (value = "member")
public class Member {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberAge;
	private String memberAddr;
	private String memberPhone;	
	private int memberLevel; 		// 관리자 = 1, 입양한 회원 = 2, 정회원 = 3
	private String enrollDate;
}
