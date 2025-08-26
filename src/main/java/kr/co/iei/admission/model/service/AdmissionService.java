package kr.co.iei.admission.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.admission.model.dao.AdmissionDao;

@Service
public class AdmissionService {
	@Autowired
	private AdmissionDao addmissionDao;

	public List selectAll() {
		List list = addmissionDao.selectAll();
		return list;
	}
}
