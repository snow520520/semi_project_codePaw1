package kr.co.iei.adoption.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.adoption.model.vo.Adoption;

@Mapper
public interface AdoptionDao {

	List selectAdoptionList(HashMap<String, Object> param);

	int selectAdoptionTotalCount();

	int insertAdoption(Adoption a);

}
