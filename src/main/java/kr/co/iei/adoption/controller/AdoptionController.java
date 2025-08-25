package kr.co.iei.adoption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.adoption.model.service.AdoptionService;

@Controller
@RequestMapping(value="/adoption")
public class AdoptionController {

	@Autowired
	private AdoptionService adoptionService;
}
