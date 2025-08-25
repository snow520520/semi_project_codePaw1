package kr.co.iei.adoption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.adoption.model.service.AdoptionService;
import kr.co.iei.adoption.model.vo.AdoptionListData;

@Controller
@RequestMapping(value="/adoption")
public class AdoptionController {

	@Autowired
	private AdoptionService adoptionService;
	
	@GetMapping(value="/list")
	public String adoptionList(int reqPage, Model model) {
		AdoptionListData ald = adoptionService.selectAdoptionList(reqPage);
		model.addAttribute("list", ald.getList());
		model.addAttribute("pageNavi", ald.getPageNavi());
		return "adoption/list";
	}
	
	@GetMapping(value="/adoptionWriteFrm")
	public String adoptionWriteFrm() {
		return "adoption/adoptionWriteFrm";
	}
}
