package kr.co.iei.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.review.model.service.ReviewService;
import kr.co.iei.review.model.vo.ReviewListData;

@Controller
@RequestMapping (value = "/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping(value = "/list")
	public String reviewList(int reqPage, Model model) {
		ReviewListData rld = reviewService.reviewList(reqPage);
		model.addAttribute("list", rld.getList());
		model.addAttribute("pageNavi", rld.getPageNavi());
		return "review/list";
		
	}
	
	@GetMapping(value="/reviewWriteFrm")
	public String reviewWriteFrm() {
		return "review/reviewWriteFrm";
	}
	
	
}
 