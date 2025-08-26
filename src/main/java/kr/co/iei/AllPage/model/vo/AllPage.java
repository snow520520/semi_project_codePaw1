package kr.co.iei.AllPage.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="allpage")
public class AllPage {
	private int protectNo;
	private String protectTitle;
	private String protectContent;
	private String protectDate;
	private int memberNo; //pk
	private int animalNo; //pk
	private String protectStatus;
}
