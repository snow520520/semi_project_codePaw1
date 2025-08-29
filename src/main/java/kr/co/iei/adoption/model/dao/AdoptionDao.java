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

	Adoption selectOneAdoption(int adoptionNo);

	int updateAdoption(Adoption a);

	int searchTitleCount(String searchTitle);

	List searchTitleAdoption(HashMap<String, Object> param);

	int deleteAdoptionNo(int adoptionNo);

	int updateStatus(Adoption adoption);
	
	int updateOtherStatus(Adoption adoption);
	
	int updateMemberLevel(String memberId);

}
