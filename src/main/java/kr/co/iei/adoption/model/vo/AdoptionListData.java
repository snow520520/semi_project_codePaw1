package kr.co.iei.adoption.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdoptionListData {
	private List list;
	private String pageNavi;
}
