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
	private int addmissionNo;
	private String addmissionTitle;
	private String addmissionContent;
	private String addmissionDate;
	private String memberId;
}
