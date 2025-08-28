package kr.co.iei.admission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


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
	private AdmissionService admissionService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private AnimalService animalService;
	
	@GetMapping(value="/list")
	public String list(int reqPage, Model model) {
		AdmissionListData ald = admissionService.selectAdmissionList(reqPage);
		model.addAttribute("list", ald.getList());
		model.addAttribute("pageNavi", ald.getPageNavi());
		return "admission/list";
	}
	@GetMapping(value="/searchTitle")
	public String searchTitle(String searchTitle, int reqPage, Model model) {
		if(!searchTitle.isEmpty()) {
			AdmissionListData ald = admissionService.searchTitleAdmissionList(reqPage,searchTitle);
			if(ald != null) {
				model.addAttribute("list", ald.getList());
				model.addAttribute("pageNavi", ald.getPageNavi());
			}
		}
		
		return "admission/list";
	}
	@GetMapping(value="/view")
	public String view(int admissionNo, Model model) {
		Admission admission = admissionService.selectOneAdmission(admissionNo);
		String memberId = admission.getMemberId();
		Member m = memberService.selectMemberId(memberId);
		int animalNo = admission.getAnimalNo();
		Animal animal = animalService.selectAnimalNo(animalNo);
		model.addAttribute("animal", animal);
		model.addAttribute("member", m);
		model.addAttribute("admission", admission);
		return "admission/view";
	}
	@GetMapping(value="/delete")
	public String delete(int admissionNo, Model model) {
		int result = admissionService.deleteAdmissionNo(admissionNo);
		if(result>0) {
			model.addAttribute("title", "삭제 성공");
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/admission/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("msg", "잠시후 다시 시도해 주세요.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/admission/list?reqPage=1");
			return "common/msg";
		}
	}
	@GetMapping(value="/insertFrm")
	public String insertFrm(@SessionAttribute Member member, Model model) {
		model.addAttribute("member", member);
		return "admission/insertFrm";
	}
	
}
