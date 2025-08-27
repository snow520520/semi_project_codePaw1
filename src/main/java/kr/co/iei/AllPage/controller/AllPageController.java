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

    @GetMapping(value="/mainAllpage/writeFrm")
    public String writeFrm(HttpSession session, Model model,Animal animal) {
        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
            return "redirect:/";
        }
        return "mainAllpage/writeFrm";
    }

    @PostMapping(value="/mainAllpage/write")
    public String write(AllPage ap,
                        @RequestParam("animalName") String animalName,
                        @RequestParam("animalAge") String animalAgeStr,
                        HttpSession session, Model model) {

        Member member = (Member) session.getAttribute("member");
        if (member == null || member.getMemberLevel() != 1) {
            return "redirect:/";
        }

        Animal animal = new Animal();
        animal.setAnimalName(animalName);

        try {
            animal.setAnimalAge(Integer.parseInt(animalAgeStr));
        } catch (NumberFormatException e) {
            animal.setAnimalAge(0); 
        }

        int result = allpageService.insertProtect(ap, member.getMemberNo(), animal);
        
        return "redirect:/";
    }

    
    @GetMapping("/mainAllpage/allpage")
    public String allpage(@RequestParam(value="page", defaultValue="1") int page,
                          Model model, HttpSession session) {

        int recordCountPerPage = 16; 
        int naviCountPerPage = 5;

        int totalCount = allpageService.getTotalCount();
        List<AllPage> list = allpageService.selectPageList(page, recordCountPerPage);

        model.addAttribute("list", list);

        String pageNavi = allpageService.getPageNavi(page, totalCount, recordCountPerPage, naviCountPerPage, "/mainAllpage/allpage");
        model.addAttribute("pageNavi", pageNavi);

        Member member = (Member) session.getAttribute("member");
        model.addAttribute("member", member);

        return "mainAllpage/allpage";
    }
    
    @GetMapping("/")
    public String index(Model model) {
        
        List<AllPage> list = allpageService.selectPageList(1, 4);
        model.addAttribute("list", list);
        return "index";
    }

    @ResponseBody
    @GetMapping("/mainAllpage/loadMore")
    public List<AllPage> loadMore(@RequestParam("start") int start, @RequestParam("count") int count) {
        
        int end = start + count - 1;
        return allpageService.selectPageListRead(start, end);
    }
    
    @GetMapping("/mainAllpage/detail")
    public String detail(@RequestParam("protectNo") int protectNo, Model model, HttpSession session) {
        AllPage ap = allpageService.selectOneProtect(protectNo); 
        if (ap == null) return "redirect:/mainAllpage/allpage";

        Animal animal = allpageService.selectAnimal(ap.getAnimalNo());
        Member writer = allpageService.selectMember(ap.getMemberNo());
        Member loginMember = (Member) session.getAttribute("member");
        
        model.addAttribute("ap", ap);
        model.addAttribute("animal", animal);
        model.addAttribute("writer", writer); 
        model.addAttribute("loginMember", loginMember);

        return "mainAllpage/detail";
    }
    
    @PostMapping(value="/mainAllpage/editorImage", produces = "plain/text;charset=utf-8")
    @ResponseBody
    public String editorImage(MultipartFile upfile) {
        String savePath = "C:/image/";
        String filename = UUID.randomUUID() + "_" + upfile.getOriginalFilename();
        File file = new File(savePath + filename);

        try {
            upfile.transferTo(file); 
        } catch (IOException e) {
            e.printStackTrace();
            return "fail"; 
        }

        return "/editorImage/" + filename;  
    }
    
}