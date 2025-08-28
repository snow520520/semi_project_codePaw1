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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.member.model.vo.Member;
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
			ReviewListData rld = reviewService.searchTitle(reqPage, searchTitle);
			if(rld != null) {
			model.addAttribute("list", rld.getList());
			model.addAttribute("pageNavi", rld.getPageNavi());
			}
		}
		return "review/list";
	}
	
	@GetMapping(value="/reviewWriteFrm")
	public String insertFrm(Member member, Model model) {
		model.addAttribute("member", member);
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
	
	@GetMapping(value="/delete")
	public String delete (int reviewNo, Model model) {
		int result = reviewService.deleteReviewNo(reviewNo);
		if(result>0) {
			model.addAttribute("title", "삭제 성공");
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/review/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("msg", "잠시 후 다시 시도해주세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/review/list?reqPage=1");
			return "common/msg";
		}
	}
	
}
 