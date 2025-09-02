package kr.co.iei.animal.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.animal.model.vo.Animal;

@Mapper
public interface AnimalDao {

	Animal selectAnimalNo(int animalNo);

	int insertAnimal(Animal animal);

	int searchAnimalNo(int memberNo);

	int updateAnimal(Animal animal);

	int admissionCheck(int animalNo);

	List selectAnimalList(HashMap<String, Object> param);

	int selectAnimalCount();

	int admissionReject(int animalNo);


}
