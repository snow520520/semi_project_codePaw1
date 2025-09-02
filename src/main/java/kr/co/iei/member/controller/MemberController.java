package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@Autowired
	private MemberService memberService;

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
	@GetMapping(value = "/findIdFrm")
	public String findIdFrm() {
		return "member/findId";
	}
}
