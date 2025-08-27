package kr.co.iei.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.iei.animal.model.service.AnimalService;

@Controller
public class AnimalController {
	@Autowired
	private AnimalService animalService;
	
	
}
