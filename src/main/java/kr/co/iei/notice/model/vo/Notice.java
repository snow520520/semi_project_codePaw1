package kr.co.iei.notice.model.vo;

import java.util.List;

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
	private List<NoticeFile> fileList;
}
