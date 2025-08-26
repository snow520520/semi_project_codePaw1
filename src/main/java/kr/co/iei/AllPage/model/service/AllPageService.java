package kr.co.iei.AllPage.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.iei.AllPage.model.dao.AllPageDao;
import kr.co.iei.AllPage.model.vo.AllPage;

@Service
public class AllPageService {

    @Autowired
    private AllPageDao allpageDao;

    @Transactional
    public int insertProtect(AllPage ap, int memberNo) {
        Integer animalNo = allpageDao.selectAnimalNo(ap.getanimalName(), ap.getanimalAge());
        if (animalNo == null) {
            return 0; 
        }

        ap.setmemberNo(memberNo);
        ap.setanimalNo(animalNo);

        int newProtectNo = allpageDao.getNextProtectNo();
        ap.setprotectNo(newProtectNo);

        return allpageDao.insertProtect(ap);
    }
}
