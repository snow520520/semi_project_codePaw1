package kr.co.iei.review.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.review.model.vo.Review;
import kr.co.iei.review.model.vo.ReviewListData;

@Service
public class ReviewService {

	@Autowired
	private ReviewDao reviewDao;

	public ReviewListData reviewList(int reqPage) {
		int numPerPage = 16;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1 ;
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("end", end);
		int totalCount = reviewDao.selectReviewList();
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		String pageNavi = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/review/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/review/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/review/list?reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-item' href='/review/list?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		List list = reviewDao.reviewList(param);
		
		ReviewListData rld = new ReviewListData(list, pageNavi);
		
		return rld;
	}


	public int insertReview(Review r) {
		int result = reviewDao.insertReview(r);
		return 0;
	}


	public ReviewListData reviewList(int reqPage, String searchTitle) {
		int numPerPage = 16;
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		
		
		return "review/list";
	}

}
