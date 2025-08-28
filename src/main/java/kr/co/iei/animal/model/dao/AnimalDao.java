package kr.co.iei.animal.model.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.animal.model.vo.Animal;

@Mapper
public interface AnimalDao {

	Animal selectAnimalNo(int animalNo);

	int insertAnimal(Animal animal);

	int searchAnimalNo(int memberNo);

}
