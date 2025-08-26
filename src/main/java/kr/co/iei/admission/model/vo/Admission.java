package kr.co.iei.admission.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="admission")
public class Admission {
	private int admissionNo;
	private String admissionTitle;
	private String admissionContent;
	private String admissionDate;
	private String memberId;
}
