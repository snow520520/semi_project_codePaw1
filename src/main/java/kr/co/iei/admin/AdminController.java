package kr.co.iei.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	public AdminController() {
		super();
		memberService = new MemberService();
	}
	@GetMapping(value = "/adminPageFrm")
	public String allMember(int reqPage ,Model model) {
		List listAnimal = memberService.allAnimal();
		List listMember = memberService.allMember();
		
		MemberListData mld = memberService.selectMemberList(reqPage);
		AnimalListData ald = animalService.selectAnimalList(reqPage);
		
		model.addAttribute("list", mld.getList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		model.addAttribute("list", ald.getList());
		model.addAttribute("pageNaviAni", ald.getPageNavi());
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
			model.addAttribute("loc", "/admin/adminPageFrm?reqPage=1");
		}else {
			model.addAttribute("title", "등급변경 완료");
			model.addAttribute("content", "Success");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/admin/adminPageFrm?reqPage=1");
		}
		return "common/msg";
	}
	@GetMapping(value = "/checkedChangeLevel")
	public String checkedChangeLevel(String no, String level) {
		boolean result = memberService.checkedChangeLevel(no, level);
		return "admin/adminPage";
	}
}