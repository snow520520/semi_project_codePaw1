package kr.co.iei.AllPage.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.AllPage.model.dao.AllPageDao;
import kr.co.iei.AllPage.model.vo.AllPage;

@Service
public class AllPageService {
	@Autowired
	private AllPageDao allpagedao;

}
