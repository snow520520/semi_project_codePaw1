package kr.co.iei.adoption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.adoption.model.service.AdoptionService;
import kr.co.iei.adoption.model.vo.Adoption;
import kr.co.iei.adoption.model.vo.AdoptionListData;
import kr.co.iei.animal.model.service.AnimalService;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/adoption")
public class AdoptionController {

	@Autowired
	private AdoptionService adoptionService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private AnimalService animalService;
	
	@GetMapping(value="/list")
	public String adoptionList(int reqPage, Model model) {
		AdoptionListData ald = adoptionService.selectAdoptionList(reqPage);
		model.addAttribute("list", ald.getList());
		model.addAttribute("pageNavi", ald.getPageNavi());
		return "adoption/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm(
	    @SessionAttribute(required = false) Member member,
	    @SessionAttribute(required = false) Animal animal,
	    Model model
	) {
	    if (member == null) {
	        model.addAttribute("title", "로그인 확인");
	        model.addAttribute("text", "로그인 후 이용 가능합니다.");
	        model.addAttribute("icon", "info");
	        model.addAttribute("loc", "/member/loginFrm");
	        return "common/msg";
	    }

	    if (animal == null) {
	        model.addAttribute("title", "오류");
	        model.addAttribute("text", "아이 정보를 찾을 수 없습니다.");
	        model.addAttribute("icon", "error");
	        model.addAttribute("loc", "/mainAllpage/allpage");
	        return "common/msg";
	    }

	    model.addAttribute("member", member);
	    model.addAttribute("animal", animal);
	    return "adoption/writeFrm";
	}

	
	@PostMapping(value="/write")
	public String adoptionWrite(Adoption a, Model model) {
		int result = adoptionService.insertAdoption(a);
		model.addAttribute("title", "입양 신청 등록 완료");
		model.addAttribute("text", "입양 신청이 등록되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/adoption/list?reqPage=1");
		return "common/msg";
	}
	
	@GetMapping(value="/view")
	public String adoptionView(int adoptionNo, Model model) {
		Adoption a = adoptionService.selectOneAdoption(adoptionNo);
		Member member = memberService.selectMemberId(a.getMemberId());
		Animal animal = animalService.selectAnimalNo(a.getAnimalNo());
		if(a == null) {
			model.addAttribute("title", "게시글 조회 실패");
			model.addAttribute("text", "이미 삭제된 게시글 입니다");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/adoption/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("a", a);
			model.addAttribute("member", member);
		    model.addAttribute("animal", animal);
			return "adoption/view";
		}
	}
	
	@GetMapping(value="/updateFrm")
	public String updateFrm(int adoptionNo, Model model) {
		Adoption a = adoptionService.selectOneAdoption(adoptionNo);
		model.addAttribute("a", a);
		return "adoption/updateFrm";
	}
	
	@PostMapping(value="/update")
	public String update(Adoption a, Model model) {
		int result = adoptionService.updateAdoption(a);
		if(result>0) {
			return "adoption/view";
		}else {
			model.addAttribute("title", "수정 실패");
			model.addAttribute("text", "수정 실패");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/adoption/view");
			return "common/msg";
		}
	}
}
