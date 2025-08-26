package kr.co.iei.review.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value = "review")
public class Review {
	private int revivewNo;
	private String reviewTitle;
	private String reviewComment;
	private String reviewDate;
	private String memberNo; //작성자
	private String animalNo;
	private String adoptionNo;
}
