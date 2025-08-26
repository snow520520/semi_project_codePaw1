package kr.co.iei.admission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.admission.model.service.AdmissionService;

@Controller
@RequestMapping(value="/admission")
public class AdmissionController {
	@Autowired
	private AdmissionService admissionSerivce;
	
	@GetMapping(value="/list")
	public String list(Model model) {
		List list = admissionSerivce.selectAll();
		model.addAttribute("list", list);
		return "admission/list";
	}
}
