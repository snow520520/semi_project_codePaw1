package kr.co.iei.AllPage.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.iei.AllPage.model.vo.AllPage;
import kr.co.iei.animal.model.vo.Animal;

@Mapper
public interface AllPageDao {
	Integer selectAnimalNo(Animal animal);
	
	int insertProtect(AllPage ap);
	int getNextProtectNo();

	List<AllPage> selectAllProtect();
}
