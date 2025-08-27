package kr.co.iei.admission.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.admission.model.dao.AdmissionDao;
import kr.co.iei.admission.model.vo.Admission;
import kr.co.iei.admission.model.vo.AdmissionListData;
import kr.co.iei.member.model.vo.Member;

@Service
public class AdmissionService {
	@Autowired
	private AdmissionDao admissionDao;


	public AdmissionListData selectAdmissionList(int reqPage) {
		int numPerPage = 13;
		
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int totalCount = admissionDao.selectAdmissionCount();
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		
		int pageNaviSize = 5;
		
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		String pageNavi = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/admission/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/admission/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/admission/list?reqPage="+pageNo+"'>";
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
		
		List list = admissionDao.selectAdmissionList(param);
		
		AdmissionListData ald = new AdmissionListData(list, pageNavi);
		
		return ald;
	}

	public AdmissionListData searchTitleAdmissionList(int reqPage, String searchTitle) {
		int numPerPage = 13;
		
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int totalCount = admissionDao.searchTitleCount(searchTitle);
		if(totalCount > 0) {
			int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
			
			int pageNaviSize = 5;
			
			int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
			
			String pageNavi = "<ul class='pagination circle-style'>";
			if(pageNo != 1) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/admission/searchTitle?searchTitle="+searchTitle+"&reqPage="+(pageNo-1)+"'>";
				pageNavi += "<span class='material-icons'>chevron_left</span>";
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			for(int i=0;i<pageNaviSize;i++) {
				pageNavi += "<li>";
				if(pageNo == reqPage) {
					pageNavi += "<a class='page-item active-page' href='/admission/searchTitle?searchTitle="+searchTitle+"&reqPage="+pageNo+"'>";
				}else {
					pageNavi += "<a class='page-item' href='/admission/searchTitle?searchTitle="+searchTitle+"&reqPage="+pageNo+"'>";
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
			List list = admissionDao.searchTitleAdmission(param);
			
			
			AdmissionListData ald = new AdmissionListData(list, pageNavi);
			
			return ald;
		}else {
			return null;
		}
	}

	public Admission selectOneAdmission(int admissionNo) {
		Admission admission = admissionDao.selectOneAdmission(admissionNo);
		return admission;
	}
	
	@Transactional
	public int deleteAdmissionNo(int admissionNo) {
		int result = admissionDao.deleteAdmissionNo(admissionNo);
		return result;
	}

	
}
