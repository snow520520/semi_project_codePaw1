package kr.co.iei.admission.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.admission.model.vo.Admission;
import kr.co.iei.member.model.vo.Member;

@Mapper
public interface AdmissionDao {

	int selectAdmissionCount();

	List selectAdmissionList(HashMap<String, Object> param);

	int searchTitleCount(String searchTitle);

	List searchTitleAdmission(HashMap<String, Object> param);

	Admission selectOneAdmission(int admissionNo);

	int deleteAdmissionNo(int admissionNo);

	int insertAdmission(Admission admission);

	

}
