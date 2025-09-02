package kr.co.iei.notice.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.admission.model.vo.AdmissionListData;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.notice.model.dao.NoticeDao;
import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeFile;
import kr.co.iei.notice.model.vo.NoticeListData;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;

	public NoticeListData selectNoticeList(int reqPage) {
		int numPerPage = 13;
		
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int totalCount = noticeDao.selectNoticeCount();
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		
		int pageNaviSize = 5;
		
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		String pageNavi = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/notice/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/notice/list?reqPage="+pageNo+"'>";
			}
			pageNavi += pageNo;
			pageNavi += "</a>";
			pageNavi += "</li>";
			
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>";
		
		List list = noticeDao.selectNoticeList(param);
		
		NoticeListData nld = new NoticeListData(list, pageNavi);
		
		return nld;
	}

	public NoticeListData searchTitle(int reqPage, String searchTitle) {
int numPerPage = 13;
		
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int totalCount = noticeDao.searchTitleCount(searchTitle);
		if(totalCount > 0) {
			int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
			
			int pageNaviSize = 5;
			
			int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
			
			String pageNavi = "<ul class='pagination circle-style'>";
			if(pageNo != 1) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/notice/searchTitle?searchTitle="+searchTitle+"&reqPage="+(pageNo-1)+"'>";
				pageNavi += "<span class='material-icons'>chevron_left</span>";
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			for(int i=0;i<pageNaviSize;i++) {
				pageNavi += "<li>";
				if(pageNo == reqPage) {
					pageNavi += "<a class='page-item active-page' href='/notice/searchTitle?searchTitle="+searchTitle+"&reqPage="+pageNo+"'>";
				}else {
					pageNavi += "<a class='page-item' href='/notice/searchTitle?searchTitle="+searchTitle+"&reqPage="+pageNo+"'>";
				}
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
				
				pageNo++;
				if(pageNo > totalPage) {
					break;
				}
			}
			if(pageNo <= totalPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/admission/list?reqPage="+pageNo+"'>";
				pageNavi += "<span class='material-icons'>chevron_right</span>";
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNavi += "</ul>";                    
			
			param.put("searchTitle", searchTitle);
			List list = noticeDao.searchTitleNotice(param);
			
			
			NoticeListData nld = new NoticeListData(list, pageNavi);
			
			return nld;
		}else {
			return null;
		}
	}

	public Notice selectOnetNotice(int noticeNo) {
		Notice notice = noticeDao.selectOneNotice(noticeNo);
		if(notice != null) {
			List fileList = noticeDao.selectNoticeFile(noticeNo);
			notice.setFileList(fileList);
		}
		return notice;
	}
	
	@Transactional
	public int deleteNotice(int noticeNo) {
		int result = noticeDao.deleteNotice(noticeNo);
		return result;
	}
	
	@Transactional
	public int insertNotice(Notice notice, List<NoticeFile> fileList) {
		int newNoticeNo = noticeDao.getNoticeNo();
		notice.setNoticeNo(newNoticeNo);
		int result = noticeDao.insertNotice(notice);
		for(NoticeFile noticeFile : fileList) {
			noticeFile.setNoticeNo(newNoticeNo);
			result += noticeDao.insertNoticeFile(noticeFile);
		}
		return result;
	}
	
	@Transactional
	public int updateNotice(Notice notice) {
		int result = noticeDao.updateNotice(notice);
		return result;
	}

	public NoticeFile selectOneNoticeFile(int noticeFileNo) {
		NoticeFile noticeFile = noticeDao.selectOneNoticeFile(noticeFileNo);
		return noticeFile;
	}
}
