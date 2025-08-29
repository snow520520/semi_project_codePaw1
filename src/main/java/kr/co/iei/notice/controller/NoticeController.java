package kr.co.iei.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.notice.model.service.NoticeService;
import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeListData;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
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
}
