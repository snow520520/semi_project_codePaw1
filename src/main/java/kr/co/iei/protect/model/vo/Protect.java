package kr.co.iei.protect.model.vo;

import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="protect")
public class Protect {
	private int protectNo;
	private String protectTitle;
	private String protectContent;
	private String protectDate;
	private int memberNo; //pk
	private int animalNo; //pk
	private String protectStatus;
    private String thumbnailUrl;
}
