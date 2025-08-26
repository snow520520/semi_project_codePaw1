package kr.co.iei.admission.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdmissionDao {

	List selectAll();

	int selectAdmissionCount();

	List selectAdmissionList(HashMap<String, Object> param);

}
