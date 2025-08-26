package kr.co.iei.admission.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.admission.model.dao.AdmissionDao;
import kr.co.iei.admission.model.vo.AdmissionListData;

@Service
public class AdmissionService {
	@Autowired
	private AdmissionDao admissionDao;

	public List selectAll() {
		List list = admissionDao.selectAll();
		return list;
	}

	public AdmissionListData selectAdmissionList(int reqPage) {
		int numPerPage = 10;
		
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
				pageNavi += "<a class='page-item' href='/admission/list?reqPage=";
			}
		}
		return null;
	}
}
