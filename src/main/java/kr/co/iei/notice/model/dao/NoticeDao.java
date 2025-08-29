package kr.co.iei.notice.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeDao {

	int selectNoticeCount();

	List selectNoticeList(HashMap<String, Object> param);

	int searchTitleCount(String searchTitle);

	List searchTitleNotice(HashMap<String, Object> param);

}
