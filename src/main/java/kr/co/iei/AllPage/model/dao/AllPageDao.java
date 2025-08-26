package kr.co.iei.AllPage.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.iei.AllPage.model.vo.AllPage;

@Mapper
public interface AllPageDao {
    Integer selectAnimalNo(@Param("animalName") String animalName,
                           @Param("animalAge") int animalAge);

    int insertProtect(AllPage ap);

    int getNextProtectNo();
}
