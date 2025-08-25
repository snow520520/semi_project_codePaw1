package kr.co.iei.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.review.model.service.ReviewService;

@Controller
@RequestMapping (value = "/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
}
