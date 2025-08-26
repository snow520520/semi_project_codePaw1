package kr.co.iei.AllPage.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.iei.AllPage.model.dao.AllPageDao;
import kr.co.iei.AllPage.model.vo.AllPage;
import kr.co.iei.animal.model.vo.Animal;

@Service
public class AllPageService {

    @Autowired
    private AllPageDao allpageDao;

    @Transactional
    public int insertProtect(AllPage ap, int memberNo,Animal animal) {
    	Integer animalNo = allpageDao.selectAnimalNo(animal);

        if (animalNo == null) {
            return 0; // 동물 이름/나이 일치하는게 없으면 실패
        }

        ap.setMemberNo(memberNo);
        ap.setAnimalNo(animalNo);
        //사용하는 이유는 여러명의 관리자가 등록을 했을때 시퀸스 번호가 고유번호라 겹칯면 에러가 남
        int newProtectNo = allpageDao.getNextProtectNo();
        ap.setProtectNo(newProtectNo);

        return allpageDao.insertProtect(ap);
    }
}
