package kr.co.iei.notice.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeFile;

@Mapper
public interface NoticeDao {

	int selectNoticeCount();

	List selectNoticeList(HashMap<String, Object> param);

	int searchTitleCount(String searchTitle);

	List searchTitleNotice(HashMap<String, Object> param);

	Notice selectOneNotice(int noticeNo);

	int deleteNotice(int noticeNo);

	int insertNotice(Notice notice);

	int updateNotice(Notice notice);

	int insertNoticeFile(NoticeFile noticeFile);

	int getNoticeNo();

	List selectNoticeFile(int noticeNo);

	NoticeFile selectOneNoticeFile(int noticeFileNo);

	int deleteNoticeFile(int noticeFileNo);

}
