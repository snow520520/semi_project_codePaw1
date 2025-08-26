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
        if (member == null || member.getmemberLevel() != 1) {
            model.addAttribute("msg", "관리자만 작성 가능합니다.");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        }
        return "mainAllpage/writeFrm";
    }

    @PostMapping(value="/write")
    public String write(AllPage ap, HttpSession session, Model model) {
    	Member member = (Member) session.getAttribute("loginMember");
        if (member == null || member.getmemberLevel() != 1) {
            model.addAttribute("msg", "관리자만 작성 가능합니다.");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        }

        int result = allpageService.insertProtect(ap, member.getmemberNo());

        if (result > 0) {
            model.addAttribute("msg", "등록 완료");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        } else {
            model.addAttribute("msg", "등록 실패. 동물 이름/나이를 확인하세요.");
            model.addAttribute("loc", "/mainAllpage/writeFrm");
            return "common/msg";
        }
    }
}
