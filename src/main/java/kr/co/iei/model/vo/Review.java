package kr.co.iei.model.vo;

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
	private String reviewCotent;
	private String reviewDate;
	private String memberNo; //작성자
	private String animalNo;
	private String adoptionNo;
}
