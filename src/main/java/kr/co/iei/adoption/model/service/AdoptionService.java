package kr.co.iei.adoption.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.adoption.model.dao.AdoptionDao;
import kr.co.iei.adoption.model.vo.Adoption;
import kr.co.iei.adoption.model.vo.AdoptionListData;

@Service
public class AdoptionService {

	@Autowired
	private AdoptionDao adoptionDao;

	public AdoptionListData selectAdoptionList(int reqPage) {
		int numPerPage = 13;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1 ;
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("end", end);
		int totalCount = adoptionDao.selectAdoptionTotalCount();
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		String pageNavi = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/adoption/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/adoption/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/adoption/list?reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-item' href='/adoption/list?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		List list = adoptionDao.selectAdoptionList(param);
		
		AdoptionListData ald = new AdoptionListData(list, pageNavi);
		
		return ald;
	}

	@Transactional
	public int insertAdoption(Adoption a) {
		int result = adoptionDao.insertAdoption(a);
		return result;
	}

	public Adoption selectOneAdoption(int adoptionNo) {
		Adoption a = adoptionDao.selectOneAdoption(adoptionNo);
		return a;
	}
	
	@Transactional
	public int updateAdoption(Adoption a) {
		int result = adoptionDao.updateAdoption(a);
		return result;
	}
}
