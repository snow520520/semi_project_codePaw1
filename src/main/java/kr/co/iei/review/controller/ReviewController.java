package kr.co.iei.review.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

import kr.co.iei.animal.model.service.AnimalService;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.review.model.service.ReviewService;
import kr.co.iei.review.model.vo.Review;
import kr.co.iei.review.model.vo.ReviewListData;

@Controller
@RequestMapping (value = "/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private AnimalService animalService;
	
	//후기 목록
	@GetMapping(value = "/list")
	public String reviewList(int reqPage, Model model) {
		ReviewListData rld = reviewService.reviewList(reqPage);
		model.addAttribute("list", rld.getList());
		model.addAttribute("pageNavi", rld.getPageNavi());
		return "review/list";
	}
	//검색
	@GetMapping(value="/searchTitle")
	public String searchTitle(String searchTitle, int reqPage, Model model) {
		if(!searchTitle.isEmpty()) {
			ReviewListData rld = reviewService.searchTitle(reqPage, searchTitle);
			if(rld != null) {
			model.addAttribute("list", rld.getList());
			model.addAttribute("pageNavi", rld.getPageNavi());
			}else {
				model.addAttribute("list", "작성된 게시글이 존재하지 않습니다.");
			}
		}
		return "review/list";
	}
	
	//게시글 상세페이지
	@GetMapping(value="/view")
	public String view(int reviewNo, Model model) {
		Review review = reviewService.selectOneReview(reviewNo);
		model.addAttribute("review", review);
		return "review/view";
	}
	
	//후기 작성 화면
	@GetMapping("/reviewWriteFrm")
	public String reviewWriteFrm() {
		return "review/reviewWriteFrm";
	}
	//이미지 받아오기
	@PostMapping(value="/reviewWriteFrm/editorImage", produces = "plain/text;charset=utf-8")
	@ResponseBody
	public String editorImage(MultipartFile upfile) {
		String savePath = "C:/image/";
		String filename = UUID.randomUUID() + "_" + upfile.getOriginalFilename();
		File file = new File (savePath + filename);
	try {
		upfile.transferTo(file);
	} catch(IOException e) {
	}
	return "/editorImage/"+filename;
	}
	
	
	//게시글 받아오고 저장
	@PostMapping(value="/reviewWriteFrm")
	public String insertReview(Review r, MultipartFile upfile, Model model) {
		int result = reviewService.reviewWrite(r, upfile);
		
		model.addAttribute("title", "후기 작성 완료!");
		model.addAttribute("text", "후기 등록이 완료되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/review/list?reqPage=1");
		return "review/list";
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
