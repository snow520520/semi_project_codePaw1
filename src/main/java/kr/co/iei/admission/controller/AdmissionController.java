package kr.co.iei.admission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.admission.model.service.AdmissionService;
import kr.co.iei.admission.model.vo.Admission;
import kr.co.iei.admission.model.vo.AdmissionListData;
import kr.co.iei.animal.model.service.AnimalService;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/admission")
public class AdmissionController {
	@Autowired
	private AdmissionService admissionSerivce;
	@Autowired
	private MemberService memberService;
	@Autowired
	private AnimalService animalService;
	
	@GetMapping(value="/list")
	public String list(int reqPage, Model model) {
		AdmissionListData ald = admissionSerivce.selectAdmissionList(reqPage);
		model.addAttribute("list", ald.getList());
		model.addAttribute("pageNavi", ald.getPageNavi());
		return "admission/list";
	}
	@GetMapping(value="/searchTitle")
	public String searchTitle(String searchTitle, int reqPage, Model model) {
		AdmissionListData ald = admissionSerivce.searchTitleAdmissionList(reqPage,searchTitle);
		model.addAttribute("list", ald.getList());
		model.addAttribute("pageNavi", ald.getPageNavi());
		return "admission/list";
	}
	@GetMapping(value="/view")
	public String view(int admissionNo, Model model) {
		Admission admission = admissionSerivce.selectOneAdmission(admissionNo);
		String memberId = admission.getMemberId();
		Member m = memberService.selectMemberId(memberId);
		int animalNo = admission.getAnimalNo();
		Animal animal = animalService.selectAnimalNo(animalNo);
		model.addAttribute("animal", animal);
		model.addAttribute("member", m);
		model.addAttribute("admission", admission);
		return "admission/view";
	}
}
