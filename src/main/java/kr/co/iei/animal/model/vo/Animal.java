package kr.co.iei.animal.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="animal")
public class Animal {
	private int animalNo;
	private String animalType;
	private String animalName;
	private String animalGender;
	private Integer animalAge;
	private String animalInocul;
	private String animalNeuter;
	private String admissionNo;
	private int memberNo;
	private int animalAdmission;
}
