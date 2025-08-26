package kr.co.iei.review.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.review.model.vo.Review;

@Mapper
public interface ReviewDao {

	List reviewList(HashMap<String, Object> param);

	int selectReviewList();

	int insertReview(Review r);

	

}
