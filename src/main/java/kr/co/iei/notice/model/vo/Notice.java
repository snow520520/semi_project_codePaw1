package kr.co.iei.notice.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="notice")
public class Notice {
	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private String memberId;
	private String noticeDate;
}
