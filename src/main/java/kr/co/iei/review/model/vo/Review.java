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
	private int reviewNo;
	private String reviewTitle;
	private String reviewContent;
	private String reviewDate;
	private int memberNo; // 작성자
	private Integer animalNo;
	private Integer adoptionNo;
	private String thumbnail;   // 첫 이미지 경로 or 기본이미지


}
