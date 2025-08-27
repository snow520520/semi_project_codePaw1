package kr.co.iei.AllPage.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.AllPage.model.service.AllPageService;
import kr.co.iei.AllPage.model.vo.AllPage;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.vo.Member;

@Controller

public class AllPageController {

    @Autowired
    private AllPageService allpageService;

    // 글 작성 페이지
    @GetMapping(value="/mainAllpage/writeFrm")
    public String writeFrm(HttpSession session, Model model,Animal animal) {
        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
            // 관리자가 아니면 접근 불가 → 전체 페이지로 이동
        	return "redirect:/";
        }
        return "mainAllpage/writeFrm";
    }

    // 글쓰기 처리 (테스트용: 페이지 이동 없이 메시지 표시)
    @PostMapping(value="/mainAllpage/write")
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
    
    @GetMapping("/mainAllpage/allpage")
    public String allpage(@RequestParam(value="page", defaultValue="1") int page,
                          Model model, HttpSession session) {

        int recordCountPerPage = 16; 
        int naviCountPerPage = 5;

        int totalCount = allpageService.getTotalCount();
        List<AllPage> list = allpageService.selectPageList(page, recordCountPerPage);

        String pageNavi = allpageService.getPageNavi(page, totalCount, recordCountPerPage, naviCountPerPage, "/mainAllpage/allpage");

        model.addAttribute("list", list);
        model.addAttribute("pageNavi", pageNavi);

        Member member = (Member) session.getAttribute("member");
        model.addAttribute("member", member);

        return "mainAllpage/allpage";
    }
    
    @GetMapping("/")
    public String index(Model model) {
        
        List<AllPage> list = allpageService.selectPageList(1, 4);
        System.out.println("Controller에서 가져온 list 크기: " + list.size());
        model.addAttribute("list", list);
        return "index";
    }

    @ResponseBody
    @GetMapping("/mainAllpage/loadMore")
    public List<AllPage> loadMore(@RequestParam("start") int start, @RequestParam("count") int count) {
        
        int end = start + count - 1;
        return allpageService.selectPageListRead(start, end);
    }

}
