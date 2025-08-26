package kr.co.iei.admission.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdmissionListData {
	private List list;
	private String pageNavi;
}
