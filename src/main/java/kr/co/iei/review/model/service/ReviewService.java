package kr.co.iei.review.model.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.admission.model.vo.AdmissionListData;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.review.model.vo.Review;
import kr.co.iei.review.model.vo.ReviewListData;

@Service
public class ReviewService {

	@Autowired
	private ReviewDao reviewDao;

	// 후기 목록
	public ReviewListData reviewList(int reqPage) {
		int numPerPage = 16;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;

		HashMap<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("end", end);

		int totalCount = reviewDao.selectReviewList();
		int totalPage = (int) Math.ceil(totalCount / (double) numPerPage);

		int pageNaviSize = 5;
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

		StringBuilder pageNavi = new StringBuilder("<ul class='pagination circle-style'>");
		if (pageNo != 1) {
			pageNavi.append("<li>").append("<a class='page-item' href='/review/list?reqPage=").append(pageNo - 1)
					.append("'>").append("<span class='material-icons'>chevron_left</span>").append("</a></li>");
		}
		for (int i = 0; i < pageNaviSize; i++) {
			pageNavi.append("<li>");
			if (pageNo == reqPage) {
				pageNavi.append("<a class='page-item active-page' href='/review/list?reqPage=").append(pageNo)
						.append("'>");
			} else {
				pageNavi.append("<a class='page-item' href='/review/list?reqPage=").append(pageNo).append("'>");
			}
			pageNavi.append(pageNo).append("</a></li>");
			pageNo++;
			if (pageNo > totalPage)
				break;
		}
		if (pageNo <= totalPage) {
			pageNavi.append("<li>").append("<a class='page-item' href='/review/list?reqPage=").append(pageNo)
					.append("'>").append("<span class='material-icons'>chevron_right</span>").append("</a></li>");
		}
		pageNavi.append("</ul>");

		//여기서 리스트 가져오기
		List<Review> list = reviewDao.reviewList(param);

		//썸네일 세팅
		for (Review r : list) {
			if (r.getReviewContent() != null && r.getReviewContent().contains("src=")) {
				String content = r.getReviewContent();
				int startIdx = content.indexOf("src=") + 5;
				int endIdx = content.indexOf("\"", startIdx);
				if (endIdx > startIdx) {
					String src = content.substring(startIdx, endIdx);
					r.setThumbnail(src);
				} else {
					r.setThumbnail("/editorImage/default.png");
				}
			} else {
				r.setThumbnail("/editorImage/default.png");
			}
		}

		return new ReviewListData(list, pageNavi.toString());
	}

	// 검색
	public ReviewListData searchTitle(int reqPage, String searchTitle) {
		int numPerPage = 16;

		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("end", end);

		int totalCount = reviewDao.searchTitleCount(searchTitle);
		if (totalCount > 0) {
			int totalPage = (int) (Math.ceil(totalCount / (double) numPerPage));

			int pageNaviSize = 5;

			int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

			String pageNavi = "<ul class = 'pagenation circle-style'>";

			if (pageNo != 1) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/review/searchTitle?searchTitle=" + searchTitle + "&reqPage="
						+ pageNo + "'>";
				pageNavi += "<span class='material-icons'>chevron_rihgt</span>";
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			for (int i = 0; i < pageNaviSize; i++) {
				pageNavi += "<li>";
				if (pageNo == reqPage) {
					pageNavi += "<a class='page-item active-page' href='/review/searchTitle?searchTitle=" + searchTitle
							+ "&reqPage=" + pageNo + "'>";
				} else {
					pageNavi += "<a class='page-item' href='/review/searchTitle?searchTitle=" + searchTitle
							+ "&reqPage=" + pageNo + "'>";
				}

				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";

				pageNo++;

				if (pageNo > totalPage) {
					break;
				}
			}
			if (pageNo <= totalPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/review/list?reqPage=" + pageNo + "'>";
				pageNavi += "<span class='material-icons'>chevron_right</span>";
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNavi += "</ul>";

			param.put("searchTitle", searchTitle);
			List<Review> list = reviewDao.searchTitleReview(param);

			for(Review r : list) {
				if(r.getReviewContent() != null && r.getReviewContent().contains("src=")) {
					String content = r.getReviewContent();
					int startIdx = content.indexOf("src=") + 5;
					int endIdx = content.indexOf("\"", startIdx);
					if(endIdx > startIdx) {
						String src = content.substring(startIdx, endIdx);
						r.setThumbnail(src);
					}else {
						r.setThumbnail("/editorImage/default.png");
					}
				}else {
					r.setThumbnail("/editorImage/default.png");
				}
			}
			ReviewListData rld = new ReviewListData(list, pageNavi);
			return rld;
		} else {
			return null;
		}
	}

	public Review selectOneReview(int reviewNo) {
		// Review review = reviewDao.selectOneReview(reviewNo);
		return reviewDao.selectOneReview(reviewNo);
	}

	@Transactional
	public int deleteReviewNo(int reviewNo) {
		int result = reviewDao.deleteReviewNo(reviewNo);
		return result;
	}

	// 후기 게시글 추가
	@Transactional
	public int reviewWrite(Review r, Member member) {
		int result = reviewDao.reviewWrite(r);
		return result;
	}
	@Transactional
	public int updateReview(Review review) {
		int result = reviewDao.updateReview(review);
		return result;
	}
}
