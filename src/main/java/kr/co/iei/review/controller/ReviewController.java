package kr.co.iei.review.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.review.model.service.ReviewService;
import kr.co.iei.review.model.vo.Review;
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
	@GetMapping(value="/searchTitle")
	public String searchTitle(String searchTitle, int reqPage, Model model) {
		if(!searchTitle.isEmpty()) {
			ReviewListData rld = reviewService.reviewList(reqPage, searchTitle);
			if(rld != null) {
			model.addAttribute("list", rld.getList());
			model.addAttribute("pageNavi", rld.getPageNavi());
			}
		}
		return "review/list";
	}
	
	@GetMapping(value="/reviewWriteFrm")
	public String reviewWriteFrm() {
		return "review/reviewWriteFrm";
	}
	
	@PostMapping(value="/reviewWrite")
	public String insertReview(Review r, Model model) {
		int result = reviewService.insertReview(r);
		model.addAttribute("title", "후기 작성 완료!");
		model.addAttribute("text", "후기 등록이 완료되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/review/list?reqPage=1");
		return "review/list";
	}
	
	@PostMapping("/uploadImage")
	@ResponseBody
	public String uploadImage(@RequestParam("upfile") MultipartFile file) {
		//원본 파일 이름
		String originalName = file.getOriginalFilename();
		//저장할 경로(내가 지정)
		
		String savepath = "C:/upload/" + originalName;
		
		try {
			file.transferTo(new File(savepath));
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	return "/upload/" + originalName;
	}
	
}
 