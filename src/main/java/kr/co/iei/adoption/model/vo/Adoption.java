package kr.co.iei.adoption.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="adoption")
public class Adoption {
	private int adoptionNo;
	private String adoptionContent;
	private String adoptionDate;
	private String adoptionTitle;
	private String adoptionStatus;
}
