package kr.co.iei.AllPage.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.iei.AllPage.model.dao.AllPageDao;
import kr.co.iei.AllPage.model.vo.AllPage;
import kr.co.iei.animal.model.vo.Animal;
import kr.co.iei.member.model.vo.Member;

@Service
public class AllPageService {

    @Autowired
    private AllPageDao allpageDao;

    @Transactional
    public int insertProtect(AllPage ap, int memberNo, Animal animal) {
        Integer animalNo = allpageDao.selectAnimalNo(animal);

        if (animalNo == null) {
            return 0;
        }

        ap.setMemberNo(memberNo);
        ap.setAnimalNo(animalNo);

        int newProtectNo = allpageDao.getNextProtectNo();
        ap.setProtectNo(newProtectNo);

        return allpageDao.insertProtect(ap);
    }

    public List<AllPage> selectAllProtect() {
        return allpageDao.selectAllProtect();
    }

    public List<AllPage> selectPageList(int currentPage, int recordCountPerPage) {
        int start = (currentPage - 1) * recordCountPerPage + 1;
        int end = currentPage * recordCountPerPage;
        return allpageDao.selectPageProtect(start, end);
    }

    public int getTotalCount() {
        return allpageDao.selectTotalCount();
    }

    public String getPageNavi(int currentPage, int totalCount, int recordCountPerPage, int naviCountPerPage, String url) {
        int totalPage = (int) Math.ceil((double) totalCount / recordCountPerPage);

        int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;
        int endNavi = startNavi + naviCountPerPage - 1;
        if(endNavi > totalPage) endNavi = totalPage;

        boolean needPrev = startNavi != 1;
        boolean needNext = endNavi != totalPage;

        StringBuilder sb = new StringBuilder();
        sb.append("<ul class='pagination circle-style'>");

        if(needPrev) {
            sb.append("<li><a class='page-item' href='" + url + "?page=" + (startNavi - 1) + "'>");
            sb.append("<span class='material-icons'>chevron_left</span></a></li>");
        }

        for(int i = startNavi; i <= endNavi; i++) {
            if(i == currentPage) {
                sb.append("<li><a class='page-item active-page' href='" + url + "?page=" + i + "'>" + i + "</a></li>");
            } else {
                sb.append("<li><a class='page-item' href='" + url + "?page=" + i + "'>" + i + "</a></li>");
            }
        }

        if(needNext) {
            sb.append("<li><a class='page-item' href='" + url + "?page=" + (endNavi + 1) + "'>");
            sb.append("<span class='material-icons'>chevron_right</span></a></li>");
        }

        sb.append("</ul>");
        return sb.toString();
    }
    
    public List<AllPage> selectPageListRead(int start, int end) {
        return allpageDao.selectPageProtect(start, end);
    }
    
    public AllPage selectOneProtect(int protectNo) {
        return allpageDao.selectOneProtect(protectNo);
    }

    public Animal selectAnimal(int animalNo) {
        return allpageDao.selectAnimal(animalNo);
    }

    public Member selectMember(int memberNo) {
        return allpageDao.selectMember(memberNo);
    }
}