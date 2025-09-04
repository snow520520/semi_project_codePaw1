package kr.co.iei.protect.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.protect.model.service.ProtectService;
import kr.co.iei.protect.model.vo.Protect;

@Controller
public class ProtectController {
    @Autowired
    private ProtectService protectService;

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
    public String write(Protect ap,
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

        if (ap.getProtectTitle() == null || ap.getProtectTitle().trim().isEmpty()) {
            model.addAttribute("title", "등록 실패");
            model.addAttribute("text", "제목을 입력해주세요.");
            model.addAttribute("icon", "warning");
            model.addAttribute("loc", "/mainAllpage/writeFrm");
            return "common/msg";
        }


        if (ap.getProtectContent() == null || ap.getProtectContent().trim().isEmpty()) {
            model.addAttribute("title", "등록 실패");
            model.addAttribute("text", "내용을 입력해주세요.");
            model.addAttribute("icon", "warning");
            model.addAttribute("loc", "/mainAllpage/writeFrm");
            return "common/msg";
        }

        Animal animal = new Animal();
        animal.setAnimalName(animalName);
        try {
            animal.setAnimalAge(Integer.parseInt(animalAgeStr));
        } catch (NumberFormatException e) {
            animal.setAnimalAge(0);
        }

        if (animal.getAnimalName() == null || animal.getAnimalName().isEmpty() || animal.getAnimalAge() <= 0) {
            model.addAttribute("title", "등록 실패");
            model.addAttribute("text", "없는 동물입니다. 이름과 나이를 정확히 입력해주세요.");
            model.addAttribute("icon", "warning");
            model.addAttribute("loc", "/mainAllpage/writeFrm");
            return "common/msg";
        }

        int result = protectService.insertProtect(ap, member.getMemberNo(), animal);

        if (result == -2) {
            model.addAttribute("title", "등록 실패");
            model.addAttribute("text", "존재하지 않는 동물입니다.");
            model.addAttribute("icon", "warning");
            model.addAttribute("loc", "/mainAllpage/writeFrm");
        } else if (result == -1) {
            model.addAttribute("title", "등록 실패");
            model.addAttribute("text", "이미 등록된 동물입니다.");
            model.addAttribute("icon", "warning");
            model.addAttribute("loc", "/mainAllpage/writeFrm");
        } else if (result > 0) {
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
    public String allpage(@RequestParam(value = "page", defaultValue = "1") int page,
                          Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        Integer memberNo = (member != null) ? member.getMemberNo() : null;

        int recordCountPerPage = 16;
        int naviCountPerPage = 5;
        int totalCount = protectService.getTotalCount();

        List<Protect> list = protectService.selectPageList(page, recordCountPerPage, memberNo);

        model.addAttribute("list", list);
        model.addAttribute("pageNavi", protectService.getPageNavi(page, totalCount, recordCountPerPage, naviCountPerPage, "/mainAllpage/allpage"));
        model.addAttribute("member", member);
        return "mainAllpage/allpage";
    }

    @GetMapping({"/", "/index"})
    public String index(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        Integer memberNo = (member != null) ? member.getMemberNo() : null;

        List<Protect> list = protectService.selectPageList(1, 8, memberNo);
        model.addAttribute("list", list);
        model.addAttribute("member", member);
        return "index";
    }
    
    @ResponseBody
    @GetMapping("/mainAllpage/loadMore")
    public List<Protect> loadMore(@RequestParam("start") int start,
                                  @RequestParam("count") int count,
                                  HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        Integer memberNo = (member != null) ? member.getMemberNo() : null;

        int end = start + count - 1;
        return protectService.selectPageList(start, count, memberNo);
    }

    @PostMapping("/mainAllpage/toggleLike")
    @ResponseBody
    public Map<String, Object> toggleLike(@RequestParam("protectNo") int protectNo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            result.put("loginRequired", true);
            return result;
        }

        boolean liked = protectService.isLiked(member.getMemberNo(), protectNo);
        if (liked) {
            protectService.deleteProtectLike(member.getMemberNo(), protectNo);
        } else {
            protectService.insertProtectLike(member.getMemberNo(), protectNo);
        }

        result.put("loginRequired", false);
        result.put("likedByUser", !liked);
        result.put("likeCount", protectService.getLikeCount(protectNo));
        return result;
    }

    @ResponseBody
    @GetMapping("/mainAllpage/checkLike")
    public boolean checkLike(@RequestParam("protectNo") int protectNo, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) return false;
        return protectService.isLiked(member.getMemberNo(), protectNo);
    }
    
    @GetMapping("/mainAllpage/detail")
    public String detail(@RequestParam("protectNo") int protectNo, Model model, HttpSession session) {
        Protect ap = protectService.selectOneProtect(protectNo);
        if (ap == null) {
            model.addAttribute("title", "게시물 없음");
            model.addAttribute("text", "해당 글이 삭제되었거나 존재하지 않습니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        }
        model.addAttribute("ap", ap);
        model.addAttribute("animal", protectService.selectAnimal(ap.getAnimalNo()));
        model.addAttribute("writer", protectService.selectMember(ap.getMemberNo()));
        model.addAttribute("loginMember", (Member) session.getAttribute("member"));
        return "mainAllpage/detail";
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
        Protect ap = protectService.selectOneProtect(protectNo);
        if (ap == null) {
            model.addAttribute("title", "글 없음");
            model.addAttribute("text", "수정할 글을 찾을 수 없습니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        }
        model.addAttribute("ap", ap);
        model.addAttribute("animal", protectService.selectAnimal(ap.getAnimalNo()));
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

        Protect ap = protectService.selectOneProtect(protectNo);
        if (ap == null) {
            model.addAttribute("title", "수정 실패");
            model.addAttribute("text", "글을 찾을 수 없습니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/allpage");
            return "common/msg";
        }

        ap.setProtectContent(protectContent);
        int result = protectService.updateProtectContent(ap);
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
    
    @GetMapping("/mainAllpage/delete")
    public String deleteProtect(@RequestParam("protectNo") int protectNo, Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if(member == null || member.getMemberLevel() != 1) {
            model.addAttribute("title", "권한 없음");
            model.addAttribute("text", "삭제는 관리자만 가능합니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/member/loginFrm");
            return "common/msg";
        }

        int result = protectService.deleteProtect(protectNo); 
        if(result > 0) {
            model.addAttribute("title", "삭제 완료");
            model.addAttribute("text", "입양 글이 정상적으로 삭제되었습니다.");
            model.addAttribute("icon", "success");
            model.addAttribute("loc", "/mainAllpage/allpage");
        } else {
            model.addAttribute("title", "삭제 실패");
            model.addAttribute("text", "오류가 발생했습니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/mainAllpage/detail?protectNo=" + protectNo);
        }
        return "common/msg";
    }

    

    
    @PostMapping(value="/mainAllpage/editorImage", produces = "plain/text;charset=utf-8")
    @ResponseBody
    public String editorImage(MultipartFile upfile) {
        String savePath = "C:/Temp/upload/image/";
        String filename = UUID.randomUUID() + "_" + upfile.getOriginalFilename();
        try { upfile.transferTo(new File(savePath + filename)); } 
        catch (IOException e) { e.printStackTrace(); return "fail"; }
        return "/editorImage/" + filename;
    }
    
    
    @GetMapping(value="/mainAllpage/Directions")
    public String Directions() {

        return "mainAllpage/Directions";
    }
}
