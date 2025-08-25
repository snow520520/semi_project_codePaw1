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
	private int protectno;
	private String protecttitle;
	private String protectcontent;
	private String protectdate;
	private int memberno; //pk
	private int animalno; //pk
	private String protectstatus;
}
