package kr.co.iei.review.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.review.model.dao.ReviewDao;

@Service
public class ReviewService {
	@Autowired
	private ReviewDao reviewDao;
<<<<<<< HEAD

=======
	
	public List selectAllReview(int reqPage) {
		int numPerPage = 16;
		
	}
>>>>>>> main
}
