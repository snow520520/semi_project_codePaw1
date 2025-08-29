package kr.co.iei.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.member.model.service.MemberService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private MemberService memberService;

	public AdminController() {
		super();
		memberService = new MemberService();
	}
	@GetMapping(value = "/adminPageFrm")
	public String allMember(Model model) {
		List list = memberService.allMember();
		model.addAttribute("list", list);
		return "admin/adminPage";
	}
}