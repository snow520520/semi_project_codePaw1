package kr.co.iei.member.controller;
import kr.co.iei.notice.controller.NoticeController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    private final NoticeController noticeController;
	@Autowired
	private MemberService memberService;

    MemberController(NoticeController noticeController) {
        this.noticeController = noticeController;
    }
    @RequestMapping(value = "/loginMsg")
    public String loginMsg(Model model) {
    	model.addAttribute("title", "로그인");
		model.addAttribute("text", "로그인 후 사용합니다.");
		model.addAttribute("icon", "info");
		model.addAttribute("loc", "/member/loginFrm");
		return "common/msg"; 
    }
    @RequestMapping(value = "/adminMsg")
	public String adminMsg(Model model) {
		model.addAttribute("title", "관리자 페이지");
		model.addAttribute("text", "관리자만 접근 가능");
		model.addAttribute("icon", "warning");
		model.addAttribute("loc", "/");
		return "common/msg"; 
	}
    @RequestMapping(value = "/reviewMsg")
	public String reviewMsg(Model model) {
		model.addAttribute("title", "관리자 / 입양 회원 페이지");
		model.addAttribute("text", "관리자와 입양 회원만 접근 가능");
		model.addAttribute("icon", "warning");
		model.addAttribute("loc", "/");
		return "common/msg"; 
	}
	@GetMapping(value = "/loginFrm")
	public String loginFrm(HttpServletRequest request, Model model) {
	    Cookie[] cookies = request.getCookies();
	    String savedId = "";
	    if(cookies != null) {
	        for(Cookie c : cookies) {
	            if(c.getName().equals("memberId")) {
	                savedId = c.getValue();
	            }
	        }
	    }
	    model.addAttribute("savedId", savedId);
	    return "member/login";
	}
	@PostMapping(value = "/login")
	public String login(Member m, boolean saveId ,Model model, HttpSession session, HttpServletResponse response) {
		Member member = memberService.login(m);
		
		if (member == null) {
			 model.addAttribute("title", "로그인 실패"); 
			 model.addAttribute("text", "아이디와 비밀번호를 확인해주세요.");
			 model.addAttribute("icon", "error");
			 model.addAttribute("loc", "/member/loginFrm");
			return "common/msg";
		} else {
			
			// 레벨별 세션 처리
            if (member.getMemberLevel() == 3) {
                // 정회원 (입양하지 않은 회원)
                session.setAttribute("member", member);
            } else if (member.getMemberLevel() == 2) {
                // 입양한 회원
                session.setAttribute("member", member);
            } else if (member.getMemberLevel() == 1) {
                // 관리자
                session.setAttribute("member", member);
            }
            
			session.setAttribute("member", member);
			if (saveId) {
	            Cookie cookie = new Cookie("memberId", member.getMemberId());
	            cookie.setPath("/");
	            response.addCookie(cookie);
	        } else {
	            Cookie cookie = new Cookie("memberId", null);
	            cookie.setMaxAge(0);
	            cookie.setPath("/");
	            response.addCookie(cookie);
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
	@GetMapping(value = "/myPage")
	public String myPageFrm() {
		return "member/myPage";
	}
	@PostMapping(value="/updateInfo")
	public String updateInfo(Member m, HttpSession session, Model model) {
		System.out.println(m);
		int result = memberService.updateInfo(m);
		if(m.getMemberId() == null) {
			model.addAttribute("title", "정보 입력");
			model.addAttribute("text", "정보를 입력해주세요.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/member/myPage");
			return "common/msg";
		}else if(m.getMemberPw() == null) {
			model.addAttribute("title", "비밀번호 입력");
			model.addAttribute("text", "정보를 입력해주세요.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "redirect:/member/myPage");
			return "common/msg";
		}else {
			if(result > 0) {
				Member member = (Member)session.getAttribute("member");
				member.setMemberPw(m.getMemberPw());
				member.setMemberName(m.getMemberName());
				member.setMemberAddr(m.getMemberAddr());
				member.setMemberPhone(m.getMemberPhone());
				
				/*
				model.addAttribute("title", "수정 완료");
				model.addAttribute("text","완 료");
				model.addAttribute("icon", "success");
				model.addAttribute("loc", "/member/myPage");
				return "common/msg";
				*/
			}else {
				model.addAttribute("title", "Fail");
				model.addAttribute("text"," fail");
				model.addAttribute("icon", "error");
				model.addAttribute("loc", "/member/myPage");
				return "common/msg";
			}
			return "redirect:/member/myPage";
					
		}
	}
	@GetMapping(value = "/deleteInfo")
	public String deleteInfo(Member m, Model model, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		if(member.getMemberNo() != 0) {
			int memberNo = member.getMemberNo();
			int result = memberService.deleteInfo(memberNo);
			if(result > 0) {
				model.addAttribute("title", "삭제 완료");
				model.addAttribute("text", "계정삭제가 완료되었습니다.");
				model.addAttribute("icon", "success");
				model.addAttribute("loc", "/member/logout");
			}
			return "common/msg";
		}else {
			return "redirect:/";
		}
	}
	@ResponseBody
	@GetMapping(value = "/searchId")  //input name값 다르게
	public Member searchId(String memberName, String memberPhone) {
		//query에 memberName 전달 시 int 타입으로 리턴한 후 memberName과 memberPhone이 둘다 일치하는지 
		Member m = memberService.searchId(memberName, memberPhone);
		
		return m;
	}
	@ResponseBody
	@GetMapping(value = "/passwordRe")
	public Member passwordRe(String memberId, String memberPhone) {
		Member m = memberService.passwordRe(memberId, memberPhone);
		return m;
	}
	@ResponseBody
	@PostMapping(value = "/RePassword")
	public boolean RePassoword(String memberPw) {
		boolean result = memberService.RePassword(memberPw);
		return result;
	}
}
