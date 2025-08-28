package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping(value = "/loginFrm")
	public String loginFrm() {
		return "member/login";
	}

	@PostMapping(value = "/login")
	public String login(Member m, Model model, HttpSession session) {
		// select * from member_tbl where member_id = memberId and member_pw = memberPw
		Member member = memberService.login(m);
		
		if (member == null) {
			// member == null 이면 로그인이 안된경우 (id or pw가 틀렸다는 문구 다시 확인하세요)
			 model.addAttribute("title", "로그인 실패"); 
			 model.addAttribute("text", "아이디와 비밀번호를 확인해주세요.");
			 model.addAttribute("icon", "error");
			 model.addAttribute("loc", "/member/loginFrm");
			return "common/msg";
			// alert css/js 넣고 처리해야함
		} else {

			// member != null 로그인 된 경우
			if (member.getMemberLevel() == 3) {
				// member != null or memberLevel == 3이면 (정회원(입양하지 않은 회원) 마이페이지에 회원정보 수정할수 있게끔만)
				session.setAttribute("member", member);

			} else if (member.getMemberLevel() == 2) {
				// member != null or memberLevel == 2이면 (입양한 회원 (마이페이지에 입양 후기쓴 글 or 입양신청서 작성 시
				// 수락 / 회원정보 수정할 수 있게))
				session.setAttribute("member", member);

			} else if (member.getMemberLevel() == 1) {
				// member != null or memberLevel == 1이면 (관리자 권한 (마이페이지 or 관리자 페이지))
				session.setAttribute("member", member);
			}
		}
		return "redirect:/";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping(value = "/joinFrm")
	public String joinFrm() {
		return "member/join";
	}

	@ResponseBody
	@GetMapping(value = "/checkId")
	public int checkId(String memberId) {
		Member member = memberService.checkId(memberId);
		if(member == null) {
			return 0;
		}else {
			return 1;
		}
	}
	@PostMapping(value ="/join")
	public String join(Member m, Model model) {
		
		int result = memberService.join(m);
		
		if(result == 0) {
			 model.addAttribute("title", "회원가입 실패"); 
			 model.addAttribute("text", "입력한 정보를 확인해주세요.");
			 model.addAttribute("icon", "error");
			 model.addAttribute("loc", "/member/join");
		}else {
			model.addAttribute("title", "회원가입 성공");
			model.addAttribute("text", "회원가입이 완료되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/member/loginFrm");
		}
		return "common/msg"; 
	}
	@GetMapping(value = "/findIdFrm")
	public String findIdFrm() {
		return "member/findId";
	}
}
