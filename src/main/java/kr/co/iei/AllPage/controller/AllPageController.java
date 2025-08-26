package kr.co.iei.AllPage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.AllPage.model.service.AllPageService;
import kr.co.iei.AllPage.model.vo.AllPage;

@Controller
@RequestMapping(value="/mainAllpage")
public class AllPageController {
	@Autowired
	private AllPageService allpageService;
	
	@GetMapping(value = "/allpage")
	public String allpage(){
		return "mainAllpage/allpage";
	}
	
	@GetMapping(value = "/writeFrm")
	public String writepage(){
		return "mainAllpage/writeFrm";
	}
	
	@PostMapping("/write")
	public String submitWrite(AllPage allpage) {
	}
	
}
