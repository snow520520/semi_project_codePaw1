package kr.co.iei.animal.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
