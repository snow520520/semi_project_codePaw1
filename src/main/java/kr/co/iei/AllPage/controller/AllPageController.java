package kr.co.iei.AllPage.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.co.iei.AllPage.model.service.AllPageService;
import kr.co.iei.AllPage.model.vo.AllPage;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/mainAllpage")
public class AllPageController {

    @Autowired
    private AllPageService allpageService;

    @GetMapping(value="/allpage")
    public String allpage() {
        return "mainAllpage/allpage";
    }

    @GetMapping(value="/writeFrm")
    public String writeFrm(HttpSession session, Model model) {
    	Member member = (Member) session.getAttribute("loginMember");
        if (member == null || member.getMemberLevel() != 1) {
        	//관리자만 문구
        	// 관리자가 아니면 접근 불가 → 메시지 페이지로 이동
            return null;
        }
        return "mainAllpage/writeFrm";
    }

    @PostMapping(value="/write")
    public String write(AllPage ap,Animal animal, HttpSession session, Model model) {
    	Member member = (Member) session.getAttribute("loginMember");
        if (member == null || member.getMemberLevel() != 1) {
        	// 관리자가 아니면 작성 불가 → 메시지 페이지
            return null;
        }

        int result = allpageService.insertProtect(ap, member.getMemberNo(), animal);

        if (result > 0) {
        	// 등록 성공
            return null;
            
        } else {
        	// 등록 실패 (동물 이름/나이 검증 실패)
            return null;
        }
    }
}
