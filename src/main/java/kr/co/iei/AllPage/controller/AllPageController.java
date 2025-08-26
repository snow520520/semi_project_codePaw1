package kr.co.iei.AllPage.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;

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

    // 전체 게시물 페이지
    @GetMapping(value="/allpage")
    public String allpage(Model model, HttpSession session) {
        List<AllPage> list = allpageService.selectAllProtect();
        model.addAttribute("list", list);

        Member member = (Member) session.getAttribute("member");
        model.addAttribute("member", member);
        return "mainAllpage/allpage";
    }

    // 글 작성 페이지
    @GetMapping(value="/writeFrm")
    public String writeFrm(HttpSession session, Model model,Animal animal) {
        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
            // 관리자가 아니면 접근 불가 → 전체 페이지로 이동
        	return "redirect:/";
        }
        return "mainAllpage/writeFrm";
    }

    // 글쓰기 처리 (테스트용: 페이지 이동 없이 메시지 표시)
    @PostMapping(value="/write")
    public String write(AllPage ap, Animal animal, HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
        	return "redirect:/";
        }
        
        
        int result = allpageService.insertProtect(ap, member.getMemberNo(), animal);

        if (result > 0) {
        	//등록 글 성공시
            System.out.println("등록 성공! protectNo = " + ap.getProtectNo());
        } else {
        	//등록 글 실패시
            System.out.println("등록 실패!");
        }

        // 같은 작성 페이지로 머물면서 메시지 표시
        return "mainAllpage/writeFrm";
    }
}
