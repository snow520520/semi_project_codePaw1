package kr.co.iei.admission.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.admission.model.service.AdmissionService;
import kr.co.iei.admission.model.vo.Admission;
import kr.co.iei.admission.model.vo.AdmissionListData;
import kr.co.iei.animal.model.service.AnimalService;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/admission")
public class AdmissionController {
	@Autowired
	private AdmissionService admissionService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private AnimalService animalService;
	
	@GetMapping(value="/list")
	public String list(int reqPage, Model model) {
		AdmissionListData ald = admissionService.selectAdmissionList(reqPage);
		model.addAttribute("list", ald.getList());
		model.addAttribute("pageNavi", ald.getPageNavi());
		return "admission/list";
	}
	@GetMapping(value="/searchTitle")
	public String searchTitle(String searchTitle, int reqPage, Model model) {
		if(!searchTitle.isEmpty()) {
			AdmissionListData ald = admissionService.searchTitleAdmissionList(reqPage,searchTitle);
			if(ald != null) {
				model.addAttribute("list", ald.getList());
				model.addAttribute("pageNavi", ald.getPageNavi());
			}
		}
		
		return "admission/list";
	}
	@GetMapping(value="/view")
	public String view(int admissionNo, Model model) {
		Admission admission = admissionService.selectOneAdmission(admissionNo);
		if(admission == null) {
			model.addAttribute("title", "게시글 조회 실패");
			model.addAttribute("text", "이미 삭제된 게시글입니다.");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/notice/list?reqPage=1");
			return "common/msg";
		}else {
			String memberId = admission.getMemberId();
			Member m = memberService.selectMemberId(memberId);
			int animalNo = admission.getAnimalNo();
			Animal animal = animalService.selectAnimalNo(animalNo);
			model.addAttribute("animal", animal);
			model.addAttribute("member", m);
			model.addAttribute("admission", admission);
			return "admission/view";			
		}
	}
	@GetMapping(value="/delete")
	public String delete(int admissionNo, Model model) {
		int result = admissionService.deleteAdmissionNo(admissionNo);
		if(result>0) {
			model.addAttribute("title", "삭제 성공");
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/admission/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("msg", "잠시후 다시 시도해 주세요.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/admission/list?reqPage=1");
			return "common/msg";
		}
	}
	@GetMapping(value="/insertFrm")
	public String insertFrm(@SessionAttribute Member member, Model model) {
		model.addAttribute("member", member);
		return "admission/insertFrm";
	}
	 @PostMapping(value="/insertFrm/editorImage", produces = "plain/text;charset=utf-8")
	 @ResponseBody
	 public String editorImage(MultipartFile upfile) {
		 String savePath = "C:/image/";
	     String filename = UUID.randomUUID() + "_" + upfile.getOriginalFilename();
	     File file = new File(savePath + filename);
      try {
           upfile.transferTo(file);
       } catch (IOException e) {
           e.printStackTrace();
           return "fail";
       }
       return "/editorImage/" + filename;
   }
	 @PostMapping(value="/insert")
	 public String insert(Admission admission, Animal animal, Model model) {
		 Member member = memberService.selectMemberId(admission.getMemberId());
		 animal.setMemberNo(member.getMemberNo());
		 System.out.println(animal);
		 int result = animalService.insertAnimal(animal);
		 if(result > 0) {
			 int animalNo = animalService.searchAnimalNo(animal.getMemberNo());
			 if(animalNo>0) {
				 admission.setAnimalNo(animalNo);
				 result = admissionService.insertAdmission(admission);
				 if(result>0) {
					 	model.addAttribute("title", "작성 성공");
						model.addAttribute("msg", "게시글이 작성되었습니다.");
						model.addAttribute("icon", "success");
						model.addAttribute("loc", "/admission/list?reqPage=1");
						return "common/msg";
				 }
			 }
		 }
		 model.addAttribute("title", "작성 실패");
		 model.addAttribute("msg", "잠시후 다시 시도해 주세요.");
		 model.addAttribute("icon", "warning");
		 model.addAttribute("loc", "/admission/list?reqPage=1");
		 return "common/msg";
	 }
	 @GetMapping(value="/updateFrm")
	 public String updateFrm(int admissionNo, int animalNo, Model model) {
		 Admission admission = admissionService.selectOneAdmission(admissionNo);
		 Animal animal = animalService.selectAnimalNo(animalNo);
		 model.addAttribute("admission", admission);
		 model.addAttribute("animal", animal);
		 return "admission/updateFrm";
	 }
	 @PostMapping(value="/update")
	 public String update(Admission admission, Animal animal, Model model) {
		 //admission update : admissionTitle, admissionContent
		 //animal update : animalName, animalType, animalAge, animalGender, animalInocul, animalNeuter
		 int result = animalService.updateAnimal(animal);
		 if(result > 0) {
			 result = admissionService.updateAdmission(admission);
			 if(result > 0) {
				 model.addAttribute("title", "수정 성공");
				 model.addAttribute("msg", "게시글이 수정되었습니다.");
				 model.addAttribute("icon", "success");
				 model.addAttribute("loc", "/admission/view?admissionNo="+admission.getAdmissionNo());
				 return "common/msg";
			 }
		 }
		 model.addAttribute("title", "수정 실패");
		 model.addAttribute("msg", "잠시후 다시 시도해 주세요.");
		 model.addAttribute("icon", "warning");
		 model.addAttribute("loc", "/admission/view?admissionNo="+admission.getAdmissionNo());
		 return "common/msg";			 
	 }
	
}
