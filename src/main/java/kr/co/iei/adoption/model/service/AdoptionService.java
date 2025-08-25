package kr.co.iei.adoption.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.adoption.model.dao.AdoptionDao;

@Service
public class AdoptionService {

	@Autowired
	private AdoptionDao adoptionDao;
}
