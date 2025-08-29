package kr.co.iei.protect.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.protect.model.vo.Protect;

@Mapper
public interface ProtectDao {

    Integer selectAnimalNo(Animal animal);

    int insertProtect(Protect ap);
    int getNextProtectNo();

    List<Protect> selectAllProtect();

    List<Protect> selectPageProtect(@Param("start") int start, @Param("end") int end);

    int selectTotalCount();

    Protect selectOneProtect(@Param("protectNo") int protectNo);
    Animal selectAnimal(@Param("animalNo") int animalNo);
    Member selectMember(@Param("memberNo") int memberNo);
    int updateProtectContent(Protect ap);
    List<Integer> selectAnimalNoList(Animal animal);
    int insertAnimal(Animal animal);

    List<Animal> selectAnimalByNameAndAgeList(Animal animal);

	List<Integer> selectAnimalNoListByNo(int animalNo);
}
