package kr.co.iei.review.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.review.model.vo.Review;

@Mapper
public interface ReviewDao {

	int selectrReviewTotalCount();

	List selectReviewList(HashMap<String, Object> param);

	int insertAdoption(Review r);
	
	
	

}
