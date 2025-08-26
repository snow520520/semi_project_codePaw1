package kr.co.iei.admissiom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.admissiom.model.service.AdmissionService;

@Controller
@RequestMapping(value="/admission")
public class AdmissionController {
	@Autowired
	private AdmissionService admissionSerivce;
	
	@GetMapping(value="/list")
	public String list() {
		return "admission/list";
	}
}
