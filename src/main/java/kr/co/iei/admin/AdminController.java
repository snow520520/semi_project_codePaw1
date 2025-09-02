package kr.co.iei.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.animal.model.service.AnimalService;
import kr.co.iei.animal.model.vo.Animal;
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
    public String allMember(int memberPage, Model model) {
    	
        MemberListData mld = memberService.selectMemberList(memberPage);
        model.addAttribute("memberList", mld.getList());
        model.addAttribute("pageNaviMember", mld.getPageNaviMember());

        return "admin/adminPage";
    }
    @GetMapping(value = "/adminPageAni")
    public String allAnimal(int animalPage, Model model) {

        AnimalListData ald = animalService.selectAnimalList(animalPage);
        model.addAttribute("animalList", ald.getList());
        model.addAttribute("pageNaviAni", ald.getPageNaviAni());

        return "admin/adminPageAni";
    }
    @GetMapping(value = "/changeLevel")
    public String changeLevel(Member m, Model model) {
        int result = memberService.changeLevel(m);
        if(result == 0) {
            model.addAttribute("title", "등급변경 실패");
            model.addAttribute("content", "재시도 해주세요.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/admin/adminPage?memberPage=1");
        } else {
            model.addAttribute("title", "등급변경 완료");
            model.addAttribute("content", "Success");
            model.addAttribute("icon", "success");
            model.addAttribute("loc", "/admin/adminPage?memberPage=1");
        }
        return "common/msg";
    }

    @GetMapping(value = "/checkedChangeLevel")
    public String checkedChangeLevel(String no, String level, Model model) {
        boolean result = memberService.checkedChangeLevel(no, level);
        if(result) {
        	 model.addAttribute("title", "등급변경 완료");
             model.addAttribute("content", "Success");
             model.addAttribute("icon", "success");
             model.addAttribute("loc", "/admin/adminPage?memberPage=1");
        }
        return "common/msg";
    }
	@GetMapping(value ="/searchMemberName")
    public String searchMemberName(String memberName, int memberPage, Model model) {
    	if(!memberName.isEmpty()) {
    		MemberListData mld = memberService.selectMemberNameList(memberPage, memberName);
    		if(mld != null) {
    			model.addAttribute("memberList", mld.getList());
    			model.addAttribute("pageNaviMember", mld.getPageNaviMember());
    		}else{
    			model.addAttribute("title", "검색 실패");
    			model.addAttribute("content", "error");
    			model.addAttribute("icon", "error");
    			model.addAttribute("loc", "/admin/adminPage?memberPage=1");
    		}
    	}else {
    		model.addAttribute("title", "이름을 입력하세요.");
			model.addAttribute("content", "error");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/admin/adminPage?memberPage=1");
	    }
    	return "common/msg";
    }
	@GetMapping(value = "/changeAdmission")
	public String changeAdmission(Animal a, Model model) {
		int result = animalService.changeAdmission(a);
		if(result == 0) {
			model.addAttribute("title", "Fail");
			model.addAttribute("content", "Fail");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/admin/adminPageAni?animalPage=1");
		}else {
			model.addAttribute("title", "입소처리 완료");
			model.addAttribute("content", "입소 완료");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/admin/adminPageAni?animalPage=1");
		}
		return "common/msg";
	}
	@GetMapping(value = "/checkedChangeAdmission")
    public String checkedChangeAdmission(String no, String admission, String inocul, String neuter, Model model) {
        boolean result = animalService.checkedChangeAdmission(no, admission, inocul, neuter);
        if(result) {
        	 model.addAttribute("title", "등급변경 완료");
             model.addAttribute("content", "Success");
             model.addAttribute("icon", "success");
             model.addAttribute("loc", "/admin/adminPageAni?animalPage=1");
        }
        return "common/msg";
    }
	@GetMapping(value = "/searchAnimalName")
	public String searchAnimalName(String animalName, int animalPage, Model model) {
		if(!animalName.isEmpty()) {
			
    		AnimalListData ald = animalService.searchAnimalName(animalName, animalPage);
    		System.out.println(ald);
    		if(ald != null) {
    			model.addAttribute("animalList", ald.getList());
    			model.addAttribute("pageNaviAni", ald.getPageNaviAni());
    		}else{
    			model.addAttribute("title", "검색 실패");
    			model.addAttribute("content", "error");
    			model.addAttribute("icon", "error");
    			model.addAttribute("loc", "/admin/adminPageAni?animalPage=1");
    		}
    	}else {
    		model.addAttribute("title", "이름을 입력하세요.");
			model.addAttribute("content", "error");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/admin/adminPageAni?animalPage=1");
	    }
    	return "common/msg";
    }
}
