package kr.co.iei.notice.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.notice.model.service.NoticeService;
import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeFile;
import kr.co.iei.notice.model.vo.NoticeListData;
import kr.co.iei.util.FileUtil;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private FileUtil fileUtil;
	
	@GetMapping(value="/list")
	public String list(Model model, int reqPage) {
		NoticeListData nld = noticeService.selectNoticeList(reqPage);
		model.addAttribute("list", nld.getList());
		model.addAttribute("pageNavi", nld.getPageNavi());
		return "notice/list";
	}
	@GetMapping(value="/searchTitle")
	public String searchTitle(String searchTitle, int reqPage, Model model) {
		if(!searchTitle.isEmpty()) {
			NoticeListData nld = noticeService.searchTitle(reqPage, searchTitle);
			if(nld != null) {
				model.addAttribute("list", nld.getList());
				model.addAttribute("pageNavi", nld.getPageNavi());
			}else {
				model.addAttribute("list", "작성된 게시글이 존재하지 않습니다.");
			}
		}else {
			model.addAttribute("list", "작성된 게시글이 존재하지 않습니다.");
		}
		return "notice/list";
	}
	@GetMapping(value="/view")
	public String view(int noticeNo, Model model) {
		Notice notice = noticeService.selectOnetNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "notice/view";
	}
	@GetMapping(value="/delete")
	public String delete(int noticeNo, Model model) {
		int result = noticeService.deleteNotice(noticeNo);
		if(result > 0) {
			model.addAttribute("title", "삭제 성공");
			model.addAttribute("text", "게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/notice/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("text", "잠시후 다시 시도해 주세요.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/notice/view?noticeNo="+noticeNo);
			return "common/msg";
		}
	}
	@GetMapping(value="/insertFrm")
	public String insertFrm(@SessionAttribute Member member,Model model) {
		return "notice/insertFrm";
	}
	@PostMapping(value="/insertFrm/editorImage", produces = "plain/text;charset=utf-8")
	 @ResponseBody
	 public String editorImage(MultipartFile upfile) {
		 String savePath = "C:/Temp/upload/image/";
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
	public String insert(Notice notice, Model model, MultipartFile[] noticeFile) {
		
		int result = noticeService.insertNotice(notice);
		List<NoticeFile> fileList = new ArrayList<NoticeFile>();
		if(!noticeFile[0].isEmpty()) {
			String savepath = "C:/Temp/upload/image/notice/";
			for(MultipartFile file : noticeFile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
			}
		}
		
		if(result > 0) {
			model.addAttribute("title", "작성 성공");
			model.addAttribute("text", "게시글이 작성되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/notice/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title", "작성 실패");
			model.addAttribute("text", "잠시후 다시 시도해 주세요.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/notice/list?reqPage=1");
			return "common/msg";
		}
	}
	@GetMapping(value="/updateFrm")
	public String updateFrm(int noticeNo, Model model) {
		Notice notice = noticeService.selectOnetNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "notice/updateFrm";
	}
	@PostMapping(value="/update")
	public String update(Notice notice, Model model) {
		int result = noticeService.updateNotice(notice);
		if(result >0) {
			model.addAttribute("title", "수정 성공");
			model.addAttribute("text", "게시글이 수정되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/notice/view?noticeNo="+notice.getNoticeNo());
			return "common/msg";
		}else {
			model.addAttribute("title", "수정 실패");
			model.addAttribute("text", "잠시후 다시 시도해 주세요.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/notice/view?noticeNo="+notice.getNoticeNo());
			return "common/msg";
		}
	}
}
