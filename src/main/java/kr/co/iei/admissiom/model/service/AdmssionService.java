package kr.co.iei.admissiom.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.admissiom.model.dao.AdmissionDao;

@Service
public class AdmssionService {
	@Autowired
	private AdmissionDao addmissionDao;
}
