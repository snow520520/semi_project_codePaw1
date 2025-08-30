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
	public String allMember(int reqPage, int reqPageAni ,Model model) {
		List listAnimal = memberService.allAnimal();
		List listMember = memberService.allMember();
		MemberListData mld = memberService.selectMemberList(reqPage);
		AnimalListData ald = animalService.selectAnimalList(reqPageAni);
		model.addAttribute("list", mld.getList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		model.addAttribute("list", ald.getList());
		model.addAttribute("pageNaviAni", ald.getPageNaviAni());
		model.addAttribute("listMember", listMember);
		model.addAttribute("listAnimal", listAnimal);
		return "admin/adminPage";
	}
}