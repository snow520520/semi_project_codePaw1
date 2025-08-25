package kr.co.iei.adoption.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdoptionDao {

	List selectAdoptionList(HashMap<String, Object> param);

	int selectAdoptionTotalCount();

}
