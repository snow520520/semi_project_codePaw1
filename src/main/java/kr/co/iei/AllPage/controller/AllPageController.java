package kr.co.iei.AllPage.controller;

import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.AllPage.model.service.AllPageService;
import kr.co.iei.AllPage.model.vo.AllPage;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.vo.Member;

@Controller
public class AllPageController {

    @Autowired
    private AllPageService allpageService;

    @GetMapping("/mainAllpage/writeFrm")
    public String writeFrm(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
            model.addAttribute("title", "권한 없음");
            model.addAttribute("text", "글 작성은 관리자만 가능합니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/member/loginFrm");
            return "common/msg";
        }
        return "mainAllpage/writeFrm";
    }

    @PostMapping("/mainAllpage/write")
    public String write(AllPage ap,
                        @RequestParam("animalName") String animalName,
                        @RequestParam("animalAge") String animalAgeStr,
                        HttpSession session, Model model) {

        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
            model.addAttribute("title", "권한 없음");
            model.addAttribute("text", "글 작성은 관리자만 가능합니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/member/loginFrm");
            return "common/msg";
        }

        Animal animal = new Animal();
        animal.setAnimalName(animalName);
        try { animal.setAnimalAge(Integer.parseInt(animalAgeStr)); } 
        catch (NumberFormatException e) { animal.setAnimalAge(0); }

        int result = allpageService.insertProtect(ap, member.getMemberNo(), animal);

        if(result == -1) {
            model.addAttribute("title", "등록 실패");
            model.addAttribute("text", "이미 등록된 동물입니다.");
            model.addAttribute("icon", "warning");
            model.addAttribute("loc", "/mainAllpage/writeFrm");
        } else if(result > 0) {
            model.addAttribute("title", "작성 완료");
            model.addAttribute("text", "입양 글이 정상적으로 등록되었습니다.");
            model.addAttribute("icon", "success");
            model.addAttribute("loc", "/mainAllpage/allpage");
        } else {
            model.addAttribute("title", "등록 실패");
            model.addAttribute("text", "오류가 발생했습니다. 다시 시도해주세요.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/writeFrm");
        }
        return "common/msg";
    }

    @GetMapping("/mainAllpage/allpage")
    public String allpage(@RequestParam(value="page", defaultValue="1") int page,
                          Model model, HttpSession session) {

        int recordCountPerPage = 16;
        int naviCountPerPage = 5;
        int totalCount = allpageService.getTotalCount();
        List<AllPage> list = allpageService.selectPageList(page, recordCountPerPage);

        model.addAttribute("list", list);
        model.addAttribute("pageNavi", allpageService.getPageNavi(page, totalCount, recordCountPerPage, naviCountPerPage, "/mainAllpage/allpage"));
        model.addAttribute("member", (Member) session.getAttribute("member"));
        return "mainAllpage/allpage";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("list", allpageService.selectPageList(1, 4));
        return "index";
    }

    @ResponseBody
    @GetMapping("/mainAllpage/loadMore")
    public List<AllPage> loadMore(@RequestParam("start") int start, @RequestParam("count") int count) {
        return allpageService.selectPageListRead(start, start + count - 1);
    }

    @GetMapping("/mainAllpage/detail")
    public String detail(@RequestParam("protectNo") int protectNo, Model model, HttpSession session) {
        AllPage ap = allpageService.selectOneProtect(protectNo);
        if (ap == null) {
            model.addAttribute("title", "게시물 없음");
            model.addAttribute("text", "해당 글이 삭제되었거나 존재하지 않습니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        }

        model.addAttribute("ap", ap);
        model.addAttribute("animal", allpageService.selectAnimal(ap.getAnimalNo()));
        model.addAttribute("writer", allpageService.selectMember(ap.getMemberNo()));
        model.addAttribute("loginMember", (Member) session.getAttribute("member"));
        return "mainAllpage/detail";
    }

    @PostMapping(value="/mainAllpage/editorImage", produces = "plain/text;charset=utf-8")
    @ResponseBody
    public String editorImage(MultipartFile upfile) {
        String savePath = "C:/image/";
        String filename = UUID.randomUUID() + "_" + upfile.getOriginalFilename();
        try { upfile.transferTo(new File(savePath + filename)); } 
        catch (IOException e) { e.printStackTrace(); return "fail"; }
        return "/editorImage/" + filename;
    }

    @GetMapping("/mainAllpage/updateFrm")
    public String updateFrm(@RequestParam("protectNo") int protectNo, Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
            model.addAttribute("title", "권한 없음");
            model.addAttribute("text", "글 수정은 관리자만 가능합니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/member/loginFrm");
            return "common/msg";
        }

        AllPage ap = allpageService.selectOneProtect(protectNo);
        if (ap == null) {
            model.addAttribute("title", "글 없음");
            model.addAttribute("text", "수정할 글을 찾을 수 없습니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        }

        model.addAttribute("ap", ap);
        model.addAttribute("animal", allpageService.selectAnimal(ap.getAnimalNo()));
        return "mainAllpage/updateFrm";
    }

    @PostMapping("/mainAllpage/update")
    public String update(@RequestParam("protectNo") int protectNo,
                         @RequestParam("protectContent") String protectContent,
                         HttpSession session, Model model) {

        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
            model.addAttribute("title", "권한 없음");
            model.addAttribute("text", "글 수정은 관리자만 가능합니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/member/loginFrm");
            return "common/msg";
        }

        AllPage ap = allpageService.selectOneProtect(protectNo);
        if (ap == null) {
            model.addAttribute("title", "수정 실패");
            model.addAttribute("text", "글을 찾을 수 없습니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        }

        ap.setProtectContent(protectContent);
        int result = allpageService.updateProtectContent(ap);
        if(result > 0) {
            model.addAttribute("title", "수정 완료");
            model.addAttribute("text", "글이 정상적으로 수정되었습니다.");
            model.addAttribute("icon", "success");
            model.addAttribute("loc", "/mainAllpage/detail?protectNo=" + protectNo);
        } else {
            model.addAttribute("title", "수정 실패");
            model.addAttribute("text", "오류가 발생했습니다. 다시 시도해주세요.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/updateFrm?protectNo=" + protectNo);
        }
        return "common/msg";
    }
}
