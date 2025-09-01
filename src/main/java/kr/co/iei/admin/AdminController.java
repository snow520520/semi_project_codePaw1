package kr.co.iei.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.iei.animal.model.service.AnimalService;
import kr.co.iei.animal.model.vo.AnimalListData;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.member.model.vo.MemberListData;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AnimalService animalService;

    @GetMapping(value = "/adminPage")
    public String allMember(
            @RequestParam(defaultValue = "1", name="memberPage") int memberPage,
            @RequestParam(defaultValue = "1", name="animalPage") int animalPage,
            Model model) {
    	//위에 파라미터 받아오는걸 페이지 2개를 받아올거면 수정
        MemberListData mld = memberService.selectMemberList(memberPage);
        model.addAttribute("memberList", mld.getList());
        model.addAttribute("pageNaviMember", mld.getPageNavi());

        AnimalListData ald = animalService.selectAnimalList(animalPage);
        model.addAttribute("animalList", ald.getList());
        model.addAttribute("pageNaviAnimal", ald.getPageNavi());

        List<Member> listMember = memberService.allMember();
        List<?> listAnimal = memberService.allAnimal();
        model.addAttribute("listMember", listMember);
        model.addAttribute("listAnimal", listAnimal);

        return "admin/adminPage";
    }

    @GetMapping(value = "/changeLevel")
    public String changeLevel(Member m, Model model) {
        int result = memberService.changeLevel(m);
        if(result == 0) {
            model.addAttribute("title", "등급변경 실패");
            model.addAttribute("content", "재시도 해주세요.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/admin/adminPage?memberPage=1&animalPage=1");
        } else {
            model.addAttribute("title", "등급변경 완료");
            model.addAttribute("content", "Success");
            model.addAttribute("icon", "success");
            model.addAttribute("loc", "/admin/adminPage?memberPage=1&animalPage=1");
        }
        return "common/msg";
    }

    @GetMapping(value = "/checkedChangeLevel")
    public String checkedChangeLevel(String no, String level) {
        boolean result = memberService.checkedChangeLevel(no, level);
        return "redirect:/admin/adminPage?memberPage=1&animalPage=1";
    }
}
