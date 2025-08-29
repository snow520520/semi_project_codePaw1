package kr.co.iei.animal.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.animal.model.dao.AnimalDao;
import kr.co.iei.animal.model.vo.Animal;

@Service
public class AnimalService {
	@Autowired
	private AnimalDao animalDao;
	
	public Animal selectAnimalNo(int animalNo) {
		Animal animal = animalDao.selectAnimalNo(animalNo);
		return animal;
	}
	@Transactional
	public int insertAnimal(Animal animal) {
		int result = animalDao.insertAnimal(animal);
		return result;
	}
	public int searchAnimalNo(int memberNo) {
		int animalNo = animalDao.searchAnimalNo(memberNo);
		return animalNo;
	}
	@Transactional
	public int updateAnimal(Animal animal) {
		int result = animalDao.updateAnimal(animal);
		return result;
	}
	@Transactional
	public int admissionCheck(int animalNo) {
		int result = animalDao.admissionCheck(animalNo);
		return result;
	}
	
	
	
}
